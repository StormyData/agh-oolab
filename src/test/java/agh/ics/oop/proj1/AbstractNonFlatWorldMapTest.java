package agh.ics.oop.proj1;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AbstractNonFlatWorldMapTest {

    @Test
    void addAndRemoveTest() {
        AbstractNonFlatWorldMap map = new AbstractNonFlatWorldMap() {
            @Override
            public AxisAlignedRectangle getMapBounds() {
                return new AxisAlignedRectangle(new Vector2d(0, 0), new Vector2d(5, 5));
            }
        };

        Grass grass1 = new Grass(new Vector2d(0, 0), map);
        Grass grass2 = new Grass(new Vector2d(1, 0), map);
        assertTrue(map.objectsAt(new Vector2d(0, 0)).contains(grass1));
        assertTrue(map.objectsAt(new Vector2d(1, 0)).contains(grass2));

        assertFalse(map.objectsAt(new Vector2d(1, 0)).contains(grass1));
        assertFalse(map.objectsAt(new Vector2d(0, 0)).contains(grass2));
    }

    @Test
    void isOccupied() {
        AbstractNonFlatWorldMap map = new AbstractNonFlatWorldMap() {
            @Override
            public AxisAlignedRectangle getMapBounds() {
                return new AxisAlignedRectangle(new Vector2d(0, 0), new Vector2d(5, 5));
            }
        };

       new Grass(new Vector2d(0, 0), map);
        assertTrue(map.isOccupied(new Vector2d(0, 0)));
        assertFalse(map.isOccupied(new Vector2d(1, 1)));
    }

    @Test
    void objectsAt() {
        AbstractNonFlatWorldMap map = new AbstractNonFlatWorldMap() {
            @Override
            public AxisAlignedRectangle getMapBounds() {
                return new AxisAlignedRectangle(new Vector2d(0, 0), new Vector2d(5, 5));
            }
        };

        Grass grass1 = new Grass(new Vector2d(0, 0), map);
        Grass grass2 = new Grass(new Vector2d(0, 0), map);
        Set<AbstractWorldMapElement> set = Set.of(grass1, grass2);
        assertEquals(set, map.objectsAt(new Vector2d(0, 0)));
    }

    @Test
    void getOccupiedPositions() {
        AbstractNonFlatWorldMap map = new AbstractNonFlatWorldMap() {
            @Override
            public AxisAlignedRectangle getMapBounds() {
                return new AxisAlignedRectangle(new Vector2d(0, 0), new Vector2d(5, 5));
            }
        };

        new Grass(new Vector2d(0, 0), map);
        new Grass(new Vector2d(1, 0), map);
        Vector2d[] array = map.getOccupiedPositions();
        Set<Vector2d> vector2dSet = new HashSet<>(List.of(array));
        Set<Vector2d> expected = Set.of(new Vector2d(0, 0), new Vector2d(1, 0));
        assertEquals(expected, vector2dSet);
        assertEquals(2, array.length);
    }
}