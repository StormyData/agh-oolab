package agh.ics.oop.proj2.stateMachineStates;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;

public class CheckBoardStateState extends AbstractMachineState {

    private static final int[] BLACK_ROWS = {Board.SIZE.y - 2, Board.SIZE.y - 1};
    private static final int[] RED_ROWS = {0, 1};

    public CheckBoardStateState(Board board, Side side) {
        super(board);
        if (checkWon(Side.PLAYER_BLACK, BLACK_ROWS))
            nextState = new GameEndedState(board, Side.PLAYER_BLACK);
        else if (checkWon(Side.PLAYER_RED, RED_ROWS))
            nextState = new GameEndedState(board, Side.PLAYER_RED);
        else
            nextState = new TurnStartState(board, side.next());
    }

    private boolean checkWon(Side side, int[] rows) {
        for (int x = 0; x < Board.SIZE.x; x++)
            for (int y : rows)
                if (board.getSideAt(new Vector2d(x, y)) != side)
                    return false;
        return true;
    }
}
