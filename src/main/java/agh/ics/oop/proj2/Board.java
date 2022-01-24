package agh.ics.oop.proj2;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.observers.IBoardStateChangedObserver;

import java.util.HashSet;
import java.util.Set;

public class Board{
    private final Set<IBoardStateChangedObserver> observers = new HashSet<>();

    private static final Vector2d origin = new Vector2d(0,0);
    private final BoardPiece[][] board = new BoardPiece[8][8];
    public static final Vector2d size = new Vector2d(7,7);


    public void addBoardPiece(Vector2d pos, Side side)
    {
        if(!isOnBoard(pos))
            throw new IllegalArgumentException(String.format("position %s is out of bounds",pos));
        if(board[pos.x][pos.y] != null)
            throw new IllegalArgumentException("cannot place piece on a non empty field");
        board[pos.x][pos.y] = new BoardPiece(side);
        onBoardPieceAdded(pos);
    }

    public void removeBoardPiece(Vector2d pos)
    {
        if(!isOnBoard(pos))
            throw new IllegalArgumentException(String.format("position %s is out of bounds",pos));
        if(board[pos.x][pos.y] == null)
            throw new IllegalArgumentException("cannot remove piece from an empty field");
        board[pos.x][pos.y] = null;
        onBoardPieceRemoved(pos);
    }
    public Side getSideAt(Vector2d pos) {
        if(!isOnBoard(pos))
            throw new IllegalArgumentException(String.format("position %s is out of bounds",pos));
        if(board[pos.x][pos.y] == null)
            return null;
        return board[pos.x][pos.y].side();
    }

    public void move(Vector2d from, Vector2d to) {
        if(!isOnBoard(from))
            throw new IllegalArgumentException(String.format("from position %s is out of bounds",from));
        if(!isOnBoard(to))
            throw new IllegalArgumentException(String.format("to position %s is out of bounds",to));
        if(board[to.x][to.y] != null)
            throw new IllegalArgumentException("cannot move to a non empty space");
        board[to.x][to.y] = board[from.x][from.y];
        board[from.x][from.y] = null;
        onBoardPieceRemoved(from);
        onBoardPieceAdded(to);
    }
    public boolean isOnBoard(Vector2d pos)
    {
        return origin.precedes(pos) && size.follows(pos);
    }

    private void onBoardPieceAdded(Vector2d pos)
    {
        observers.forEach(observer -> observer.boardPieceAdded(pos,getSideAt(pos)));
    }

    private void onBoardPieceRemoved(Vector2d pos)
    {
        observers.forEach(observer -> observer.boardPieceRemoved(pos));
    }

    public void addObserver(IBoardStateChangedObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IBoardStateChangedObserver observer) {
        observers.remove(observer);
    }
}
