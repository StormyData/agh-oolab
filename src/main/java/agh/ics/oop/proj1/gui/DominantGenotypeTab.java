package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.Genotype;
import agh.ics.oop.proj1.observers.IDominantGenomeChangedObserver;
import agh.ics.oop.proj1.trackers.DominantGenotypeTracker;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class DominantGenotypeTab extends Tab implements IDominantGenomeChangedObserver {
    private final Set<Text> genotypeTextsSet = new HashSet<>();
    private final VBox genotypeTextsBox = new VBox();
    private final GridManager manager;
    private Button stopHighlightingAnimalsButton;


    public DominantGenotypeTab(AbstractNonFlatWorldMap map, GridManager manager) {
        DominantGenotypeTracker tracker = new DominantGenotypeTracker(map);
        tracker.addObserver(this);
        this.manager = manager;

        updateGenotypeText(tracker.getDominantGenotypes());

        createStopHighlightingAnimalsButton(manager);

        VBox box = new VBox(new ScrollPane(genotypeTextsBox), stopHighlightingAnimalsButton);
        setContent(box);
        setText(ResourceBundle.getBundle("strings").getString("dominantGenotypeTab.title"));
        setClosable(false);
    }


    private void highlightAnimalsWithGenotype(Genotype genotype) {
        manager.highlightMatchingMapElements(
                element -> element instanceof Animal &&
                        ((Animal) element).getGenotype().equals(genotype)
        );
    }

    private void createStopHighlightingAnimalsButton(GridManager manager) {
        stopHighlightingAnimalsButton = new Button(ResourceBundle.getBundle("strings").
                getString("dominantGenotypeTab.clearHighlights"));
        stopHighlightingAnimalsButton.setOnAction(
                event -> manager.clearHighlighted()
        );
    }


    private void updateGenotypeText(Set<Genotype> genotypes) {
        Platform.runLater(() ->
        {
            genotypeTextsBox.getChildren().removeAll(genotypeTextsSet);
            genotypeTextsSet.clear();

            genotypes.forEach(genotype ->
            {
                Text text = new Text(genotype.displayString());
                text.setOnMouseClicked(event -> highlightAnimalsWithGenotype(genotype));
                genotypeTextsSet.add(text);
            });

            genotypeTextsBox.getChildren().addAll(genotypeTextsSet);
        });
    }

    @Override
    public void dominantGenomeChanged(Set<Genotype> newDominantGenotypes) {
        updateGenotypeText(newDominantGenotypes);
    }
}
