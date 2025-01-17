package agh.ics.oop.darwinWorld.UI;

import agh.ics.oop.darwinWorld.Config;

import java.io.*;

public class CSVManager {
    private static final String fileName = "DarwinWorld/src/main/resources/configurations.csv";


    public CSVManager() {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Plik nie istnieje, próbuję go utworzyć...");

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("ID,mapHeight,mapWidth,initialPlantCount,dailyPlantGrowth,plantEnergy,initialAnimalCount,initialAnimalEnergy,energyToBeFed,parentEnergyCost,genomeLength,minMutations,maxMutations,lifeGivingCorpses,geneSwap");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    public boolean loadConfig(int id) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int configId = Integer.parseInt(parts[0]);
                if (configId == id) {
                    Config.mapHeight = Integer.parseInt(parts[1]);
                    Config.mapWidth = Integer.parseInt(parts[2]);
                    Config.initialPlantCount = Integer.parseInt(parts[3]);
                    Config.dailyPlantGrowth = Integer.parseInt(parts[4]);
                    Config.plantEnergy = Integer.parseInt(parts[5]);
                    Config.initialAnimalCount = Integer.parseInt(parts[6]);
                    Config.initialAnimalEnergy = Integer.parseInt(parts[7]);
                    Config.energyToBeFed = Integer.parseInt(parts[8]);
                    Config.parentEnergyCost = Integer.parseInt(parts[9]);
                    Config.genomeLength = Integer.parseInt(parts[10]);
                    Config.minMutations = Integer.parseInt(parts[11]);
                    Config.maxMutations = Integer.parseInt(parts[12]);
                    Config.lifeGivingCorpses = Boolean.parseBoolean(parts[13]);
                    Config.geneSwap = Boolean.parseBoolean(parts[14]);
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
            StringBuilder sb = new StringBuilder();
            sb.append(id).append(",");
            sb.append(Config.mapHeight).append(",");
            sb.append(Config.mapWidth).append(",");
            sb.append(Config.initialPlantCount).append(",");
            sb.append(Config.dailyPlantGrowth).append(",");
            sb.append(Config.plantEnergy).append(",");
            sb.append(Config.initialAnimalCount).append(",");
            sb.append(Config.initialAnimalEnergy).append(",");
            sb.append(Config.energyToBeFed).append(",");
            sb.append(Config.parentEnergyCost).append(",");
            sb.append(Config.genomeLength).append(",");
            sb.append(Config.minMutations).append(",");
            sb.append(Config.maxMutations).append(",");
            sb.append(Config.lifeGivingCorpses).append(",");
            sb.append(Config.geneSwap);
            writer.println(sb.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}