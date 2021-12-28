package agh.ics.oop.proj1.observers;

import agh.ics.oop.proj1.Genotype;

import java.util.Set;

public interface IDominantGenomeChangedObserver extends IObserver {
    void dominantGenomeChanged(Set<Genotype> newDominantGenotype);
}
