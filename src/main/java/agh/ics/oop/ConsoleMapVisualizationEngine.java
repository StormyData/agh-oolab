package agh.ics.oop;

import java.io.PrintStream;
public class ConsoleMapVisualizationEngine implements IMapVisualizationEngine{
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final PrintStream stream;
    private final MapVisualizer mapVisualizer;
    private int lastLength=0;

    public ConsoleMapVisualizationEngine(IWorldMap map, Vector2d lowerLeft, Vector2d upperRight, PrintStream stream) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.stream = stream;
        mapVisualizer=new MapVisualizer(map);
    }

    @Override
    public void draw() {
        String tmp=mapVisualizer.draw(lowerLeft,upperRight);
        lastLength=tmp.length();
        stream.print(tmp);
    }

    @Override
    public void redraw() {
        stream.print("\b".repeat(lastLength));
        String tmp=mapVisualizer.draw(lowerLeft,upperRight);
        lastLength=tmp.length();
        stream.print(tmp);
    }
}
