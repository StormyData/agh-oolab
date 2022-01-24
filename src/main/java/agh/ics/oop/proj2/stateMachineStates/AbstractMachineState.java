package agh.ics.oop.proj2.stateMachineStates;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.HighlightData;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMachineState {
    //Class does nothing on its own, even through it has no abstract methods

    protected final Board board;
    protected AbstractMachineState nextState = this;

    public AbstractMachineState(Board board) {
        this.board = board;
    }

    @Nonnull
    public AbstractMachineState nextState() {
        return nextState;
    }

    public void cellClicked(Vector2d pos) {

    }

    public List<HighlightData> getCellsToHighlight() {
        return Collections.emptyList();
    }


}
