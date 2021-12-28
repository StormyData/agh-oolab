package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.observers.IAnimalDiedObserver;
import agh.ics.oop.proj1.observers.IWorldMapElementAddedObserver;

import java.util.ResourceBundle;

public class AvgAnimalLifespanTracker implements IWorldMapElementAddedObserver, IAnimalDiedObserver, INumericTracker {
    private long sum;
    private long nofAnimals;

    public AvgAnimalLifespanTracker(AbstractNonFlatWorldMap map) {
        map.addObserver(this);
        map.getStreamOfAllObjectsOfClass(Animal.class).
                forEach(animal -> animal.addObserver(this));
    }

    @Override
    public void elementAdded(AbstractWorldMapElement element) {
        if (element instanceof Animal animal) {
            animal.addObserver(this);
        }
    }


    public void animalDied(Animal animal, int age) {
        sum += age - animal.getAgeBorn();
        nofAnimals += 1;
        animal.removeObserver(this);
    }

    public double getAvgLifespan() {
        if (nofAnimals == 0)
            return 0;
        return Long.valueOf(sum).doubleValue() / Long.valueOf(nofAnimals).doubleValue();
    }

    @Override
    public double getValue() {
        return getAvgLifespan();
    }

    @Override
    public String getName() {
        return ResourceBundle.getBundle("strings").getString("trackers.averageAnimalLifespan.name");
    }
}
