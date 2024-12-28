package agh.ics.oop.darwinWorld;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import agh.ics.oop.darwinWorld.Maps.AbstractMap;
import agh.ics.oop.darwinWorld.Maps.JungleMap;

public class Main {
    public static void main(String[] args)
    {
        JungleMap map = new JungleMap();
        Vector2d pos = new Vector2d(1,1);
        Animal animal1 = new Animal(pos);
        Animal animal2 = new Animal(pos);
        animal1.setEnergy(200);
        map.place(animal1);
        map.place(animal2);
        map.place(animal1.breeding(animal2));
        map.addInitialAnimals();
        map.spawnGrass();
        System.out.println('a');
    }
}