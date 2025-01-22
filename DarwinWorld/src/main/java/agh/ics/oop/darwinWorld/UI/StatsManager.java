package agh.ics.oop.darwinWorld.UI;

import agh.ics.oop.darwinWorld.Config;
import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Maps.WorldMap;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StatsManager {
    private final Path statsDirectory;
    private final String uniqueId;
    private final WorldMap map;
    private final Config config;
    private Animal animal;

    public StatsManager(WorldMap map, String uniqueId) {
        this.map = map;
        this.config = this.map.getConfig();
        this.uniqueId = uniqueId;
        this.statsDirectory = Paths.get("DarwinWorld/src/main/resources/simulation_stats");

        try {
            if (!Files.exists(statsDirectory)) {
                Files.createDirectory(statsDirectory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void saveStatsToCSV() {
        String filename = "simulation_stats_" + uniqueId + ".csv";
        Path filePath = statsDirectory.resolve(filename);

        try (FileWriter writer = new FileWriter(filePath.toFile(), true)) {
            if (Files.size(filePath) == 0) {
                writer.write("Day;AnimalsCount;GrassCount;FreeFields;AvgAnimalEnergy;AvgDaysAlive;AvgChildCount;MostPopularGen\n");
            }

            writer.write(
                    map.getDay() + ";" +
                        map.getAnimalsCount() + ";" +
                        map.getGrassCount() + ";" +
                        map.getFreeFields() + ";" +
                        String.format("%.3f", (map.getAvgAnimalEnergy())) + ";" +
                        String.format("%.3f",(map.getAvgDaysAlive())) + ";" +
                        String.format("%.3f", (map.getAvgChildCount())) + ";" +
                        map.getMostPopularGen().toString().replace(",", " ") + "\n"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentDay() {
        return "Current day: " + map.getDay();
    }

    public String getAnimalsCount() {
        return "Total number of animals: " + map.getAnimalsCount();
    }

    public String getGrassCount() {
        return "Total number of plants: " + map.getGrassCount();
    }

    public String getFreeFields() {
        return "Number of free fields: " + map.getFreeFields();
    }

    public String getAvgAnimalEnergy() {
        return "Average energy level: " + (int) Math.round(map.getAvgAnimalEnergy());
    }

    public String getAvgDaysAlive() {
        return "Average days alive: " + (int) Math.round(map.getAvgDaysAlive());
    }

    public String getAvgChildCount() {
        return "Average number of children: " + (int) Math.round(map.getAvgChildCount());
    }

    public String getMostPopularGen() {
        return "Most popular genotype: \n" + map.getMostPopularGen().toString();
    }

    public String getGenome() {
        return "Genome: " + animal.getGenes().getGenes().toString();
    }

    public String getCurrentGene() {
        return "Current Gene: " + animal.getGenes().getIndex();
    }

    public String getEnergy() {
        return "Energy amount: " + animal.getEnergy();
    }

    public String getPlantsEaten() {
        return "Number of plants eaten: " + animal.getEatenGrass();
    }

    public String getChildrenCount() {
        return "Number of children: " + animal.getChildrenAmount();
    }

    public String getDescendantsCount() {
        return "Number of descendants: " + animal.getDescendantsAmount();
    }

    public String getDaysAlive() {
        return "Days Alive: " + animal.getDaysAlive();
    }

    public String getFuneralDay() {
        return animal.getEnergy() > config.dailyEnergyCost ? "Funeral Day: -" : "Funeral Day: " + animal.getFuneralDay();
    }
}
