package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.Genotype;
import agh.ics.oop.proj1.observers.*;

import java.util.*;

public class DominantGenotypeTracker implements IWorldMapElementAddedObserver, IWorldMapElementRemovedObserver, IObservable {
    private final Map<Genotype, Integer> genotypes = new HashMap<>();
    private final ObserverHolder observers = new ObserverHolder(IDominantGenomeChangedObserver.class);
    private final Set<Genotype> currentDominantGenotypes = new HashSet<>();
    private int dominantCount = 0;

    public DominantGenotypeTracker(AbstractNonFlatWorldMap map) {
        map.addObserver(this);
        map.getStreamOfAllObjectsOfClass(Animal.class).
                map(Animal::getGenotype).forEach(this::addGenotype);
    }

    private void addGenotype(Genotype genotype) {
        genotypes.putIfAbsent(genotype, 0);
        genotypes.compute(genotype, (g1, count) -> count + 1);

        if (currentDominantGenotypes.contains(genotype)) {
            currentDominantGenotypes.clear();
            dominantCount = genotypes.get(genotype);
            currentDominantGenotypes.add(genotype);
            onDominantGenotypesChanged();
        } else {
            if (genotypes.get(genotype) >= dominantCount) {
                dominantCount = genotypes.get(genotype);
                currentDominantGenotypes.add(genotype);
                onDominantGenotypesChanged();
            }
        }
    }

    private void recalculateDominantGenotype() {
        int newCount = calcDominantCount();
        if (dominantCount == newCount)
            return;

        List<Genotype> newDominantGenotypes = genotypes.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == newCount)
                .map(Map.Entry::getKey)
                .toList();

        currentDominantGenotypes.clear();
        currentDominantGenotypes.addAll(newDominantGenotypes);
        dominantCount = newCount;

        onDominantGenotypesChanged();
    }

    private int calcDominantCount() {
        Optional<Integer> maxCount = genotypes.values().stream().
                max(Integer::compare);
        return maxCount.orElse(0);
    }

    private void onDominantGenotypesChanged() {
        Set<Genotype> unmodifiableSet = Collections.unmodifiableSet(currentDominantGenotypes);
        observers.notifyObservers(IDominantGenomeChangedObserver.class,
                observer -> observer.dominantGenomeChanged(unmodifiableSet));
    }

    private void removeGenotype(Genotype genotype) {
        if (!genotypes.containsKey(genotype))
            return;
        if (genotypes.compute(genotype, (g1, count) -> count - 1) == 0)
            genotypes.remove(genotype);
        if (currentDominantGenotypes.contains(genotype)) {
            currentDominantGenotypes.remove(genotype);
            if (currentDominantGenotypes.isEmpty())
                recalculateDominantGenotype();
            else
                onDominantGenotypesChanged();
        }

    }

    @Override
    public void elementAdded(AbstractWorldMapElement element) {
        if (element instanceof Animal animal)
            addGenotype(animal.getGenotype());
    }

    @Override
    public void elementRemoved(AbstractWorldMapElement element) {
        if (element instanceof Animal animal)
            removeGenotype(animal.getGenotype());
    }

    public Set<Genotype> getDominantGenotypes() {
        return Collections.unmodifiableSet(currentDominantGenotypes);
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.removeObserver(observer);
    }
}
