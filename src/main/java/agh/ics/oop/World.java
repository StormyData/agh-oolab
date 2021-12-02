package agh.ics.oop;

import agh.ics.oop.Config.VisualizationEngineType;
import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        Application.launch(App.class,args);
        System.out.println("system wystartował");
        try
        {
            //run(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("system zakończył działanie");


    }
    private static void run(String[] args) {
        MoveDirection[] directions = new OptionsParser().parse(args);
        //IWorldMap map = new RectangularMap(10, 5);
        IWorldMap map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine;
        if(Config.STEPPED)
        {
            IMapVisualizationEngine mve = switch (Config.visualizationEngineType)
                    {
                        case SWINGIMG -> new SwingImageBasedMapVisualizationEngine(map,new Vector2d(-10,-10),new Vector2d(10,10));
                        case SWINGTEXT ->  new SwingTextBasedMapVisualizationEngine(map,new Vector2d(-10,-10),new Vector2d(10,10));
                        case CONSOLEFULL -> new ConsoleFullMapVisualizationEngine((AbstractWorldMap) map,System.out);
                        case CONSOLE -> new ConsoleMapVisualizationEngine(map,new Vector2d(0,0),new Vector2d(9,4),System.out);
                    };
            engine = new SteppedSimulationEngine(directions, map, positions,mve,1000);
        }
        else
            engine = new SimulationEngine(directions,map,positions);

        engine.run();
        System.out.println(map);
        if(Config.STEPPED && (Config.visualizationEngineType == VisualizationEngineType.CONSOLE || Config.visualizationEngineType == VisualizationEngineType.CONSOLEFULL)) {
            try {
                Thread.sleep(1000*(1+directions.length));
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
     }


}
