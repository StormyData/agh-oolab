package agh.ics.oop.proj2.observers;

import agh.ics.oop.Vector2d;
import agh.ics.oop.observers.IObserver;
import agh.ics.oop.proj2.Side;

public interface IBoardStateChangedObserver extends IObserver {
    void boardPieceAdded(Vector2d pos, Side side);
    void boardPieceRemoved(Vector2d pos);
}
