package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgEnergyLevelTrackerTest {

    @Test
    void getValue() {
        AbstractNonFlatWorldMap map = TestHelper.generateTestMap();
        AvgEnergyLevelTracker tracker = new AvgEnergyLevelTracker(map);
        assertEquals(0.0, tracker.getValue());
        new Animal(map, new Vector2d(0, 0), MapDirection.NORTH, new Genotype(), 5, 5, () -> 0);
        assertEquals(5.0, tracker.getValue());
        new Animal(map, new Vector2d(0, 0), MapDirection.NORTH, new Genotype(), 3, 5, () -> 0);
        assertEquals(4.0, tracker.getValue());
        new Animal(map, new Vector2d(0, 0), MapDirection.NORTH, new Genotype(), 7, 5, () -> 0);
        assertEquals(5.0, tracker.getValue());
    }
}