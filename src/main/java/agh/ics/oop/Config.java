package agh.ics.oop;

public class Config {
    public enum VisualizationEngineType
    {
        SWINGTEXT,
        SWINGIMG,
        CONSOLEFULL,
        CONSOLE;

    }
    public static final boolean DEBUG=false;
    public static final boolean STEPPED=true;
    public static final VisualizationEngineType visualizationEngineType = VisualizationEngineType.CONSOLEFULL;
}
