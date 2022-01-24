package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;

public class StartState extends AbstractMachineState {
    public StartState(Board board)
    {
        super(board);
        nextState = new TurnStartState(board, Side.PLAYER_BLACK);

        for (int i = 0; i < 8; i++) {
            board.addBoardPiece(new Vector2d(i,0),Side.PLAYER_BLACK);
            board.addBoardPiece(new Vector2d(i,1),Side.PLAYER_BLACK);
        }

        for (int i = 0; i < 8; i++) {
            board.addBoardPiece(new Vector2d(i,6),Side.PLAYER_RED);
            board.addBoardPiece(new Vector2d(i,7),Side.PLAYER_RED);
        }
    }
}
