package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.AxisAlignedRectangle;
import agh.ics.oop.proj1.RectangularWorldMap;
import agh.ics.oop.proj1.Vector2d;
import agh.ics.oop.proj1.WrapAroundWorldMap;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;

public class InitialSelectorPane extends Pane {
    private final Spinner<Integer> width = new Spinner<>(1, Integer.MAX_VALUE, 10);
    private final Spinner<Integer> height = new Spinner<>(1, Integer.MAX_VALUE, 10);
    private final Spinner<Integer> initialAmountOfAnimals = new Spinner<>(1, Integer.MAX_VALUE, 10);
    private final Spinner<String> language = new Spinner<>(FXCollections.observableList(List.of("pl-PL", "en-US")));
    private MapHolderBox rightBox;
    private MapHolderBox leftBox;

    InitialSelectorPane(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.add(width, 0, 0);
        gridPane.add(new Text("x"), 1, 0);
        gridPane.add(height, 2, 0);

        gridPane.add(new Text("start animals:"), 0, 1);
        gridPane.add(initialAmountOfAnimals, 1, 1, 2, 1);

        gridPane.add(language, 0, 2, 3, 1);

        Button enter = new Button("enter");
        gridPane.add(enter, 0, 3, 3, 1);

        getChildren().add(gridPane);

        initialAmountOfAnimals.setEditable(true);
        width.setEditable(true);
        height.setEditable(true);
        language.setEditable(true);

        enter.setOnAction(event ->
        {
            Vector2d mapSize = new Vector2d(width.getValue(), height.getValue());
            AxisAlignedRectangle mapBounds = new AxisAlignedRectangle(new Vector2d(0, 0), mapSize.subtract(new Vector2d(1, 1)));
            int startingAnimals = initialAmountOfAnimals.getValue();
            String lang = language.getValue();

            Locale.setDefault(Locale.forLanguageTag(lang));

            rightBox = new MapHolderBox(new RectangularWorldMap(mapBounds), mapBounds, startingAnimals);
            leftBox = new MapHolderBox(new WrapAroundWorldMap(mapBounds), mapBounds, startingAnimals);

            primaryStage.setScene(new Scene(new HBox(leftBox, rightBox)));
        });
    }


    public void close() {
        if (rightBox != null)
            rightBox.close();
        if (leftBox != null)
            leftBox.close();
    }
}
