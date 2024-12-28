package agh.ics.oop.darwinWorld;

public class Config
{

    public static int dailyEnergyCost = 5;

//    Wszystkie zmienne konfiguracyjne z punktu symulacja
//    Na 90% te zmienne nie mogą być final bo wtedy nie będzie się ich dało ustawić z poziomu FXa

    public static int mapHeight = 50;
    public static int mapWidth = 100;
    public static int initialPlantCount = 70;
    public static int plantEnergy = 15;
    public static int dailyPlantGrowth;
    public static boolean lifeGivingCorpses = false;
    public static int initialAnimalCount = 10;
    public static int initialAnimalEnergy = 100;
    public static int energyToBeFed = 20;
    public static int parentEnergyCost = 40;
    public static int minMutations = 1;
    public static int maxMutations = 4;
    public static boolean geneSwap = false;
    public static int genomeLength = 10;

}
