package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void canMoveTo() {
        RectangularMap map=new RectangularMap(5,5);
        assertFalse(map.canMoveTo(new Vector2d(5,5)));
        assertTrue(map.canMoveTo(new Vector2d(3,3)));
        Animal animal =new Animal(map,new Vector2d(3,3),MapDirection.NORTH);
        assertFalse(map.canMoveTo(new Vector2d(3,3)));

    }

    @Test
    void isOccupied() {
        RectangularMap map=new RectangularMap(5,5);
        assertTrue(map.isOccupied(new Vector2d(5,5)));
        assertFalse(map.isOccupied(new Vector2d(3,3)));
        Animal animal =new Animal(map,new Vector2d(3,3),MapDirection.NORTH);
        assertTrue(map.isOccupied(new Vector2d(3,3)));

    }

    @Test
    void objectAt() {
        RectangularMap map=new RectangularMap(5,5);
        assertNull(map.objectAt(new Vector2d(5,5)));
        assertNull(map.objectAt(new Vector2d(3,3)));
        Animal animal =new Animal(map,new Vector2d(3,3),MapDirection.NORTH);
        Animal animal2 =new Animal(map,new Vector2d(3,4),MapDirection.SOUTH);
        assertEquals(animal,map.objectAt(new Vector2d(3,3)));
        assertEquals(animal2,map.objectAt(new Vector2d(3,4)));

    }
}