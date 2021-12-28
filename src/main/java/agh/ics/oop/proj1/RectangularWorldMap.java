package agh.ics.oop.proj1;

public class RectangularWorldMap extends AbstractNonFlatWorldMap {
    private final AxisAlignedRectangle bounds;

    public RectangularWorldMap(AxisAlignedRectangle bounds) {
        this.bounds = bounds;
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!bounds.contains(position))
            return false;
        return super.canMoveTo(position);
    }


    @Override
    public AxisAlignedRectangle getMapBounds() {
        return bounds;
    }
}
