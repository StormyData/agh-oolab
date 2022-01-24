package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.HighlightData;
import agh.ics.oop.proj2.Side;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceSelectedState extends AbstractMachineState {
    private final Side side;

    private enum MoveType {
        JUMP(HighlightData.HighlightType.PATH),
        MOVE(HighlightData.HighlightType.PATH),
        REVERT(HighlightData.HighlightType.RESET);
        public final HighlightData.HighlightType highlightType;

        MoveType(HighlightData.HighlightType highlightType) {
            this.highlightType = highlightType;
        }
    }
    private final Vector2d selectedPos;
    private static final Vector2d[] moveOffsets = {new Vector2d(-1,0),new Vector2d(1,0),new Vector2d(0,1),new Vector2d(0,-1)};
    private final Map<Vector2d, MoveType> moves = new HashMap<>();
    public PieceSelectedState(Board board, Side side, Vector2d selectedPos) {
        super(board);
        this.side = side;
        this.selectedPos = selectedPos;
        for (Vector2d moveOffset : moveOffsets) {
            Vector2d movePos = selectedPos.add(moveOffset);
            Vector2d jumpPos = movePos.add(moveOffset);
            if(board.isOnBoard(movePos) && board.getSideAt(movePos) == null)
                moves.put(movePos, MoveType.MOVE);
            else if(board.isOnBoard(jumpPos) && board.getSideAt(jumpPos) == null)
                moves.put(jumpPos, MoveType.JUMP);
        }
        moves.put(selectedPos,MoveType.REVERT);
    }

    @Override
    public void cellClicked(Vector2d pos) {
        if(!moves.containsKey(pos))
            return;
        nextState = switch (moves.get(pos))
        {
            case JUMP -> new JumpState(board,selectedPos,pos, side);
            case MOVE -> new MoveState(board,selectedPos,pos, side);
            case REVERT -> new TurnStartState(board,side);
        };
    }

    @Override
    public List<HighlightData> getCellsToHighlight() {
        return moves.entrySet()
                .stream()
                .map(entry -> new HighlightData(entry.getKey(),entry.getValue().highlightType))
                .toList();
    }
}
