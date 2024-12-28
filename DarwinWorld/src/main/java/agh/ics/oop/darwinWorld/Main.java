package agh.ics.oop.darwinWorld;

import agh.ics.oop.darwinWorld.Animals.Animal;
import agh.ics.oop.darwinWorld.Animals.Vector2d;

public class Main {
    public static void main(String[] args)
    {
        TempAnimalMap map = new TempAnimalMap();
        Vector2d pos = new Vector2d(1,1);
        Animal animal1 = new Animal(pos);
        Animal animal2 = new Animal(pos);
        animal1.setEnergy(200);
        map.insertnew(animal1);
        map.insertnew(animal2);
        map.insertnew(animal1.breeding(animal2));
        System.out.println('a');
    }
}