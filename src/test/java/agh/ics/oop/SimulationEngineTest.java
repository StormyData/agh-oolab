package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationEngineTest {

    @Test
    void run() {
        MoveDirection[] moves=new MoveDirection[]{MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD};
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        SimulationEngine engine = new SimulationEngine(moves,map,positions);
        engine.run();
        RectangularMap expected_map=new RectangularMap(10,5);
        Animal animal1 =new Animal(expected_map,new Vector2d(2,0),MapDirection.SOUTH);
        Animal animal2 =new Animal(expected_map,new Vector2d(3,4),MapDirection.NORTH);
        assertEquals(expected_map.toString(),map.toString());

        MoveDirection[] moves2=new MoveDirection[]{MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.RIGHT,MoveDirection.LEFT};
        IWorldMap map2 = new RectangularMap(10, 5);
        Vector2d[] positions2 = { new Vector2d(2,2), new Vector2d(3,4) };
        SimulationEngine engine2 = new SimulationEngine(moves2,map2,positions2);
        engine2.run();
        RectangularMap expected_map2=new RectangularMap(10,5);
        Animal animal3 =new Animal(expected_map2,new Vector2d(2,3),MapDirection.WEST);
        Animal animal4 =new Animal(expected_map2,new Vector2d(3,3),MapDirection.EAST);
        assertEquals(expected_map.toString(),map.toString());
    }
}