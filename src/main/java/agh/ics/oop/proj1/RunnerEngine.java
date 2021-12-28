package agh.ics.oop.proj1;

import agh.ics.oop.proj1.observers.*;


public class RunnerEngine extends Thread implements ICurrentAgeProvider, IObservable, IAllAnimalsDiedObserver {
    protected final Simulator simulator;
    protected final ObserverHolder observers = new ObserverHolder(IEngineTickedObserver.class);
    protected SimulationOptions options;
    protected Boolean running = false;
    protected boolean closed;

    public RunnerEngine(AbstractNonFlatWorldMap map, SimulationOptions options) {
        this.options = options;
        simulator = new Simulator(map, options);
        simulator.addObserver(this);
        simulator.spawnAnimals();
    }

    @Override
    public void run() {
        try {
            while (!closed) {
                while (!running)
                    synchronized (this) {
                        wait();
                    }
                simulator.step();
                observers.notifyObservers(IEngineTickedObserver.class, IEngineTickedObserver::tick);

                Thread.sleep(options.getDelay());

            }
        } catch (InterruptedException ignored) {

        }
    }

    public void setSimulationOptions(SimulationOptions options) {
        this.options = options;
        simulator.setOptions(options);
    }

    public void setRunning(boolean state) {
        synchronized (this) {
            running = state;
            notify();
        }
    }

    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
        simulator.addObserver(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.addObserver(observer);
        simulator.removeObserver(observer);
    }

    public void close() {
        closed = true;
    }

    @Override
    public int currentAge() {
        return simulator.currentAge();
    }

    @Override
    public void allAnimalsDied() {
        simulator.removeObserver(this);
        close();
        setRunning(false);
    }
}
