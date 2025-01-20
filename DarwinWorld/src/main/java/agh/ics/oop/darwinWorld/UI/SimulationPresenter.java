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

public class SimulationPresenter {
    private WorldMap map;
    private int gridsize;
    private Config config;
    private boolean simulationStopped = false;
    private Animal selectedAnimal = null;
    @FXML private GridPane mapGrid;
    @FXML private Button runButton;
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

    @FXML
    public void initialize() {
        this.gridsize = Math.min(900 / config.mapHeight, 1600 / config.mapWidth);
        this.map = (config.lifeGivingCorpses) ? new CorpseMap(config) : new JungleMap(config);
        map.spawnGrass(config.initialPlantCount);
        drawGrid();

        runButton.setOnAction(event -> toggleSimulation());
        geneButton.setOnAction(event -> drawAnimalsWithMostPopularGene());
        greenButton.setOnAction(event -> drawGreenFields());

    }

    private void drawGreenFields() {
        if (simulationStopped) {
            mapGrid.getChildren().clear();
            for (int row = 0; row < config.mapHeight; row++) {
                for (int col = 0; col < config.mapWidth; col++) {
                    Vector2d position = new Vector2d(col, row);
                    StackPane cell = new StackPane();
                    cell.setAlignment(Pos.CENTER);
                    if (map.getJunglePositions().contains(position)) {
                        Rectangle tile = new Rectangle(gridsize, gridsize, Color.rgb(60, 105, 48));
                        cell.getChildren().add(tile);
                        cell.setOnMouseClicked(event -> onCellClicked(position));

                    } else {
                        Rectangle tile = new Rectangle(gridsize, gridsize, Color.rgb(160, 231, 104));
                        cell.getChildren().add(tile);
                        cell.setOnMouseClicked(event -> onCellClicked(position));

                    }
                    if (map.getGrasses().containsKey(position)) {
                        Circle circle = new Circle((double) gridsize / 3, Color.rgb(0, 255, 0)); // Fill color
                        circle.setStroke(Color.GRAY);
                        circle.setStrokeWidth(1);
                        cell.getChildren().add(circle);
                    }
                    if (map.getDailyAnimals().containsKey(position)) {
                        LinkedList<Animal> animals = map.getDailyAnimals().get(position);
                        Animal animal = animals.getFirst();
                        Circle circle = new Circle((double) gridsize / 2, animal.getColor());
                        cell.getChildren().add(circle);
                    }
                    mapGrid.add(cell, col, config.mapHeight - row - 1);
                }
            }
        }
    }

    private void drawAnimalsWithMostPopularGene() {
        if (simulationStopped) {
            mapGrid.getChildren().clear();
            for (int row = 0; row < config.mapHeight; row++) {
                for (int col = 0; col < config.mapWidth; col++) {
                    Vector2d position = new Vector2d(col, row);
                    StackPane cell = new StackPane();
                    cell.setAlignment(Pos.CENTER);
                    Rectangle tile = new Rectangle(gridsize, gridsize, Color.rgb(160, 231, 104));
                    cell.getChildren().add(tile);
                    cell.setOnMouseClicked(event -> onCellClicked(position));
                    if (map.getGrasses().containsKey(position)) {
                        Circle circle = new Circle((double) gridsize / 3, Color.rgb(0, 255, 0)); // Fill color
                        circle.setStroke(Color.GRAY);
                        circle.setStrokeWidth(1);
                        cell.getChildren().add(circle);
                    }
                    if (map.getDailyAnimals().containsKey(position)) {
                        LinkedList<Animal> animals = map.getDailyAnimals().get(position);
                        Animal animal = animals.getFirst();
                        boolean hasCommonGenes = false;
                        for (Integer gene : animal.getGenes().getGenes()) {
                            if (map.getMostPopularGen().contains(gene)) {
                                hasCommonGenes = true;
                                break;
                            }
                        }
                        if (hasCommonGenes) {
                            Circle circle = new Circle((double) gridsize / 2, Color.rgb(148, 0, 211));
                            cell.getChildren().add(circle);
                        } else {
                            Circle circle = new Circle((double) gridsize / 2, animal.getColor());
                            cell.getChildren().add(circle);
                        }
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
                Rectangle tile = new Rectangle(gridsize, gridsize, Color.rgb(160, 231, 104));
                cell.getChildren().add(tile);
                cell.setOnMouseClicked(event -> onCellClicked(position));
                if (map.getGrasses().containsKey(position)) {
                    Circle circle = new Circle((double) gridsize / 3, Color.rgb(0, 255, 0)); // Fill color
                    circle.setStroke(Color.GRAY);
                    circle.setStrokeWidth(1);
                    cell.getChildren().add(circle);
                }
                if (map.getDailyAnimals().containsKey(position)) {
                    LinkedList<Animal> animals = map.getDailyAnimals().get(position);
                    Animal animal = animals.getFirst();
                    Circle circle = new Circle((double) gridsize / 2, animal.getColor());
                    cell.getChildren().add(circle);
                }
                mapGrid.add(cell, col, config.mapHeight - row - 1);
            }
        }
        if (!simulationStopped) {
            PauseTransition pause = new PauseTransition(Duration.millis(60));  // Adjust the duration as needed
            pause.setOnFinished(event -> updateMap());
            pause.play();
        }
    }

    private void drawStats() {
        animalsCount.setText("Total number of animals: " + map.getAnimalsCount());
        grassCount.setText("Total number of plants: " + map.getGrassCount());
        freeFields.setText("Number of free fields: " + map.getFreeFields());
        avgAnimalEnergy.setText("Average energy level: " + (int) Math.round(map.getAvgAnimalEnergy()));
        avgDaysAlive.setText("Average days alive: " + (int) Math.round(map.getAvgDaysAlive()));
        avgChildCount.setText("Average number of children: " + (int) Math.round(map.getAvgChildCount()));
        mostPopularGen.setText("Most popular genotype: " + map.getMostPopularGen().toString());
    }

    private void drawAnimalStats() {
        if (selectedAnimal != null) {
            obsGens.setText("Genome: " + selectedAnimal.getGenes().getGenes().toString());
            obsCurrGen.setText("Current Gene: " + selectedAnimal.getGenes().getCurrentGene());
            obsEnergy.setText("Energy amount: " + selectedAnimal.getEnergy());
            obsGrassCount.setText("Number of plants eaten: " + selectedAnimal.getEatenGrass());
            obsChildCount.setText("Number of children: " + selectedAnimal.getChildrenAmount());
            obsDescendantsCount.setText("Number of descedants: " + selectedAnimal.getDescendantsAmount());
            obsDaysAlive.setText("Days Alive: " + selectedAnimal.getDaysAlive());
            if (selectedAnimal.getEnergy() > 0) {
                obsFuneralDay.setText("Funeral Day: -");
            } else {
                obsFuneralDay.setText("Funeral Day: " + selectedAnimal.getFuneralDay());
            }
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
    }
}
