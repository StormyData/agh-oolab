package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.AbstractWorldMapElement;

public interface IWorldMapElementRemovedObserver extends IObserver {
    void elementRemoved(AbstractWorldMapElement element);
}
