package agh.ics.oop.darwinWorld.Simulation;

import agh.ics.oop.darwinWorld.Config;
import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import agh.ics.oop.darwinWorld.Maps.CorpseMap;
import agh.ics.oop.darwinWorld.Maps.JungleMap;
import agh.ics.oop.darwinWorld.Maps.WorldMap;

import java.util.ArrayList;
import java.util.Random;

import static agh.ics.oop.darwinWorld.Config.*;

public class Simulation {
    public WorldMap worldMap;
    public Simulation(){
        this.worldMap = (Config.lifeGivingCorpses) ? new CorpseMap() : new JungleMap();
        this.worldMap.addInitialAnimals();
    }

    public void run() {
        worldMap.spawnGrass(initialPlantCount);
        do {
            worldMap.removeDeadAnimals();
            worldMap.animalsMovement();
            worldMap.animalActivities();
            worldMap.spawnGrass(dailyPlantGrowth);
        } while (true);
    }
}
