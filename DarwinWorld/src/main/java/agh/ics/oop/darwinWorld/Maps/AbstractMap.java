package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import agh.ics.oop.darwinWorld.Elements.WorldElement;

import java.util.*;
import java.util.stream.Collectors;

import static agh.ics.oop.darwinWorld.Config.*;

public abstract class AbstractMap implements WorldMap {
    protected int day;
    public LinkedList<Animal> animals;
    public LinkedList<Animal> animalsHistory;
    protected HashMap<Vector2d, Grass> grasses;
    protected HashMap<Vector2d, LinkedList<Animal>> dailyAnimals;
    ArrayList<Vector2d> junglePositions;
    ArrayList<Vector2d> steppePositions;

    public AbstractMap() {
        this.day = 0;
        this.animalsHistory = new LinkedList<>();
        this.animals = new LinkedList<>();
        this.grasses = new HashMap<>();
        this.dailyAnimals = new HashMap<>();
    }

    public void addInitialAnimals() {
        Random random = new Random();
        for (int i = 0; i < initialAnimalCount; i++) {
            int randomX = random.nextInt(mapWidth-1);
            int randomY = random.nextInt(mapHeight-1);
            Vector2d position = new Vector2d(randomX, randomY);
            Animal animal = new Animal(position);
            this.animals.add(animal);
            this.animalsHistory.add(animal);
        }
    }

    public abstract void removeDeadAnimals();

    public void animalsMovement() {
        this.day++;
        this.dailyAnimals = new HashMap<>();
        for (Animal animal : animals) {
            animal.move();
            if (dailyAnimals.containsKey(animal.getPosition())) {
                addAnimal(dailyAnimals.get(animal.getPosition()), animal);
            } else {
                dailyAnimals.put(animal.getPosition(), new LinkedList<>(List.of(animal)));
            }
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
                if (parent1.getEnergy() >= energyToBeFed && parent2.getEnergy() >= energyToBeFed) {
                    Animal child = parent1.breeding(parent2);
                    this.animals.add(child);
                    this.animalsHistory.add(child);
                }
            }
        }
    }

    private void spawnGrassInArea(List<Vector2d> positions, int grassCount) {
        int grassPlaced = 0;
        int lastIndex = positions.size() - 1;
        while (grassPlaced < grassCount && lastIndex >= 0) {
            int randomIndex = new Random().nextInt(lastIndex + 1);
            Vector2d tempPosition = positions.get(randomIndex);
            if (grasses.get(tempPosition) == null) {
                place(new Grass(tempPosition));
                grassPlaced++;
            }
            positions.set(randomIndex, positions.get(lastIndex));
            positions.set(lastIndex, tempPosition);
            lastIndex--;
        }
    }

    public void spawnGrass(Integer amount) {
        int jungleGrass = (int) Math.ceil((double) (amount * 4) / 5);
        int steppeGrass = (int) Math.floor((double) (amount / 5));
        spawnGrassInArea(this.junglePositions, jungleGrass);
        spawnGrassInArea(this.steppePositions, steppeGrass);
    }

    protected void addAnimal(LinkedList<Animal> animalsAtPosition, Animal animal) {
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

    private void place(WorldElement element) {
        if (element instanceof Animal) {
            animals.add((Animal) element);
            if (dailyAnimals.containsKey(element.getPosition())) {
                addAnimal(dailyAnimals.get(element.getPosition()), (Animal) element);
            } else {
                dailyAnimals.put(element.getPosition(), new LinkedList<>(List.of((Animal) element)));
            }
        } else {
            grasses.put(element.getPosition(), new Grass(element.getPosition()));
        }
    }
}