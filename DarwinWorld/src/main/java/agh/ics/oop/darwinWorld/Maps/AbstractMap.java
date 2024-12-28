package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class AnimalMap implements WorldMap {
    private HashMap<Vector2d, LinkedList<Animal>> animals;

    public AnimalMap() {
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
