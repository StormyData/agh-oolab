package agh.ics.oop;


public class RectangularMap extends AbstractWorldMap{
    private final Vector2d lowerLeft = new Vector2d(0,0);
    private final Vector2d upperRight;

    public RectangularMap(int width, int height) {
        upperRight = new Vector2d(width -1,height -1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if(!super.canMoveTo(position))
            return false;
        return position.follows(lowerLeft)&&position.precedes(upperRight);
    }

    @Override
    public Vector2d getUpperBound() {
        return upperRight;
    }

    @Override
    public Vector2d getLowerBound() {
        return lowerLeft;
    }

}
