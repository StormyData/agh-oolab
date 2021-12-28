package agh.ics.oop.proj1.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class App extends Application {
    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("pl-PL"));
        Application.launch(args);
        System.exit(0);
    }

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) {
        InitialSelectorPane pane = new InitialSelectorPane(primaryStage);
        Scene scene = new Scene(pane);
        primaryStage.setOnCloseRequest(event -> pane.close());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
