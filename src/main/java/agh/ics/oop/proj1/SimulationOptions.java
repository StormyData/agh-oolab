package agh.ics.oop.proj1;


public class SimulationOptions {
    private int moveEnergy = 1;
    private int plantEnergy = 10;
    private int startEnergy = 50;
    private int reproductionEnergy = startEnergy / 2;
    private long delayMilliseconds = 100;
    private int numberOfStartingAnimals = 10;
    private double jungleRatio = 0.3;

    private AxisAlignedRectangle jungleBounds =
            new AxisAlignedRectangle(new Vector2d(0, 0), new Vector2d(0, 0));
    private boolean isMagical;

    private SimulationOptions() {

    }

    public SimulationOptions(SimulationOptions other) {
        moveEnergy = other.moveEnergy;
        plantEnergy = other.plantEnergy;
        startEnergy = other.startEnergy;
        reproductionEnergy = other.reproductionEnergy;
        delayMilliseconds = other.delayMilliseconds;
        numberOfStartingAnimals = other.numberOfStartingAnimals;
        jungleBounds = other.jungleBounds;
        isMagical = other.isMagical;
        jungleRatio = other.jungleRatio;
    }

    public static SimulationOptions getDefaultSimulationOptions() {
        return new SimulationOptions();
    }

    public static AxisAlignedRectangle calculateJungleBounds(double jungleToSavannaRatio, AxisAlignedRectangle mapBounds) {
        double jungleToMapRatio = jungleToSavannaRatio / (jungleToSavannaRatio + 1);
        Vector2d jungleSizeHalf = mapBounds.getSize().multiply(jungleToMapRatio / 2);
        Vector2d mapCenter = mapBounds.getCenter();
        Vector2d lowerLeft = mapCenter.subtract(jungleSizeHalf);
        Vector2d upperRight = mapCenter.add(jungleSizeHalf);
        return new AxisAlignedRectangle(lowerLeft, upperRight);
    }

    public static double calculateJungleRatio(AxisAlignedRectangle jungleBounds, AxisAlignedRectangle mapBounds) {
        double jungleToSavannaAreaRatio = ((double) jungleBounds.getArea()) / (mapBounds.getArea() - jungleBounds.getArea());
        return Math.sqrt(jungleToSavannaAreaRatio);
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getReproductionEnergy() {
        return reproductionEnergy;
    }

    public long getDelay() {
        return delayMilliseconds;
    }

    public int getNumberOfStartingAnimals() {
        return numberOfStartingAnimals;
    }

    public AxisAlignedRectangle getJungleBounds() {
        return jungleBounds;
    }

    public boolean isMagical() {
        return isMagical;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public static class SimulationOptionsBuilder {
        protected final AxisAlignedRectangle mapBounds;
        protected final SimulationOptions options = getDefaultSimulationOptions();

        public SimulationOptionsBuilder(AxisAlignedRectangle mapBounds) {
            this.mapBounds = mapBounds;
        }

        public SimulationOptionsBuilder setMoveEnergy(int energy) {
            options.moveEnergy = energy;
            return this;
        }

        public SimulationOptionsBuilder setPlantEnergy(int energy) {
            options.plantEnergy = energy;
            return this;
        }

        public SimulationOptionsBuilder setStartEnergy(int energy) {
            options.startEnergy = energy;
            return this;
        }

        public SimulationOptionsBuilder setReproductionEnergy(int energy) {
            options.reproductionEnergy = energy;
            return this;
        }

        public SimulationOptionsBuilder setDelay(long delay) {
            options.delayMilliseconds = delay;
            return this;
        }

        public SimulationOptionsBuilder setNumberOfStartingAnimals(int animals) {
            options.numberOfStartingAnimals = animals;
            return this;
        }

        public SimulationOptionsBuilder setJungleBounds(AxisAlignedRectangle bounds) {
            options.jungleBounds = bounds;
            options.jungleRatio = calculateJungleRatio(bounds, mapBounds);
            return this;
        }

        public SimulationOptionsBuilder setJungleRatio(double jungleRatio) {
            options.jungleRatio = jungleRatio;
            options.jungleBounds = calculateJungleBounds(jungleRatio, mapBounds);
            return this;
        }

        public void setMagical(boolean magical) {
            options.isMagical = magical;
        }

        public SimulationOptions getOptions() {
            return new SimulationOptions(options);
        }
    }


}
