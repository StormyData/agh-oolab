package agh.ics.oop.proj1;


import java.util.ResourceBundle;

public class Grass extends AbstractWorldMapElement {

    public Grass(Vector2d position, AbstractNonFlatWorldMap map) {
        super(position, map);
        map.add(this);
    }

    @Override
    public String getImageName() {  // dobrze by to było przenieść do GUI
        return "gras.png";
    }

    @Override
    public int displayPriority() {  //jw.
        return -1;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public String getDisplayName() {
        return ResourceBundle.getBundle("strings").getString("grass.displayName");
    }
}
