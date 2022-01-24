package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;

public class CommitMoveState extends AbstractMachineState {
    public CommitMoveState(Board board, AbstractMachineState stateToRevertTo, Vector2d from, Vector2d to, Side side) {
        super(board, stateToRevertTo);
        board.move(from,to);
        nextState = new CheckBoardStateState(board,stateToRevertTo,side);
    }
}
