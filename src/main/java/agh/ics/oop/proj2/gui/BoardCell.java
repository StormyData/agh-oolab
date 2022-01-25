package agh.ics.oop.proj2.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Side;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;


public class BoardCell extends VBox {
    private final Circle circle = new Circle(50);

    public BoardCell(@Nullable Side side, Vector2d pos, Consumer<Vector2d> onClicked) {
        Color circleColor = getSideColor(side);
        circle.setFill(circleColor);
        getChildren().add(circle);
        setOnMouseClicked(event -> onClicked.accept(pos));
    }


    public void setHighlighted(Color color) {
        setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    public void setSide(@Nullable Side side) {
        circle.setFill(getSideColor(side));
    }

    private Color getSideColor(Side side) {
        if (side == null)
            return Color.TRANSPARENT;

        return switch (side) {
                case PLAYER_BLACK -> Color.BLACK;
                case PLAYER_RED -> Color.RED;
            };
    }
}
