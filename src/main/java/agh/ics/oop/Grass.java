package agh.ics.oop;


import java.util.Random;

public class Grass extends AbstractWorldMapElement{
    static private final Random random =new Random();
    private int grassGenBound;

    Grass(IWorldMap map, int grassGenBound)
    {
        super(null,map);
        this.grassGenBound = grassGenBound;
        regenerate();
    }
    Grass(Vector2d position, IWorldMap map, int grassGenBound)
    {
        super(position,map);
        this.grassGenBound = grassGenBound;
    }

    @Override
    public String toString() {
        return "*";
    }
    public void eat()
    {
        Vector2d oldPos=position;
        regenerate();
        if(config.DEBUG)
            System.out.printf("grass at %s got eaten and regenerated at %s\n",oldPos,position);
    }
    private void regenerate()
    {
        Vector2d oldPosition=position;
        position=null;
        int tries=1;
        Vector2d nextPosition=new Vector2d(random.nextInt(grassGenBound), random.nextInt(grassGenBound));
        while(map.isOccupied(nextPosition))
        {
            nextPosition = new Vector2d(random.nextInt(grassGenBound), random.nextInt(grassGenBound));
            tries++;
            if(tries>grassGenBound*grassGenBound*grassGenBound)
            {
                position=oldPosition;
                return;
            }
        }
        position=nextPosition;
    }
}
