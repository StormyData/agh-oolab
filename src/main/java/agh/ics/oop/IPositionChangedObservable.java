package agh.ics.oop;

public interface IPositionChangedObservable {
    void addObserver(IPositionChangeObserver observer);
    void removeObserver(IPositionChangeObserver observer);
}
