package agh.ics.oop.proj1.observers;

public interface IEnergyChangedObserver extends IObserver {
    void energyChanged(int newEnergy, int oldEnergy);
}
