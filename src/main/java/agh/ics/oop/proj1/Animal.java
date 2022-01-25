package agh.ics.oop.proj1;

import agh.ics.oop.proj1.observers.*;

import java.util.Random;
import java.util.ResourceBundle;

public class Animal extends AbstractWorldMapElement implements IObservable {
    private static final Random random = new Random();

    private final ObserverHolder observers = new ObserverHolder(
            IPositionChangedObserver.class,
            IAnimalStateChangedObserver.class
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



    @Override
    public String toString() {
        return facing.toAbbreviatedString();
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition = direction.newPosition(facing,position);
        MapDirection newFacing = direction.newFacing(facing);

        Vector2d actualNewPosition = map.transformCoordinates(newPosition);
        if (!position.equals(actualNewPosition) && map.canMoveTo(actualNewPosition)) {
            onPositionChanged(position, actualNewPosition);
            position = actualNewPosition;
        }
        if (facing != newFacing) {
            onFacingChanged(newFacing);
            facing = newFacing;
        }
    }

    private void onFacingChanged(MapDirection newFacing) {
        observers.notifyObservers(IAnimalStateChangedObserver.class, observer -> observer.facingChanged(newFacing));
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

    public static void reproduce(Animal animal1,Animal animal2) {
        float alpha = (animal1.energy * 1.0f) / (animal1.energy + animal2.energy);

        int energyFromThis = animal1.energy / 4;
        int energyFromOther = animal2.energy / 4;

        animal1.addEnergy(-energyFromThis);
        animal2.addEnergy(-energyFromOther);

        Animal child = new Animal(animal1.map,
                animal1.position,
                MapDirection.randomDirection(random),
                Genotype.combineWithAlpha(animal1.genotype,animal2.genotype, alpha),
                energyFromOther + energyFromThis,
                animal1.moveEnergy, animal1.currentAgeProvider);
        animal2.onReproduced(child);
        animal1.onReproduced(child);
    }

    private void onPositionChanged(Vector2d oldPos, Vector2d newPos) {
        observers.notifyObservers(IPositionChangedObserver.class,
                observer -> observer.positionChanged(this, oldPos, newPos));
    }
    private void onReproduced(Animal child) {
        observers.notifyObservers(IAnimalStateChangedObserver.class, observer -> observer.reproduced(this, child));
    }

    private void onEnergyChanged(int newEnergy, int oldEnergy) {
        observers.notifyObservers(IAnimalStateChangedObserver.class, observer -> observer.energyChanged(newEnergy, oldEnergy));
        int currentAge = currentAgeProvider.currentAge();
        if (energy <= 0)
            observers.notifyObservers(IAnimalStateChangedObserver.class, observer -> observer.animalDied(this, currentAge));
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
