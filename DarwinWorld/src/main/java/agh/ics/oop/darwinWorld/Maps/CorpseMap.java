package agh.ics.oop.darwinWorld.Maps;

import agh.ics.oop.darwinWorld.Elements.Animal;
import agh.ics.oop.darwinWorld.Elements.Vector2d;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static agh.ics.oop.darwinWorld.Config.*;

public class CorpseMap extends AbstractMap {
    public HashSet<Vector2d> steppePositionsSet;

    public CorpseMap() {
        this.junglePositions = new ArrayList<>();
        this.steppePositionsSet = new HashSet<>();
        this.steppePositions = new ArrayList<>();
        allPositions(steppePositions, steppePositionsSet);
    }

    private void allPositions(ArrayList<Vector2d> arrayList, HashSet<Vector2d> hashSet) {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
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
                    if (animal.getEnergy() < dailyEnergyCost) {
                        animal.setFuneralDay(this.day);
                        for (int i = 0; i <= 8; i+=2) {
                            Vector2d position = animal.getPosition();
                            if (i != 8){
                                position = position.add(MapDirection.values()[i].toUnitVector());
                            }
                            if (steppePositionsSet.contains(position)) {
                                steppePositions.remove(position);
                                steppePositionsSet.remove(position);
                                junglePositions.add(position);
                            }
                        }
                    }
                })
                .filter(animal -> animal.getEnergy() >= dailyEnergyCost)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}