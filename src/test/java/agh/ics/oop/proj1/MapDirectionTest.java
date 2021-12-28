package agh.ics.oop.proj1;

import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

    @Test
    void next() {
        MapDirection md = MapDirection.EAST;
        md = md.next();
        assertEquals(MapDirection.SOUTHEAST, md);
        md = md.next();
        assertEquals(MapDirection.SOUTH, md);
        md = md.next();
        assertEquals(MapDirection.SOUTHWEST, md);
        md = md.next();
        assertEquals(MapDirection.WEST, md);
        md = md.next();
        assertEquals(MapDirection.NORTHWEST, md);
        md = md.next();
        assertEquals(MapDirection.NORTH, md);
        md = md.next();
        assertEquals(MapDirection.NORTHEAST, md);
        md = md.next();
        assertEquals(MapDirection.EAST, md);

        assertEquals(MapDirection.NORTHWEST, md.next(5));
    }

    @Test
    void previous() {
        MapDirection md = MapDirection.EAST;
        md = md.previous();
        assertEquals(MapDirection.NORTHEAST, md);
        md = md.previous();
        assertEquals(MapDirection.NORTH, md);
        md = md.previous();
        assertEquals(MapDirection.NORTHWEST, md);
        md = md.previous();
        assertEquals(MapDirection.WEST, md);
        md = md.previous();
        assertEquals(MapDirection.SOUTHWEST, md);
        md = md.previous();
        assertEquals(MapDirection.SOUTH, md);
        md = md.previous();
        assertEquals(MapDirection.SOUTHEAST, md);
        md = md.previous();
        assertEquals(MapDirection.EAST, md);
    }

}
