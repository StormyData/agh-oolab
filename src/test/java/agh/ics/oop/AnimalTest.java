package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void testToString() {
        IWorldMap map= new RectangularMap(5,5);
        Animal animal = new Animal(map);
        assertEquals("N",animal.toString());
        animal=new Animal(map,new Vector2d(2,3),MapDirection.NORTH);
        assertEquals("N",animal.toString());
        animal=new Animal(map,new Vector2d(1,3),MapDirection.WEST);
        assertEquals("W",animal.toString());
    }

    @Test
    void move() {
        IWorldMap map= new RectangularMap(5,5);
        Animal animal = new Animal(map);
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

        animal = new Animal(map,new Vector2d(4,4),MapDirection.NORTH);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4,4),animal.getPosition());
        assertEquals(MapDirection.NORTH,animal.getFacing());
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4,4),animal.getPosition());
        assertEquals(MapDirection.EAST,animal.getFacing());


    }
}