package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.MapDirection;

public interface IFacingChangedObserver extends IObserver {
    void facingChanged(MapDirection newFacing);
}
