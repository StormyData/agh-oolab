package agh.ics.oop.proj1;

import java.util.Random;
import java.util.ResourceBundle;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public static MapDirection randomDirection(Random random) {
        return values()[random.nextInt(values().length)];
    }

    public String toString() {
        return ResourceBundle.getBundle("strings").getString(
                switch (this) {
                    case NORTH -> "mapDirection.NORTH";
                    case NORTHEAST -> "mapDirection.NORTHEAST";
                    case EAST -> "mapDirection.EAST";
                    case SOUTHEAST -> "mapDirection.SOUTHEAST";
                    case SOUTH -> "mapDirection.SOUTH";
                    case SOUTHWEST -> "mapDirection.SOUTHWEST";
                    case WEST -> "mapDirection.WEST";
                    case NORTHWEST -> "mapDirection.NORTHWEST";
                });
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
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);

        };
    }

    public String toAbbreviatedString() {
        return switch (this) {
            case SOUTH -> "\uD83E\uDC63";
            case WEST -> "\uD83E\uDC60";
            case NORTHEAST -> "\uD83E\uDC65";
            case EAST -> "\uD83E\uDC62";
            case NORTH -> "\uD83E\uDC61";
            case SOUTHEAST -> "\uD83E\uDC66";
            case SOUTHWEST -> "\uD83E\uDC67";
            case NORTHWEST -> "\uD83E\uDC64";
        };
    }
}
