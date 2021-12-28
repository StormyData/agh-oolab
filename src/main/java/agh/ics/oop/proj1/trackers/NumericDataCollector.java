package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.observers.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class NumericDataCollector implements IEngineTickedObserver, IObservable {
    protected final ObserverHolder observers = new ObserverHolder(INumericDataCollectorUpdateObserver.class);
    protected final List<Double> values = new LinkedList<>();
    protected final INumericTracker tracker;

    public NumericDataCollector(INumericTracker tracker) {
        this.tracker = tracker;
        values.add(tracker.getValue());
    }

    @Override
    public void tick() {
        values.add(tracker.getValue());
        observers.notifyObservers(INumericDataCollectorUpdateObserver.class, observer -> observer.addNextValue(tracker.getValue()));
    }

    public String getDesc() {
        return tracker.getName();
    }

    public List<Double> getValues() {
        return Collections.unmodifiableList(values);
    }

    public String getName() {
        return tracker.getName();
    }

    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    public double getAverage() {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
    }
}
