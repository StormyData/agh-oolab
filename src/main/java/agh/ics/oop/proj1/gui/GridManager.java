package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.*;
import agh.ics.oop.proj1.observers.*;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.*;
import java.util.function.Predicate;

public class GridManager extends GridPane implements IPositionChangedObserver, IWorldMapElementRemovedObserver,
        IWorldMapElementAddedObserver, IMapObjectClickedObserver, IObservable {
    private static final double cell_width = 35;
    private static final double cell_height = 35;
    private final AxisAlignedRectangle drawBounds;
    private final Vector2d size;
    private final HashMap<Vector2d, Set<GuiElementBox>> guiElementBoxSets = new LinkedHashMap<>();
    private final HashMap<Vector2d, GuiElementBox> topGuiElementBoxes = new LinkedHashMap<>();
    private final ObserverHolder observers = new ObserverHolder(IMapObjectClickedObserver.class);
    private final AbstractNonFlatWorldMap map;
    private final Set<GuiElementBox> highlighted = new HashSet<>();

    public GridManager(AxisAlignedRectangle drawBounds, AbstractNonFlatWorldMap map) {
        this.drawBounds = drawBounds;
        this.map = map;
        size = drawBounds.getSize();

        map.addObserver(this);

        addAllWorldMapElementsFromMap(map);

        setGridLinesVisible(true);

        addGridLabels();

        for (int i = 0; i <= size.x; i++)
            getColumnConstraints().add(new ColumnConstraints(cell_width));
        for (int i = 0; i <= size.y; i++)
            getRowConstraints().add(new RowConstraints(cell_height));

    }

    private void addAllWorldMapElementsFromMap(AbstractNonFlatWorldMap map) {
        for (Vector2d position : map.getOccupiedPositions()) {
            Set<AbstractWorldMapElement> elements = map.objectsAt(position);
            if (elements == null)
                continue;
            Util.filterInstancesAndCastStream(elements.stream(), IObservable.class).
                    forEach(observable -> observable.addObserver(this));

            if (!guiElementBoxSets.containsKey(position))
                guiElementBoxSets.put(position, new HashSet<>());
            guiElementBoxSets.get(position).addAll(
                    elements.stream().map(
                            (AbstractWorldMapElement element) -> {
                                GuiElementBox obj = new GuiElementBox(element);
                                obj.addObserver(this);
                                GridPane.setHalignment(obj, HPos.CENTER);
                                return obj;
                            }).toList());
            updateTopGuiElementBoxAtPosition(position);
        }
    }

    private void addGridLabels() {
        Label labelYX = new Label("y/x");

        add(labelYX, 0, 0);
        GridPane.setHalignment(labelYX, HPos.CENTER);
        for (int i = 0; i < size.x; i++) {
            Label labelX = new Label(Integer.toString(i + drawBounds.lowerLeft.x));
            GridPane.setHalignment(labelX, HPos.CENTER);
            add(labelX, 1 + i, 0);
        }
        for (int i = 0; i < size.y; i++) {
            Label labelY = new Label(Integer.toString(size.y - i + drawBounds.lowerLeft.y - 1));
            GridPane.setHalignment(labelY, HPos.CENTER);
            add(labelY, 0, 1 + i);
        }
    }

    private void updateTopGuiElementBoxAtPosition(Vector2d position) {
        removeTopGuiElementBoxAtPosition(position);
        addTopGuiElementBoxAtPosition(position);
    }

    private void updateTopGuiElementBoxAtPosition(Vector2d position, GuiElementBox oldGuiElementBox) {
        if (topGuiElementBoxes.get(position) == oldGuiElementBox) {
            removeTopGuiElementBoxAtPosition(position);
            addTopGuiElementBoxAtPosition(position);
        }
    }

    private void removeTopGuiElementBoxAtPosition(Vector2d position) {
        GuiElementBox topGuiElementBox = topGuiElementBoxes.get(position);
        topGuiElementBoxes.remove(position);
        Platform.runLater(() -> getChildren().remove(topGuiElementBox));
    }

    private void removeTopGuiElementBoxAtPosition(Vector2d position, GuiElementBox guiElementBox) {
        if (topGuiElementBoxes.get(position) == guiElementBox)
            removeTopGuiElementBoxAtPosition(position);
    }

    private void addTopGuiElementBoxAtPosition(Vector2d position) {
        if (isInView(position) && guiElementBoxSets.containsKey(position)) {
            Optional<GuiElementBox> maxPriorityGuiElementBox = guiElementBoxSets.get(position).stream().
                    max(Comparator.comparingInt(GuiElementBox::getDisplayPriority));
            if (maxPriorityGuiElementBox.isPresent()) {
                Vector2d relativePosition = position.subtract(drawBounds.lowerLeft);
                topGuiElementBoxes.put(position, maxPriorityGuiElementBox.get());
                Platform.runLater(() ->
                        add(maxPriorityGuiElementBox.get(), relativePosition.x + 1, size.y - relativePosition.y));
            }
        }
    }

    private GuiElementBox findAssociatedGuiElementBoxAtPosition(Object object, Vector2d position) throws IllegalArgumentException {
        Optional<GuiElementBox> optionalGuiElementBox = guiElementBoxSets.get(position).stream().
                filter((GuiElementBox box) -> box.isAssociated(object)).findAny();
        if (optionalGuiElementBox.isEmpty())
            throw new IllegalArgumentException(String.format("cannot find associated GuiElementBox for %s at %s", object, position));
        return optionalGuiElementBox.get();
    }

    private void addGuiElementBoxAtPositionToSet(Vector2d position, GuiElementBox guiElementBox) {
        guiElementBoxSets.computeIfAbsent(position, pos -> new HashSet<>()).add(guiElementBox);
    }

    private void removeGuiElementBoxAtPositionFromSet(Vector2d position, GuiElementBox guiElementBox) {
        guiElementBoxSets.get(position).remove(guiElementBox);
        if (guiElementBoxSets.get(position).isEmpty())
            guiElementBoxSets.remove(position);
    }

    public void clearHighlighted() {
        synchronized (highlighted) {
            highlighted.forEach(guiElementBox -> guiElementBox.setHighlighted(false));
            highlighted.clear();
        }
    }

    public void setHighlighted(AbstractWorldMapElement element) {
        GuiElementBox box = findAssociatedGuiElementBoxAtPosition(element, element.getPosition());
        synchronized (highlighted) {
            box.setHighlighted(true);
            highlighted.add(box);
        }
    }

    public void highlightMatchingMapElements(Predicate<AbstractWorldMapElement> predicate) {
        map.getStreamOfAllObjects().
                filter(predicate).
                forEach(this::setHighlighted);

    }

    private boolean isInView(Vector2d position) {
        return drawBounds.contains(position);
    }

    @Override
    public void positionChanged(IObservable observable, Vector2d oldPosition, Vector2d newPosition)
            throws IllegalArgumentException {
        if (!guiElementBoxSets.containsKey(oldPosition))
            throw new IllegalArgumentException(String.format("cannot move object %s from %s", observable, oldPosition));

        GuiElementBox guiElementBoxMoved = findAssociatedGuiElementBoxAtPosition(observable, oldPosition);

        removeGuiElementBoxAtPositionFromSet(oldPosition, guiElementBoxMoved);
        addGuiElementBoxAtPositionToSet(newPosition, guiElementBoxMoved);

        updateTopGuiElementBoxAtPosition(oldPosition, guiElementBoxMoved);
        updateTopGuiElementBoxAtPosition(newPosition);
    }

    @Override
    public void elementRemoved(AbstractWorldMapElement element) {
        Vector2d position = element.getPosition();
        GuiElementBox associatedBox = findAssociatedGuiElementBoxAtPosition(element, position);
        removeGuiElementBoxAtPositionFromSet(position, associatedBox);
        updateTopGuiElementBoxAtPosition(position, associatedBox);
        associatedBox.dispose();
        synchronized (highlighted) {
            associatedBox.setHighlighted(false);
            highlighted.remove(associatedBox);
        }
        if (element instanceof IObservable observable)
            observable.removeObserver(this);
    }

    @Override
    public void elementAdded(AbstractWorldMapElement element) {
        Vector2d position = element.getPosition();
        GuiElementBox newBox = new GuiElementBox(element);
        newBox.addObserver(this);
        addGuiElementBoxAtPositionToSet(position, newBox);
        updateTopGuiElementBoxAtPosition(position);

        if (element instanceof IObservable observable)
            observable.addObserver(this);
    }

    public void dispose() {
        map.removeObserver(this);
        guiElementBoxSets.values().forEach((Set<GuiElementBox> set) ->
                set.forEach(GuiElementBox::dispose));
    }

    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.removeObserver(observer);
    }

    @Override
    public void mapObjectClicked(AbstractWorldMapElement element) {
        observers.notifyObservers(IMapObjectClickedObserver.class, observer -> observer.mapObjectClicked(element));
    }
}
