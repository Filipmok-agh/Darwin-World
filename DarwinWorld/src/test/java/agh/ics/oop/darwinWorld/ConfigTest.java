package agh.ics.oop.darwinWorld;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigTest {
    @Test
    void testToString() {
        Config config = new Config();
        config.initialAnimalEnergy = 100;
        config.genomeLength = 10;
        config.minMutations = 1;
        config.maxMutations = 3;
        config.geneSwap = true;
        assertEquals("Config{dailyEnergyCost=5, mapHeight=0, mapWidth=0, initialPlantCount=0, plantEnergy=0, dailyPlantGrowth=0, lifeGivingCorpses=false, initialAnimalCount=0, initialAnimalEnergy=100, energyToBeFed=0, parentEnergyCost=0, minMutations=1, maxMutations=3, geneSwap=true, genomeLength=10, saveStats=false}", config.toString());
    }
}