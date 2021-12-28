package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.Animal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgAnimalChildrenCountTrackerTest {

    @Test
    void getValue() {
        AbstractNonFlatWorldMap map = TestHelper.generateTestMap();
        AvgAnimalChildrenCountTracker tracker = new AvgAnimalChildrenCountTracker(map);
        assertEquals(0, tracker.getValue());
        Animal animal1 = TestHelper.generateRandomAnimal(map);
        assertEquals(0, tracker.getValue());
        Animal animal2 = TestHelper.generateRandomAnimal(map);
        assertEquals(0, tracker.getValue());
        TestHelper.generateRandomAnimal(map);
        assertEquals(0, tracker.getValue());
        animal1.reproduce(animal2);
        assertEquals(0.5, tracker.getValue());
    }
}