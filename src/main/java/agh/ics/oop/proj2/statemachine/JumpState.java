package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;

import java.util.HashMap;
import java.util.Map;

public class JumpState extends AbstractMachineState {
    private final Side side;

    private enum Type{
        JUMP,
        COMMIT
    }

    private static final Vector2d[] moveOffsets = {new Vector2d(-1,0),new Vector2d(1,0),new Vector2d(0,1),new Vector2d(0,-1)};

    private final Map<Vector2d,Type > moves = new HashMap<>();
    private final Vector2d startPos;
    private final Vector2d nextPos;

    public JumpState(Board board, Vector2d startPos, Vector2d nextPos, AbstractMachineState stateToRevertTo, Side side) {
        super(board,stateToRevertTo);
        this.startPos = startPos;
        this.nextPos = nextPos;
        this.side = side;

        for (Vector2d moveOffset : moveOffsets) {
            Vector2d movePos = nextPos.add(moveOffset);
            Vector2d jumpPos = movePos.add(moveOffset);
            if(board.isOnBoard(movePos) && board.getSideAt(movePos) != null &&
                    board.isOnBoard(jumpPos) && board.getSideAt(jumpPos) == null)
                moves.put(jumpPos, Type.JUMP);
        }
        moves.put(nextPos,Type.COMMIT);
    }


    @Override
    public void cellClicked(Vector2d pos) {
        if(!moves.containsKey(pos))
            return;
        nextState = switch (moves.get(pos))
                {
                    case JUMP -> new JumpState(board,startPos,pos,stateToRevertTo, side);
                    case COMMIT -> new CommitMoveState(board,stateToRevertTo,startPos,nextPos,side);
                };
    }

    @Override
    public Vector2d[] getCellsToHighlight() {
        return moves.keySet().toArray(new Vector2d[0]);
    }

}
