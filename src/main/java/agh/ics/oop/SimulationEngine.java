package agh.ics.oop;

public class SimulationEngine implements IEngine{
    private MoveDirection[] moves;
    private IWorldMap map;
    private Animal[] animals;

    SimulationEngine(MoveDirection[] moves,IWorldMap map,Vector2d[] positions)
    {
        this.moves=moves;
        this.map=map;
        animals = new Animal[positions.length];
        for(int i=0;i< positions.length;i++)
            animals[i]=new Animal(this.map,positions[i],MapDirection.NORTH);
    }
    @Override
    public void run() {
        for(int i=0;i< moves.length;i++)
            animals[i%animals.length].move(moves[i]);

    }

}
