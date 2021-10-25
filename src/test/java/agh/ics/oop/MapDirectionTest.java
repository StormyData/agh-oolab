package agh.ics.oop;

import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {

    @Test
    void next() {
        MapDirection md=MapDirection.EAST;
        md=md.next();
        assertEquals(md,MapDirection.SOUTH);
        md=md.next();
        assertEquals(md,MapDirection.WEST);
        md=md.next();
        assertEquals(md,MapDirection.NORTH);
        md=md.next();
        assertEquals(md,MapDirection.EAST);
    }

    @Test
    void previous() {
        MapDirection md=MapDirection.EAST;
        md=md.previous();
        assertEquals(md,MapDirection.NORTH);
        md=md.previous();
        assertEquals(md,MapDirection.WEST);
        md=md.previous();
        assertEquals(md,MapDirection.SOUTH);
        md=md.previous();
        assertEquals(md,MapDirection.EAST);
    }

}
