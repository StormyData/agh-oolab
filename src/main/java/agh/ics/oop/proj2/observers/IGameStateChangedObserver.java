package agh.ics.oop.proj2.observers;

import agh.ics.oop.Vector2d;
import agh.ics.oop.observers.IObserver;
import agh.ics.oop.proj2.Side;

public interface IGameStateChangedObserver extends IObserver {
    void gameEnded(Side sideWon);
    void highlightChanged(Vector2d[] newHighlights);
}
