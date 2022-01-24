package agh.ics.oop.proj2.observers;

import agh.ics.oop.proj2.HighlightData;
import agh.ics.oop.proj2.Side;

import java.util.List;

public interface IGameStateChangedObserver {
    void gameEnded(Side sideWon);

    void highlightChanged(List<HighlightData> newHighlights);
}
