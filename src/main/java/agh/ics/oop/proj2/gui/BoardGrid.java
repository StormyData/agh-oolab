package agh.ics.oop.proj2.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.HighlightData;
import agh.ics.oop.proj2.Side;
import agh.ics.oop.proj2.observers.IBoardStateChangedObserver;
import agh.ics.oop.proj2.observers.ICellClickedObserver;
import agh.ics.oop.proj2.observers.IGameStateChangedObserver;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BoardGrid extends GridPane implements IBoardStateChangedObserver, IGameStateChangedObserver{
    final Set<ICellClickedObserver> observers = new HashSet<>();
    final BoardSquare[][] squares = new BoardSquare[8][8];
    public BoardGrid(Board board)
    {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Side side = board.getSideAt(new Vector2d(i,j));
                squares[i][j] = new BoardSquare(side, new Vector2d(i,j),this);
                GridPane.setHalignment(squares[i][j], HPos.CENTER);
                GridPane.setValignment(squares[i][j], VPos.CENTER);
                add(squares[i][j],i,j);
            }
        }
        board.addObserver(this);
        setGridLinesVisible(true);
    }
    public void setHighlighted(List<HighlightData> newHighlighted)
    {
        setHighlighted(Color.TRANSPARENT);
        for (HighlightData data : newHighlighted) {
            Color color = switch (data.type())
                    {
                        case PATH -> Color.CORAL;
                        case BEGIN -> Color.AQUA;
                        case COMMIT -> Color.GREEN;
                        case RESET -> Color.RED;
                    };
            setHighlighted(color,data.pos());
        }
    }

    public void setSide(Vector2d pos, Side side)
    {
        squares[pos.x][pos.y].setSide(side);
    }

    public void onSquareClicked(Vector2d pos) {
        observers.forEach(observer -> observer.cellClicked(pos));
    }

    @Override
    public void boardPieceAdded(Vector2d pos, Side side) {
        setSide(pos,side);
    }

    @Override
    public void boardPieceRemoved(Vector2d pos) {
        setSide(pos,null);
    }

    public void addObserver(ICellClickedObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ICellClickedObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void gameEnded(Side sideWon) {
        //do nothing
    }

    @Override
    public void highlightChanged(List<HighlightData> newHighlights) {
        setHighlighted(newHighlights);
    }


    private void setHighlighted(Color color, Vector2d pos)
    {
        squares[pos.x][pos.y].setHighlighted(color);
    }
    private void setHighlighted(Color color)
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j].setHighlighted(color);
            }
        }
    }
}
