package agh.ics.oop.proj1;

import agh.ics.oop.proj1.observers.*;

import java.util.*;

public class Simulator implements ICurrentAgeProvider, IObservable {
    private static final Random random = new Random();
    private final AbstractNonFlatWorldMap map;
    private final ObserverHolder observers = new ObserverHolder(IDoneMagicObserver.class, IAllAnimalsDiedObserver.class);
    private final SimulationOptions options;
    private int currentAge;
    private int numberOfMagicalSpawnsLeft = 3;

    public Simulator(AbstractNonFlatWorldMap map, SimulationOptions options) {
        this.map = map;
        this.options = options;
    }

    public void spawnAnimals() {
        Random random = new Random();
        for (int i = 0; i < options.getNumberOfStartingAnimals(); i++) {
            Vector2d pos = getRandomUnoccupiedPositionOrNull(map.getMapBounds());
            if (pos != null)
                new Animal(map, pos, MapDirection.randomDirection(random),
                        new Genotype(), options.getStartEnergy(), options.getMoveEnergy(),
                        this);

        }
    }

    public void step() {
        synchronized (this) {
            currentAge++;
            deleteDeadAnimals();
            updateAnimalPositions();
            doGrassEating();
            doReproduction();
            spawnGrass();
            if (options.isMagical())
                checkAndDoMagic();
        }
    }

    private void checkAndDoMagic() {
        if (numberOfMagicalSpawnsLeft > 0 && map.getStreamOfAllObjectsOfClass(Animal.class).count() == 5) {
            numberOfMagicalSpawnsLeft--;
            List<Animal> animalsToCopy = map.getStreamOfAllObjectsOfClass(Animal.class).toList();
            animalsToCopy.forEach(
                    animal ->
                    {
                        Vector2d pos = getRandomUnoccupiedPositionOrNull(map.getMapBounds());
                        if (pos != null)
                            new Animal(map, pos, MapDirection.randomDirection(random),
                                    animal.getGenotype(), options.getStartEnergy(), options.getMoveEnergy(),
                                    this);
                    }
            );
            observers.notifyObservers(IDoneMagicObserver.class, observer -> observer.doneMagic(currentAge));
        }

    }

    private Vector2d getRandomUnoccupiedPositionOrNull(AxisAlignedRectangle rectangle) {
        int tries = 3 * rectangle.getArea();
        while (tries > 0) {
            Vector2d pos = rectangle.getRandomVectorInside(random);
            if (!map.isOccupied(pos))
                return pos;
            tries--;
        }
        return null;
    }

    private void spawnGrass() {
        Vector2d pos = getRandomUnoccupiedPositionOrNull(options.getJungleBounds());
        if (pos != null)
            new Grass(pos, map);

        Vector2d pos2;
        int tries = 3 * (map.getMapBounds().getArea() - options.getJungleBounds().getArea());
        do {
            pos2 = map.getMapBounds().getRandomVectorInside(random);
            tries--;
        } while (
                (map.isOccupied(pos2) ||
                        options.getJungleBounds().contains(pos2))
                        && tries > 0);
        if (tries > 0)
            new Grass(pos2, map);
    }

    private void doReproduction() {
        for (Vector2d pos : map.getOccupiedPositions()) {
            List<Animal> potentialAnimals = map.getStreamOfAllObjectsOfClassAtPosition(Animal.class, pos)
                    .filter(animal -> animal.getEnergy() > options.getReproductionEnergy())
                    .sorted(Comparator.comparingInt(Animal::getEnergy).reversed())
                    .toList();
            if (potentialAnimals.size() < 2)
                continue;


            int energyOfFirstAnimal = potentialAnimals.get(0).getEnergy();

            List<Animal> chosenAnimals;
            if (potentialAnimals.get(1).getEnergy() == energyOfFirstAnimal) {

                List<Animal> animals = potentialAnimals.stream().
                        filter(animal -> animal.getEnergy() == energyOfFirstAnimal).
                        toList();
                chosenAnimals = Util.chooseRandomElementsOfListDistinct(random, animals, 2);

            } else {

                chosenAnimals = new ArrayList<>(2);
                chosenAnimals.add(0, potentialAnimals.get(0));
                int energyOfSecondAnimal = potentialAnimals.get(1).getEnergy();
                List<Animal> animals = potentialAnimals.stream().
                        filter(animal -> animal.getEnergy() == energyOfSecondAnimal).
                        toList();
                chosenAnimals.add(1, Util.chooseRandomElementOfList(random, animals));

            }

            Animal.reproduce(chosenAnimals.get(0),chosenAnimals.get(1));
        }
    }

    private void doGrassEating() {
        for (Vector2d pos : map.getOccupiedPositions()) {
            Optional<Grass> grass =
                    map.getStreamOfAllObjectsOfClassAtPosition(Grass.class, pos).findAny();
            if (grass.isEmpty())
                continue;

            List<Animal> animals =
                    map.getStreamOfAllObjectsOfClassAtPosition(Animal.class, pos).toList();
            Optional<Integer> maxEnergyOptional = animals.stream().map(Animal::getEnergy).max(Integer::compareTo);
            if (maxEnergyOptional.isEmpty())
                continue;
            int maxEnergy = maxEnergyOptional.get();

            List<Animal> animalsEatingGrass = animals.stream().filter(animal -> maxEnergy == animal.getEnergy()).toList();

            int firstPassEnergy = options.getPlantEnergy() / animalsEatingGrass.size();
            animalsEatingGrass.forEach(animal -> animal.addEnergy(firstPassEnergy));

            int secondPassEnergy = options.getPlantEnergy() % animalsEatingGrass.size();
            if (secondPassEnergy > 0) {
                List<Animal> secondPassAnimals = Util.chooseRandomElementsOfListDistinct(random, animalsEatingGrass, secondPassEnergy);
                secondPassAnimals.forEach(animal -> animal.addEnergy(1));
            }

            map.remove(grass.get());
        }
    }

    private void updateAnimalPositions() {
        List<Animal> animals = map.getStreamOfAllObjectsOfClass(Animal.class).toList();
        animals.forEach(Animal::update);
    }

    private void deleteDeadAnimals() {
        List<Animal> toDelete = map.getStreamOfAllObjectsOfClass(Animal.class).
                filter(Animal::isDead)
                .toList();
        toDelete.forEach(map::remove);
        if (map.getStreamOfAllObjectsOfClass(Animal.class).findAny().isEmpty())
            observers.notifyObservers(IAllAnimalsDiedObserver.class, IAllAnimalsDiedObserver::allAnimalsDied);
    }

    public void setOptions(final SimulationOptions options) {
        synchronized (this) {
            map.getStreamOfAllObjectsOfClass(Animal.class).
                    forEach(animal -> animal.setMoveEnergy(options.getMoveEnergy()));
        }
    }

    @Override
    public int currentAge() {
        return currentAge;
    }

    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.removeObserver(observer);
    }
}
