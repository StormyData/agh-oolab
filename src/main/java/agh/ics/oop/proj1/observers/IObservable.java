package agh.ics.oop.proj1.observers;

public interface IObservable {
    void addObserver(IObserver observer);

    void removeObserver(IObserver observer);
}
