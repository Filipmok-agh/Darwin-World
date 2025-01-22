package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Config;
import agh.ics.oop.darwinWorld.Elements.Vector2d;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class JungleMap extends AbstractMap {
    private final int lines;
    private final int lowerJungleBound;


    public JungleMap(Config config) {
        super(config);
        this.lines = Math.round((float) config.mapHeight / 5);
        this.lowerJungleBound = (config.mapHeight - lines) / 2;
        this.junglePositions = junglePossiblePositions();
        this.steppePositions = steppePossiblePositions();
    }

    private ArrayList<Vector2d> junglePossiblePositions() {
        ArrayList<Vector2d> possiblePositions = new ArrayList<>();
        for (int i = this.lowerJungleBound; i < this.lowerJungleBound + lines; i++) {
            for (int j = 0; j < config.mapWidth; j++) {
                possiblePositions.add(new Vector2d(j, i));
            }
        }
        return possiblePositions;
    }

    private ArrayList<Vector2d> steppePossiblePositions() {
        ArrayList<Vector2d> possiblePositions = new ArrayList<>();
        for (int i = 0; i < this.lowerJungleBound; i++) {
            for (int j = 0; j < config.mapWidth; j++) {
                possiblePositions.add(new Vector2d(j, i));
            }
        }
        for (int i = this.lowerJungleBound + lines; i < config.mapHeight; i++) {
            for (int j = 0; j < config.mapWidth; j++) {
                possiblePositions.add(new Vector2d(j, i));
            }
        }
        return possiblePositions;
    }

    @Override
    public void removeDeadAnimals() {
        AtomicInteger totalDeadDaysCount = new AtomicInteger(0);
        AtomicInteger deadAnimalsCount = new AtomicInteger(0);
        animals = animals.stream()
                .peek(animal -> {
                    if (animal.getEnergy() < config.dailyEnergyCost) {
                        totalDeadDaysCount.addAndGet(animal.getDaysAlive());
                        deadAnimalsCount.incrementAndGet();
                        animal.setFuneralDay(this.day);
                    }
                })
                .filter(animal -> animal.getEnergy() >= config.dailyEnergyCost)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
