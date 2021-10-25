package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testToString() {
        Vector2d position1 = new Vector2d(1, 2);
        assertEquals("(1,2)",position1.toString());
        Vector2d position2 = new Vector2d(-2, 1);
        assertEquals("(-2,1)",position2.toString());
    }

    @Test
    void precedes() {
        Vector2d v1= new Vector2d(1,1);
        Vector2d v2= new Vector2d(2,2);
        Vector2d v3= new Vector2d(2,3);
        assertTrue(v1.precedes(v1));
        assertTrue(v2.precedes(v2));
        assertTrue(v3.precedes(v3));

        assertTrue(v1.precedes(v2));
        assertTrue(v1.precedes(v3));
        assertTrue(v2.precedes(v3));

        assertFalse(v2.precedes(v1));
        assertFalse(v3.precedes(v1));
        assertFalse(v3.precedes(v2));
    }

    @Test
    void follows() {

        Vector2d v1= new Vector2d(1,1);
        Vector2d v2= new Vector2d(2,2);
        Vector2d v3= new Vector2d(2,3);
        assertTrue(v2.follows(v2));
        assertTrue(v1.follows(v1));
        assertTrue(v3.follows(v3));

        assertFalse(v1.follows(v2));
        assertFalse(v1.follows(v3));
        assertFalse(v2.follows(v3));

        assertTrue(v2.follows(v1));
        assertTrue(v3.follows(v1));
        assertTrue(v3.follows(v2));
    }
    @Test
    void lowerLeft()
    {
        Vector2d v1= new Vector2d(2,1);
        Vector2d v2= new Vector2d(1,2);
        assertEquals(new Vector2d(1,1),v1.lowerLeft(v2));
        assertEquals(new Vector2d(1,1),v2.lowerLeft(v1));
    }
    @Test
    void upperRight() {
        Vector2d v1= new Vector2d(2,1);
        Vector2d v2= new Vector2d(1,2);
        assertEquals(new Vector2d(2,2),v1.upperRight(v2));
        assertEquals(new Vector2d(2,2),v2.upperRight(v1));
    }

    @Test
    void add() {
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(-2,1);
        assertEquals(new Vector2d(-1,3),position1.add(position2));
        assertEquals(new Vector2d(-1,3),position2.add(position1));
    }

    @Test
    void subtract() {
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(-2,1);
        assertEquals(new Vector2d(3,1),position1.subtract(position2));
        assertEquals(new Vector2d(-3,-1),position2.subtract(position1));
    }

    @Test
    void opposite() {
        Vector2d position1 = new Vector2d(1,2);
        assertEquals(new Vector2d(-1,-2),position1.opposite());
    }

    @Test
    void testEquals() {
        Vector2d v1= new Vector2d(1,1);
        Vector2d v2= new Vector2d(2,2);
        Vector2d v3= new Vector2d(2,2);
        assertFalse(v1.equals(v2));
        assertFalse(v2.equals(v1));

        assertTrue(v2.equals(v3));
        assertTrue(v3.equals(v2));

        assertFalse(v3.equals(v1));
        assertFalse(v1.equals(v3));

    }
}