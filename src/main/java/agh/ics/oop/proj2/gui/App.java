package agh.ics.oop.proj2.gui;

import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.HighlightData;
import agh.ics.oop.proj2.Side;
import agh.ics.oop.proj2.observers.IGameStateChangedObserver;
import agh.ics.oop.proj2.statemachine.StateMachine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;


public class App extends Application implements IGameStateChangedObserver {
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        Board board = new Board();
        BoardGrid grid = new BoardGrid(board);
        StateMachine stateMachine = new StateMachine(board);
        stateMachine.addObserver(grid);
        stateMachine.addObserver(this);
        grid.addObserver(stateMachine);

        grid.setHighlighted(stateMachine.getHighlighted());

        primaryStage.setTitle("Skoczki");
        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }

    @Override
    public void gameEnded(Side sideWon) {
        String text = switch (sideWon)
                {
                    case PLAYER_RED -> "WYGRYWA GRACZ CZERWONY";
                    case PLAYER_BLACK -> "WYGRYWA GRACZ CZARNY";
                };
        Stage stage = new Stage();
        stage.setScene(new Scene(new HBox(new Text(text))));
        stage.show();
    }

    @Override
    public void highlightChanged(List<HighlightData> newHighlights) {
        //do nothing
    }
}
