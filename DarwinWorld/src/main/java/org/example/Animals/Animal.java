package org.example.Animals;

import org.example.Enums_Vectors.MapDirection;
import org.example.Enums_Vectors.Vector2d;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import static org.example.Config.*;

public class Animal
{
    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private  final ArrayList<Animal> parents;
    private Gens gens;
    private int children;
    private int grandChildren;
    private int eatenGrass;
    private int daysAlive;
    private int funeralDay;
    public Animal(Animal parent1,Animal parent2)
    {
        this.position = parent1.getPosition();
        this.gens = new Gens(parent1,parent2);
        this.energy = (int) Math.floor ((double) (parent1.getEnergy()/2 + parent2.getEnergy()/2));
        this.parents = new ArrayList<Animal>();
        this.parents.add(parent1);
        this.parents.add(parent2);
        this.children = 0;
        this.grandChildren = 0;
        this.eatenGrass = 0;
        this.daysAlive = 0;
    }
    public Animal(Vector2d position)
    {
        this.position = position;
        this.gens = new Gens();
        this.energy = startEnergy;
        this.parents = new ArrayList<Animal>();
        this.children = 0;
        this.grandChildren = 0;
        this.eatenGrass = 0;
        this.daysAlive = 0;
    }
    public Vector2d getPosition()
    {
        return this.position;
    }

    public MapDirection getDirection()
    {
        return this.direction;
    }

    public int getEnergy()
    {
        return this.energy;
    }

    public Gens getGens()
    {
        return this.gens;
    }


    public void setPosition(Vector2d position)
    {
        this.position = position;
    }

    public void setDirection(MapDirection direction)
    {
        this.direction = direction;
    }

    public void move()
    {
        for(int i=0;i<this.gens.next();i++)
        {
            this.direction = this.direction.next();
        }
        this.position.add(this.direction.toUnitVector());
    }

    public void eat()
    {
        this.eatenGrass++;
        this.energy=this.energy+grassEnergy;
    }

    public void sleep()
    {
        this.energy=this.energy-dailyEnergyLose;
        this.daysAlive++;
    }

    private void addGeneration()
    {
        this.grandChildren++;
        if(this.parents.size()!=0)
        {
            this.parents.get(0).addGeneration();
            this.parents.get(1).addGeneration();
        }
    }
    public void breeding(Animal parent)
    {
        Animal child = new Animal(parent,this);
        this.children++;
        parent.children++;
        this.addGeneration();
        parent.addGeneration();
        this.energy = (int) Math.floor((double) this.energy/2);
        parent.energy =(int) Math.floor((double) parent.energy/2);
    }
    public void setFuneralDay(int day)
    {
        this.funeralDay=day;
    }

    public boolean greaterThan(Animal animal)
    {
        if(this.getEnergy()>animal.getEnergy())
            return true;
        if(this.getEnergy()<animal.getEnergy())
            return false;
        if(this.daysAlive > animal.daysAlive)
            return true;
        if(this.daysAlive<animal.daysAlive)
            return false;
        if (this.children>animal.children)
            return true;
        if(this.children<animal.children)
            return false;
        Random random = new Random();
        return random.nextBoolean();
    }
}
