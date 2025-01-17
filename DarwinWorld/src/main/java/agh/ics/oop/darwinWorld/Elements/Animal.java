package agh.ics.oop.darwinWorld.Elements;

import agh.ics.oop.darwinWorld.Maps.MapDirection;

import java.util.*;

import static agh.ics.oop.darwinWorld.Config.*;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private final ArrayList<Animal> parents;
    private HashSet<Animal> descendantsSet;
    private final Genes genes;
    private int children;
    private int descendants;
    private int eatenGrass;
    private int daysAlive;
    private int funeralDay;

    public Animal(Vector2d position) {
        this.initializeAnimalStats();
        this.direction = MapDirection.values()[new Random().nextInt(MapDirection.values().length)];
        this.position = position;
        this.genes = new Genes();
        this.energy = initialAnimalEnergy;
        this.parents = new ArrayList<>();
    }

    public Animal(Animal parent1, Animal parent2) {
        this.initializeAnimalStats();
        this.direction = MapDirection.values()[new Random().nextInt(MapDirection.values().length)];
        this.position = parent1.getPosition();
        this.genes = new Genes(parent1, parent2);
        this.energy = 2 * parentEnergyCost;
        this.parents = new ArrayList<>(List.of(parent1, parent2));
    }

    private void initializeAnimalStats() {
        this.descendantsSet = new HashSet<Animal>();
        this.children = 0;
        this.descendants = 0;
        this.eatenGrass = 0;
        this.daysAlive = 0;
        this.funeralDay = 0;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public int getEnergy() {
        return this.energy;
    }

    public Genes getGenes() {
        return this.genes;
    }

    public int getDaysAlive() {
        return this.daysAlive;
    }

    public int getChildren() {
        return this.children;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    private void overRightBound(Vector2d positionToProcess) {
        this.position = new Vector2d(0, positionToProcess.getY());
    }

    private void overLeftBound(Vector2d positionToProcess) {
        this.position = new Vector2d(mapWidth, positionToProcess.getY());
    }


    public void move() {
        this.energy = this.energy - dailyEnergyCost;
        this.daysAlive++;
        Vector2d positionToProcess = this.getPosition();
        MapDirection directionToProcess = this.getDirection();

        for (int i = 0; i < this.genes.curr(); i++) {
            directionToProcess = directionToProcess.next();
        }
        positionToProcess = positionToProcess.add(directionToProcess.toUnitVector());

        if (positionToProcess.isYInRange(0, mapHeight)) {
            if (positionToProcess.isXGreaterThan(mapWidth)) {
                this.overRightBound(positionToProcess);
            } else if (positionToProcess.isXLessThan(mapWidth)) {
                this.overLeftBound(positionToProcess);
            } else {
                this.position = positionToProcess;
            }
        } else {
            this.direction = this.direction.opposite();
        }
        this.genes.next();
    }

    public void eat() {
        this.eatenGrass++;
        this.energy = this.energy + plantEnergy;
    }

    private void updateDescendantsSet(Animal newDescendant) {
        if (!this.descendantsSet.contains(newDescendant)) {
            this.descendantsSet.add(newDescendant);
            this.descendants++;
            for (Animal parent : this.parents) {
                parent.updateDescendantsSet(newDescendant);
            }
        }
    }

    public Animal breeding(Animal parent) {
        Animal child = new Animal(parent, this);
        this.children++;
        parent.children++;
        this.updateDescendantsSet(child);
        parent.updateDescendantsSet(child);
        this.energy -= parentEnergyCost;
        parent.energy -= parentEnergyCost;
        return child;
    }

    public void setFuneralDay(int day) {
        this.funeralDay = day;
    }

    public boolean isStronger(Animal animal) {
        if (this.energy != animal.getEnergy()) {
            return this.energy > animal.getEnergy();
        }
        if (this.daysAlive != animal.getDaysAlive()) {
            return this.daysAlive > animal.getDaysAlive();
        }
        return this.children > animal.getChildren();
    }
}
