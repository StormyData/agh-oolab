package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapBoundaryTest {
    @Test
    void mapBoundaryTest()
    {
        AbstractWorldMap map = new AbstractWorldMap() {
            @Override
            public Vector2d getUpperBound() {
                return null;
            }

            @Override
            public Vector2d getLowerBound() {
                return null;
            }
        };
        BaseWorldMapElement mapElement=new BaseWorldMapElement(new Vector2d(2,1),map);
        BaseWorldMapElement mapElement2=new BaseWorldMapElement(new Vector2d(-5,2),map);
        Animal animal = new Animal(map,new Vector2d(3,3),MapDirection.NORTH);
        MapBoundary mapBoundary=new MapBoundary();
        mapBoundary.addMapObject(mapElement);
        assertEquals(new Vector2d(2,1),mapBoundary.getLowerLeft());
        assertEquals(new Vector2d(2,1),mapBoundary.getUpperRight());
        mapBoundary.addMapObject(mapElement2);
        assertEquals(new Vector2d(-5,1),mapBoundary.getLowerLeft());
        assertEquals(new Vector2d(2,2),mapBoundary.getUpperRight());
        mapBoundary.addMapObject(animal);
        assertEquals(new Vector2d(-5,1),mapBoundary.getLowerLeft());
        assertEquals(new Vector2d(3,3),mapBoundary.getUpperRight());
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(-5,1),mapBoundary.getLowerLeft());
        assertEquals(new Vector2d(3,4),mapBoundary.getUpperRight());
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(-5,1),mapBoundary.getLowerLeft());
        assertEquals(new Vector2d(2,4),mapBoundary.getUpperRight());
        animal.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(-5,1),mapBoundary.getLowerLeft());
        assertEquals(new Vector2d(2,4),mapBoundary.getUpperRight());

    }
}
