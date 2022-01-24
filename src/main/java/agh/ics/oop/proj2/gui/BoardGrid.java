package agh.ics.oop.proj2.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.observers.IObservable;
import agh.ics.oop.observers.IObserver;
import agh.ics.oop.observers.ObserverHolder;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;
import agh.ics.oop.proj2.observers.IBoardStateChangedObserver;
import agh.ics.oop.proj2.observers.ICellClickedObserver;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class BoardGrid extends GridPane implements IBoardStateChangedObserver, IObservable {
    final ObserverHolder observers = new ObserverHolder(ICellClickedObserver.class);
    final BoardSquare[][] squares = new BoardSquare[8][8];
    public BoardGrid(Board board)
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Side side = board.getSideAt(new Vector2d(i,j));
                squares[i][j] = new BoardSquare(side, new Vector2d(i,j),this);
                add(squares[i][j],i,j);
            }
        }
        board.addObserver(this);
    }
    public void setHighlighted(Color color, Vector2d pos)
    {
        squares[pos.x][pos.y].setHighlighted(color);
    }
    public void setHighlighted(Color color)
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j].setHighlighted(color);
            }
        }
    }

    public void setSide(Vector2d pos, Side side)
    {
        squares[pos.x][pos.y].setSide(side);
    }

    public void onSquareClicked(Vector2d pos) {
        observers.notifyObservers(ICellClickedObserver.class,observer -> observer.cellClicked(pos));
    }

    @Override
    public void boardPieceAdded(Vector2d pos, Side side) {
        setSide(pos,side);
    }

    @Override
    public void boardPieceRemoved(Vector2d pos) {
        setSide(pos,null);
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.removeObserver(observer);
    }


}
