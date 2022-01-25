package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgAnimalLifespanTrackerTest {

    @Test
    void getValue() {
        AbstractNonFlatWorldMap map = TestHelper.generateTestMap();
        AvgAnimalLifespanTracker tracker = new AvgAnimalLifespanTracker(map);
        var ref = new Object() {
            int currentAge = 0;
        };
        assertEquals(0.0, tracker.getValue());
        Animal animal1 = new Animal(map, new Vector2d(0, 0), MapDirection.NORTH, new Genotype(), 5, 5, () -> ref.currentAge);
        assertEquals(0.0, tracker.getValue());
        ref.currentAge = 3;
        Animal animal2 = new Animal(map, new Vector2d(0, 0), MapDirection.NORTH, new Genotype(), 5, 5, () -> ref.currentAge);
        assertEquals(0.0, tracker.getValue());
        ref.currentAge = 5;
        animal1.addEnergy(-5);
        assertEquals(5.0, tracker.getValue());
        ref.currentAge = 7;
        animal2.addEnergy(-5);
        assertEquals(4.5, tracker.getValue());


    }
}