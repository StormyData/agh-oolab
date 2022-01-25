package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.Grass;
import agh.ics.oop.proj1.MapDirection;
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

import java.util.EnumMap;
import java.util.Map;
public class GuiElementBox extends VBox implements IAnimalStateChangedObserver, IObservable {
    private static  EnumMap<MapDirection,String> imageNameForAnimalFacing = new EnumMap<>(Map.ofEntries(
            Map.entry(MapDirection.NORTH, "sheepNorth.png"),
            Map.entry(MapDirection.NORTHEAST, "sheepNorthEast.png"),
            Map.entry(MapDirection.EAST , "sheepEast.png"),
            Map.entry(MapDirection.SOUTHEAST , "sheepSouthEast.png"),
            Map.entry(MapDirection.SOUTH , "sheepSouth.png"),
            Map.entry(MapDirection.SOUTHWEST , "sheepSouthWest.png"),
            Map.entry(MapDirection.WEST , "sheepWest.png"),
            Map.entry(MapDirection.NORTHWEST , "sheepNorthWest.png")
    ));

    private final ImageView view = new ImageView();
    private final Text text = new Text();
    private final AbstractWorldMapElement mapElement;
    private final ObserverHolder observers = new ObserverHolder(IMapObjectClickedObserver.class);
    private int displayPriority;

    GuiElementBox(AbstractWorldMapElement mapElement) {
        this.mapElement = mapElement;
        if (mapElement instanceof IObservable observable)
            observable.addObserver(this);

        String imageName=null;

        if (mapElement instanceof Animal animal) {
            text.setText(Integer.toString(animal.getEnergy()));
            getChildren().add(text);

            view.setScaleX(2);
            view.setScaleY(2);
            view.setFitHeight(20);
            view.setFitWidth(20);
            displayPriority = 1;
            imageName = imageNameForAnimalFacing.get(animal.getFacing());
        } else if (mapElement instanceof Grass) {
            view.setFitHeight(30);
            view.setFitWidth(30);
            displayPriority = -1;
            imageName="gras.png";
        }

        getChildren().add(view);
        view.setImage(getImageForName(imageName));

        text.setTextAlignment(TextAlignment.CENTER);
        setAlignment(Pos.CENTER);
        setOnMouseClicked(this::onMouseClicked);
    }

    private static Image getImageForName(String imageName) {
        return ImageContainerSingleton.getSingleton().getImageForName(imageName);
    }



    public int getDisplayPriority() {
        return displayPriority;
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

    @Override
    public void facingChanged(MapDirection newFacing) {
        Platform.runLater(() -> view.setImage(getImageForName(imageNameForAnimalFacing.get(newFacing))));
    }

    public void setHighlighted(boolean bool) {
        Platform.runLater(() -> {

            Color backgroundColor = bool ? Color.RED : Color.TRANSPARENT;
            setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
        });
    }
}
