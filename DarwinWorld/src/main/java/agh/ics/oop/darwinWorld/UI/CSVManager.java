package agh.ics.oop.darwinWorld.UI;

import agh.ics.oop.darwinWorld.Config;

import java.io.*;

public class CSVManager {
    private static final String fileName = "DarwinWorld/src/main/resources/configurations.csv";
    private Config config = new Config();

    public CSVManager() {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File does not exist, creating a new one...");

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("ID,mapHeight,mapWidth,initialPlantCount,dailyPlantGrowth,plantEnergy,initialAnimalCount,initialAnimalEnergy,energyToBeFed,parentEnergyCost,genomeLength,minMutations,maxMutations,lifeGivingCorpses,geneSwap,dailyEnergyCost");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public boolean loadConfig(int id) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int configId = Integer.parseInt(parts[0]);
                if (configId == id) {
                    config.mapHeight = Integer.parseInt(parts[1]);
                    config.mapWidth = Integer.parseInt(parts[2]);
                    config.initialPlantCount = Integer.parseInt(parts[3]);
                    config.dailyPlantGrowth = Integer.parseInt(parts[4]);
                    config.plantEnergy = Integer.parseInt(parts[5]);
                    config.initialAnimalCount = Integer.parseInt(parts[6]);
                    config.initialAnimalEnergy = Integer.parseInt(parts[7]);
                    config.energyToBeFed = Integer.parseInt(parts[8]);
                    config.parentEnergyCost = Integer.parseInt(parts[9]);
                    config.genomeLength = Integer.parseInt(parts[10]);
                    config.minMutations = Integer.parseInt(parts[11]);
                    config.maxMutations = Integer.parseInt(parts[12]);
                    config.lifeGivingCorpses = Boolean.parseBoolean(parts[13]);
                    config.geneSwap = Boolean.parseBoolean(parts[14]);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveConfig(int id) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int configId = Integer.parseInt(parts[0]);
                if (configId == id) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            String row = id + "," +
                    config.mapHeight + "," +
                    config.mapWidth + "," +
                    config.initialPlantCount + "," +
                    config.dailyPlantGrowth + "," +
                    config.plantEnergy + "," +
                    config.initialAnimalCount + "," +
                    config.initialAnimalEnergy + "," +
                    config.energyToBeFed + "," +
                    config.parentEnergyCost + "," +
                    config.genomeLength + "," +
                    config.minMutations + "," +
                    config.maxMutations + "," +
                    config.lifeGivingCorpses + "," +
                    config.geneSwap + "," +
                    config.dailyEnergyCost;
            writer.println(row);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}