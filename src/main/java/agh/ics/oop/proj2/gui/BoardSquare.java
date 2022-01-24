package agh.ics.oop.proj2.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Side;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.Nullable;


public class BoardSquare extends Pane {
    private final Circle circle = new Circle();
    public BoardSquare(@Nullable Side side, Vector2d pos, BoardGrid grid)
    {
        Color circleColor = getSideColor(side);
        circle.setFill(circleColor);
        getChildren().add(circle);
        setOnMouseClicked(event -> grid.onSquareClicked(pos));
    }


    public void setHighlighted(Color color) {
        setBackground(new Background(new BackgroundFill(color,null,null)));
    }

    public void setSide(@Nullable Side side) {
        circle.setFill(getSideColor(side));
    }

    private Color getSideColor(Side side) {
        Color circleColor = Color.TRANSPARENT;

        if(side != null)
            circleColor= switch(side)
                    {
                        case PLAYER_BLACK -> Color.BLACK;
                        case PLAYER_RED -> Color.RED;
                    };
        return circleColor;
    }
}
