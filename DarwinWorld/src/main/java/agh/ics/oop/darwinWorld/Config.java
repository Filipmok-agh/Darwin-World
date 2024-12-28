package agh.ics.oop.darwinWorld;

public class Config
{

    public static int dailyEnergyCost = 5;

//    Wszystkie zmienne konfiguracyjne z punktu symulacja
//    Na 90% te zmienne nie mogą być final bo wtedy nie będzie się ich dało ustawić z poziomu FXa

    public static int mapHeight;
    public static int mapWidth;
    public static int initialPlantCount;
    public static int plantEnergy = 15;
    public static int dailyPlantGrowth;
    public static boolean lifeGivingCorpses;
    public static int initialAnimalCount;
    public static int initialAnimalEnergy = 100;
    public static int energyToBeFed;
    public static int parentEnergyCost = 40;
    public static int minMutations;
    public static int maxMutations;
    public static boolean geneSwap = false;
    public static int genomeLength = 10;

}
