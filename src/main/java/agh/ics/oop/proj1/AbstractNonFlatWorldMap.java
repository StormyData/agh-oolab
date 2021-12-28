package agh.ics.oop.proj1;

import agh.ics.oop.proj1.observers.*;

import java.util.*;
import java.util.stream.Stream;

public abstract class AbstractNonFlatWorldMap implements IPositionChangedObserver, IObservable {
    protected final Map<Vector2d, Set<AbstractWorldMapElement>> mapElements = new LinkedHashMap<>();
    protected final ObserverHolder observers = new ObserverHolder(
            IWorldMapElementAddedObserver.class,
            IWorldMapElementRemovedObserver.class
    );


    public void add(AbstractWorldMapElement mapElement) throws IllegalArgumentException {
        Vector2d pos = mapElement.getPosition();
        if (!canMoveTo(pos))
            throw new IllegalArgumentException(String.format("cannot add object %s at %s", mapElement, pos));
        add(pos, mapElement);
        if (mapElement instanceof IObservable observable)
            observable.addObserver(this);
        onWorldMapElementAdded(mapElement);
    }

    public void remove(AbstractWorldMapElement mapElement) throws IllegalArgumentException {
        Vector2d pos = mapElement.getPosition();
        if (!mapElements.containsKey(pos) || !mapElements.get(pos).contains(mapElement))
            throw new IllegalArgumentException(String.format("Object %s not in map", mapElement));
        remove(pos, mapElement);
        if (mapElement instanceof IObservable observable)
            observable.removeObserver(this);
        onWorldMapElementRemoved(mapElement);
    }

    public Vector2d transformCoordinates(Vector2d intended) {
        return intended;
    }

    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    public boolean isOccupied(Vector2d position) {
        return mapElements.containsKey(position);
    }

    public Set<AbstractWorldMapElement> objectsAt(Vector2d position) {
        return Collections.unmodifiableSet(mapElements.get(position));
    }

    public abstract AxisAlignedRectangle getMapBounds();


    @Override
    public void positionChanged(IObservable observable, Vector2d oldPosition, Vector2d newPosition)
            throws IllegalArgumentException {
        if (!(observable instanceof AbstractWorldMapElement))
            throw new IllegalArgumentException(
                    String.format("position change observable %s for map must be an instance of AbstractWorldMapElement",
                            observable));
        remove(oldPosition, (AbstractWorldMapElement) observable);
        add(newPosition, (AbstractWorldMapElement) observable);
    }

    protected void add(Vector2d pos, AbstractWorldMapElement mapElement) {
        mapElements.computeIfAbsent(pos, k -> new HashSet<>()).add(mapElement);
    }

    protected void remove(Vector2d pos, AbstractWorldMapElement mapElement) {
        mapElements.get(pos).remove(mapElement);
        if (mapElements.get(pos).isEmpty())
            mapElements.remove(pos);
    }

    public Vector2d[] getOccupiedPositions() {
        return mapElements.keySet().toArray(new Vector2d[0]);
    }


    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.removeObserver(observer);
    }


    protected void onWorldMapElementAdded(AbstractWorldMapElement element) {
        observers.notifyObservers(IWorldMapElementAddedObserver.class, observer -> observer.elementAdded(element));
    }

    protected void onWorldMapElementRemoved(AbstractWorldMapElement element) {
        observers.notifyObservers(IWorldMapElementRemovedObserver.class, observer -> observer.elementRemoved(element));
    }

    public <T> Stream<T> getStreamOfAllObjectsOfClass(Class<T> type) {
        return Util.filterInstancesAndCastStream(getStreamOfAllObjects(), type);
    }

    public <T> Stream<T> getStreamOfAllObjectsOfClassAtPosition(Class<T> type, Vector2d pos) {
        return Util.filterInstancesAndCastStream(mapElements.get(pos).stream(), type);
    }

    public Stream<AbstractWorldMapElement> getStreamOfAllObjects() {
        return mapElements.values().stream().flatMap(Collection::stream);
    }
}
