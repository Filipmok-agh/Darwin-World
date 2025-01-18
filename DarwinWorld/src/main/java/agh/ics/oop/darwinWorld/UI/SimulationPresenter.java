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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.LinkedList;

import static agh.ics.oop.darwinWorld.Config.*;
import static agh.ics.oop.darwinWorld.Config.dailyPlantGrowth;

public class SimulationPresenter {
    @FXML
    private GridPane mapGrid;
    private WorldMap map;
    private int gridsize;

    @FXML
    public void initialize() {
        this.gridsize = Math.min(900/mapHeight,1600/mapWidth);
        this.map = (Config.lifeGivingCorpses) ? new CorpseMap() : new JungleMap();
        map.addInitialAnimals();
        map.spawnGrass(initialPlantCount);
        drawGrid();
    }

    private void drawGrid() {
        mapGrid.getChildren().clear();
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                Vector2d position = new Vector2d(col, row);
                StackPane cell = new StackPane();
                cell.setAlignment(Pos.CENTER);
                if (map.getJunglePositions().contains(position)) {
                    Rectangle tile = new Rectangle(gridsize, gridsize, Color.rgb(60,105,48));
                    cell.getChildren().add(tile);
                }
                else {
                    Rectangle tile = new Rectangle(gridsize, gridsize, Color.rgb(160, 231, 104));
                    cell.getChildren().add(tile);
                }
                if (map.getGrasses().containsKey(position)) {
                    Circle circle = new Circle((double) gridsize /3, Color.rgb(0, 255, 0));
                    cell.getChildren().add(circle);
                }
                if (map.getDailyAnimals().containsKey(position)) {
                    LinkedList<Animal> animals = map.getDailyAnimals().get(position);
                    Animal animal = animals.getFirst();
                    Circle circle = new Circle((double) gridsize /2, animal.getColor());
                    cell.getChildren().add(circle);
                }
                mapGrid.add(cell, col, mapHeight - row - 1);
            }
        }
        PauseTransition pause = new PauseTransition(Duration.millis(100)); // Adjust the duration as needed
        pause.setOnFinished(event -> UpdateMap());
        pause.play();
    }

    private void UpdateMap(){
        map.removeDeadAnimals();
        map.animalsMovement();
        map.animalActivities();
        map.spawnGrass(dailyPlantGrowth);
        drawGrid();
    }
}
