package agh.ics.oop;

import java.util.LinkedList;

public class RectangularMap implements IWorldMap{
    private LinkedList<Animal> animals = new LinkedList<>();
    private final Vector2d lowerLeft = new Vector2d(0,0);
    private final Vector2d upperRight;

    public RectangularMap(int width, int height) {
        upperRight = new Vector2d(width -1,height -1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if(isOccupied(animal.getPosition()))
            return false;
        animals.add(animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if(!(position.follows(lowerLeft)&&position.precedes(upperRight)))
            return true;
        for(Animal animal : animals)
        {
            if(animal.getPosition().equals(position))
                return true;
        }

        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(Animal animal : animals)
        {
            if(animal.getPosition().equals(position))
                return animal;
        }
        return null;
    }
    @Override
    public String toString()
    {
        return new MapVisualizer(this).draw(lowerLeft,upperRight);
    }
}
