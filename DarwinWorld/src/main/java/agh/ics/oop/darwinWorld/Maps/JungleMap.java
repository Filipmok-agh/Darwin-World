package agh.ics.oop.darwinWorld.Maps;


import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

import static agh.ics.oop.darwinWorld.Config.*;

public class JungleMap extends AbstractMap {
    int upperJungleBound;
    int lowerJungleBound;
    int jungleArea;


    public JungleMap() {
        super();
        int lines = Math.round((float) mapHeight / 5);
        this.upperJungleBound = (mapHeight+lines) / 2;
        this.lowerJungleBound = (mapHeight-lines) / 2;
        this.jungleArea = (upperJungleBound-lowerJungleBound-1)*mapWidth;
        this.junglePositions = junglePossiblePositions();
        this.steppePositions = steppePossiblePositions();
    }

    public ArrayList<Vector2d> junglePossiblePositions()
    {
        ArrayList<Vector2d> possiblePositions = new ArrayList<>();
        for(int i=this.lowerJungleBound; i<=this.upperJungleBound+1; i++)
        {
            for(int j=0;j<mapWidth+1;j++)
            {
                possiblePositions.add(new Vector2d(i, j));
            }
        }
        return possiblePositions;
    }


    public ArrayList<Vector2d> steppePossiblePositions()
    {
        ArrayList<Vector2d> possiblePositions = new ArrayList<>();
        for (int i =0;i<this.lowerJungleBound;i++)
        {
            for (int j=0;j<mapWidth+1;j++)
            {
                possiblePositions.add(new Vector2d(i, j));
            }
        }
        for (int i =this.upperJungleBound+1;i<mapHeight+1;i++)
        {
            for (int j=0;j<mapWidth+1;j++)
            {
                possiblePositions.add(new Vector2d(i, j));
            }
        }
        return possiblePositions;
    }
    @Override
    public void removeDeadAnimals()
    {
        animals = animals.stream()
                .filter(animal -> animal.getEnergy() >= dailyEnergyCost)
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
