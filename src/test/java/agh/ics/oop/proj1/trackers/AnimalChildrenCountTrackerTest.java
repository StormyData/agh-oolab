package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.Animal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalChildrenCountTrackerTest {


    @Test
    void getValue() {
        AbstractNonFlatWorldMap map = TestHelper.generateTestMap();
        Animal animal1 = TestHelper.generateRandomAnimal(map);
        Animal animal2 = TestHelper.generateRandomAnimal(map);
        AnimalChildrenCountTracker tracker = new AnimalChildrenCountTracker(animal1);
        assertEquals(0, tracker.getChildrenCount());
        animal1.reproduce(animal2);
        assertEquals(1, tracker.getChildrenCount());
        animal1.addEnergy(-3);
        assertEquals(1, tracker.getChildrenCount());
    }

}