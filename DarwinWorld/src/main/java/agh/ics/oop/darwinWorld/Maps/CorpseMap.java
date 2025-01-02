package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static agh.ics.oop.darwinWorld.Config.*;

public class CorpseMap extends AbstractMap {

    ArrayList<Vector2d> junglePositions;
    ArrayList<Vector2d> steppePositions;

    public CorpseMap()
    {
        junglePositions = new ArrayList<>();
        steppePositions = allPositions();
    }

    private ArrayList<Vector2d> allPositions()
    {
        ArrayList<Vector2d> positions = new ArrayList<>();
        for(int i = 0; i < mapHeight; i++)
        {
            for (int j=0; j<mapWidth; j++)
            {
                positions.add(new Vector2d(j, i));
            }
        }
        return positions;
    }

    @Override
    public void removeDeadAnimals()
    {
        Iterator<Animal> iterator = animals.iterator();
        while(iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() < dailyEnergyCost) {
                animal.setFuneralDay(this.day);
                if (steppePositions.contains(animal.getPosition())) {
                    steppePositions.remove(animal.getPosition());
                    junglePositions.add(animal.getPosition());
                }
                iterator.remove();
            }
        }
    }
}
