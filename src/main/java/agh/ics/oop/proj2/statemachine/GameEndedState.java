package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;

public class GameEndedState extends AbstractMachineState {
    public final Side sideWon;
    public GameEndedState(Board board, Side sideWon) {
        super(board);
        this.sideWon = sideWon;
    }
}
