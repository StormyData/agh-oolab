package agh.ics.oop.proj1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenotypeTest {

    @Test
    void getRandomMove() {
        int[] genes = new int[32];
        for (int i = 0; i < 32 - 8; i++) {
            genes[i] = 0;
        }
        genes[31 - 8] = 2;
        genes[31 - 7] = 2;
        genes[31 - 6] = 2;
        genes[31 - 5] = 3;
        genes[31 - 4] = 3;
        genes[31 - 3] = 3;
        genes[31 - 2] = 6;
        genes[31 - 1] = 6;
        genes[31] = 6;

        Genotype genotype = new Genotype(genes);
        for (int i = 0; i < 128; i++) {
            MoveDirection direction = genotype.getRandomMove();
            assertTrue(direction == MoveDirection.FORWARD ||
                    direction == MoveDirection.RIGHT ||
                    direction == MoveDirection.RIGHT135 ||
                    direction == MoveDirection.LEFT
            );
        }

    }

    @Test
    void combineWithAlpha() {
        Genotype genotype1 = new Genotype(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7});
        Genotype genotype2 = new Genotype(new int[]
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 7});

        assertThrows(IllegalArgumentException.class, () -> genotype1.combineWithAlpha(genotype2, 1.5f));

        assertThrows(IllegalArgumentException.class, () -> genotype1.combineWithAlpha(genotype2, -0.5f));

        Genotype expectedGenotype50percent1 = new Genotype(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 7});
        Genotype expectedGenotype50percent2 = new Genotype(new int[]
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7});

        for (int i = 0; i < 10; i++) {
            Genotype combinedGenotype1 = genotype1.combineWithAlpha(genotype2, 0.5f);
            assertTrue(combinedGenotype1.equals(expectedGenotype50percent1) || combinedGenotype1.equals(expectedGenotype50percent2),
                    String.format("combined genotype %s is neither %s nor %s", combinedGenotype1, expectedGenotype50percent1, expectedGenotype50percent2));
        }
        Genotype expectedGenotype30percent1 = new Genotype(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 7});
        Genotype expectedGenotype30percent2 = new Genotype(new int[]
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 5, 4, 5, 5, 6, 6, 7, 7, 7, 7});

        for (int i = 0; i < 10; i++) {
            Genotype combinedGenotype2 = genotype1.combineWithAlpha(genotype2, 0.3f);
            assertTrue(combinedGenotype2.equals(expectedGenotype30percent1) || combinedGenotype2.equals(expectedGenotype30percent2),
                    String.format("combined genotype %s is neither %s nor %s", combinedGenotype2, expectedGenotype30percent1, expectedGenotype30percent2));

        }
    }

    @Test
    void displayString() {
        int[] genes = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7};
        String expected = "0 0 0 0 0 0 0 0 1 1 2 2 2 2 2 2 3 3 4 4 4 4 4 4 5 5 6 6 7 7 7 7";
        assertEquals(expected, new Genotype(genes).displayString());
    }
}