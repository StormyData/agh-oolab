package agh.ics.oop.proj1;

import java.util.Random;
import java.util.ResourceBundle;



public enum MapDirection {
    NORTH("mapDirection.NORTH", new Vector2d(0, 1), "\uD83E\uDC63"),
    NORTHEAST("mapDirection.NORTHEAST", new Vector2d(1, 1), "\uD83E\uDC60"),
    EAST("mapDirection.EAST", new Vector2d(1, 0), "\uD83E\uDC65"),
    SOUTHEAST("mapDirection.SOUTHEAST", new Vector2d(1, -1), "\uD83E\uDC62"),
    SOUTH("mapDirection.SOUTH", new Vector2d(0, -1), "\uD83E\uDC61"),
    SOUTHWEST("mapDirection.SOUTHWEST", new Vector2d(-1, -1), "\uD83E\uDC66"),
    WEST("mapDirection.WEST", new Vector2d(-1, 0), "\uD83E\uDC67"),
    NORTHWEST("mapDirection.NORTHWEST", new Vector2d(-1, 1), "\uD83E\uDC64");
    private final String name;
    private final Vector2d unitVector;
    private final String abbreviatedString;

    MapDirection(String bundleKey, Vector2d unitVector, String abbreviatedString) {
        name = ResourceBundle.getBundle("strings").getString(bundleKey);
        this.unitVector = unitVector;
        this.abbreviatedString = abbreviatedString;
    }

    public static MapDirection randomDirection(Random random) {
        return values()[random.nextInt(values().length)];
    }

    public String toString() {
        return name;
    }

    public MapDirection next() {
        return next(1);
    }

    public MapDirection next(int i) {
        return values()[(ordinal() + i + values().length) % values().length];
    }

    public MapDirection previous() {
        return previous(1);
    }

    public MapDirection previous(int i) {
        return next(-i);
    }

    public Vector2d toUnitVector() {
        return unitVector;
    }

    public String toAbbreviatedString() {
        return abbreviatedString;
    }
}
