package agh.ics.oop.darwinWorld;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import agh.ics.oop.darwinWorld.Maps.AbstractMap;
import agh.ics.oop.darwinWorld.Maps.JungleMap;

public class Main {
    public static void main(String[] args)
    {
        JungleMap map = new JungleMap();
        Vector2d pos = new Vector2d(20,20);
        for (int i = 0; i < 10; i++){
            Animal animal = new Animal(pos);
            map.place(animal);
        }
        map.animalProcreation();
        map.animalsMovement();
        map.addInitialAnimals();
        map.spawnGrass();
        System.out.println('a');
    }
}