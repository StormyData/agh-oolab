package agh.ics.oop.proj1.trackers;

public class NumericTestTracker implements INumericTracker {
    private double value;
    private String name;

    public NumericTestTracker(double value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
