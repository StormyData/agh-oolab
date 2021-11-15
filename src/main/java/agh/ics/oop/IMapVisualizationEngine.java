package agh.ics.oop;

public interface IMapVisualizationEngine {
    void draw();
    void redraw();
    default void close(){}
}
