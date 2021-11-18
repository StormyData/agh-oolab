package agh.ics.oop;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

abstract public class AbstractWorldMap implements IWorldMap,IPositionChangeObserver{
    protected final Map<Vector2d,AbstractWorldMapElement> mapElements= new LinkedHashMap<>();
    protected final MapVisualizer mapVisualizer=new MapVisualizer(this);

    @Override
    public boolean place(Animal animal) {
        if(!canMoveTo(animal.getPosition()))
            return false;
        mapElements.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        if(!isOccupied(position))
            return true;
        AbstractWorldMapElement mapElement = mapElements.get(position);
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
    abstract protected Vector2d getUpperBound();
    abstract protected Vector2d getLowerBound();
    @Override
    public String toString()
    {
        return mapVisualizer.draw(getLowerBound(),getUpperBound());
    }
    @Override
    public void positionChange(Vector2d oldPosition, Vector2d newPosition)
    {
        AbstractWorldMapElement mapElement = mapElements.get(oldPosition);
        if(Config.DEBUG)
            System.out.printf("received position change event object %s moved from %s to %s\n",mapElement,oldPosition,newPosition);
        mapElements.remove(oldPosition);
        mapElements.put(newPosition,mapElement);
    }
}
