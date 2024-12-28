package agh.ics.oop.darwinWorld.Maps;


import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

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
    }

    @Override
    public void spawnGrass() {
        int grassPlaced = 0;
        int positionsTried = 0;
        int jungleGrass = initialPlantCount * 4 / 5;
        int steppeGrass = initialPlantCount / 5;
        Random random = new Random();

        while (positionsTried < 3*jungleGrass && grassPlaced < jungleGrass) {
            Vector2d newGrass = new Vector2d(random.nextInt(mapWidth), random.nextInt(lowerJungleBound, upperJungleBound));
            if (grasses.get(newGrass) == null) {
                place(new Grass(newGrass));
                grassPlaced++;
            }
            positionsTried++;
        }

        if (grassPlaced != jungleGrass) {
            steppeGrass += jungleGrass - grassPlaced;
        }

        positionsTried = 0;
        while (positionsTried < 5*steppeGrass && grassPlaced < initialPlantCount) {
            Vector2d newGrass = new Vector2d(random.nextInt(mapWidth), random.nextInt(mapHeight));
            if (grasses.get(newGrass) == null) {
                place(new Grass(newGrass));
                grassPlaced++;
            }
            positionsTried++;
        }
        System.out.println(grassPlaced);
    }
}
