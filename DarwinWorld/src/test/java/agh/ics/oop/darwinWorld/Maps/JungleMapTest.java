package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Config;
import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class JungleMapTest {

    private JungleMap jungleMap;
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
        jungleMap = new JungleMap(config);
    }

    @Test
    void testSpawnGrass() {
        jungleMap.spawnGrass(10);
        assertEquals(10, jungleMap.getGrassCount());
    }

    @Test
    void testSpawnInitialGrass() {
        int initialGrassCount = config.initialPlantCount;
        jungleMap.spawnGrass(initialGrassCount);
        assertEquals(initialGrassCount, jungleMap.getGrassCount());
    }

    @Test
    void testAnimalActivities() {
        jungleMap.spawnGrass(config.initialPlantCount);
        jungleMap.spawnGrass(config.dailyPlantGrowth);
        jungleMap.animalsMovement();
        jungleMap.animalActivities();
        jungleMap.animalsMovement();
        assertTrue(jungleMap.getAnimalsCount() >= config.initialAnimalCount);
    }

    @Test
    void testDayProgression() {
        assertEquals(0, jungleMap.getDay());
        jungleMap.animalsMovement();
        assertEquals(1, jungleMap.getDay());
    }

    @Test
    void testMetricsCalculation() {
        jungleMap.animalsMovement();
        double avgEnergy = jungleMap.getAvgAnimalEnergy();
        assertTrue(avgEnergy <= config.initialAnimalEnergy);

        double avgDaysAlive = jungleMap.getAvgDaysAlive();
        assertTrue(avgDaysAlive >= 0);

        double avgChildCount = jungleMap.getAvgChildCount();
        assertEquals(0, avgChildCount);
    }

    @Test
    void testMostPopularGen() {
        ArrayList<Integer> mostPopularGen = jungleMap.getMostPopularGen();
        assertNotNull(mostPopularGen);
        assertFalse(mostPopularGen.isEmpty());
    }

    @Test
    void testGetJunglePositions() {
        ArrayList<Vector2d> junglePositions = jungleMap.getJunglePositions();
        assertNotNull(junglePositions);
        assertFalse(junglePositions.isEmpty());
    }

    @Test
    void testGetDailyAnimals() {
        jungleMap.animalsMovement();
        HashMap<Vector2d, LinkedList<Animal>> dailyAnimals = jungleMap.getDailyAnimals();
        assertNotNull(dailyAnimals);
        assertFalse(dailyAnimals.isEmpty());
    }

    @Test
    void testGetGrasses() {
        jungleMap.spawnGrass(config.initialPlantCount);
        HashMap<Vector2d, Grass> grasses = jungleMap.getGrasses();
        assertNotNull(grasses);
        assertFalse(grasses.isEmpty());
    }

    @Test
    void testRemoveDeadAnimals() {
        jungleMap.animalsMovement();
        jungleMap.animalsMovement();
        jungleMap.animalsMovement();
        jungleMap.removeDeadAnimals();
        assertEquals(0, jungleMap.getAnimalsCount());
    }
}
