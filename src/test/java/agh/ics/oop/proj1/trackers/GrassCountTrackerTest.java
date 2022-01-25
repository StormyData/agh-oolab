package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.Grass;
import agh.ics.oop.proj1.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrassCountTrackerTest {

    @Test
    void getValue() {
        AbstractNonFlatWorldMap map = TestHelper.generateTestMap();
        GrassCountTracker tracker = new GrassCountTracker(map);
        assertEquals(0L, tracker.getValue());
        Grass grass1 = new Grass(new Vector2d(0, 0), map);
        assertEquals(1L, tracker.getValue());
        new Grass(new Vector2d(0, 0), map);
        new Grass(new Vector2d(0, 0), map);
        assertEquals(3L, tracker.getValue());
        map.remove(grass1);
        assertEquals(2L, tracker.getValue());
    }
}