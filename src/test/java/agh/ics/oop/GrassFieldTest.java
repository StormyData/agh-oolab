package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void canMoveTo() {
        GrassField map=new GrassField(10);
        assertTrue(map.canMoveTo(new Vector2d(5,5)));
        assertTrue(map.canMoveTo(new Vector2d(3,3)));
        Animal animal =new Animal(map,new Vector2d(3,3),MapDirection.NORTH);
        assertFalse(map.canMoveTo(new Vector2d(3,3)));

    }

    @Test
    void isOccupied() {
        GrassField map=new GrassField(5);
        assertTrue(!map.isOccupied(new Vector2d(3,3)) || (map.isOccupied(new Vector2d(3,3)) && map.objectAt(new Vector2d(3,3)) instanceof Grass));
        Animal animal =new Animal(map,new Vector2d(3,3),MapDirection.NORTH);
        assertTrue(map.isOccupied(new Vector2d(3,3)));

    }

    @Test
    void objectAt() {
        GrassField map=new GrassField(5);
        assertTrue(map.objectAt(new Vector2d(5,5)) == null || map.objectAt(new Vector2d(5,5)) instanceof Grass);
        assertTrue(map.objectAt(new Vector2d(3,3)) == null || map.objectAt(new Vector2d(3,3)) instanceof Grass);
        Animal animal =new Animal(map,new Vector2d(3,3),MapDirection.NORTH);
        Animal animal2 =new Animal(map,new Vector2d(3,4),MapDirection.SOUTH);
        assertEquals(animal,map.objectAt(new Vector2d(3,3)));
        assertEquals(animal2,map.objectAt(new Vector2d(3,4)));

    }
}