package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;

public abstract class AbstractMachineState {
    protected AbstractMachineState nextState = this;
    protected final Board board;
    protected AbstractMachineState stateToRevertTo = this;

    public AbstractMachineState(Board board, AbstractMachineState stateToRevertTo) {
        if(stateToRevertTo != null)
            this.stateToRevertTo = stateToRevertTo;
        this.board = board;
    }

    public AbstractMachineState nextState()
    {
        return nextState;
    }
    public void cellClicked(Vector2d pos)
    {

    }
    public Vector2d[] getCellsToHighlight()
    {
        return new Vector2d[0];
    }
    public AbstractMachineState revertState()
    {
        return stateToRevertTo;
    }


}
