package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Config;
import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class CorpseMapTest {
    private CorpseMap corpseMap;
    private Config config;

    @BeforeEach
    void setUp() {
        config = new Config();
        config.mapWidth = 10;
        config.mapHeight = 5;
        config.initialPlantCount = 10;
        config.initialAnimalCount = 40;
        config.initialAnimalEnergy = 15;
        config.dailyPlantGrowth = 40;
        config.dailyEnergyCost = 10;
        config.energyToBeFed = 50;
        config.genomeLength = 4;
        config.minMutations = 1;
        config.maxMutations = 3;
        corpseMap = new CorpseMap(config);
    }

    @Test
    void testSpawnGrass() {
        corpseMap.spawnGrass(10);
        assertEquals(2, corpseMap.getGrassCount());
    }

    @Test
    void testSpawnInitialGrass() {
        int initialGrassCount = config.initialPlantCount;
        corpseMap.spawnGrass(initialGrassCount);
        assertEquals(initialGrassCount/5, corpseMap.getGrassCount());
    }

    @Test
    void testAnimalActivities() {
        corpseMap.spawnGrass(config.initialPlantCount);
        corpseMap.spawnGrass(config.dailyPlantGrowth);
        corpseMap.animalsMovement();
        corpseMap.animalActivities();
        corpseMap.animalsMovement();
        assertTrue(corpseMap.getAnimalsCount() >= config.initialAnimalCount);
    }

    @Test
    void testDayProgression() {
        assertEquals(0, corpseMap.getDay());
        corpseMap.animalsMovement();
        assertEquals(1, corpseMap.getDay());
    }

    @Test
    void testMetricsCalculation() {
        corpseMap.animalsMovement();
        double avgEnergy = corpseMap.getAvgAnimalEnergy();
        assertTrue(avgEnergy <= config.initialAnimalEnergy);

        double avgDaysAlive = corpseMap.getAvgDaysAlive();
        assertTrue(avgDaysAlive >= 0);

        double avgChildCount = corpseMap.getAvgChildCount();
        assertEquals(0, avgChildCount);
    }

    @Test
    void testMostPopularGen() {
        ArrayList<Integer> mostPopularGen = corpseMap.getMostPopularGen();
        assertNotNull(mostPopularGen);
        assertFalse(mostPopularGen.isEmpty());
    }

    @Test
    void testGetJunglePositions() {
        ArrayList<Vector2d> junglePositions = corpseMap.getJunglePositions();
        assertNotNull(junglePositions);
        assertTrue(junglePositions.isEmpty());
    }

    @Test
    void testGetDailyAnimals() {
        corpseMap.animalsMovement();
        HashMap<Vector2d, LinkedList<Animal>> dailyAnimals = corpseMap.getDailyAnimals();
        assertNotNull(dailyAnimals);
        assertFalse(dailyAnimals.isEmpty());
    }

    @Test
    void testGetGrasses() {
        corpseMap.spawnGrass(config.initialPlantCount);
        HashMap<Vector2d, Grass> grasses = corpseMap.getGrasses();
        assertNotNull(grasses);
        assertFalse(grasses.isEmpty());
    }

    @Test
    void testRemoveDeadAnimals() {
        corpseMap.animalsMovement();
        corpseMap.animalsMovement();
        corpseMap.animalsMovement();
        corpseMap.removeDeadAnimals();
        assertEquals(0, corpseMap.getAnimalsCount());
    }

    @Test
    void testCountAvgChildCountAndAvgLifeDuration(){
        corpseMap.countAvgChildCountAndAvgLifeDuration();
        assertEquals(0, corpseMap.getAvgChildCount());
        assertEquals(0, corpseMap.getAvgDaysAlive());
    }
}
