package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void testToString() {
        Animal animal = new Animal();
        assertEquals("Animal at (2,2) facing Północ",animal.toString());
        animal=new Animal(new Vector2d(2,3),MapDirection.NORTH);
        assertEquals("Animal at (2,3) facing Północ",animal.toString());
        animal=new Animal(new Vector2d(1,3),MapDirection.WEST);
        assertEquals("Animal at (1,3) facing Zachód",animal.toString());
    }

    @Test
    void move() {
        Animal animal = new Animal();
        animal.move(MoveDirection.LEFT);
        assertEquals(MapDirection.WEST,animal.getFacing());
        assertEquals(new Vector2d(2,2),animal.getPosition());

        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(1,2),animal.getPosition());
        assertEquals(MapDirection.WEST,animal.getFacing());
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0,2),animal.getPosition());
        assertEquals(MapDirection.WEST,animal.getFacing());
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0,2),animal.getPosition());
        assertEquals(MapDirection.WEST,animal.getFacing());

        animal.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(0,2),animal.getPosition());
        assertEquals(MapDirection.NORTH,animal.getFacing());

        animal.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(0,2),animal.getPosition());
        assertEquals(MapDirection.EAST,animal.getFacing());

        animal.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(0,2),animal.getPosition());
        assertEquals(MapDirection.SOUTH,animal.getFacing());

        animal = new Animal(new Vector2d(4,4),MapDirection.NORTH);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4,4),animal.getPosition());
        assertEquals(MapDirection.NORTH,animal.getFacing());
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4,4),animal.getPosition());
        assertEquals(MapDirection.EAST,animal.getFacing());


    }
}