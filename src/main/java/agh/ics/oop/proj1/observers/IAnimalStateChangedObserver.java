package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.MapDirection;

public interface IAnimalStateChangedObserver extends IObserver{
    default void facingChanged(MapDirection newFacing){}
    default void animalDied(Animal animal, int age){}
    default void energyChanged(int newEnergy, int oldEnergy){}


    default void reproduced(Animal animal, Animal child){}
}
