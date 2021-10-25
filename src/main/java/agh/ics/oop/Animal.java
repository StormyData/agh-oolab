package agh.ics.oop;

public class Animal {
    private Vector2d position = new Vector2d(2,2);
    private MapDirection facing = MapDirection.NORTH;
    Animal()
    {

    }

    Animal(Vector2d pos,MapDirection facing)
    {
        this.position=pos;
        this.facing=facing;
    }

    public String toString() {
        return String.format("Animal at %s facing %s",position,facing);
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
        if(newPos.follows(new Vector2d(0,0)) && newPos.precedes(new Vector2d(4,4)))
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
