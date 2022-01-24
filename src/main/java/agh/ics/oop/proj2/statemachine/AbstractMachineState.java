package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.HighlightData;

import java.util.Collections;
import java.util.List;

public abstract class AbstractMachineState {
    protected AbstractMachineState nextState = this;
    protected final Board board;

    public AbstractMachineState(Board board) {
        this.board = board;
    }

    public AbstractMachineState nextState()
    {
        return nextState;
    }
    public void cellClicked(Vector2d pos)
    {

    }
    public List<HighlightData> getCellsToHighlight()
    {
        return Collections.emptyList();
    }


}
