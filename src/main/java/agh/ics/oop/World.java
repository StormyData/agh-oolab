package agh.ics.oop;

import java.util.Timer;

public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");
        try
        {
            run(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("system zakończył działanie");


    }
    private static void run(String[] args) throws InterruptedException {
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        //IMapVisualizationEngine mve = new ConsoleMapVisualizationEngine(map,new Vector2d(0,0),new Vector2d(9,4),System.out);
        IMapVisualizationEngine mve = new SwingMapVisualizationEngine(map,new Vector2d(0,0),new Vector2d(9,4));
        IEngine engine = new SteppedSimulationEngine(directions, map, positions,mve,1000);
        engine.run();
        Thread.sleep(1000*(1+directions.length));
     }


}
