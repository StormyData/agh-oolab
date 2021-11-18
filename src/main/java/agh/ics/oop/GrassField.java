package agh.ics.oop;

import static java.lang.Math.sqrt;


public class GrassField extends AbstractWorldMap{
    private final int grassGenBound;

    public GrassField(int n) {
        grassGenBound = (int)sqrt(10*n)+1;
        for(int i=0;i<n;i++)
            tryGenerateGrass();
    }

    private void tryGenerateGrass() {
        Grass grass =new Grass(this, grassGenBound);
        mapElements.put(grass.getPosition(),grass);
        grass.addObserver(this);
    }

    protected Vector2d getLowerBound()
    {
        if(mapElements.size() == 0)
            return null;
        Vector2d lowerBound = ((AbstractWorldMapElement)mapElements.values().toArray()[0]).getPosition();//Będzie lepiej na następnym labie
        for(AbstractWorldMapElement mapElement : mapElements.values())
            lowerBound = mapElement.getPosition().lowerLeft(lowerBound);
        return lowerBound;
    }
    protected Vector2d getUpperBound()
    {
        if(mapElements.size() == 0)
            return null;
        Vector2d upperBound = ((AbstractWorldMapElement)mapElements.values().toArray()[0]).getPosition();//Będzie lepiej na następnym labie
        for(AbstractWorldMapElement mapElement : mapElements.values())
            upperBound = mapElement.getPosition().upperRight(upperBound);
        return upperBound;
    }
}
