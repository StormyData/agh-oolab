package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.*;
import agh.ics.oop.proj1.observers.IEngineTickedObserver;
import agh.ics.oop.proj1.observers.IMapObjectClickedObserver;
import agh.ics.oop.proj1.trackers.*;
import javafx.scene.control.TabPane;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class StatisticsAndControlPanel extends TabPane implements IMapObjectClickedObserver, IEngineTickedObserver {
    private final HashSet<NumericDataCollector> collectors = new HashSet<>();
    private final HashSet<NumericDataCollector> additionalCollectors = new HashSet<>();
    private final AbstractNonFlatWorldMap map;

    public StatisticsAndControlPanel(AbstractNonFlatWorldMap map, RunnerEngine engine, GridManager manager, AxisAlignedRectangle mapBounds) {
        engine.addObserver(this);
        manager.addObserver(this);

        this.map = map;

        getTabs().add(new ControlTab(engine, this, mapBounds));
        getTabs().add(new DominantGenotypeTab(map, manager));

        addGeneralTrackerTabs(map);

    }

    private void addGeneralTrackerTabs(AbstractNonFlatWorldMap map) {
        INumericTracker[] generalTrackers = {
                new AvgEnergyLevelTracker(map),
                new AnimalCountTracker(map),
                new GrassCountTracker(map),
                new AvgAnimalChildrenCountTracker(map),
                new AvgAnimalLifespanTracker(map)
        };

        collectors.addAll(Arrays.stream(generalTrackers).
                map(NumericDataCollector::new).toList());
        getTabs().addAll(
                collectors.stream().
                        map(collector -> new NumericStatisticsChart(collector, false)).
                        toList());
    }

    public void tick() {
        collectors.forEach(IEngineTickedObserver::tick);
        additionalCollectors.forEach(IEngineTickedObserver::tick);
    }

    @Override
    public void mapObjectClicked(AbstractWorldMapElement element) {
        getTabs().add(new MapElementInfoTab(element, map));

        if (element instanceof Animal animal) {
            addAdditionalAnimalTrackers(animal);
        }

    }

    private void addAdditionalAnimalTrackers(Animal animal) {
        INumericTracker[] additionalTrackers = {
                new AnimalChildrenCountTracker(animal),
                new AnimalSuccessorCountTracker(animal, map)
        };

        List<NumericDataCollector> newCollectors = Arrays.stream(additionalTrackers).
                map(NumericDataCollector::new).
                toList();

        newCollectors.forEach(this::addAdditionalCollector);
    }

    private void addAdditionalCollector(NumericDataCollector collector) {
        additionalCollectors.add(collector);

        NumericStatisticsChart chart = new NumericStatisticsChart(collector, true);
        getTabs().add(chart);

        chart.setOnCloseRequest(event -> additionalCollectors.remove(collector));
    }

    public String getCsvValueString() {
        return Util.getCsvValueString(collectors.stream().toList());
    }

}
