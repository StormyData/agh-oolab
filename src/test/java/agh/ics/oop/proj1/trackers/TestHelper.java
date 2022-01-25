package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.*;

import java.util.Random;

public class TestHelper {
    private static Random random = new Random();
    public static Animal generateRandomAnimal(AbstractNonFlatWorldMap map) {
        return generateRandomAnimal(map, new Vector2d(0, 0));
    }

    public static Animal generateRandomAnimal(AbstractNonFlatWorldMap map, Vector2d pos) {
        return new Animal(map, pos, MapDirection.randomDirection(random), new Genotype(), 3, 3, () -> 0);
    }

    static RectangularWorldMap generateTestMap() {
        return new RectangularWorldMap(new AxisAlignedRectangle(new Vector2d(0, 0), new Vector2d(5, 5)));
    }
}
