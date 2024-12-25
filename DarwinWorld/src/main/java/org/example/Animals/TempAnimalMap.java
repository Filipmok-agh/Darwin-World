package org.example.Animals;

import org.example.Enums_Vectors.Vector2d;

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
            if (animal.greaterThan(animalsAtPosition.get(i))) {
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
