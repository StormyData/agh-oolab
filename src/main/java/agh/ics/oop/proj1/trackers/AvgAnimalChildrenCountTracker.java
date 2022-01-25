package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.observers.IAnimalStateChangedObserver;
import agh.ics.oop.proj1.observers.IWorldMapElementAddedObserver;
import agh.ics.oop.proj1.observers.IWorldMapElementRemovedObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.ResourceBundle;

public class AvgAnimalChildrenCountTracker implements IWorldMapElementAddedObserver,
        IWorldMapElementRemovedObserver, IAnimalStateChangedObserver, INumericTracker {
    protected final Map<Animal, Integer> animalChildrenCountMap = new HashMap<>();

    public AvgAnimalChildrenCountTracker(AbstractNonFlatWorldMap map) {
        map.addObserver(this);
        map.getStreamOfAllObjectsOfClass(Animal.class)
                .forEach(this::addAnimal);
    }

    @Override
    public void reproduced(Animal animal, Animal child) {
        animalChildrenCountMap.computeIfPresent(animal, (object, count) -> count + 1);
    }

    @Override
    public void elementAdded(AbstractWorldMapElement element) {
        if (element instanceof Animal animal) {
            addAnimal(animal);
        }
    }

    private void addAnimal(Animal animal) {
        animal.addObserver(this);
        animalChildrenCountMap.put(animal, 0);
    }

    @Override
    public void elementRemoved(AbstractWorldMapElement element) {
        if (element instanceof Animal animal) {
            animal.removeObserver(this);
            animalChildrenCountMap.remove(animal);
        }
    }

    public double getAvgChildren() {
        OptionalDouble result = animalChildrenCountMap.values().stream().mapToInt(Integer::intValue).average();
        return result.orElse(0);
    }

    @Override
    public Number getValue() {
        return getAvgChildren();
    }

    @Override
    public String getName() {
        return ResourceBundle.getBundle("strings").getString("trackers.averageChildrenCount.name");
    }
}
