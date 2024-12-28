package agh.ics.oop.darwinWorld.Elements;

import agh.ics.oop.darwinWorld.Maps.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static agh.ics.oop.darwinWorld.Config.*;

public class Animal implements WorldElement
{
    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private final ArrayList<Animal> parents;
    private Genes genes;
    private int children;
    private int descendants;
    private int eatenGrass;
    private int daysAlive;
    private int funeralDay;

    public Animal(Animal parent1,Animal parent2) {
        this.position = parent1.getPosition();
        this.genes = new Genes(parent1,parent2);
        this.energy = 2 * parentEnergyCost;
        this.parents = new ArrayList<>(List.of(parent1, parent2));
        this.children = 0;
        this.descendants = 0;
        this.eatenGrass = 0;
        this.daysAlive = 0;
    }
    public Animal(Vector2d position) {
        this.position = position;
        this.genes = new Genes();
        this.energy = initialAnimalEnergy;
        this.parents = new ArrayList<Animal>();
        this.children = 0;
        this.descendants = 0;
        this.eatenGrass = 0;
        this.daysAlive = 0;
    }
    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return this.energy;
    }

    public Genes getGenes() {
        return this.genes;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public void move() {
        for(int i = 0; i<this.genes.next(); i++)
        {
            this.direction = this.direction.next();
        }
        this.position.add(this.direction.toUnitVector());
    }

    public void eat() {
        this.eatenGrass++;
        this.energy = this.energy + plantEnergy;
    }

//    Wydaje się niepotrzebne bo move może wykonać wszystkie te rzeczy
    public void sleep() {
        this.energy = this.energy - dailyEnergyCost;
        this.daysAlive++;
    }

    private void updateDescendantsCount() {
        this.descendants++;
        if(!this.parents.isEmpty()) {
            this.parents.get(0).updateDescendantsCount();
            this.parents.get(1).updateDescendantsCount();
        }
    }

    public Animal breeding(Animal parent) {
        Animal child = new Animal(parent,this);
        this.children++;
        parent.children++;
        this.updateDescendantsCount();
        parent.updateDescendantsCount();
        this.energy -= parentEnergyCost;
        parent.energy -= parentEnergyCost;
        return child;
    }

    public void setFuneralDay(int day) {
        this.funeralDay = day;
    }

    public boolean isStronger(Animal animal) {
        if(this.getEnergy() > animal.getEnergy())
            return true;
        if(this.getEnergy() < animal.getEnergy())
            return false;
        if(this.daysAlive > animal.daysAlive)
            return true;
        if(this.daysAlive < animal.daysAlive)
            return false;
        if (this.children > animal.children)
            return true;
        if(this.children < animal.children)
            return false;
        Random random = new Random();
        return random.nextBoolean();
    }
}
