package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class Animal extends AbstractWorldMapElement implements IPositionChangedObservable {
    private MapDirection facing = MapDirection.NORTH;
    private List<IPositionChangeObserver> positionChangeObservers = new LinkedList<>();
    Animal(IWorldMap map)
    {
        super(new Vector2d(2,2),map);
        if(map.objectAt(position) instanceof Grass grass)
        {
            grass.eat();
        }
        //TODO: Throw if cannot place
        this.map.place(this);

    }

    Animal(IWorldMap map, Vector2d pos,MapDirection facing)
    {
        super(pos,map);
        this.facing=facing;
        this.map=map;
        this.map.place(this);
    }
    @Override
    public String toString() {
        return switch (facing)
                {
                    case SOUTH -> "S";
                    case WEST -> "W";
                    case EAST -> "E";
                    case NORTH -> "N";
                };
    }
    public void move(MoveDirection direction)
    {
        Vector2d newPos = position;
        switch (direction) {
            case FORWARD -> newPos = position.add(facing.toUnitVector());
            case BACKWARD -> newPos = position.add(facing.toUnitVector().opposite());
            case RIGHT -> facing = facing.next();
            case LEFT -> facing = facing.previous();
        }
        if(map.canMoveTo(newPos))
        {
            Object object=map.objectAt(newPos);
            if(object instanceof Grass grass)
            {
                grass.eat();
            }
            if(!position.equals(newPos))
                onPositionChange(position,newPos);
            position=newPos;
        }


    }
    MapDirection getFacing()
    {
        return facing;
    }

    @Override
    public boolean getCollision()
    {
        return true;
    }

    private void onPositionChange(Vector2d oldPos, Vector2d newPos)
    {
        for(IPositionChangeObserver positionChangeObserver : positionChangeObservers)
            positionChangeObserver.positionChange(oldPos,newPos);
    }
    @Override
    public void addObserver(IPositionChangeObserver observer) {
        positionChangeObservers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
        positionChangeObservers.remove(observer);
    }
}
