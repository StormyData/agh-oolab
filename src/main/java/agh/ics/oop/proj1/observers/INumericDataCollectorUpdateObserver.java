package agh.ics.oop.proj1.observers;

public interface INumericDataCollectorUpdateObserver extends IObserver {
    void addNextValue(Number value);
}
