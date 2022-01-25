package agh.ics.oop.proj1;

public abstract class AbstractWorldMapElement {
    protected final AbstractNonFlatWorldMap map;
    protected Vector2d position;

    AbstractWorldMapElement(Vector2d position, AbstractNonFlatWorldMap map) {
        this.position = map.transformCoordinates(position);
        this.map = map;
    }

    public Vector2d getPosition() {
        return position;
    }


    public abstract String getDisplayName();
}
