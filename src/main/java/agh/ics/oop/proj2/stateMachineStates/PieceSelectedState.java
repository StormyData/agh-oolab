package agh.ics.oop.proj2.stateMachineStates;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.HighlightData;
import agh.ics.oop.proj2.Side;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceSelectedState extends AbstractMachineState {
    private static final Vector2d[] MOVE_OFFSETS = {new Vector2d(-1, 0), new Vector2d(1, 0), new Vector2d(0, 1), new Vector2d(0, -1)};
    private final Side side;
    private final Vector2d selectedPos;
    private final List<HighlightData> highlightedCells;
    private final Map<Vector2d, Type> moves = new HashMap<>();
    public PieceSelectedState(Board board, Side side, Vector2d selectedPos) {
        super(board);
        this.side = side;
        this.selectedPos = selectedPos;

        for (Vector2d moveOffset : MOVE_OFFSETS) {
            Vector2d movePos = selectedPos.add(moveOffset);
            Vector2d jumpPos = movePos.add(moveOffset);
            if (board.isOnBoard(movePos) && board.getSideAt(movePos) == null)
                moves.put(movePos, Type.MOVE);
            else if (board.isOnBoard(jumpPos) && board.getSideAt(jumpPos) == null)
                moves.put(jumpPos, Type.JUMP);
        }
        moves.put(selectedPos, Type.REVERT);

        highlightedCells = moves.entrySet()
                .stream()
                .map(entry -> new HighlightData(entry.getKey(), entry.getValue().highlightType))
                .toList();
    }

    @Override
    public void cellClicked(Vector2d pos) {
        if (!moves.containsKey(pos))
            return;
        nextState = switch (moves.get(pos)) {
            case JUMP -> new JumpState(board, selectedPos, pos, side);
            case MOVE -> new MoveState(board, selectedPos, pos, side);
            case REVERT -> new TurnStartState(board, side);
        };
    }

    @Override
    public List<HighlightData> getCellsToHighlight() {
        return highlightedCells;
    }

    private enum Type {
        JUMP(HighlightData.HighlightType.PATH),
        MOVE(HighlightData.HighlightType.PATH),
        REVERT(HighlightData.HighlightType.RESET);

        public final HighlightData.HighlightType highlightType;

        Type(HighlightData.HighlightType highlightType) {
            this.highlightType = highlightType;
        }
    }
}
