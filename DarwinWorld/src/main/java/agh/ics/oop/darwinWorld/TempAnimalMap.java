package agh.ics.oop.darwinWorld;

import agh.ics.oop.darwinWorld.Animals.Animal;
import agh.ics.oop.darwinWorld.Animals.Vector2d;

import java.util.HashMap;
import java.util.LinkedList;

public class TempAnimalMap
{
    private HashMap<Vector2d, LinkedList<Animal>> animals;

    public TempAnimalMap() {
        this.animals = new HashMap<>();
    }
    public void insertnew(Animal animal) {
        Vector2d position = animal.getPosition();
        LinkedList<Animal> animalsAtPosition = animals.computeIfAbsent(position, k -> new LinkedList<>());

        boolean inserted = false;
        for (int i = 0; i < animalsAtPosition.size(); i++) {
            if (animal.isStronger(animalsAtPosition.get(i))) {
                animalsAtPosition.add(i, animal);
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            animalsAtPosition.addLast(animal);
        }
    }
}
