package agh.ics.oop.darwinWorld.Elements;

import agh.ics.oop.darwinWorld.Maps.MapDirection;

import java.util.ArrayList;
import java.util.HashSet;
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
        MapDirection[] directions = MapDirection.values();
        this.direction = directions[new Random().nextInt(directions.length)];
        this.position = parent1.getPosition();
        this.genes = new Genes(parent1,parent2);
        this.energy = 2 * parentEnergyCost;
        this.parents = new ArrayList<>(List.of(parent1, parent2));
        this.children = 0;
        this.descendants = 0;
        this.eatenGrass = 0;
        this.daysAlive = 0;
        this.funeralDay = 0;
    }
    public Animal(Vector2d position) {
        MapDirection[] directions = MapDirection.values();
        this.direction = directions[new Random().nextInt(directions.length)];
        this.position = position;
        this.genes = new Genes();
        this.energy = initialAnimalEnergy;
        this.parents = new ArrayList<Animal>();
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


    public void move() {
        this.energy = this.energy - dailyEnergyCost;
        this.daysAlive++;
        for(int i = 0; i < this.genes.curr(); i++)
        {
            this.direction = this.direction.next();
        }
        this.position = this.position.add(this.direction.toUnitVector());
        this.genes.next();
    }

    public void eat() {
        this.eatenGrass++;
        this.energy = this.energy + plantEnergy;
    }

    private void updateDescendantsCount(HashSet<Animal> updatedDescendantsSet)
    {
        if (!updatedDescendantsSet.contains(this))
        {
            this.descendants++;
            updatedDescendantsSet.add(this);
            if(!this.parents.isEmpty()) 
            {
                this.parents.get(0).updateDescendantsCount(updatedDescendantsSet);
                this.parents.get(1).updateDescendantsCount(updatedDescendantsSet);
            }
        }
        }

    public Animal breeding(Animal parent) {
        Animal child = new Animal(parent,this);
        this.children++;
        parent.children++;
        HashSet<Animal> updatedDescendantsSet = new HashSet<>();
        this.updateDescendantsCount(updatedDescendantsSet);
        parent.updateDescendantsCount(updatedDescendantsSet);
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
