package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.AbstractWorldMapElement;

public interface IMapObjectClickedObserver extends IObserver {
    void mapObjectClicked(AbstractWorldMapElement element);
}
