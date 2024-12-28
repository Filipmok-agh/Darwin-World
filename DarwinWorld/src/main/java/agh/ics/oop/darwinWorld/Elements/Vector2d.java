package agh.ics.oop.darwinWorld.Elements;

import java.util.Objects;

public class Vector2d
{
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Vector2d add(Vector2d v) {
        int newX = this.x + v.x;
        int newY = this.y + v.y;
        return new Vector2d(newX, newY);
    }

    public boolean IsYInRange(int bottom, int top) {
        return this.y >= bottom && this.y <= top;
    }

    public boolean isXGreaterThan(int right) {
        return this.x > right;
    }

    public boolean isXLessThan(int left) {
        return this.x < left;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
