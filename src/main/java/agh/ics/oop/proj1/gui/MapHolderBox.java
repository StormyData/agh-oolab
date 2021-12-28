package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AxisAlignedRectangle;
import agh.ics.oop.proj1.RunnerEngine;
import agh.ics.oop.proj1.SimulationOptions;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MapHolderBox extends VBox {
    final RunnerEngine engine;

    MapHolderBox(AbstractNonFlatWorldMap map, AxisAlignedRectangle mapBounds, int startingAnimals) {
        GridManager manager = new GridManager(mapBounds, map);

        SimulationOptions.SimulationOptionsBuilder builder = new SimulationOptions.SimulationOptionsBuilder(mapBounds);

        builder.setJungleRatio(0.3)
                .setMoveEnergy(1)
                .setPlantEnergy(10)
                .setStartEnergy(50)
                .setReproductionEnergy(25)
                .setDelay(100)
                .setNumberOfStartingAnimals(startingAnimals)
                .setMagical(false);

        engine = new RunnerEngine(map, builder.getOptions());

        StatisticsAndControlPanel panel = new StatisticsAndControlPanel(map, engine, manager, mapBounds);
        engine.start();
        getChildren().add(new ScrollPane(manager));
        getChildren().add(new ScrollPane(panel));
    }

    public void close() {
        engine.close();
    }
}
