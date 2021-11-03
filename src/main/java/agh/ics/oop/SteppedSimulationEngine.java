package agh.ics.oop;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

public class SteppedSimulationEngine implements IEngine{
     private MoveDirection[] moves;
        private IWorldMap map;
        private Animal[] animals;
        private Timer timer;
        private IMapVisualizationEngine mve;
        private int delay;
        private int i=0;
        SteppedSimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] positions, IMapVisualizationEngine mve,int delay)
        {
            this.delay=delay;
            this.moves=moves;
            this.map=map;
            animals = new Animal[positions.length];
            for(int i=0;i< positions.length;i++)
                animals[i]=new Animal(this.map,positions[i],MapDirection.NORTH);
            this.mve = mve;
            this.mve.draw();
        }
        @Override
        public void run() {
            i=0;
            timer=new Timer(delay,(ActionEvent actionEvent) -> step());
            timer.start();
        }
        private void step()
        {
            animals[i%animals.length].move(moves[i]);
            i++;
            if(i==moves.length)
            {
                timer.stop();
                mve.close();
            }
            mve.redraw();

        }

}
