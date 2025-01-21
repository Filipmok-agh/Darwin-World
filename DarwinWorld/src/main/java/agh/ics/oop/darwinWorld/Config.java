package agh.ics.oop.darwinWorld;

public class Config {

    public int dailyEnergyCost = 5;

    public int mapHeight;
    public int mapWidth;
    public int initialPlantCount;
    public int plantEnergy;
    public int dailyPlantGrowth;
    public boolean lifeGivingCorpses;
    public int initialAnimalCount;
    public int initialAnimalEnergy;
    public int energyToBeFed;
    public int parentEnergyCost;
    public int minMutations;
    public int maxMutations;
    public boolean geneSwap;
    public int genomeLength;
    public boolean saveStats;

    public Config()
    {
    }

    @Override
    public String toString() {
        return "Config{" +
                "dailyEnergyCost=" + dailyEnergyCost +
                ", mapHeight=" + mapHeight +
                ", mapWidth=" + mapWidth +
                ", initialPlantCount=" + initialPlantCount +
                ", plantEnergy=" + plantEnergy +
                ", dailyPlantGrowth=" + dailyPlantGrowth +
                ", lifeGivingCorpses=" + lifeGivingCorpses +
                ", initialAnimalCount=" + initialAnimalCount +
                ", initialAnimalEnergy=" + initialAnimalEnergy +
                ", energyToBeFed=" + energyToBeFed +
                ", parentEnergyCost=" + parentEnergyCost +
                ", minMutations=" + minMutations +
                ", maxMutations=" + maxMutations +
                ", geneSwap=" + geneSwap +
                ", genomeLength=" + genomeLength +
                ", saveStats=" + saveStats +
                '}';
    }
}
