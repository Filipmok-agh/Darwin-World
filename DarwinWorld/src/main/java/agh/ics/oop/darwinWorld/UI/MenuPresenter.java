package agh.ics.oop.darwinWorld.UI;

import agh.ics.oop.darwinWorld.Config;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuPresenter {
    @FXML private Spinner<Integer> initialID;
    @FXML private Button loadButton;
    @FXML private Spinner<Integer> saveNumberID;
    @FXML private CheckBox saveBoxID;
    @FXML private CheckBox statSaveBoxID;
    @FXML private Button startButton;
    @FXML private Spinner<Integer> mapHeightID;
    @FXML private Spinner<Integer> initialPlantCountID;
    @FXML private Spinner<Integer> mapWidthID;
    @FXML private Spinner<Integer> dailyPlantGrowthID;
    @FXML private Spinner<Integer> plantEnergyID;
    @FXML private Spinner<Integer> initialAnimalCountID;
    @FXML private Spinner<Integer> initialAnimalEnergyID;
    @FXML private Spinner<Integer> energyToBeFedID;
    @FXML private Spinner<Integer> parentEnergyCostID;
    @FXML private Spinner<Integer> genomeLengthID;
    @FXML private Spinner<Integer> minMutationsID;
    @FXML private Spinner<Integer> maxMutationsID;
    @FXML private CheckBox lifeGivingCorpsesID;
    @FXML private CheckBox geneSwapID;
    @FXML private Spinner<Integer> dailyEnergyCostID;
    @FXML private Spinner<Integer> refreshRateID;

    private Config config = new Config();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd konfiguracji");
            alert.setHeaderText("Nieprawidłowe ustawienia");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Dane");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void fillUIFromConfig() {
        Platform.runLater(() -> {
            mapHeightID.getValueFactory().setValue(config.mapHeight);
            mapWidthID.getValueFactory().setValue(config.mapWidth);
            initialPlantCountID.getValueFactory().setValue(config.initialPlantCount);
            dailyPlantGrowthID.getValueFactory().setValue(config.dailyPlantGrowth);
            plantEnergyID.getValueFactory().setValue(config.plantEnergy);
            initialAnimalCountID.getValueFactory().setValue(config.initialAnimalCount);
            initialAnimalEnergyID.getValueFactory().setValue(config.initialAnimalEnergy);
            energyToBeFedID.getValueFactory().setValue(config.energyToBeFed);
            parentEnergyCostID.getValueFactory().setValue(config.parentEnergyCost);
            genomeLengthID.getValueFactory().setValue(config.genomeLength);
            minMutationsID.getValueFactory().setValue(config.minMutations);
            maxMutationsID.getValueFactory().setValue(config.maxMutations);
            lifeGivingCorpsesID.setSelected(config.lifeGivingCorpses);
            geneSwapID.setSelected(config.geneSwap);
            dailyEnergyCostID.getValueFactory().setValue(config.dailyEnergyCost);
        });
    }

    private void validateAndStartSimulation() {
        try {
            int mapHeight = mapHeightID.getValue();
            if (mapHeight < 5) throw new IllegalArgumentException("Wysokość mapy musi wynosić co najmniej 5");
            int mapWidth = mapWidthID.getValue();
            if (mapWidth < 5) throw new IllegalArgumentException("Szerokość mapy musi wynosić co najmniej 5");
            int initialPlantCount = initialPlantCountID.getValue();
            if (initialPlantCount > mapHeight * mapWidth)
                throw new IllegalArgumentException("Startowa ilość roślin jest większa niż ilość wszystkich pól");
            int dailyPlantGrowth = dailyPlantGrowthID.getValue();
            if (dailyPlantGrowth > mapHeight * mapWidth)
                throw new IllegalArgumentException("Ilość roślin wyrastająca każdego dnia jest większa niż ilość wszystkich pól");
            int parentEnergyCost = parentEnergyCostID.getValue();
            int energyToBeFed = energyToBeFedID.getValue();
            if (parentEnergyCost > energyToBeFed)
                throw new IllegalArgumentException("Koszt energii stworzenia potomka jest większy niż energia potrzebna do romnażania się");
            int minMutations = minMutationsID.getValue();
            int maxMutations = maxMutationsID.getValue();
            if (minMutations > maxMutations)
                throw new IllegalArgumentException("Minimalna ilość mutacji jest większa od maksymalnej ilości mutacji");

            config.mapHeight = mapHeight;
            config.mapWidth = mapWidth;
            config.initialPlantCount = initialPlantCount;
            config.dailyPlantGrowth = dailyPlantGrowth;
            config.plantEnergy = plantEnergyID.getValue();
            config.initialAnimalCount = initialAnimalCountID.getValue();
            config.initialAnimalEnergy = initialAnimalEnergyID.getValue();
            config.energyToBeFed = energyToBeFed;
            config.parentEnergyCost = parentEnergyCost;
            config.genomeLength = genomeLengthID.getValue();
            config.minMutations = minMutations;
            config.maxMutations = maxMutations;
            config.lifeGivingCorpses = lifeGivingCorpsesID.isSelected();
            config.geneSwap = geneSwapID.isSelected();
            config.saveStats = statSaveBoxID.isSelected();
            config.refreshRate = refreshRateID.getValue();
            config.dailyEnergyCost = dailyEnergyCostID.getValue();

            boolean saveBox = saveBoxID.isSelected();
            int saveNumber = saveNumberID.getValue();


            if (saveBox) {
                CSVManager manager = new CSVManager();
                manager.setConfig(config);
                if (!manager.saveConfig(saveNumber))
                    throw new IllegalArgumentException("Podane ID jest już zajęte");
                showAlert("Dane zapisane poprawnie");
                saveBoxID.setSelected(false);
            }

            threadPool.execute(() -> {
                try {
                    Platform.runLater(() -> {
                        try {
                            new SimulationApp(config).start(new Stage());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        loadButton.setOnAction(event -> {
            CSVManager manager = new CSVManager();
            try {
                boolean loaded = manager.loadConfig(initialID.getValue());
                if (loaded) {
                    config = manager.getConfig();
                    fillUIFromConfig();
                    showAlert("Dane załadowane poprawnie");
                } else {
                    showError("Podane ID nie istnieje");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        startButton.setOnAction(event -> validateAndStartSimulation());
    }

    public void shutdownThreadPool() {
        threadPool.shutdown();
    }
}
