package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.Animal;

public interface IReproductionObserver extends IObserver {
    void reproduced(Animal animal, Animal child);
}
