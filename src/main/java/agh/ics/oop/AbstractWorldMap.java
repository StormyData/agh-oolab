package agh.ics.oop;

import java.util.LinkedHashMap;
import java.util.Map;

abstract public class AbstractWorldMap implements IWorldMap,IPositionChangeObserver{
    protected final Map<Vector2d, BaseWorldMapElement> mapElements= new LinkedHashMap<>();
    protected final MapVisualizer mapVisualizer=new MapVisualizer(this);

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if(!canMoveTo(animal.getPosition()))
            throw new IllegalArgumentException(String.format("cannot place animal at %s", animal.getPosition()));
        mapElements.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        if(!isOccupied(position))
            return true;
        BaseWorldMapElement mapElement = mapElements.get(position);
        if(mapElement == null)
            return true;
        return !mapElement.getCollision();
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        return mapElements.containsKey(position);
    }
    @Override
    public Object objectAt(Vector2d position) {
        return mapElements.get(position);
    }
    abstract public Vector2d getUpperBound();
    abstract public Vector2d getLowerBound();
    @Override
    public String toString()
    {
        return mapVisualizer.draw(getLowerBound(),getUpperBound());
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        BaseWorldMapElement mapElement = mapElements.get(oldPosition);
        if(Config.DEBUG)
            System.out.printf("received position changed event object %s moved from %s to %s\n",mapElement,oldPosition,newPosition);
        mapElements.remove(oldPosition);
        mapElements.put(newPosition,mapElement);
    }
}
