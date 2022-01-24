package agh.ics.oop.observers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObserverHolderTest {


    @Test
    void addAcceptedObserverType() {
        TestObserver testObserver = new TestObserver(5);
        ObserverHolder holder = new ObserverHolder();

        holder.addObserver(testObserver);
        holder.notifyObservers(ITestObserver.class, observer -> observer.setValue(3));
        assertEquals(5, testObserver.getValue());

        holder.addAcceptedObserverType(ITestObserver.class);

        holder.addObserver(testObserver);
        holder.notifyObservers(ITestObserver.class, observer -> observer.setValue(3));
        assertEquals(3, testObserver.getValue());
    }

    @Test
    void removeAcceptedObserverType() {
        TestObserver testObserver = new TestObserver(5);
        ObserverHolder holder = new ObserverHolder(ITestObserver.class);

        holder.addObserver(testObserver);
        holder.notifyObservers(ITestObserver.class, observer -> observer.setValue(3));
        assertEquals(3, testObserver.getValue());

        TestObserver testObserver2 = new TestObserver(5);
        holder.removeAcceptedObserverType(ITestObserver.class);

        holder.addObserver(testObserver2);
        holder.notifyObservers(ITestObserver.class, observer -> observer.setValue(2));
        assertEquals(5, testObserver2.getValue());
        assertEquals(3, testObserver.getValue());
    }

    @Test
    void notifyObservers() {
        TestObserver testObserver = new TestObserver(5);
        ObserverHolder holder = new ObserverHolder(ITestObserver.class);
        holder.addObserver(testObserver);
        holder.notifyObservers(ITestObserver.class, observer -> observer.setValue(3));
        assertEquals(3, testObserver.getValue());

    }

    @Test
    void addObserver() {
        TestObserver testObserver = new TestObserver(5);
        ObserverHolder holder = new ObserverHolder(ITestObserver.class);

        holder.notifyObservers(ITestObserver.class, observer -> observer.setValue(3));
        assertEquals(5, testObserver.getValue());

        holder.addObserver(testObserver);
        holder.notifyObservers(ITestObserver.class, observer -> observer.setValue(3));
        assertEquals(3, testObserver.getValue());

    }

    @Test
    void removeObserver() {
        TestObserver testObserver = new TestObserver(5);
        ObserverHolder holder = new ObserverHolder(ITestObserver.class);
        holder.addObserver(testObserver);

        holder.notifyObservers(ITestObserver.class, observer -> observer.setValue(3));
        assertEquals(3, testObserver.getValue());

        holder.removeObserver(testObserver);

        holder.notifyObservers(ITestObserver.class, observer -> observer.setValue(4));
        assertEquals(3, testObserver.getValue());
    }
}