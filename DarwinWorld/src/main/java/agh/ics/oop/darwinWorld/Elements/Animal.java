package agh.ics.oop.darwinWorld.Elements;

import agh.ics.oop.darwinWorld.Config;
import agh.ics.oop.darwinWorld.Maps.MapDirection;
import javafx.scene.paint.Color;

import java.util.*;

import static java.lang.Math.min;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private final Animal[] parents;
    private final Genes genes;
    private ArrayList<Animal> children;
    private int childrenAmount;
    private int descendantsAmount;
    private int eatenGrass;
    private int daysAlive;
    private int funeralDay;
    private Config config;
    private boolean highlight = false;

    public Animal(Vector2d position, Config config) {
        this.initializeAnimalStats();
        this.direction = MapDirection.values()[new Random().nextInt(MapDirection.values().length)];
        this.position = position;
        this.config = config;
        this.genes = new Genes(config);
        this.energy = config.initialAnimalEnergy;
        this.parents = new Animal[]{};
    }

    public Animal(Animal parent1, Animal parent2) {
        this.initializeAnimalStats();
        this.config = parent1.config;
        this.direction = MapDirection.values()[new Random().nextInt(MapDirection.values().length)];
        this.position = parent1.getPosition();
        this.genes = new Genes(parent1, parent2);
        this.energy = 2 * config.parentEnergyCost;
        this.parents = new Animal[]{parent1, parent2};
    }

    private void initializeAnimalStats() {
        this.children = new ArrayList<>();
        this.childrenAmount = 0;
        this.descendantsAmount = 0;
        this.eatenGrass = 0;
        this.daysAlive = 0;
        this.funeralDay = 0;
    }

    public int getDescendantsAmount() {
        this.updateDescendantsCount();
        return this.descendantsAmount;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public int getEatenGrass() {
        return this.eatenGrass;
    }

    public int getFuneralDay() {
        return this.funeralDay;
    }

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

    public int getChildrenAmount() {
        return this.childrenAmount;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public Config getConfig() {
        return config;
    }

    private void overRightBound(Vector2d positionToProcess) {
        this.position = new Vector2d(0, positionToProcess.getY());
    }

    private void overLeftBound(Vector2d positionToProcess) {
        this.position = new Vector2d(config.mapWidth - 1, positionToProcess.getY());
    }


    public void move() {
        this.energy = this.energy - config.dailyEnergyCost;
        this.daysAlive++;
        Integer currentDirection = this.getDirection().toNumber();
        Integer currentMove = this.genes.getCurrentGene();
        MapDirection directionToProcess = MapDirection.fromNumber((currentDirection + currentMove) % 8);
        Vector2d positionToProcess = this.getPosition().add(directionToProcess.toUnitVector());
        if (positionToProcess.isYInRange(0, config.mapHeight - 1)) {
            if (positionToProcess.isXGreaterThan(config.mapWidth - 1)) {
                this.overRightBound(positionToProcess);
            } else if (positionToProcess.isXLessThan(0)) {
                this.overLeftBound(positionToProcess);
            } else {
                this.position = positionToProcess;
            }
            this.direction = directionToProcess;
        } else {
            this.direction = this.direction.opposite();
        }
        this.genes.nextGene();
    }

    public void eat() {
        this.eatenGrass++;
        this.energy = this.energy + config.plantEnergy;
    }

    private void updateDescendantsCount() {
        Set<Animal> descendants = new HashSet<>(this.children);
        for (Animal child : this.children) {
            child.updateChildDescendantsSet(descendants);
        }
        this.descendantsAmount = descendants.size();
    }

    private void updateChildDescendantsSet(Set<Animal> descendants) {
        for (Animal child : this.children) {
            if (!descendants.contains(child)) {
                descendants.add(child);
                child.updateChildDescendantsSet(descendants);
            }
        }
    }

    public Animal breeding(Animal parent) {
        Animal child = new Animal(parent, this);
        this.children.add(child);
        parent.children.add(child);
        this.childrenAmount += 1;
        parent.childrenAmount += 1;
        this.energy -= config.parentEnergyCost;
        parent.energy -= config.parentEnergyCost;
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
        return this.childrenAmount > animal.getChildrenAmount();
    }

    public Color getColor() {
        if (highlight) {
            return Color.BLACK;
        }
        double daysLeft = (double) energy / config.dailyEnergyCost;
        if (daysLeft > 30) {
            return Color.CYAN;
        }
        int green = (int) (255 * Math.max(0.0, Math.min(1, (daysLeft / 30))));
        return Color.rgb(255, green, 0);
    }
}