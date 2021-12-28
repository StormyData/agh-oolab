package agh.ics.oop.proj1.trackers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumericDataCollectorTest {

    @Test
    void getValues() {
        NumericTestTracker tracker = new NumericTestTracker(5, "test tracker");
        NumericDataCollector collector = new NumericDataCollector(tracker);
        assertEquals(List.of(5.0), collector.getValues());
        tracker.setValue(2);
        assertEquals(List.of(5.0), collector.getValues());
        collector.tick();
        assertEquals(List.of(5.0, 2.0), collector.getValues());
        tracker.setValue(3);
        collector.tick();
        assertEquals(List.of(5.0, 2.0, 3.0), collector.getValues());
    }

    @Test
    void getName() {
        NumericTestTracker tracker = new NumericTestTracker(5, "test tracker");
        NumericDataCollector collector = new NumericDataCollector(tracker);
        assertEquals("test tracker", collector.getName());
    }
}