package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ObserverHolder {
    private final Set<IObserver> observers = new HashSet<>();
    private final Set<Class<? extends IObserver>> acceptedObservers = new HashSet<>();

    public ObserverHolder() {

    }

    public ObserverHolder(Set<Class<? extends IObserver>> acceptedObservers) {
        this.acceptedObservers.addAll(acceptedObservers);
    }

    @SafeVarargs
    public ObserverHolder(Class<? extends IObserver>... observers) {
        acceptedObservers.addAll(Arrays.asList(observers));
    }

    public void addAcceptedObserverType(Class<? extends IObserver> type) {
        acceptedObservers.add(type);
    }

    public void removeAcceptedObserverType(Class<? extends IObserver> type) {
        acceptedObservers.remove(type);
        observers.removeIf(observer -> !isAccepted(observer));
    }

    public <T extends IObserver> void notifyObservers(Class<T> type, Consumer<T> consumer) {
        List<T> notifiedObservers = Util.filterInstancesAndCastStream(observers.stream(), type).toList();
        notifiedObservers.forEach(consumer);
    }

    public void addObserver(IObserver observer) {
        if (isAccepted(observer))
            observers.add(observer);
    }

    private boolean isAccepted(IObserver observer) {
        return acceptedObservers.stream().anyMatch(type -> type.isInstance(observer));
    }

    public void removeObserver(IObserver observer) {
        if (isAccepted(observer))
            observers.remove(observer);
    }
}
