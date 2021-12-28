package agh.ics.oop.proj1;

import agh.ics.oop.proj1.trackers.NumericDataCollector;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Util {
    public static <T> T chooseRandomElementOfList(Random random, List<T> list) {
        if (list.isEmpty())
            throw new IllegalArgumentException("cannot choose random element from empty list");
        return list.get(random.nextInt(list.size()));
    }

    public static <T> List<T> chooseRandomElementsOfListDistinct(Random random, List<T> list, int n) {
        if (list.size() < n)
            throw new IllegalArgumentException("not enough elements in list to choose from");
        int[] indices = chooseRandomIndices(random, list.size(), n);
        return Arrays.stream(indices).mapToObj(list::get).toList();
    }

    public static int[] chooseRandomIndices(Random random, int domainSize, int numberOfIndices) {
        if (numberOfIndices > domainSize)
            throw new IllegalArgumentException(
                    String.format("domain size (%d) must be at least as large as the number of Indices to choose (%d)", domainSize, numberOfIndices));
        if (numberOfIndices == domainSize)// we have to choose all indices
            return IntStream.range(0, domainSize).toArray();
        int[] indices = new int[numberOfIndices];
        for (int i = 0; i < numberOfIndices; i++) {
            int nextIndex;
            do {
                nextIndex = random.nextInt(domainSize);
            } while (isAlreadyPresent(indices, nextIndex, i));
            indices[i] = nextIndex;
        }
        return indices;
    }

    private static boolean isAlreadyPresent(int[] indices, int nextIndex, int i) {
        for (int j = 0; j < i; j++) {
            if (indices[j] == nextIndex)
                return true;
        }
        return false;
    }

    public static <T, U> Stream<T> filterInstancesAndCastStream(Stream<U> stream, Class<T> type) {
        return stream.filter(type::isInstance).map(type::cast);
    }

    public static String getCsvValueString(List<NumericDataCollector> collectors) {
        StringBuilder builder = new StringBuilder();

        //header
        builder.append(
                        String.join(",", collectors.stream().map(NumericDataCollector::getName).toList()))
                .append('\n');

        //main body
        int size = collectors.get(0).getValues().size();
        for (int i = 0; i < size; i++) {
            final int finalInt = i;
            builder.append(
                    String.join(",", collectors.stream().
                            map(collector -> collector.getValues().get(finalInt).toString()).toList())
            ).append('\n');
        }
        //footer
        builder.append(
                String.join(",", collectors.stream().
                        map(collector -> Double.toString(collector.getAverage())).toList())
        );
        return builder.toString();
    }
}
