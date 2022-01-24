package agh.ics.oop.proj2.observers;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Side;

public interface IGameStateChangedObserver{
    void gameEnded(Side sideWon);
    void highlightChanged(Vector2d[] newHighlights);
}
