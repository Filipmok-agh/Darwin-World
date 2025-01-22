package agh.ics.oop.darwinWorld.Elements;

public record Vector2d(int x, int y) {

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    public Vector2d add(Vector2d v) {
        int newX = this.x + v.x;
        int newY = this.y + v.y;
        return new Vector2d(newX, newY);
    }

    public boolean isYInRange(int bottom, int top) {
        return this.y >= bottom && this.y <= top;
    }

    public boolean isXGreaterThan(int right) {
        return this.x > right;
    }

    public boolean isXLessThan(int left) {
        return this.x < left;
    }

}
