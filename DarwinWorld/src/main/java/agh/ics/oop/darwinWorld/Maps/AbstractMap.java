package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import agh.ics.oop.darwinWorld.Elements.WorldElement;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

import static agh.ics.oop.darwinWorld.Config.*;

public abstract class AbstractMap implements WorldMap {
    protected LinkedList<Animal> animals;
    protected HashMap<Vector2d, Grass> grasses;
    protected LinkedHashMap<Vector2d, LinkedList<Animal>> dailyAnimals;

    public AbstractMap() {
        this.animals = new LinkedList<>();
        this.grasses = new HashMap<>();
        this.dailyAnimals = new LinkedHashMap<>();
    }

    public void addInitialAnimals(){
        Random random = new Random();
        for (int i = 0; i < initialAnimalCount; i++) {
            int randomX = random.nextInt(mapWidth);
            int randomY = random.nextInt(mapHeight);
            Vector2d position = new Vector2d(randomX, randomY);
            Animal animal = new Animal(position);
            //Dodajemy zwierzęta do linkedlisty zwierząt
            this.animals.add(animal);
        }
    }
    public void animalsMovement()
    {
        this.dailyAnimals = new LinkedHashMap<>();
        for(Animal animal : animals)
        {
            animal.sleep();
            if(animal.getEnergy()<=0)
            {
                //tutaj wiadomo usuwamy go ustawiamy date smierci itp
            }
            else
            {
                animal.move();
                LinkedList<Animal> temp = new LinkedList<>();
                temp = dailyAnimals.get(animal.getPosition());
                if(temp!=null)
                {
                    addAnimal(temp, animal);
                }
                else
                {
                    LinkedList<Animal> newList = new LinkedList<>();
                    newList.add(animal);
                    dailyAnimals.put(animal.getPosition(), newList);
                }
            }
        }
    }

    protected void addAnimal(LinkedList<Animal> animalsAtPosition, Animal animal) {
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


    public void place(WorldElement element) {
        if (element instanceof Animal) {
            addAnimal((Animal) element);
        }
        else{
            grasses.put(element.getPosition(), new Grass(element.getPosition()));
        }
    }


    abstract public void spawnGrass();
}
