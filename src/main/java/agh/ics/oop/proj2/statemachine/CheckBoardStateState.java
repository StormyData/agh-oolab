package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;

public class CheckBoardStateState extends AbstractMachineState {
    public CheckBoardStateState(Board board, Side side) {
        super(board);
        if(checkBlackWon())
            nextState = new GameEndedState(board, Side.PLAYER_BLACK);
        else if(checkRedWon())
            nextState = new GameEndedState(board, Side.PLAYER_RED);
        else
            nextState = new TurnStartState(board,side.other());
    }
    private boolean checkRedWon()
    {
        for (int i = 0; i < 8; i++) {
            if(board.getSideAt(new Vector2d(i,0)) != Side.PLAYER_RED)
                return false;
            if(board.getSideAt(new Vector2d(i,1)) != Side.PLAYER_RED)
                return false;
        }
        return true;
    }
    private boolean checkBlackWon()
    {
        for (int i = 0; i < 8; i++) {
            if(board.getSideAt(new Vector2d(i,6)) != Side.PLAYER_BLACK)
                return false;
            if(board.getSideAt(new Vector2d(i,7)) != Side.PLAYER_BLACK)
                return false;
        }
        return true;
    }
}
