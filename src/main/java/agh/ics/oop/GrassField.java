package agh.ics.oop;

import static java.lang.Math.sqrt;


public class GrassField extends AbstractWorldMap{
    private final int grassGenBound;
    private final MapBoundary mapBoundary = new MapBoundary();
    public GrassField(int n) {
        grassGenBound = (int)sqrt(10*n)+1;
        for(int i=0;i<n;i++)
            tryGenerateGrass();
    }
    @Override
    public boolean place(Animal animal)
    {
        super.place(animal);
        mapBoundary.addMapObject(animal);
        return true;
    }
    private void tryGenerateGrass() {
        Grass grass =new Grass(this, grassGenBound);
        mapBoundary.addMapObject(grass);
        mapElements.put(grass.getPosition(),grass);
        grass.addObserver(this);
    }

    public Vector2d getLowerBound()
    {
        return mapBoundary.getLowerLeft();
    }
    public Vector2d getUpperBound()
    {
        return mapBoundary.getUpperRight();
    }
}
