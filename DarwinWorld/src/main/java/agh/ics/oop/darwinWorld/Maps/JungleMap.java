package agh.ics.oop.darwinWorld.Maps;


import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import static agh.ics.oop.darwinWorld.Config.*;

public class JungleMap extends AbstractMap {
    int upperJungleBound;
    int lowerJungleBound;
    int jungleArea;
    ArrayList<Vector2d> junglePositions;
    ArrayList<Vector2d> steppePositions;

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
    public void spawnGrass() {
        int jungleGrass = initialPlantCount * 4 / 5;
        int steppeGrass = initialPlantCount / 5;
        int grassPlaced = 0;
        int i=0;
        super.shuffle(junglePositions);
        while (grassPlaced < jungleGrass && i <this.junglePositions.size())
        {
            if (grasses.get(this.junglePositions.get(i)) == null)
            {
                place(new Grass(this.junglePositions.get(i)));
                grassPlaced++;
            }
            i+=1;
        }

        grassPlaced = 0;
        i= 0;
        while (grassPlaced < steppeGrass && i <this.steppePositions.size())
        {
            if (grasses.get(this.steppePositions.get(i)) == null)
            {
                place(new Grass(this.steppePositions.get(i)));
                grassPlaced++;
            }
            i+=1;
        }
    }
}
