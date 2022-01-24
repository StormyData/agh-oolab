package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;

import java.util.LinkedList;
import java.util.List;

public class TurnStartState extends AbstractMachineState {
    private final Side side;
    private final Vector2d[] highlightedCells;

    public TurnStartState(Board board, Side side)
    {
        super(board);
        this.side=side;

        List<Vector2d> highlightedCellsList = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Vector2d pos = new Vector2d(i,j);
                if(board.getSideAt(pos) == side)
                    highlightedCellsList.add(pos);
            }
        }
        highlightedCells = highlightedCellsList.toArray(new Vector2d[0]);
    }

    @Override
    public void cellClicked(Vector2d pos) {
        if(board.getSideAt(pos) != side)
            return;
        nextState = new PieceSelectedState(board, side, pos);
    }

    @Override
    public Vector2d[] getCellsToHighlight() {

        return highlightedCells;
    }
}
