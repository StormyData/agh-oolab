package agh.ics.oop;

public class Animal {
    private Vector2d position = new Vector2d(2,2);
    private MapDirection facing = MapDirection.NORTH;
    private IWorldMap map;
    Animal(IWorldMap map)
    {
        this.map=map;
        this.map.place(this);
    }

    Animal(IWorldMap map, Vector2d pos,MapDirection facing)
    {
        this.position=pos;
        this.facing=facing;
        this.map=map;
        this.map.place(this);
    }

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
        switch (direction)
        {
            case FORWARD:
                newPos=position.add(facing.toUnitVector());
                break;
            case BACKWARD:
                newPos=position.add(facing.toUnitVector().opposite());
                break;
            case RIGHT:
                facing=facing.next();
                break;
            case LEFT:
                facing=facing.previous();
                break;
        }
        if(map.canMoveTo(newPos))
            position=newPos;

    }
    Vector2d getPosition()
    {
        return position;
    }
    MapDirection getFacing()
    {
        return facing;
    }
}
