package agh.ics.oop;

public class AbstractWorldMapElement {
    protected Vector2d position;
    protected IWorldMap map;
    AbstractWorldMapElement(Vector2d position,IWorldMap map)
    {
        this.position=position;
        this.map = map;
    }
    public Vector2d getPosition() {
        return position;
    }
    public boolean getCollision()
    {
        return false;
    }
}
