package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.Animal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalCountTrackerTest {

    @Test
    void getValue() {
        AbstractNonFlatWorldMap map = TestHelper.generateTestMap();
        AnimalCountTracker tracker = new AnimalCountTracker(map);
        assertEquals(0, tracker.getValue());
        Animal animal = TestHelper.generateRandomAnimal(map);
        assertEquals(1, tracker.getValue());
        TestHelper.generateRandomAnimal(map);
        TestHelper.generateRandomAnimal(map);
        assertEquals(3, tracker.getValue());
        map.remove(animal);

    }
}