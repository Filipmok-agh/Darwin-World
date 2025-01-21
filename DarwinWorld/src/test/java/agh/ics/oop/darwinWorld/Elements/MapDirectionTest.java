package agh.ics.oop.darwinWorld.Elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {
    @Test
    void testToUnitVector() {
        MapDirection north = MapDirection.NORTH;
        MapDirection north_west = MapDirection.NORTH_WEST;
        MapDirection west = MapDirection.WEST;
        MapDirection south_west = MapDirection.SOUTH_WEST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection south_east = MapDirection.SOUTH_EAST;
        MapDirection east = MapDirection.EAST;
        MapDirection north_east = MapDirection.NORTH_EAST;

        assertEquals(north.toUnitVector(),new Vector2d(0,1));
        assertEquals(north_east.toUnitVector(),new Vector2d(1,1));
        assertEquals(west.toUnitVector(),new Vector2d(-1,0));
        assertEquals(south.toUnitVector(),new Vector2d(0,-1));
        assertEquals(south_east.toUnitVector(),new Vector2d(1,-1));
        assertEquals(east.toUnitVector(),new Vector2d(1,0));
        assertEquals(south_west.toUnitVector(),new Vector2d(-1,-1));
        assertEquals(north_west.toUnitVector(),new Vector2d(-1,1));
    }

    @Test
    void testOpposite() {
        MapDirection north = MapDirection.NORTH;
        MapDirection north_west = MapDirection.NORTH_WEST;
        MapDirection west = MapDirection.WEST;
        MapDirection south_west = MapDirection.SOUTH_WEST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection south_east = MapDirection.SOUTH_EAST;
        MapDirection east = MapDirection.EAST;
        MapDirection north_east = MapDirection.NORTH_EAST;

        north = north.opposite();
        north_west= north_west.opposite();
        south_west= south_west.opposite();
        south_east= south_east.opposite();
        east= east.opposite();
        south= south.opposite();
        west= west.opposite();
        north_east= north_east.opposite();

        assertEquals(MapDirection.NORTH,south );
        assertEquals(MapDirection.SOUTH,north );
        assertEquals(MapDirection.WEST,east );
        assertEquals(MapDirection.EAST,west );
        assertEquals(MapDirection.NORTH_EAST,south_west );
        assertEquals(MapDirection.SOUTH_EAST,north_west );
        assertEquals(MapDirection.NORTH_WEST,south_east );
        assertEquals(MapDirection.SOUTH_WEST,north_east );
    }

    @Test
    void testToNumber() {
        MapDirection north = MapDirection.NORTH;
        MapDirection north_west = MapDirection.NORTH_WEST;
        MapDirection west = MapDirection.WEST;
        MapDirection south_west = MapDirection.SOUTH_WEST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection south_east = MapDirection.SOUTH_EAST;
        MapDirection east = MapDirection.EAST;
        MapDirection north_east = MapDirection.NORTH_EAST;

        assertEquals(0, north.toNumber());
        assertEquals(1, north_east.toNumber());
        assertEquals(2, east.toNumber());
        assertEquals(3, south_east.toNumber());
        assertEquals(4, south.toNumber());
        assertEquals(5, south_west.toNumber());
        assertEquals(6, west.toNumber());
        assertEquals(7, north_west.toNumber());
    }

    @Test
    void testFromNumber() {
        assertEquals(MapDirection.NORTH, MapDirection.fromNumber(0));
        assertEquals(MapDirection.NORTH_EAST, MapDirection.fromNumber(1));
        assertEquals(MapDirection.EAST, MapDirection.fromNumber(2));
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.fromNumber(3));
        assertEquals(MapDirection.SOUTH, MapDirection.fromNumber(4));
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.fromNumber(5));
        assertEquals(MapDirection.WEST, MapDirection.fromNumber(6));
        assertEquals(MapDirection.NORTH_WEST, MapDirection.fromNumber(7));

        assertThrows(IllegalArgumentException.class, () -> MapDirection.fromNumber(8));
        assertThrows(IllegalArgumentException.class, () -> MapDirection.fromNumber(-1));
    }
}