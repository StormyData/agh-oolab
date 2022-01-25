package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.Animal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalSuccessorCountTrackerTest {

    @Test
    void getValue() {
        AbstractNonFlatWorldMap map = TestHelper.generateTestMap();
        Animal trackedAnimal = TestHelper.generateRandomAnimal(map);
        AnimalSuccessorCountTracker tracker = new AnimalSuccessorCountTracker(trackedAnimal, map);
        Animal animal1 = TestHelper.generateRandomAnimal(map);
        assertEquals(0L, tracker.getValue());
        Animal.reproduce(trackedAnimal,animal1);
        assertEquals(1L, tracker.getValue());
        map.getStreamOfAllObjectsOfClass(Animal.class).
                filter(animal -> animal != animal1 && animal != trackedAnimal).findAny().
                ifPresent(animal -> Animal.reproduce(animal,animal1));
        assertEquals(2L, tracker.getValue());
    }
}