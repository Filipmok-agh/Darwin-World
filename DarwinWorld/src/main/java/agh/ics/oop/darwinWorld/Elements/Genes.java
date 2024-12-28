package agh.ics.oop.darwinWorld.Animals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static agh.ics.oop.darwinWorld.Config.*;

public class Genes
{
    private final ArrayList<Integer> genes = new ArrayList<>();
    private int index;
    private final int length;

    public Genes(Animal parent1, Animal parent2) {
        this.length = genomeLength;
        Random random = new Random();
        index = random.nextInt(length);

        Genes parent1Genes = parent1.getGenes();
        Genes parent2Genes = parent2.getGenes();
        int genesFromParent1 = parent1.getEnergy()*length/(parent2.getEnergy()+parent1.getEnergy());
        if (random.nextBoolean()) {
            this.genes.addAll(parent1Genes.getLeftGenes(genesFromParent1));
            this.genes.addAll(parent2Genes.getRightGenes(genesFromParent1));
        }
        else
        {
            int genesFromParent2 = length - genesFromParent1;
            this.genes.addAll(parent2Genes.getLeftGenes(genesFromParent2));
            this.genes.addAll(parent1Genes.getRightGenes(genesFromParent2));
        }
        int mutationsNumber = random.nextInt(minMutations, maxMutations + 1);
        if (mutationsNumber != 0){
            for (int i = 0; i < mutationsNumber; i++) {
                mutation();
            }
        }

    }

    public Genes() {
        this.length = genomeLength;
        Random random = new Random();
        index = random.nextInt(length);
        for(int i=0; i<length; i++) {
            int randomGen = random.nextInt(8);
            this.genes.add(randomGen);
        }
    }

    public int next() {
        if (index >= length) {
            index = 0;
        }
        int gen = this.genes.get(index);
        index++;
        return gen;
    }

    public int curr() {
        return this.genes.get(index);
    }

    public int getGen(int i)
    {
        return this.genes.get(i);
    }

    private void mutation(){
        if (geneSwap && new Random().nextBoolean()) {
            swapMutation();
        } else {
            randomMutation();
        }
    }

    private void swapMutation() {
        Random random = new Random();
        int randomIndex1 = random.nextInt(length);
        int randomIndex2 = random.nextInt(length);
        int temp = this.genes.get(randomIndex1);
        this.genes.set(randomIndex1, this.genes.get(randomIndex2));
        this.genes.set(randomIndex2, temp);
    }

    private void randomMutation()
    {
        Random random = new Random();
        int randomIndex1 = random.nextInt(length);
        int randomGen = random.nextInt(8);
        this.genes.set(randomIndex1, randomGen);
    }

    private List<Integer> getLeftGenes(Integer num){
        return this.genes.subList(0,num);
    }

    private List<Integer> getRightGenes(Integer num){
        return this.genes.subList(num,length);
    }
}