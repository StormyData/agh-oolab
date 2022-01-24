package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.HighlightData;
import agh.ics.oop.proj2.Side;

import java.util.LinkedList;
import java.util.List;

public class TurnStartState extends AbstractMachineState {
    private final Side side;
    private final List<HighlightData> highlightedCells;

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
        highlightedCells = highlightedCellsList.stream().map(pos -> new HighlightData(pos, HighlightData.HighlightType.BEGIN)).toList();
    }

    @Override
    public void cellClicked(Vector2d pos) {
        if(board.getSideAt(pos) != side)
            return;
        nextState = new PieceSelectedState(board, side, pos);
    }

    @Override
    public List<HighlightData> getCellsToHighlight() {

        return highlightedCells;
    }
}
