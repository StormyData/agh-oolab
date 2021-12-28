package agh.ics.oop.proj1;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AxisAlignedRectangleTest {

    @Test
    void isContaining() {
        AxisAlignedRectangle rectangle = new AxisAlignedRectangle(new Vector2d(-5, -5), new Vector2d(5, 5));
        Vector2d tested1 = new Vector2d(1, 1);
        assertTrue(rectangle.contains(tested1));

        Vector2d tested2 = new Vector2d(5, 5);
        assertTrue(rectangle.contains(tested2));

        Vector2d tested3 = new Vector2d(7, 7);
        assertFalse(rectangle.contains(tested3));
    }

    @Test
    void getRandomVectorInside() {
        AxisAlignedRectangle rectangle = new AxisAlignedRectangle(new Vector2d(-5, -5), new Vector2d(5, 5));

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Vector2d pos = rectangle.getRandomVectorInside(random);
            assertTrue(rectangle.contains(pos));
        }

    }

    @Test
    void getArea() {
        AxisAlignedRectangle rectangle = new AxisAlignedRectangle(new Vector2d(-5, -5), new Vector2d(5, 5));
        assertEquals(121, rectangle.getArea());
    }


    @Test
    void getSize() {
        AxisAlignedRectangle rectangle = new AxisAlignedRectangle(new Vector2d(-5, -5), new Vector2d(5, 5));
        assertEquals(new Vector2d(11, 11), rectangle.getSize());
    }

    @Test
    void getCenter() {
        AxisAlignedRectangle rectangle = new AxisAlignedRectangle(new Vector2d(-5, -5), new Vector2d(5, 5));
        assertEquals(new Vector2d(0, 0), rectangle.getCenter());
    }
}