package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;

public interface WorldMap {
    void removeDeadAnimals();
    void animalsMovement();
    void animalActivities();
    void spawnGrass(Integer amount);
    void addInitialAnimals();
}
