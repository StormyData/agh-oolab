package agh.ics.oop.proj1;

import agh.ics.oop.proj1.observers.*;

import java.util.Random;
import java.util.ResourceBundle;

public class Animal extends AbstractWorldMapElement implements IObservable {
    private final ObserverHolder observers = new ObserverHolder(
            IPositionChangedObserver.class,
            IImageChangedObserver.class,
            IReproductionObserver.class,
            IEnergyChangedObserver.class,
            IAnimalDiedObserver.class,
            IFacingChangedObserver.class
    );
    private final Genotype genotype;
    private final ICurrentAgeProvider currentAgeProvider;
    private final int ageBorn;
    private int moveEnergy;
    private MapDirection facing;
    private int energy;

    public Animal(AbstractNonFlatWorldMap map, Vector2d pos, MapDirection facing, Genotype genotype, int energy, int moveEnergy, ICurrentAgeProvider currentAgeProvider) {
        super(pos, map);
        this.facing = facing;
        this.genotype = genotype;
        this.energy = energy;
        this.moveEnergy = moveEnergy;
        this.currentAgeProvider = currentAgeProvider;
        ageBorn = currentAgeProvider.currentAge();
        map.add(this);
    }

    private static String getImageNameForDirection(MapDirection direction) {
        return switch (direction) {
            case NORTH -> "sheepNorth.png";
            case NORTHEAST -> "sheepNorthEast.png";
            case EAST -> "sheepEast.png";
            case SOUTHEAST -> "sheepSouthEast.png";
            case SOUTH -> "sheepSouth.png";
            case SOUTHWEST -> "sheepSouthWest.png";
            case WEST -> "sheepWest.png";
            case NORTHWEST -> "sheepNorthWest.png";
        };
    }

    @Override
    public String toString() {
        return facing.toAbbreviatedString();
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition = position;
        MapDirection newFacing = facing;
        switch (direction) {
            case FORWARD -> newPosition = position.add(facing.toUnitVector());
            case RIGHT45 -> newFacing = facing.next();
            case RIGHT -> newFacing = facing.next(2);
            case RIGHT135 -> newFacing = facing.previous(3);
            case BACKWARD -> newPosition = position.add(facing.toUnitVector().opposite());
            case LEFT135 -> newFacing = facing.next(3);
            case LEFT -> newFacing = facing.previous(2);
            case LEFT45 -> newFacing = facing.previous();
        }
        Vector2d actualNewPosition = map.transformCoordinates(newPosition);
        if (!position.equals(actualNewPosition) && map.canMoveTo(actualNewPosition)) {
            onPositionChanged(position, actualNewPosition);
            position = actualNewPosition;
        }
        if (facing != newFacing) {
            onFacingChanged(newFacing);
            onImageChanged(newFacing);
            facing = newFacing;
        }
    }

    private void onFacingChanged(MapDirection newFacing) {
        observers.notifyObservers(IFacingChangedObserver.class, observer -> observer.facingChanged(newFacing));
    }

    public MapDirection getFacing() {
        return facing;
    }

    public void update() {
        move(genotype.getRandomMove());
        addEnergy(-moveEnergy);
    }

    public void addEnergy(int energy) {
        setEnergy(this.energy + energy);
    }

    public int getEnergy() {
        return energy;
    }

    private void setEnergy(int newEnergy) {
        int oldEnergy = energy;
        energy = Math.max(newEnergy, 0);
        onEnergyChanged(energy, oldEnergy);
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public void reproduce(Animal other) {
        float alpha = (energy * 1.0f) / (energy + other.energy);

        int energyFromThis = energy / 4;
        int energyFromOther = other.energy / 4;

        addEnergy(-energyFromThis);
        other.addEnergy(-energyFromOther);

        Animal child = new Animal(map,
                position,
                MapDirection.randomDirection(new Random()),
                genotype.combineWithAlpha(other.genotype, alpha),
                energyFromOther + energyFromThis,
                moveEnergy, currentAgeProvider);
        other.onReproduced(child);
        onReproduced(child);
    }

    private void onPositionChanged(Vector2d oldPos, Vector2d newPos) {
        observers.notifyObservers(IPositionChangedObserver.class,
                observer -> observer.positionChanged(this, oldPos, newPos));
    }

    @Override
    public int displayPriority() {
        return 1;
    }

    protected void onImageChanged(MapDirection newFacing) {
        observers.notifyObservers(IImageChangedObserver.class, observer ->
                observer.imageChanged(getImageNameForDirection(newFacing)));
    }

    @Override
    public String getImageName() {
        return getImageNameForDirection(facing);
    }

    private void onReproduced(Animal child) {
        observers.notifyObservers(IReproductionObserver.class, observer -> observer.reproduced(this, child));
    }

    private void onEnergyChanged(int newEnergy, int oldEnergy) {
        observers.notifyObservers(IEnergyChangedObserver.class, observer -> observer.energyChanged(newEnergy, oldEnergy));
        int currentAge = currentAgeProvider.currentAge();
        if (energy <= 0)
            observers.notifyObservers(IAnimalDiedObserver.class, observer -> observer.animalDied(this, currentAge));
    }

    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.removeObserver(observer);
    }

    @Override
    public String getDisplayName() {
        return ResourceBundle.getBundle("strings").getString("animal.displayName");
    }

    public void setMoveEnergy(int moveEnergy) {
        this.moveEnergy = moveEnergy;
    }

    public int getAgeBorn() {
        return ageBorn;
    }

    public boolean isDead() {
        return energy <= 0;
    }
}
