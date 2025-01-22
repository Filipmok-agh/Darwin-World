package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Config;
import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.util.*;

public abstract class AbstractMap implements WorldMap {
    protected int day = 0;
    protected LinkedList<Animal> animals = new LinkedList<>();
    protected LinkedList<Animal> animalsHistory = new LinkedList<>();
    protected HashMap<Vector2d, Grass> grasses = new HashMap<>();
    protected HashMap<Vector2d, LinkedList<Animal>> dailyAnimals = new HashMap<>();
    protected ArrayList<Vector2d> junglePositions = new ArrayList<>();
    protected ArrayList<Vector2d> steppePositions;
    protected Config config;
    private double avgAnimalEnergy;
    protected double avgDaysAlive = 0;
    private double avgChildCount = 0;
    private ArrayList<Integer> mostPopularGen;

    public AbstractMap(Config config) {
        this.config = config;
        this.avgAnimalEnergy = config.initialAnimalEnergy;
        addInitialAnimals();
    }

    public int getDay() {
        return this.day;
    }

    public int getAnimalsCount() {
        return this.animals.size();
    }

    @Override
    public int getGrassCount() {
        return this.grasses.size();
    }

    @Override
    public int getFreeFields() {
        return (config.mapHeight) * (config.mapWidth) - this.grasses.size();
    }

    @Override
    public double getAvgAnimalEnergy() {
        return this.avgAnimalEnergy;
    }

    @Override
    public double getAvgDaysAlive() {
        return this.avgDaysAlive;
    }

    @Override
    public double getAvgChildCount() {
        return this.avgChildCount;
    }

    @Override
    public ArrayList<Integer> getMostPopularGen() {
        return this.mostPopularGen;
    }

    @Override
    public ArrayList<Vector2d> getJunglePositions() {
        return junglePositions;
    }

    @Override
    public HashMap<Vector2d, LinkedList<Animal>> getDailyAnimals() {
        return dailyAnimals;
    }

    @Override
    public HashMap<Vector2d, Grass> getGrasses() {
        return grasses;
    }

    private void addInitialAnimals() {
        Random random = new Random();
        Map<ArrayList<Integer>, Integer> geneFrequencyMap = new HashMap<>();
        for (int i = 0; i < config.initialAnimalCount; i++) {
            int randomX = random.nextInt(config.mapWidth-1);
            int randomY = random.nextInt(config.mapHeight-1);
            Vector2d position = new Vector2d(randomX, randomY);
            Animal animal = new Animal(position,config);
            ArrayList<Integer> gene = animal.getGenes().getGenes();
            geneFrequencyMap.put(gene, geneFrequencyMap.getOrDefault(gene, 0) + 1);
            this.animals.add(animal);
            this.animalsHistory.add(animal);
        }
        int maxCount = Collections.max(geneFrequencyMap.values());
        for (Map.Entry<ArrayList<Integer>, Integer> entry : geneFrequencyMap.entrySet()) {
            if (entry.getValue() == maxCount) {
                this.mostPopularGen = entry.getKey();
            }
        }
    }

    public abstract void removeDeadAnimals();

    public void animalsMovement() {
        this.day++;
        this.dailyAnimals = new HashMap<>();
        double sumOfAnimalEnergy = 0;
        Map<ArrayList<Integer>, Integer> geneFrequencyMap = new HashMap<>();
        if (!animals.isEmpty()) {
            for (Animal animal : animals) {
                ArrayList<Integer> gene = animal.getGenes().getGenes();
                geneFrequencyMap.put(gene, geneFrequencyMap.getOrDefault(gene, 0) + 1);
                sumOfAnimalEnergy += animal.getEnergy();
                animal.move();
                if (dailyAnimals.containsKey(animal.getPosition())) {
                    addAnimal(dailyAnimals.get(animal.getPosition()), animal);
                } else {
                    dailyAnimals.put(animal.getPosition(), new LinkedList<>(List.of(animal)));
                }
            }
            this.avgAnimalEnergy=sumOfAnimalEnergy/animals.size();
            int maxCount = Collections.max(geneFrequencyMap.values());
            this.mostPopularGen = new ArrayList<>();
            for (Map.Entry<ArrayList<Integer>, Integer> entry : geneFrequencyMap.entrySet()) {
                if (entry.getValue() == maxCount) {
                    this.mostPopularGen = entry.getKey();
                }
            }
        }
        else{
            this.avgAnimalEnergy=0;
        }
    }

    private void eatPlant(Animal animal) {
        if (grasses.containsKey(animal.getPosition())) {
            animal.eat();
            grasses.remove(animal.getPosition());
        }
    }

    public void animalActivities() {
        for (LinkedList<Animal> animalsAtThisPosition : dailyAnimals.values()) {
            eatPlant(animalsAtThisPosition.getFirst());
            if (animalsAtThisPosition.size() < 2) {
                continue;
            }
            int potentialPairs = animalsAtThisPosition.size() / 2;
            for (int i = 0; i < potentialPairs; i++) {
                Animal parent1 = animalsAtThisPosition.get(2 * i);
                Animal parent2 = animalsAtThisPosition.get(2 * i + 1);
                if (parent1.getEnergy() >= config.energyToBeFed && parent2.getEnergy() >= config.energyToBeFed) {
                    Animal child = parent1.breeding(parent2);
                    this.animals.add(child);
                    this.animalsHistory.add(child);
                }
            }
        }
    }

    private void spawnGrassInArea(ArrayList<Vector2d> positions, int grassCount) {
        int grassPlaced = 0;
        if (!positions.isEmpty()) {
            int lastIndex = positions.size() - 1;
            while (grassPlaced < grassCount && lastIndex >= 0) {
                int randomIndex = new Random().nextInt(lastIndex + 1);
                Vector2d tempPosition = positions.get(randomIndex);
                if (grasses.get(tempPosition) == null) {
                    grasses.put(tempPosition, new Grass(tempPosition));
                    grassPlaced++;
                }
                positions.set(randomIndex, positions.get(lastIndex));
                positions.set(lastIndex, tempPosition);
                lastIndex--;
            }
        }
    }

    public void spawnGrass(Integer amount) {
        int jungleGrass = (int) Math.ceil((double) (amount * 4) / 5);
        int steppeGrass = (int) Math.floor((double) amount / 5) ;
        while (this.steppePositions.size() < steppeGrass*5) {
            jungleGrass += (int) Math.ceil((double) steppeGrass/5);
            steppeGrass -= (int) Math.ceil((double) steppeGrass/5);
        }
        spawnGrassInArea(this.junglePositions, jungleGrass);
        spawnGrassInArea(this.steppePositions, steppeGrass);
    }

    private void addAnimal(LinkedList<Animal> animalsAtPosition, Animal animal) {
        boolean inserted = false;
        for (int i = 0; i < animalsAtPosition.size(); i++) {
            if (animal.isStronger(animalsAtPosition.get(i))) {
                animalsAtPosition.add(i, animal);
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            animalsAtPosition.addLast(animal);
        }
    }
    public void countAvgChildCountAndAvgLifeDuration(){
        int sumChildren = 0;
        int sumDaysAlive = 0;
        int countDead = 0;
        for (Animal animal : animalsHistory) {
            sumChildren += animal.getChildrenAmount();
            if (animal.getFuneralDay() != 0){
                sumDaysAlive += animal.getDaysAlive();
                countDead++;
            }
        }
        this.avgDaysAlive = (countDead == 0) ? 0 : (double) sumDaysAlive / countDead;
        this.avgChildCount = (double) sumChildren / animalsHistory.size();
    }
}