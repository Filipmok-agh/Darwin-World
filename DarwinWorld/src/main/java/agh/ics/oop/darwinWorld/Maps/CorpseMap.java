package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Config;
import agh.ics.oop.darwinWorld.Elements.MapDirection;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public class CorpseMap extends AbstractMap {
    protected HashSet<Vector2d> steppePositionsSet;
    protected Queue<Vector2d> grassOrder;

    public CorpseMap(Config config) {
        super(config);
        this.junglePositions = new ArrayList<>();
        this.steppePositionsSet = new HashSet<>();
        this.steppePositions = new ArrayList<>();
        this.grassOrder = new ArrayDeque<>();
        allPositions(steppePositions, steppePositionsSet);
    }

    private void allPositions(ArrayList<Vector2d> arrayList, HashSet<Vector2d> hashSet) {
        for (int i = 0; i < config.mapHeight; i++) {
            for (int j = 0; j < config.mapWidth; j++) {
                Vector2d position = new Vector2d(j, i);
                arrayList.add(position);
                hashSet.add(position);
            }
        }
    }

    @Override
    public void removeDeadAnimals() {
        animals = animals.stream()
                .peek(animal -> {
                    if (animal.getEnergy() < config.dailyEnergyCost) {
                        animal.setFuneralDay(this.day);
                        for (int i = 0; i <= 8; i += 2) {
                            Vector2d position = animal.getPosition();
                            if (i != 8) {
                                position = position.add(MapDirection.values()[i].toUnitVector());
                            }
                            if (steppePositionsSet.contains(position)) {
                                steppePositions.remove(position);
                                steppePositionsSet.remove(position);
                                if (junglePositions.size()<(config.mapHeight*config.mapWidth)/5)
                                {
                                    junglePositions.add(position);
                                    grassOrder.offer(position);
                                }
                                else
                                {
                                    junglePositions.add(position);
                                    grassOrder.offer(position);
                                    Vector2d positonToRemove = grassOrder.poll();
                                    junglePositions.remove(positonToRemove);
                                    steppePositions.add(positonToRemove);
                                    steppePositionsSet.add(positonToRemove);
                                }
                            }
                        }
                    }
                })
                .filter(animal -> animal.getEnergy() >= config.dailyEnergyCost)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}