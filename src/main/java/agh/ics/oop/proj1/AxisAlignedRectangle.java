package agh.ics.oop.proj1;

import java.util.Random;


public class AxisAlignedRectangle {
    public final Vector2d lowerLeft;
    public final Vector2d upperRight;

    public AxisAlignedRectangle(Vector2d lowerLeft, Vector2d upperRight) {
        if (!lowerLeft.precedes(upperRight))
            throw new IllegalArgumentException(String.format("cannot construct a rectangle with negative size from corners %s and %s", lowerLeft, upperRight));
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public Vector2d getRandomVectorInside(Random random) {
        int minX = lowerLeft.x;
        int minY = lowerLeft.y;
        int maxX = upperRight.x;
        int maxY = upperRight.y;
        int x = random.nextInt(maxX - minX + 1) + minX;
        int y = random.nextInt(maxY - minY + 1) + minY;
        return new Vector2d(x, y);
    }

    public int getArea() {
        return ((upperRight.x + 1 - lowerLeft.x) * (upperRight.y + 1 - lowerLeft.y));
    }

    public boolean contains(Vector2d vector) {
        return upperRight.follows(vector) && lowerLeft.precedes(vector);
    }

    public Vector2d getSize() {
        return upperRight.subtract(lowerLeft).add(new Vector2d(1, 1));
    }

    public Vector2d getCenter() {
        return upperRight.add(lowerLeft).multiply(0.5);
    }

}
