package org.example.Animals;

import org.example.Enums_Vectors.MapDirection;
import org.example.Enums_Vectors.Vector2d;

import java.util.ArrayList;

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
    public Animal(Vector2d position,int energy,Gens gens,Animal parent1,Animal parent2)
    {
        this.position = position;
        this.gens = gens;
        this.energy = energy;
        this.parents = new ArrayList<Animal>();
        this.parents.add(parent1);
        this.parents.add(parent2);
        this.children = 0;
        this.grandChildren = 0;
        this.eatenGrass = 0;
        this.daysAlive = 0;
    }
    public Animal(Vector2d position,int energy,Gens gens)
    {
        this.position = position;
        this.gens = gens;
        this.energy = energy;
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

    public void eat(int energy)
    {
        this.eatenGrass++;
        this.energy=this.energy+energy;
    }
}
