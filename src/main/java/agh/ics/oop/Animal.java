package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class Animal extends BaseWorldMapElement implements IPositionChangedObservable {
    private MapDirection facing = MapDirection.NORTH;
    private List<IPositionChangeObserver> positionChangeObservers = new LinkedList<>();
    Animal(IWorldMap map)
    {
        this(map,new Vector2d(2,2),MapDirection.NORTH);
    }

    public Animal(IWorldMap map, Vector2d pos, MapDirection facing)
    {
        super(pos,map);
        if(map.objectAt(position) instanceof Grass grass)
        {
            grass.eat();
        }
        this.facing=facing;
        this.map.place(this);
        //TODO: Throw if cannot place
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
            Vector2d oldPos= position;
            position=newPos;
            if(!position.equals(oldPos))
                onPositionChanged(oldPos,position);

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

    private void onPositionChanged(Vector2d oldPos, Vector2d newPos)
    {
        for(IPositionChangeObserver positionChangeObserver : positionChangeObservers)
            positionChangeObserver.positionChanged(oldPos,newPos);
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
