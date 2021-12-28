package agh.ics.oop.proj1.observers;

public interface IImageChangedObserver extends IObserver {
    void imageChanged(String newName);
}
