package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import agh.ics.oop.darwinWorld.Elements.WorldElement;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import static agh.ics.oop.darwinWorld.Config.*;

public abstract class AbstractMap implements WorldMap {
    protected HashMap<Vector2d, LinkedList<Animal>> animals;
    protected HashMap<Vector2d, Grass> grasses;

    public AbstractMap() {
        this.animals = new HashMap<>();
        this.grasses = new HashMap<>();
    }

    public void addInitialAnimals(){
        Random random = new Random();
        for (int i = 0; i < initialAnimalCount; i++) {
            int randomX = random.nextInt(mapWidth);
            int randomY = random.nextInt(mapHeight);
            Vector2d position = new Vector2d(randomX, randomY);
            Animal animal = new Animal(position);
            addAnimal(animal);
        }
    }

    public void place(WorldElement element) {
        if (element instanceof Animal) {
            addAnimal((Animal) element);
        }
        else{
            grasses.put(element.getPosition(), new Grass(element.getPosition()));
        }
    }

    private void addAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        LinkedList<Animal> animalsAtPosition = animals.computeIfAbsent(position, k -> new LinkedList<>());

        boolean inserted = false;
        for (int i = 0; i < animalsAtPosition.size(); i++) {
            if (((Animal) animal).isStronger(animalsAtPosition.get(i))) {
                animalsAtPosition.add(i, animal);
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            animalsAtPosition.addLast(animal);
        }
    }

    abstract public void spawnGrass();
}
