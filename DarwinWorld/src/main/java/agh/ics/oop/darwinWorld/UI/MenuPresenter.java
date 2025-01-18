package agh.ics.oop.darwinWorld.UI;

import agh.ics.oop.darwinWorld.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;

import java.io.IOException;

public class MenuPresenter
{
    @FXML
    private Spinner<Integer> initialID;
    @FXML
    private Button loadButton;
    @FXML
    private Spinner<Integer> saveNumberID;
    @FXML
    private CheckBox saveBoxID;
    @FXML
    private Button startButton;
    @FXML
    private Spinner<Integer> mapHeightID;
    @FXML
    private Spinner<Integer> initialPlantCountID;
    @FXML
    private Spinner<Integer> mapWidthID;
    @FXML
    private Spinner<Integer> dailyPlantGrowthID;
    @FXML
    private Spinner<Integer> plantEnergyID;
    @FXML
    private Spinner<Integer> initialAnimalCountID;
    @FXML
    private Spinner<Integer> initialAnimalEnergyID;
    @FXML
    private Spinner<Integer> energyToBeFedID;
    @FXML
    private Spinner<Integer> parentEnergyCostID;
    @FXML
    private Spinner<Integer> genomeLengthID;
    @FXML
    private Spinner<Integer> minMutationsID;
    @FXML
    private Spinner<Integer> maxMutationsID;
    @FXML
    private CheckBox lifeGivingCorpsesID;
    @FXML
    private CheckBox geneSwapID;

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd konfiguracji");
        alert.setHeaderText("Nieprawidłowe ustawienia");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAllert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Dane");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void fillUIFromConfig() {
        mapHeightID.getValueFactory().setValue(Config.mapHeight);
        mapWidthID.getValueFactory().setValue(Config.mapWidth);
        initialPlantCountID.getValueFactory().setValue(Config.initialPlantCount);
        dailyPlantGrowthID.getValueFactory().setValue(Config.dailyPlantGrowth);
        plantEnergyID.getValueFactory().setValue(Config.plantEnergy);
        initialAnimalCountID.getValueFactory().setValue(Config.initialAnimalCount);
        initialAnimalEnergyID.getValueFactory().setValue(Config.initialAnimalEnergy);
        energyToBeFedID.getValueFactory().setValue(Config.energyToBeFed);
        parentEnergyCostID.getValueFactory().setValue(Config.parentEnergyCost);
        genomeLengthID.getValueFactory().setValue(Config.genomeLength);
        minMutationsID.getValueFactory().setValue(Config.minMutations);
        maxMutationsID.getValueFactory().setValue(Config.maxMutations);
        lifeGivingCorpsesID.setSelected(Config.lifeGivingCorpses);
        geneSwapID.setSelected(Config.geneSwap);
    }


    @FXML
    void initialize() {
        loadButton.setOnAction(event -> {
            CSVManager manager = new CSVManager();
            try {
                boolean loaded = manager.loadConfig(initialID.getValue());
                if (loaded)
                {

                    fillUIFromConfig();
                    showAllert("Dane załadowane poprawnie");
                }
                else {
                    showError("Podane ID nie istnieje");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        startButton.setOnAction(event -> {
            int mapHeight = mapHeightID.getValue();
            if (mapHeight <5)
            {
                showError("Wysokość mapy musi wynosić co najmniej 5");
                return;
            }
            int mapWidth = mapWidthID.getValue();
            if (mapWidth <5)
            {
                showError("Szerokość mapy musi wynosić co najmniej 5");
            }
            int initialPlantCount = initialPlantCountID.getValue();
            if (initialPlantCount > mapHeight*mapWidth)
            {
                showError("Startowa ilość roślin jest większa niż ilość wszystkich pól");
                return;
            }
            int dailyPlantGrowth = dailyPlantGrowthID.getValue();
            if (dailyPlantGrowth > mapHeight*mapWidth)
            {
                showError("Ilość roślin wyrastająca każdego dnia jest większa niż ilość wszystkich pól");
                return;
            }
            int plantEnergy = plantEnergyID.getValue();
            int initialAnimalCount = initialAnimalCountID.getValue();
            int initialAnimalEnergy = initialAnimalEnergyID.getValue();
            int energyToBeFed = energyToBeFedID.getValue();
            int parentEnergyCost = parentEnergyCostID.getValue();
            if (parentEnergyCost>energyToBeFed)
            {
                showError("Koszt energii stworzenia potomka jest większy niż energia potrzebna do romnażania się");
                return;
            }
            int genomeLength = genomeLengthID.getValue();
            int minMutations = minMutationsID.getValue();
            int maxMutations = maxMutationsID.getValue();
            if (minMutations>maxMutations)
            {
                showError("Minimalna ilość mutacji jest większa od maksymalnej ilości mutacji");
                return;
            }
            boolean lifeGivingCorpses = lifeGivingCorpsesID.isSelected();
            boolean geneSwap = geneSwapID.isSelected();
            boolean saveBox = saveBoxID.isSelected();
            int saveNumber = saveNumberID.getValue();


            Config.mapHeight = mapHeight;
            Config.mapWidth = mapWidth;
            Config.initialPlantCount = initialPlantCount;
            Config.dailyPlantGrowth = dailyPlantGrowth;
            Config.plantEnergy = plantEnergy;
            Config.initialAnimalCount = initialAnimalCount;
            Config.initialAnimalEnergy = initialAnimalEnergy;
            Config.energyToBeFed = energyToBeFed;
            Config.parentEnergyCost = parentEnergyCost;
            Config.genomeLength = genomeLength;
            Config.minMutations = minMutations;
            Config.maxMutations = maxMutations;
            Config.lifeGivingCorpses = lifeGivingCorpses;
            Config.geneSwap = geneSwap;

            if (saveBox)
            {
                CSVManager manager = new CSVManager();
                try {
                    boolean saved = manager.saveConfig(saveNumber);
                    if (saved)
                    {
                        fillUIFromConfig();
                        showAllert("Dane zapisane poprawnie");
                        showAllert("Symulacja rozpoczęta");
//                        startSimulation()
                    }
                    else {
                        showError("Podane ID jest już zajęte");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                showAllert("Symulacja rozpoczęta");
//                startSimulation()
            }

        });
    }

}
