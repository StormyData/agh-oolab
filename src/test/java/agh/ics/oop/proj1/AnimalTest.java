package agh.ics.oop.proj1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalTest {

    @Test
    void move() {
        AxisAlignedRectangle bounds = new AxisAlignedRectangle(new Vector2d(0, 0), new Vector2d(5, 5));
        AbstractNonFlatWorldMap map = new RectangularWorldMap(bounds);
        Animal animal = new Animal(map, new Vector2d(2, 2), MapDirection.NORTH, new Genotype(), 10, 3, () -> 0);
        animal.move(MoveDirection.LEFT);
        assertEquals(MapDirection.WEST, animal.getFacing());
        assertEquals(new Vector2d(2, 2), animal.getPosition());

        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(1, 2), animal.getPosition());
        assertEquals(MapDirection.WEST, animal.getFacing());
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0, 2), animal.getPosition());
        assertEquals(MapDirection.WEST, animal.getFacing());
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0, 2), animal.getPosition());
        assertEquals(MapDirection.WEST, animal.getFacing());

        animal.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(0, 2), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getFacing());

        animal.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(0, 2), animal.getPosition());
        assertEquals(MapDirection.EAST, animal.getFacing());

        animal.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(0, 2), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getFacing());
    }
}