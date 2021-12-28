package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.Vector2d;

public interface IPositionChangedObserver extends IObserver {
    void positionChanged(IObservable observable,
                         Vector2d oldPosition, Vector2d newPosition);
}
