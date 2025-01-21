package agh.ics.oop.darwinWorld.Elements;

import agh.ics.oop.darwinWorld.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest
{
    private Config config;
    private Animal animal;

    @BeforeEach
    void setUp() {
        config = new Config();
        config.dailyEnergyCost = 10;
        config.mapHeight = 10;
        config.mapWidth = 10;
        config.initialPlantCount = 20;
        config.plantEnergy = 50;
        config.dailyPlantGrowth = 5;
        config.lifeGivingCorpses = false;
        config.initialAnimalCount = 5;
        config.initialAnimalEnergy = 100;
        config.energyToBeFed = 80;
        config.parentEnergyCost = 20;
        config.minMutations = 1;
        config.maxMutations = 5;
        config.geneSwap = true;
        config.genomeLength = 8;
        config.saveStats = false;

        animal = new Animal(new Vector2d(5, 5), config);
    }

    @Test
    void testMove() {
        int initialEnergy = animal.getEnergy();
        int initialDaysAlive = animal.getDaysAlive();

        animal.move();
        assertEquals(initialEnergy - config.dailyEnergyCost, animal.getEnergy());
        assertEquals(initialDaysAlive+1, animal.getDaysAlive());
    }

    @Test
    void testEat() {
        int initialEnergy = animal.getEnergy();
        int initialEatenGrass = animal.getEatenGrass();

        animal.eat();
        assertEquals(initialEnergy + config.plantEnergy, animal.getEnergy());
        assertEquals(initialEatenGrass + 1, animal.getEatenGrass());
    }

    @Test
    void testBreeding() {
        Animal parent2 = new Animal(new Vector2d(6, 6), config);

        Animal child = animal.breeding(parent2);

        assertNotNull(child);
        assertTrue(animal.getChildrenAmount() > 0);
        assertTrue(parent2.getChildrenAmount() > 0);
        assertEquals(config.parentEnergyCost, 100 - animal.getEnergy());
        assertEquals(config.parentEnergyCost, 100 - parent2.getEnergy());
    }

    @Test
    void testIsStronger() {
        Animal weakerAnimal = new Animal(new Vector2d(6, 6), config);
        weakerAnimal.move(); // Reduce energy by daily cost

        assertTrue(animal.isStronger(weakerAnimal));
    }

    @Test
    void testGetDescendantsAmount() {
        Animal child1 = animal.breeding(new Animal(new Vector2d(6, 6), config));
        Animal child2 = child1.breeding(new Animal(new Vector2d(7, 7), config));

        assertEquals(2, animal.getDescendantsAmount());
    }

}