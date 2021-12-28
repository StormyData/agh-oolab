package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Animal;

import java.util.ResourceBundle;

public class AnimalCountTracker extends AbstractCountTracker {
    public AnimalCountTracker(AbstractNonFlatWorldMap map) {
        super(map);
    }

    @Override
    protected boolean isCounted(AbstractWorldMapElement element) {
        return element instanceof Animal;
    }

    @Override
    public String getName() {
        return ResourceBundle.getBundle("strings").getString("trackers.numberOfAnimals.name");
    }
}
