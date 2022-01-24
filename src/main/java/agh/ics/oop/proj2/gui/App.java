package agh.ics.oop.proj2.gui;

import agh.ics.oop.proj2.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        Board board = new Board();
        BoardGrid grid = new BoardGrid(board);

        primaryStage.setScene(new Scene(grid));
    }
}
