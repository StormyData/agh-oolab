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
        mapElements.add(new Grass(this, grassGenBound));
    }

    protected Vector2d getLowerBound()
    {
        if(mapElements.size() == 0)
            return null;
        Vector2d lowerBound = mapElements.get(0).getPosition();
        for(AbstractWorldMapElement mapElement : mapElements)
            lowerBound = mapElement.getPosition().lowerLeft(lowerBound);
        return lowerBound;
    }
    protected Vector2d getUpperBound()
    {
        if(mapElements.size() == 0)
            return null;
        Vector2d upperBound = mapElements.get(0).getPosition();
        for(AbstractWorldMapElement mapElement : mapElements)
            upperBound = mapElement.getPosition().upperRight(upperBound);
        return upperBound;
    }
}
