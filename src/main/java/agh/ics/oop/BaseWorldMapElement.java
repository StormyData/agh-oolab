package agh.ics.oop;

public class BaseWorldMapElement {
    protected Vector2d position;
    protected IWorldMap map;
    BaseWorldMapElement(Vector2d position, IWorldMap map)
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
