package agh.ics.oop;

import java.util.LinkedList;

abstract public class AbstractWorldMap implements IWorldMap{
    protected final LinkedList<AbstractWorldMapElement> mapElements = new LinkedList<>();
    protected final MapVisualizer mapVisualizer=new MapVisualizer(this);

    @Override
    public boolean place(Animal animal) {
        if(!canMoveTo(animal.getPosition()))
            return false;
        mapElements.add(animal);
        return true;
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        for(AbstractWorldMapElement mapElement : mapElements)
            if (position.equals(mapElement.getPosition())&& mapElement.getCollision())
                return false;
        return true;
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        for(AbstractWorldMapElement mapElement : mapElements)
            if (position.equals(mapElement.getPosition()))
                return true;
        return false;
    }
    @Override
    public Object objectAt(Vector2d position) {
        for(AbstractWorldMapElement mapElement : mapElements)
            if (mapElement.getPosition().equals(position))
                return mapElement;
        return null;
    }
    abstract protected Vector2d getUpperBound();
    abstract protected Vector2d getLowerBound();
    @Override
    public String toString()
    {
        return mapVisualizer.draw(getLowerBound(),getUpperBound());
    }
}
