package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;

public class MoveState extends AbstractMachineState {
    private final Vector2d startPos;
    private final Vector2d currentPos;
    private final Side side;

    public MoveState(Board board, Vector2d startPos, Vector2d currentPos, AbstractMachineState stateToRevertTo, Side side) {
        super(board,stateToRevertTo);
        this.startPos = startPos;
        this.currentPos = currentPos;
        this.side = side;
    }

    @Override
    public void cellClicked(Vector2d pos) {
        if(pos == currentPos)
            nextState = new CommitMoveState(board,stateToRevertTo,startPos,currentPos,side);
    }

    @Override
    public Vector2d[] getCellsToHighlight() {
        return new Vector2d[]{currentPos};
    }
}
