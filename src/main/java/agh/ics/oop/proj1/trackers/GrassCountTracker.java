package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Grass;

import java.util.ResourceBundle;

public class GrassCountTracker extends AbstractCountTracker {
    public GrassCountTracker(AbstractNonFlatWorldMap map) {
        super(map);
    }

    @Override
    protected boolean isCounted(AbstractWorldMapElement element) {
        return element instanceof Grass;
    }

    @Override
    public String getName() {
        return ResourceBundle.getBundle("strings").getString("trackers.numberOfPlants.name");
    }
}
