package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static agh.ics.oop.darwinWorld.Config.*;

public class CorpseMap extends AbstractMap {

    ArrayList<Vector2d> junglePositions;
    ArrayList<Vector2d> steppePositions;
    HashSet<Vector2d> steppePositionsSet;

    public CorpseMap()
    {
        junglePositions = new ArrayList<>();
        steppePositionsSet = new HashSet<>();
        steppePositions = new ArrayList<>();
        allPositions(steppePositions, steppePositionsSet);
    }

    private void allPositions(ArrayList<Vector2d> arrayList,HashSet<Vector2d> hashSet)
    {

        for(int i = 0; i < mapHeight; i++)
        {
            for (int j=0; j<mapWidth; j++)
            {
                Vector2d position = new Vector2d(j, i);
                arrayList.add(position);
                hashSet.add(position);
            }
        }
    }

    @Override
    public void removeDeadAnimals()
    {
        Iterator<Animal> iterator = animals.iterator();
        while(iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() < dailyEnergyCost) {
                animal.setFuneralDay(this.day);
                if (steppePositionsSet.contains(animal.getPosition()))
                {
                    steppePositions.remove(animal.getPosition());
                    steppePositionsSet.remove(animal.getPosition());
                    junglePositions.add(animal.getPosition());
                }
                iterator.remove();
            }
        }
    }
}
