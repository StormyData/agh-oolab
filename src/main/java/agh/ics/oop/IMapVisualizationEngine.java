package agh.ics.oop;

public interface IMapVisualizationEngine {
    public void draw();
    public void redraw();
    public default void close(){};
}
