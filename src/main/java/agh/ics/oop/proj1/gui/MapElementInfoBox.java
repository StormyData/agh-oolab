package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Vector2d;
import agh.ics.oop.proj1.observers.IObservable;
import agh.ics.oop.proj1.observers.IPositionChangedObserver;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MapElementInfoBox extends GridPane implements IPositionChangedObserver {
    protected final Map<String, Text> properties = new HashMap<>();
    protected final AbstractWorldMapElement element;

    public MapElementInfoBox(AbstractWorldMapElement element) {
        this.element = element;
        add(new GuiElementBox(element), 0, 0, 2, 1);
        properties.put("mapElementInfoBox.type", new Text(element.getDisplayName()));
        properties.put("mapElementInfoBox.position", new Text(element.getPosition().toString()));

        addAdditionalProperties();

        int i = 1;
        for (Map.Entry<String, Text> entry : properties.entrySet()) {
            Text label = new Text(ResourceBundle.getBundle("strings").getString(entry.getKey()));
            GridPane.setHalignment(label, HPos.CENTER);
            add(label, 0, i);
            add(entry.getValue(), 1, i);
            GridPane.setHalignment(entry.getValue(), HPos.CENTER);
            i++;
        }
        if (element instanceof IObservable observable)
            observable.addObserver(this);
    }

    protected void addAdditionalProperties() {
    }

    @Override
    public void positionChanged(IObservable observable, Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(
                () -> properties.get("mapElementInfoBox.position").setText(newPosition.toString())
        );
    }

    public void close() {
        if (element instanceof IObservable observable)
            observable.removeObserver(this);
    }
}
