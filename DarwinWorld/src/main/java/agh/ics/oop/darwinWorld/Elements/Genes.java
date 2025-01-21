package agh.ics.oop.darwinWorld.Elements;

import agh.ics.oop.darwinWorld.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Genes {


    private final ArrayList<Integer> genes = new ArrayList<>();
    private int currentGeneIndex;
    private final int genomeLength;
    private Config config;


    public Genes(Animal parent1, Animal parent2) {
        this.config = parent1.getConfig();
        this.genomeLength = config.genomeLength;
        Random random = new Random();
        currentGeneIndex = random.nextInt(genomeLength);

        Genes parent1Genes = parent1.getGenes();
        Genes parent2Genes = parent2.getGenes();
        int genesFromParent1 = parent1.getEnergy() * genomeLength / (parent2.getEnergy() + parent1.getEnergy());
        if (random.nextBoolean()) {
            this.genes.addAll(parent1Genes.getLeftGenes(genesFromParent1));
            this.genes.addAll(parent2Genes.getRightGenes(genesFromParent1));
        } else {
            int genesFromParent2 = genomeLength - genesFromParent1;
            this.genes.addAll(parent2Genes.getLeftGenes(genesFromParent2));
            this.genes.addAll(parent1Genes.getRightGenes(genesFromParent2));
        }
        int mutationsNumber = random.nextInt(config.minMutations, config.maxMutations + 1);
        if (mutationsNumber != 0) {
            for (int i = 0; i < mutationsNumber; i++) {
                mutation();
            }
        }
    }

    public Genes(Config config) {
        this.genomeLength = config.genomeLength;
        Random random = new Random();
        currentGeneIndex = random.nextInt(genomeLength);
        for (int i = 0; i < genomeLength; i++) {
            int randomGen = random.nextInt(8);
            this.genes.add(randomGen);
        }
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public void nextGene() {
        if (currentGeneIndex == genomeLength - 1) {
            currentGeneIndex = 0;
        } else {
            currentGeneIndex++;
        }
    }

    public int getCurrentGene() {
        return this.genes.get(currentGeneIndex);
    }

    private void mutation() {
        if (config.geneSwap && new Random().nextBoolean()) {
            swapMutation();
        } else {
            randomMutation();
        }
    }

    private void swapMutation() {
        Random random = new Random();
        int randomIndex1 = random.nextInt(genomeLength);
        int randomIndex2 = random.nextInt(genomeLength);
        int temp = this.genes.get(randomIndex1);
        this.genes.set(randomIndex1, this.genes.get(randomIndex2));
        this.genes.set(randomIndex2, temp);
    }

    private void randomMutation() {
        Random random = new Random();
        int randomIndex1 = random.nextInt(genomeLength);
        int randomGen = random.nextInt(8);
        this.genes.set(randomIndex1, randomGen);
    }

    private List<Integer> getLeftGenes(Integer num) {
        return this.genes.subList(0, num);
    }

    private List<Integer> getRightGenes(Integer num) {
        return this.genes.subList(num, genomeLength);
    }
}