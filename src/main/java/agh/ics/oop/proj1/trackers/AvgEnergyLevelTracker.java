package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.observers.IEnergyChangedObserver;
import agh.ics.oop.proj1.observers.IWorldMapElementAddedObserver;
import agh.ics.oop.proj1.observers.IWorldMapElementRemovedObserver;

import java.util.ResourceBundle;

public class AvgEnergyLevelTracker implements INumericTracker, IEnergyChangedObserver, IWorldMapElementRemovedObserver, IWorldMapElementAddedObserver {
    private long amount;
    private long count;

    public AvgEnergyLevelTracker(AbstractNonFlatWorldMap map) {
        map.addObserver(this);
        map.getStreamOfAllObjectsOfClass(Animal.class).
                forEach(animal -> {
                    count++;
                    amount += animal.getEnergy();
                });

    }

    public double getAvgEnergyLevel() {
        if (count == 0)
            return 0;
        return ((double) amount) / count;
    }

    @Override
    public double getValue() {
        return getAvgEnergyLevel();
    }

    @Override
    public String getName() {
        return ResourceBundle.getBundle("strings").getString("trackers.averageEnergyLevelOfAnimals.name");
    }

    @Override
    public void energyChanged(int newEnergy, int oldEnergy) {
        amount += newEnergy - oldEnergy;
    }

    @Override
    public void elementAdded(AbstractWorldMapElement element) {
        if (element instanceof Animal animal) {
            count++;
            amount += animal.getEnergy();
        }
    }

    @Override
    public void elementRemoved(AbstractWorldMapElement element) {
        if (element instanceof Animal animal) {
            count--;
            amount -= animal.getEnergy();
        }

    }
}
