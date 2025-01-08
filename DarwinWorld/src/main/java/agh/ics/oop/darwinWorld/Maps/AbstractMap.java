package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Grass;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import agh.ics.oop.darwinWorld.Elements.WorldElement;

import java.util.*;
import java.util.stream.Collectors;

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
    public abstract void removeDeadAnimals();

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
//    Kuba: Nie jestem pewien czy dobrze to rozumiem, ale wygląda na to, że im więcej dodanej trawy => niższy lastIndex => pozycje z niższym lastIndexem będą częściej brane pod uwagę w losowaniu
//    Filip: Ta metoda działa na takiej zasadzie, że mamy tą tablicę pozycji positions
//    ona ma rozmiar n to przy pierwszym wywołaniu gdy wylosujemy jakaś pozycję (z zakresu 0,lastindex) j to sadzimy na niej trawę(bądź też nie)
//    a następnie zamieniamy miejscami element j z elementem na ostatniej pozycji
//    ponownie losujemy index (z zakresu 0, lastindex-1) przez co każda pozycja ma takie same szanse na wylosowanie
//    a już nie wylosujemy tej pozycji którą wylosowaliśmy za pierwszym razem
//    ta funckja pozwala nam na losowanie w najgorszym wypadku n razy i gwarantuje, że posadzimy tą ilość trawy którą chcemy( no chyba, że nie ma tylu wolnych miejsc)
    private void spawnGrassInArea(List<Vector2d> positions, int grassCount)
    {
        int grassPlaced = 0;
        int lastIndex = positions.size() - 1;
        while (grassPlaced < grassCount && lastIndex >= 0)
        {
            int randomIndex = new Random().nextInt(lastIndex+1);
            Vector2d tempPosition = positions.get(randomIndex);
            if (grasses.get(tempPosition) == null) {
                place(new Grass(tempPosition));
                grassPlaced++;
            }
            positions.set(randomIndex, positions.get(lastIndex));
            positions.set(lastIndex, tempPosition);
            lastIndex--;
        }
    }

    public void spawnGrass()
    {
        int jungleGrass = (int) Math.ceil((double) (initialPlantCount * 4) / 5);
        int steppeGrass = (int) Math.floor((double) (initialPlantCount / 5));
        spawnGrassInArea(this.junglePositions, jungleGrass);
        spawnGrassInArea(this.steppePositions, steppeGrass);
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
}
