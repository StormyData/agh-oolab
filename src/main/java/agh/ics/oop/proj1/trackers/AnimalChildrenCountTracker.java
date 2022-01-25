package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.observers.IAnimalStateChangedObserver;

import java.util.ResourceBundle;

public class AnimalChildrenCountTracker implements INumericTracker, IAnimalStateChangedObserver {
    private final Animal trackedAnimal;
    private long childrenCount;

    public AnimalChildrenCountTracker(Animal animal) {
        trackedAnimal = animal;
        trackedAnimal.addObserver(this);
    }

    @Override
    public void reproduced(Animal animal, Animal child) {
        if (trackedAnimal == animal)
            childrenCount++;
    }

    public void dispose() {
        trackedAnimal.removeObserver(this);
    }

    public long getChildrenCount() {
        return childrenCount;
    }

    @Override
    public Number getValue() {
        return getChildrenCount();
    }

    @Override
    public String getName() {
        return ResourceBundle.getBundle("strings").getString("trackers.numberOfAliveChildren.name");
    }

    @Override
    public void animalDied(Animal animal, int age) {
        dispose();
    }
}
