package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import agh.ics.oop.darwinWorld.Elements.WorldElement;

import java.util.*;

import static agh.ics.oop.darwinWorld.Config.*;

public abstract class AbstractMap implements WorldMap {
    protected int day;
    protected LinkedList<Animal> animals;
    protected HashMap<Vector2d, Grass> grasses;
    protected HashMap<Vector2d, LinkedList<Animal>> dailyAnimals;
    ArrayList<Vector2d> junglePositions;
    ArrayList<Vector2d> steppePositions;

    public AbstractMap() {
        this.day = 0;
        this.animals = new LinkedList<>();
        this.grasses = new HashMap<>();
        this.dailyAnimals = new HashMap<>();
    }

    public void addInitialAnimals(){
        Random random = new Random();
        for (int i = 0; i < initialAnimalCount; i++) {
            int randomX = random.nextInt(mapWidth);
            int randomY = random.nextInt(mapHeight);
            Vector2d position = new Vector2d(randomX, randomY);
            Animal animal = new Animal(position);
            this.animals.add(animal);

        }
    }

    public void removeDeadAnimals(){
        Iterator<Animal> iterator = animals.iterator();
        while(iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() < dailyEnergyCost) {
                animal.setFuneralDay(this.day);
                iterator.remove();
            }
        }
    }


    public void animalsMovement() {
        this.dailyAnimals = new HashMap<>();
        for(Animal animal : animals)
        {
            animal.move();
            if(dailyAnimals.containsKey(animal.getPosition()))
            {
                addAnimal(dailyAnimals.get(animal.getPosition()), animal);
            }
            else {
                dailyAnimals.put(animal.getPosition(), new LinkedList<>(List.of(animal)));
            }
        }
    }

    public void eatPlant(Animal animal)
    {
        if (grasses.containsKey(animal.getPosition()))
        {
            animal.eat();
            grasses.remove(animal.getPosition());
        }
    }
    //Skróciłem tą metodę bo bez sensu jest przechodzić po każdym zwierzaku i sprawdzać czy akurat tam jest trawa

    public void animalActivities(){
        for(LinkedList<Animal> animalsAtThisPosition : dailyAnimals.values())
        {
            eatPlant(animalsAtThisPosition.getFirst());
            if (animalsAtThisPosition.size() < 2) {break;}
            int potentialPairs = animalsAtThisPosition.size() / 2;
            for (int i = 0; i < potentialPairs; i++) {
                Animal parent1 = animalsAtThisPosition.get(2*i);
                Animal parent2 = animalsAtThisPosition.get(2*i+1);
                if (parent1.getEnergy() >= energyToBeFed && parent2.getEnergy() >= energyToBeFed) {
                    Animal child = parent1.breeding(parent2);
                    this.animals.add(child);
                }
            }
        }
    }

    public void spawnGrass() {
        int jungleGrass = (int) Math.ceil((double) (initialPlantCount * 4) / 5);
        int steppeGrass = (int) Math.ceil((double) (initialPlantCount/ 5));
        int grassPlaced = 0;
        int i=0;
        shuffle(junglePositions);
        while (grassPlaced < jungleGrass && i <this.junglePositions.size())
        {
            if (grasses.get(this.junglePositions.get(i)) == null)
            {
                place(new Grass(this.junglePositions.get(i)));
                grassPlaced++;
            }
            i+=1;
        }

        grassPlaced = 0;
        i= 0;
        while (grassPlaced < steppeGrass && i <this.steppePositions.size())
        {
            if (grasses.get(this.steppePositions.get(i)) == null)
            {
                place(new Grass(this.steppePositions.get(i)));
                grassPlaced++;
            }
            i+=1;
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
            animals.add((Animal) element);
            if (dailyAnimals.containsKey(element.getPosition())) {
                addAnimal(dailyAnimals.get(element.getPosition()), (Animal) element);
            }
            else{
                dailyAnimals.put(element.getPosition(), new LinkedList<>(List.of((Animal) element)));
            }
        }
        else{
            grasses.put(element.getPosition(), new Grass(element.getPosition()));
        }
    }

    public void shuffle(ArrayList<Vector2d> possiblePositions)
    {
        Random rand = new Random();
        for (int i=0;i<possiblePositions.size();i++)
        {
            int randomIndex = rand.nextInt(possiblePositions.size());
            Vector2d temp = possiblePositions.get(i);
            possiblePositions.set(i, possiblePositions.get(randomIndex));
            possiblePositions.set(randomIndex, temp);
        }
    }

}
