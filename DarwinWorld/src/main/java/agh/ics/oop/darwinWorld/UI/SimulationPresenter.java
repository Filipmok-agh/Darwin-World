package agh.ics.oop.darwinWorld.UI;

import agh.ics.oop.darwinWorld.Config;
import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import agh.ics.oop.darwinWorld.Maps.CorpseMap;
import agh.ics.oop.darwinWorld.Maps.JungleMap;
import agh.ics.oop.darwinWorld.Maps.WorldMap;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.Button;

import java.util.LinkedList;
import java.util.UUID;

public class SimulationPresenter {
    private WorldMap map;
    private int gridSize;
    private Config config;
    private boolean simulationStopped = false;
    private Animal selectedAnimal = null;
    private final String uniqueId = UUID.randomUUID().toString();
    private StatsManager statsManager;
    boolean isGreenFieldsVisible = false;
    @FXML private GridPane mapGrid;
    @FXML private Button runButton;
    @FXML private Label day;
    @FXML private Label animalsCount;
    @FXML private Label grassCount;
    @FXML private Label freeFields;
    @FXML private Label mostPopularGen;
    @FXML private Label avgAnimalEnergy;
    @FXML private Label avgDaysAlive;
    @FXML private Label avgChildCount;
    @FXML private Label obsGens;
    @FXML private Label obsCurrGen;
    @FXML private Label obsEnergy;
    @FXML private Label obsGrassCount;
    @FXML private Label obsChildCount;
    @FXML private Label obsDescendantsCount;
    @FXML private Label obsDaysAlive;
    @FXML private Label obsFuneralDay;
    @FXML private Button geneButton;
    @FXML private Button greenButton;

    public void setConfig(Config config) {
        this.config = config;
    }

    public void stopSimulation() {
        simulationStopped = true;
    }

    @FXML
    private void initialize() {
        this.gridSize = Math.min(900 / config.mapHeight, 1600 / config.mapWidth);
        this.map = (config.lifeGivingCorpses) ? new CorpseMap(config) : new JungleMap(config);
        map.spawnGrass(config.initialPlantCount);
        this.statsManager = new StatsManager(this.map, uniqueId);
        drawGrid();

        runButton.setOnAction(event -> toggleSimulation());
        geneButton.setOnAction(event -> drawAnimalsWithMostPopularGene());
        greenButton.setOnAction(event -> {
            isGreenFieldsVisible = !isGreenFieldsVisible;
        });
    }

    private void drawAnimalsWithMostPopularGene() {
        if (simulationStopped) {
            mapGrid.getChildren().clear();
            for (int row = 0; row < config.mapHeight; row++) {
                for (int col = 0; col < config.mapWidth; col++) {
                    Vector2d position = new Vector2d(col, row);
                    StackPane cell = new StackPane();
                    cell.setAlignment(Pos.CENTER);
                    Rectangle tile = new Rectangle(gridSize, gridSize, Color.rgb(160, 231, 104));
                    cell.getChildren().add(tile);
                    cell.setOnMouseClicked(event -> onCellClicked(position));
                    if (map.getGrasses().containsKey(position)) {
                        Circle circle = new Circle((double) gridSize / 3, Color.rgb(0, 255, 0)); // Fill color
                        circle.setStroke(Color.GRAY);
                        circle.setStrokeWidth(1);
                        cell.getChildren().add(circle);
                    }
                    if (map.getDailyAnimals().containsKey(position)) {
                        LinkedList<Animal> animals = map.getDailyAnimals().get(position);
                        boolean hasCommonGenes = false;
                        for (Animal animal : animals) {
                            hasCommonGenes = map.getMostPopularGen().equals(animal.getGenes().getGenes());
                            if (hasCommonGenes) {
                                break;
                            }
                        }
                        Circle circle;
                        if (hasCommonGenes) {
                            circle = new Circle((double) gridSize / 2, Color.rgb(148, 0, 211));
                        } else {
                            circle = new Circle((double) gridSize / 2, animals.getFirst().getColor());
                        }
                        cell.getChildren().add(circle);
                    }
                    mapGrid.add(cell, col, config.mapHeight - row - 1);
                }
            }
        }
    }

    private void drawGrid() {
        mapGrid.getChildren().clear();
        for (int row = 0; row < config.mapHeight; row++) {
            for (int col = 0; col < config.mapWidth; col++) {
                Vector2d position = new Vector2d(col, row);
                StackPane cell = new StackPane();
                cell.setAlignment(Pos.CENTER);
                if (map.getJunglePositions().contains(position) && isGreenFieldsVisible) {
                    Rectangle tile = new Rectangle(gridSize, gridSize, Color.rgb(60, 105, 48));
                    cell.getChildren().add(tile);
                    cell.setOnMouseClicked(event -> onCellClicked(position));

                } else {
                    Rectangle tile = new Rectangle(gridSize, gridSize, Color.rgb(160, 231, 104));
                    cell.getChildren().add(tile);
                    cell.setOnMouseClicked(event -> onCellClicked(position));
                }
                if (map.getGrasses().containsKey(position)) {
                    Circle circle = new Circle((double) gridSize / 3, Color.rgb(0, 255, 0)); // Fill color
                    circle.setStroke(Color.GRAY);
                    circle.setStrokeWidth(1);
                    cell.getChildren().add(circle);
                }
                if (map.getDailyAnimals().containsKey(position)) {
                    LinkedList<Animal> animals = map.getDailyAnimals().get(position);
                    Animal animal = animals.getFirst();
                    Circle circle = new Circle((double) gridSize / 2, animal.getColor());
                    cell.getChildren().add(circle);
                }
                mapGrid.add(cell, col, config.mapHeight - row - 1);
            }
        }
        if (!simulationStopped) {
            PauseTransition pause = new PauseTransition(Duration.millis(config.refreshRate));
            pause.setOnFinished(event -> updateMap());
            pause.play();
        }
    }

    private void drawStats() {
        day.setText(statsManager.getCurrentDay());
        animalsCount.setText(statsManager.getAnimalsCount());
        grassCount.setText(statsManager.getGrassCount());
        freeFields.setText(statsManager.getFreeFields());
        avgAnimalEnergy.setText(statsManager.getAvgAnimalEnergy());
        avgDaysAlive.setText(statsManager.getAvgDaysAlive());
        avgChildCount.setText(statsManager.getAvgChildCount());
        mostPopularGen.setText(statsManager.getMostPopularGen());
    }

    private void drawAnimalStats() {
        if (selectedAnimal != null) {
            statsManager.setAnimal(selectedAnimal);
            obsGens.setText(statsManager.getGenome());
            obsCurrGen.setText(statsManager.getCurrentGene());
            obsEnergy.setText(statsManager.getEnergy());
            obsGrassCount.setText(statsManager.getPlantsEaten());
            obsChildCount.setText(statsManager.getChildrenCount());
            obsDescendantsCount.setText(statsManager.getDescendantsCount());
            obsDaysAlive.setText(statsManager.getDaysAlive());
            obsFuneralDay.setText(statsManager.getFuneralDay());
        }
    }

    private void toggleSimulation() {
        simulationStopped = !simulationStopped;
        if (simulationStopped) {
            runButton.setText("Start");
        } else {
            runButton.setText("Stop");
            drawGrid();
        }
    }

    private void onCellClicked(Vector2d position) {
        if (simulationStopped) {
            if (selectedAnimal != null) {
                selectedAnimal.setHighlight(false);
            }
            if (map.getDailyAnimals().containsKey(position)) {
                LinkedList<Animal> animals = map.getDailyAnimals().get(position);
                selectedAnimal = animals.getFirst();
                selectedAnimal.setHighlight(true);
                drawAnimalStats();
                drawGrid();
            }
        }
    }

    private void updateMap() {
        map.removeDeadAnimals();
        map.animalsMovement();
        if (map.getAnimalsCount() == 0) {
            simulationStopped = true;
            runButton.disabledProperty();
        } else {
            map.animalActivities();
            map.spawnGrass(config.dailyPlantGrowth);
        }
        drawGrid();
        drawStats();
        drawAnimalStats();
        map.countAvgChildCountAndAvgLifeDuration();
        if (config.saveStats) {
            statsManager.saveStatsToCSV();
        }
    }
}