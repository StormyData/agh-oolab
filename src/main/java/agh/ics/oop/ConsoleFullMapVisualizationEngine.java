package agh.ics.oop;

import java.io.PrintStream;

public class ConsoleFullMapVisualizationEngine implements IMapVisualizationEngine{
    private final PrintStream stream;
    private final MapVisualizer mapVisualizer;
    private int lastLength=0;
    private final AbstractWorldMap map;

    public ConsoleFullMapVisualizationEngine(AbstractWorldMap map, PrintStream stream) {
        this.stream = stream;
        mapVisualizer=new MapVisualizer(map);
        this.map=map;
    }

    @Override
    public void draw() {
        String tmp=mapVisualizer.draw(map.getLowerBound(),map.getUpperBound());
        lastLength=tmp.length();
        stream.print(tmp);
    }

    @Override
    public void redraw() {
        stream.print("\b".repeat(lastLength));
        String tmp=mapVisualizer.draw(map.getLowerBound(),map.getUpperBound());
        lastLength=tmp.length();
        stream.print(tmp);
    }
}
