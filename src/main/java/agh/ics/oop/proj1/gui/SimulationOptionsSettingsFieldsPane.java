package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.AxisAlignedRectangle;
import agh.ics.oop.proj1.SimulationOptions;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SimulationOptionsSettingsFieldsPane extends GridPane {
    private final AxisAlignedRectangle mapBounds;
    private final Map<String, Spinner<Integer>> intFields;
    private final Map<String, Spinner<Double>> doubleFields;
    private final Map<String, CheckBox> checkBoxes;

    SimulationOptionsSettingsFieldsPane(AxisAlignedRectangle mapBounds) {
        SimulationOptions defaultSimulationOptions = SimulationOptions.getDefaultSimulationOptions();

        intFields = Map.ofEntries(
                Map.entry("simulationOptionsSettingsPane.moveEnergy", new Spinner<>(0, Integer.MAX_VALUE, defaultSimulationOptions.getMoveEnergy())),
                Map.entry("simulationOptionsSettingsPane.plantEnergy", new Spinner<>(0, Integer.MAX_VALUE, defaultSimulationOptions.getPlantEnergy())),
                Map.entry("simulationOptionsSettingsPane.startEnergy", new Spinner<>(0, Integer.MAX_VALUE, defaultSimulationOptions.getStartEnergy())),
                Map.entry("simulationOptionsSettingsPane.delay", new Spinner<>(10, Integer.MAX_VALUE, defaultSimulationOptions.getDelay()))
        );
        Map<String, Node> fields = new HashMap<>(intFields);

        doubleFields = Map.ofEntries(
                Map.entry("simulationOptionsSettingsPane.jungleRatio", new Spinner<>(0, Double.MAX_VALUE, defaultSimulationOptions.getJungleRatio()))
        );
        fields.putAll(doubleFields);

        checkBoxes = Map.ofEntries(
                Map.entry("simulationOptionsSettingsPane.isMagical", new CheckBox())
        );
        checkBoxes.get("simulationOptionsSettingsPane.isMagical").setSelected(defaultSimulationOptions.isMagical());
        fields.putAll(checkBoxes);

        this.mapBounds = mapBounds;
        int i = 0;
        for (Map.Entry<String, Node> entry : fields.entrySet()) {
            add(new Text(ResourceBundle.getBundle("strings").getString(entry.getKey())), 0, i);
            add(entry.getValue(), 1, i);
            if (entry.getValue() instanceof Spinner spinner)
                spinner.setEditable(true);
            i++;
        }
    }

    SimulationOptions computeSimulationOptions() {
        SimulationOptions.SimulationOptionsBuilder builder = new SimulationOptions.SimulationOptionsBuilder(mapBounds);
        int moveEnergy = getIntValue("simulationOptionsSettingsPane.moveEnergy");
        builder.setMoveEnergy(moveEnergy);
        int plantEnergy = getIntValue("simulationOptionsSettingsPane.plantEnergy");
        builder.setPlantEnergy(plantEnergy);

        int startEnergy = getIntValue("simulationOptionsSettingsPane.startEnergy");
        builder.setStartEnergy(startEnergy);
        builder.setReproductionEnergy(startEnergy / 2);

        double jungleToSavannaRatio = getDoubleValue("simulationOptionsSettingsPane.jungleRatio");
        builder.setJungleRatio(jungleToSavannaRatio);

        long delay = getIntValue("simulationOptionsSettingsPane.delay");
        builder.setDelay(delay);
        builder.setMagical(checkBoxes.get("simulationOptionsSettingsPane.isMagical").isSelected());
        return builder.getOptions();
    }

    private Integer getIntValue(String s) {
        Number n = intFields.get(s).getValue();
        return n.intValue();
    }

    private Double getDoubleValue(String s) {
        return doubleFields.get(s).getValue();
    }

}
