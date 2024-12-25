package org.example.Animals;

import java.util.ArrayList;
import java.util.Random;

import static org.example.Config.genLength;

public class Gens
{
    private final ArrayList<Integer> gens = new ArrayList<>();
    private int index;
    private final int length;

    public Gens(Animal parent1, Animal parent2)
    {
        Random random = new Random();
        this.length =genLength;
        index = random.nextInt(length);
        Gens parent1Gens= parent1.getGens();
        Gens parent2Gens= parent2.getGens();
        boolean side =random.nextBoolean();
        if (side)
        {
            double proportion =(double) parent1.getEnergy()/(parent2.getEnergy()+parent1.getEnergy());
            int left= (int) Math.floor(proportion*length);
            int i =0;
            while (i<left)
            {
                this.gens.add(parent1Gens.getGen(i));
                i+=1;
            }
            while (i<length)
            {
                this.gens.add(parent2Gens.getGen(i));
                i+=1;
            }
        }
        else
        {
            double proportion =(double) parent2.getEnergy()/(parent2.getEnergy()+parent1.getEnergy());
            int left= (int) Math.floor(proportion*length);
            int i =0;
            while (i<left)
            {
                this.gens.add(parent2Gens.getGen(i));
                i+=1;
            }
            while (i<length)
            {
                this.gens.add(parent1Gens.getGen(i));
                i+=1;
            }
        }
        boolean choice =random.nextBoolean();
        if (choice)
        {
            this.randomMutation();
        }
        choice =random.nextBoolean();
        if(choice)
        {
            this.swapMutation();
        }
    }

    public Gens()
    {
        this.length =genLength;
        Random random = new Random();
        index = random.nextInt(length);
        for(int i=0;i<length;i++)
        {
            int randomGen = random.nextInt(8);
            this.gens.add(randomGen);
        }
    }
    public int next()
    {
        if (index >= length)
        {
            index = 0;
        }
        int gen=this.gens.get(index);
        index++;
        return gen;
    }

    public int curr()
    {
        return this.gens.get(index);
    }

    public int getGen(int i)
    {
        return this.gens.get(i);
    }

    public void swapMutation()
    {
        Random random = new Random();
        int randomIndex1 = random.nextInt(length);
        int randomIndex2 = random.nextInt(length);
        int temp = this.gens.get(randomIndex1);
        this.gens.set(randomIndex1, this.gens.get(randomIndex2));
        this.gens.set(randomIndex2, temp);
    }

    public void randomMutation()
    {
        Random random = new Random();
        int randomIndex1 = random.nextInt(length);
        int randomGen = random.nextInt(8);
        this.gens.set(randomIndex1, randomGen);
    }
}
