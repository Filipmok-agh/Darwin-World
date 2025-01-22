package agh.ics.oop.darwinWorld.Elements;

import org.junit.jupiter.api.Test;

public class GrassTest {
    @Test
    void TestGrass() {
        Vector2d position = new Vector2d(2, 2);
        Grass grass = new Grass(position);
    }
}
