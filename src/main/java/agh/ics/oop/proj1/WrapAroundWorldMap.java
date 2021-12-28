package agh.ics.oop.proj1;

public class WrapAroundWorldMap extends AbstractNonFlatWorldMap {
    final AxisAlignedRectangle mapBounds;

    public WrapAroundWorldMap(AxisAlignedRectangle mapBounds) {
        this.mapBounds = mapBounds;
    }

    @Override
    public Vector2d transformCoordinates(Vector2d intended) {
        if (mapBounds.contains(intended))
            return intended;
        int x = intended.x;
        while (x < mapBounds.lowerLeft.x)
            x += mapBounds.getSize().x;
        while (x > mapBounds.upperRight.x)
            x -= mapBounds.getSize().x;

        int y = intended.y;
        while (y < mapBounds.lowerLeft.y)
            y += mapBounds.getSize().y;
        while (y > mapBounds.upperRight.y)
            y -= mapBounds.getSize().y;

        return new Vector2d(x, y);
    }

    @Override
    public AxisAlignedRectangle getMapBounds() {
        return mapBounds;
    }
}
