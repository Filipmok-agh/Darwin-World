package agh.ics.oop.darwinWorld.Elements;

public class Grass implements WorldElement{
    protected Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
}
