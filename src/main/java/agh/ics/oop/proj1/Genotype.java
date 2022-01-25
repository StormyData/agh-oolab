package agh.ics.oop.proj1;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Genotype {
    private static final int nofGenes = 32;
    private static final int maxGene = 8;
    private static final Random random = new Random();
    private final int[] genes;

    public Genotype() {
        genes = IntStream.range(0, nofGenes).
                map(i -> random.nextInt(maxGene)).
                sorted().
                toArray();
    }

    public Genotype(int[] genes) {
        if (genes.length != nofGenes)
            throw new IllegalArgumentException(String.format("genotype must have exactly %d genes", nofGenes));
        if (Arrays.stream(genes).
                anyMatch(a -> 0 > a || a >= maxGene))
            throw new IllegalArgumentException(String.format("genes must be between 0 and %d", maxGene - 1));
        this.genes = Arrays.copyOf(genes, genes.length);
        Arrays.sort(this.genes);
    }

    public MoveDirection getRandomMove() {
        int gene = genes[random.nextInt(nofGenes)];
        return MoveDirection.values()[gene];
    }

    public static Genotype combineWithAlpha(Genotype left,Genotype right, float alpha) {
        if (alpha < 0 || alpha > 1)
            throw new IllegalArgumentException(
                    String.format("alpha must be between 0 and 1, got %f", alpha));

        int numberOfElementsFromThis = (int) (nofGenes * alpha);
        if (random.nextBoolean()) {
            return combineWithAlphaSided(left, right, numberOfElementsFromThis);
        } else {
            return combineWithAlphaSided(right, left, nofGenes - numberOfElementsFromThis);
        }
    }

    private static Genotype combineWithAlphaSided(Genotype genotypeLeft, Genotype genotypeRight, int numberOfElementsFromLeft) {
        int[] combinedGenome = new int[nofGenes];

        //copy genes from LeftGenome
        if (numberOfElementsFromLeft >= 0)
            System.arraycopy(genotypeLeft.genes, 0, combinedGenome, 0, numberOfElementsFromLeft);

        //copy genes from RightGenome
        int numberOfElementsFromRight = nofGenes - numberOfElementsFromLeft;
        if (numberOfElementsFromRight >= 0)
            System.arraycopy(genotypeRight.genes, numberOfElementsFromLeft, combinedGenome, numberOfElementsFromLeft, numberOfElementsFromRight);

        Arrays.sort(combinedGenome);
        return new Genotype(combinedGenome);
    }

    public int[] getGenes() {
        return Arrays.copyOf(genes, nofGenes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return Arrays.equals(genes, genotype.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }

    @Override
    public String toString() {
        return "Genotype{" +
                "genes=" + Arrays.toString(genes) +
                '}';
    }

    public String displayString() {
        return String.join(" ", Arrays.stream(genes).mapToObj(Integer::toString).toList());
    }
}
