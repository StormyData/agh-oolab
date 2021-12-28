package agh.ics.oop.proj1;

import agh.ics.oop.proj1.trackers.NumericDataCollector;
import agh.ics.oop.proj1.trackers.NumericTestTracker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void chooseRandomElementOfList() {
        List<Integer> list = List.of(1, 5, 3, 6, 10, 1);
        Random random = new Random();
        for (int i = 0; i < 100; i++)
            assertTrue(list.contains(Util.chooseRandomElementOfList(random, list)));
    }

    @Test
    void chooseRandomElementsOfListDistinct() {
        List<Integer> list = List.of(1, 5, 3, 6, 10, 2);
        List<Integer> listSorted = list.stream().sorted().toList();
        Random random = new Random();
        List<Integer> chosen1 = Util.chooseRandomElementsOfListDistinct(random, list, 5);
        assertTrue(list.containsAll(chosen1));
        assertEquals(5, chosen1.stream().distinct().count());

        List<Integer> chosen2 = Util.chooseRandomElementsOfListDistinct(random, list, 6);
        List<Integer> chosen2sorted = chosen2.stream().sorted().toList();
        assertEquals(listSorted, chosen2sorted);

        assertThrows(IllegalArgumentException.class, () -> Util.chooseRandomElementsOfListDistinct(random, list, 7));

    }

    @Test
    void chooseRandomIndices() {
        Random random = new Random();
        int[] chosen1 = Util.chooseRandomIndices(random, 5, 3);

        assertTrue(Arrays.stream(chosen1).allMatch(index -> 0 <= index && index < 5));
        assertEquals(3, Arrays.stream(chosen1).distinct().count());


        int[] chosen2 = Util.chooseRandomIndices(random, 5, 5);

        assertTrue(Arrays.stream(chosen2).allMatch(index -> 0 <= index && index < 5));
        assertEquals(5, Arrays.stream(chosen2).distinct().count());


        assertThrows(IllegalArgumentException.class, () -> Util.chooseRandomIndices(random, 5, 6));
    }

    @Test
    void filterInstancesAndCastStream() {
        List<Object> list = new LinkedList<>();
        list.add("string1");
        list.add(5);
        list.add(7);
        list.add("string2");
        list.add(new Vector2d(3, 5));

        List<String> listOfStrings = Util.filterInstancesAndCastStream(list.stream(), String.class).toList();
        assertTrue(listOfStrings.contains("string1"));
        assertTrue(listOfStrings.contains("string2"));
        assertEquals(2, listOfStrings.size());


        List<Integer> listOfInts = Util.filterInstancesAndCastStream(list.stream(), Integer.class).toList();
        assertTrue(listOfInts.contains(5));
        assertTrue(listOfInts.contains(7));
        assertEquals(2, listOfInts.size());


        List<Vector2d> listOfVectors = Util.filterInstancesAndCastStream(list.stream(), Vector2d.class).toList();
        assertTrue(listOfVectors.contains(new Vector2d(3, 5)));
        assertEquals(1, listOfVectors.size());
    }

    @Test
    void getCsvValueString() {
        List<NumericDataCollector> collectors = new LinkedList<>();

        NumericTestTracker tracker1 = new NumericTestTracker(3, "tracker 1");
        NumericTestTracker tracker2 = new NumericTestTracker(2, "tracker 2");
        collectors.add(new NumericDataCollector(tracker1));
        collectors.add(new NumericDataCollector(tracker2));
        tracker1.setValue(5);
        tracker2.setValue(3);
        collectors.forEach(NumericDataCollector::tick);
        tracker1.setValue(1);
        tracker2.setValue(1);
        collectors.forEach(NumericDataCollector::tick);

        String expected = "tracker 1,tracker 2\n3.0,2.0\n5.0,3.0\n1.0,1.0\n3.0,2.0";
        String actual = Util.getCsvValueString(collectors);
        assertEquals(expected, actual);
    }
}