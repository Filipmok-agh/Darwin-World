package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public interface WorldMap {
    void removeDeadAnimals();

    void animalsMovement();

    void animalActivities();

    void spawnGrass(Integer amount);

    ArrayList<Vector2d> getJunglePositions();

    HashMap<Vector2d, LinkedList<Animal>> getDailyAnimals();

    HashMap<Vector2d, Grass> getGrasses();

    int getAnimalsCount();

    int getGrassCount();

    int getFreeFields();

    double getAvgAnimalEnergy();

    double getAvgDaysAlive();

    double getAvgChildCount();

    ArrayList<Integer> getMostPopularGen();

    int getDay();

    void countAvgChildCountAndAvgLifeDuration();
}
