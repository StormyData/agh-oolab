package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.MapDirection;
import agh.ics.oop.proj1.observers.IAnimalDiedObserver;
import agh.ics.oop.proj1.observers.IEnergyChangedObserver;
import agh.ics.oop.proj1.observers.IFacingChangedObserver;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class AnimalInfoBox extends MapElementInfoBox implements IEnergyChangedObserver, IAnimalDiedObserver, IFacingChangedObserver {
    public AnimalInfoBox(Animal animal) {
        super(animal);
    }

    public void energyChanged(int newEnergy, int oldEnergy) {
        Platform.runLater(
                () -> properties.get("animalInfoBox.energy").setText(Integer.toString(newEnergy))
        );
    }

    @Override
    protected void addAdditionalProperties() {
        Animal animal = (Animal) element;
        properties.put("animalInfoBox.genotype", new Text(animal.getGenotype().displayString()));
        properties.put("animalInfoBox.energy", new Text(Integer.toString(animal.getEnergy())));
        properties.put("animalInfoBox.whenDied", new Text(ResourceBundle.getBundle("strings").getString("animalInfoBox.stillAlive")));
        properties.put("animalInfoBox.ageBorn", new Text(Integer.toString(animal.getAgeBorn())));
        properties.put("animalInfoBox.facing", new Text(animal.getFacing().toString()));
    }


    @Override
    public void animalDied(Animal animal, int age) {
        Platform.runLater(() -> properties.get("animalInfoBox.whenDied").setText(Integer.toString(age)));
    }

    @Override
    public void facingChanged(MapDirection newFacing) {
        Platform.runLater(() -> properties.get("animalInfoBox.facing").setText(newFacing.toString()));
    }
}
