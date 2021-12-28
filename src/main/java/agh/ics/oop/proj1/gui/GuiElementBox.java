package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.Grass;
import agh.ics.oop.proj1.observers.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.InputStream;

public class GuiElementBox extends VBox implements IImageChangedObserver, IEnergyChangedObserver, IObservable {
    private final ImageView view = new ImageView();
    private final Text text = new Text();
    private final AbstractWorldMapElement mapElement;
    private final ObserverHolder observers = new ObserverHolder(IMapObjectClickedObserver.class);

    GuiElementBox(AbstractWorldMapElement mapElement) {
        this.mapElement = mapElement;
        if (mapElement instanceof IObservable observable)
            observable.addObserver(this);

        view.setImage(getImageForName(mapElement.getImageName()));

        getChildren().add(view);

        if (mapElement instanceof Animal animal) {
            text.setText(Integer.toString(animal.getEnergy()));
            getChildren().add(text);

            view.setScaleX(2);
            view.setScaleY(2);
            view.setFitHeight(20);
            view.setFitWidth(20);
        } else if (mapElement instanceof Grass) {
            view.setFitHeight(30);
            view.setFitWidth(30);
        }
        text.setTextAlignment(TextAlignment.CENTER);
        setAlignment(Pos.CENTER);
        setOnMouseClicked(this::onMouseClicked);
    }

    private static Image getImageForName(String imageName) {
        InputStream stream = Thread.currentThread().getContextClassLoader().
                getResourceAsStream(imageName);
        if (stream == null)
            return null;
        return new Image(stream);
    }

    @Override
    public void imageChanged(String newName) {
        Platform.runLater(() -> view.setImage(getImageForName(newName)));
    }


    public int getDisplayPriority() {
        return mapElement.displayPriority();
    }

    public boolean isAssociated(Object observable) {
        return observable == mapElement;
    }

    public void dispose() {
        if (mapElement instanceof IObservable observable)
            observable.removeObserver(this);
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        observers.notifyObservers(IMapObjectClickedObserver.class, observer -> observer.mapObjectClicked(mapElement));
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.removeObserver(observer);
    }

    @Override
    public void energyChanged(int newEnergy, int oldEnergy) {
        Platform.runLater(() -> text.setText(Integer.toString(newEnergy)));
    }

    public void setHighlighted(boolean bool) {
        Platform.runLater(() -> {

            Color backgroundColor = bool ? Color.RED : Color.TRANSPARENT;
            setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
        });
    }
}
