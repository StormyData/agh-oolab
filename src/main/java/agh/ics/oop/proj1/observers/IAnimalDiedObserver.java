package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.Animal;

public interface IAnimalDiedObserver extends IObserver {
    void animalDied(Animal animal, int age);
}
