package org.example.Animals;

import java.util.ArrayList;

public class Gens
{
    private ArrayList<Integer> gens = new ArrayList<>();
    private int index;
    private int length;

    public Gens(Animal parent1, Animal parent2,int length)
    {

    }
    public int next()
    {
        int gen=gens.get(index);
        index++;
        return gen;
    }
}
