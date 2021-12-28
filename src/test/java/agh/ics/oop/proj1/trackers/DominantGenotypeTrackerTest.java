package agh.ics.oop.proj1.trackers;

import agh.ics.oop.proj1.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DominantGenotypeTrackerTest {

    @Test
    void getDominantGenotypes() {
        AbstractNonFlatWorldMap map = TestHelper.generateTestMap();
        DominantGenotypeTracker tracker = new DominantGenotypeTracker(map);
        assertTrue(tracker.getDominantGenotypes().isEmpty());
        Genotype genotype1 = new Genotype(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});
        Genotype genotype2 = new Genotype(genotype1.getGenes());
        generateAnimalWithGenotype(map, genotype1);
        assertEquals(Set.of(genotype1), tracker.getDominantGenotypes());
        generateAnimalWithGenotype(map, genotype2);
        assertEquals(Set.of(genotype1), tracker.getDominantGenotypes());
        Genotype genotype3 = new Genotype(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3});
        generateAnimalWithGenotype(map, genotype3);
        generateAnimalWithGenotype(map, genotype3);
        assertEquals(Set.of(genotype1, genotype3), tracker.getDominantGenotypes());
        Animal animal3 = generateAnimalWithGenotype(map, genotype3);
        assertEquals(Set.of(genotype3), tracker.getDominantGenotypes());
        Genotype genotype4 = new Genotype(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4});
        Animal animal4 = generateAnimalWithGenotype(map,genotype4);
        assertEquals(Set.of(genotype3), tracker.getDominantGenotypes());
        map.remove(animal4);
        assertEquals(Set.of(genotype3), tracker.getDominantGenotypes());
        map.remove(animal3);
        assertEquals(Set.of(genotype1, genotype3), tracker.getDominantGenotypes());

    }

    private Animal generateAnimalWithGenotype(AbstractNonFlatWorldMap map, Genotype genotype) {
        return new Animal(map, new Vector2d(0, 0), MapDirection.NORTH, genotype, 5, 5, () -> 0);
    }
}