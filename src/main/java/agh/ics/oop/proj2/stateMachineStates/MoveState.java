package agh.ics.oop.proj2.stateMachineStates;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;

public class MoveState extends AbstractMachineState {

    public MoveState(Board board, Vector2d startPos, Vector2d currentPos, Side side) {
        super(board);
        nextState = new CommitMoveState(board, startPos, currentPos, side);
    }
}
