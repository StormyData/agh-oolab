package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.AxisAlignedRectangle;
import agh.ics.oop.proj1.RunnerEngine;
import agh.ics.oop.proj1.SimulationOptions;
import agh.ics.oop.proj1.observers.IDoneMagicObserver;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

public class ControlTab extends Tab implements IDoneMagicObserver {

    private final Text logText = new Text("");
    private SimulationOptionsSettingsFieldsPane fieldsPane;
    private Button setNewSimulationOptionsButton;
    private Button saveStatisticsToCSV;
    private TextField filename;

    public ControlTab(RunnerEngine engine, StatisticsAndControlPanel panel, AxisAlignedRectangle mapBounds) {
        Button startButton = new Button(ResourceBundle.getBundle("strings").getString("controlTab.start"));
        startButton.setOnAction(event -> engine.setRunning(true));
        Button stopButton = new Button(ResourceBundle.getBundle("strings").getString("controlTab.stop"));
        stopButton.setOnAction(event -> engine.setRunning(false));

        setupSaveStatisticsSection(panel);

        setupSimulationOptionsSettingsSection(engine, mapBounds);

        engine.addObserver(this);

        VBox box = new VBox(new HBox(startButton, stopButton),
                fieldsPane, setNewSimulationOptionsButton, saveStatisticsToCSV, filename, logText);
        setContent(box);
        setClosable(false);
        setText(ResourceBundle.getBundle("strings").getString("controlTab.tabName"));

    }

    private void setupSimulationOptionsSettingsSection(RunnerEngine engine, AxisAlignedRectangle mapBounds) {
        fieldsPane = new SimulationOptionsSettingsFieldsPane(mapBounds);

        setNewSimulationOptionsButton = new Button(ResourceBundle.getBundle("strings").getString("controlTab.setSimulationOptions"));
        setNewSimulationOptionsButton.setOnAction(
                event ->
                {
                    SimulationOptions options = fieldsPane.computeSimulationOptions();
                    if (options != null)
                        engine.setSimulationOptions(options);

                }
        );
    }

    private void setupSaveStatisticsSection(StatisticsAndControlPanel panel) {
        saveStatisticsToCSV = new Button(ResourceBundle.getBundle("strings").getString("controlTab.saveStatisticsToCSV"));
        filename = new TextField("output.csv");
        saveStatisticsToCSV.setOnAction(
                event -> {
                    String csv = panel.getCsvValueString();
                    try (FileWriter writer = new FileWriter(filename.getText())) {
                        writer.write(csv);
                    } catch (IOException e) {
                        logMessage(e.getMessage());
                    }
                }
        );
    }

    private void logMessage(String message) {
        String oldText = logText.getText();
        logText.setText(oldText + "\n" + message);
    }

    @Override
    public void doneMagic(int age) {
        Platform.runLater(() ->
                logMessage(
                        String.format(ResourceBundle.getBundle("strings").getString("ControlTab.doneMagic"), age)
                )
        );
    }

}
