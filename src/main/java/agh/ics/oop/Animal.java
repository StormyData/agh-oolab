package agh.ics.oop;

public class Animal extends AbstractWorldMapElement {
    private MapDirection facing = MapDirection.NORTH;
    Animal(IWorldMap map)
    {
        super(new Vector2d(2,2),map);
        if(map.objectAt(position) instanceof Grass grass)
        {
            grass.eat();
        }
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
}
