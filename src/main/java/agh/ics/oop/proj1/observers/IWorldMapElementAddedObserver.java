package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.AbstractWorldMapElement;

public interface IWorldMapElementAddedObserver extends IObserver {
    void elementAdded(AbstractWorldMapElement element);
}
