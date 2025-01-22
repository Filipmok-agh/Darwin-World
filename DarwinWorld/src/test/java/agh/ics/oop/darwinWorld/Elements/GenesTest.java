package agh.ics.oop.darwinWorld.Elements;

import agh.ics.oop.darwinWorld.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenesTest {

    private Config config;

    @BeforeEach
    void setup() {
        config = new Config();
        config.initialAnimalEnergy = 100;
        config.genomeLength = 10;
        config.minMutations = 1;
        config.maxMutations = 3;
        config.geneSwap = true;
    }

    @Test
    void testInitialGenesRandomConstructor() {
        Genes genes = new Genes(config);
        assertEquals(config.genomeLength, genes.getGenes().size());

        for (int gene : genes.getGenes()) {
            assertTrue(gene >= 0 && gene < 8);
        }
    }

    @Test
    void testGeneInheritance() {
        Animal parent1 = new Animal(new Vector2d(2, 2), config);
        Animal parent2 = new Animal(new Vector2d(2, 2), config);


        Genes childGenes = new Genes(parent1, parent2);

        assertEquals(config.genomeLength, childGenes.getGenes().size());
    }

    @Test
    void testLoopThroughGenes() {
        Genes genes = new Genes(config);
        Integer firstGene = genes.getCurrentGene();
        for (int i = 0; i < config.genomeLength; i++) {
            genes.nextGene();
        }
        assertEquals(firstGene, genes.getCurrentGene());
    }
    @Test
    void testGetIndex(){
        Genes genes = new Genes(config);
        assertTrue(genes.getIndex() >= 0 && genes.getIndex() < config.genomeLength);
    }
}