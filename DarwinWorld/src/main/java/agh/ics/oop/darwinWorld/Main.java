package agh.ics.oop.darwinWorld;

import agh.ics.oop.darwinWorld.Simulation.Simulation;
import agh.ics.oop.darwinWorld.UI.CSVManager;
import agh.ics.oop.darwinWorld.UI.MenuApp;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
//        Application.launch(MenuApp.class);
        Config.mapHeight = 13;
        Config.mapWidth = 13;
        Config.initialPlantCount = 10;
        Config.dailyPlantGrowth = 6;
        Config.plantEnergy = 50;
        Config.initialAnimalCount = 10;
        Config.initialAnimalEnergy = 200;
        Config.energyToBeFed = 40;
        Config.parentEnergyCost = 20;
        Config.genomeLength = 7;
        Config.minMutations = 1;
        Config.maxMutations = 3;
        Config.lifeGivingCorpses = false;
        Config.geneSwap = false;
        Simulation simulation = new Simulation();
        simulation.run();
    }
}