package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.observers.IWorldMapElementAddedObserver;
import agh.ics.oop.proj1.observers.IWorldMapElementRemovedObserver;


public abstract class AbstractCountTracker implements IWorldMapElementRemovedObserver, IWorldMapElementAddedObserver, INumericTracker {
    private long count;

    AbstractCountTracker(AbstractNonFlatWorldMap map) {
        map.addObserver(this);
        count = map.getStreamOfAllObjects().
                filter(this::isCounted).
                count();
    }

    protected abstract boolean isCounted(AbstractWorldMapElement element);

    @Override
    public void elementAdded(AbstractWorldMapElement element) {
        if (isCounted(element))
            count += 1;
    }

    @Override
    public void elementRemoved(AbstractWorldMapElement element) {
        if (isCounted(element))
            count -= 1;
    }

    public long getCount() {
        return count;
    }

    @Override
    public double getValue() {
        return getCount();
    }
}
