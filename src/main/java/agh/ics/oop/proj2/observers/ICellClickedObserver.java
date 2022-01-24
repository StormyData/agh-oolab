package agh.ics.oop.proj2.observers;

import agh.ics.oop.Vector2d;
import agh.ics.oop.observers.IObserver;

public interface ICellClickedObserver extends IObserver {
    void cellClicked(Vector2d pos);
}
