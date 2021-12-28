package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.observers.IReproductionObserver;
import agh.ics.oop.proj1.observers.IWorldMapElementRemovedObserver;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AnimalSuccessorCountTracker implements IReproductionObserver, IWorldMapElementRemovedObserver, INumericTracker {
    private final Set<Animal> observedAnimals = new HashSet<>();
    private long deadSuccessors;

    public AnimalSuccessorCountTracker(Animal animal, AbstractNonFlatWorldMap map) {
        observedAnimals.add(animal);
        animal.addObserver(this);
        map.addObserver(this);
    }

    @Override
    public void reproduced(Animal animal, Animal child) {
        if (!observedAnimals.contains(animal) || observedAnimals.contains(child))
            return;
        child.addObserver(this);
        observedAnimals.add(child);
    }

    public long getSuccessors() {
        return observedAnimals.size() + deadSuccessors - 1; //to not count the tracked animal itself
    }

    @Override
    public void elementRemoved(AbstractWorldMapElement element) {
        if (element instanceof Animal animal && observedAnimals.contains(animal)) {
            observedAnimals.remove(animal);
            animal.removeObserver(this);
            deadSuccessors++;
        }
    }

    @Override
    public double getValue() {
        return getSuccessors();
    }

    @Override
    public String getName() {
        return ResourceBundle.getBundle("strings").getString("trackers.numberOfSuccessors.name");
    }
}
