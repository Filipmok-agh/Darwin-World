package agh.ics.oop.darwinWorld;

import agh.ics.oop.darwinWorld.Animals.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void testNext() {
//        given
        MapDirection north = MapDirection.NORTH;
        MapDirection north_west = MapDirection.NORTH_WEST;
        MapDirection west = MapDirection.WEST;
        MapDirection south_west = MapDirection.SOUTH_WEST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection south_east = MapDirection.SOUTH_EAST;
        MapDirection east = MapDirection.EAST;
        MapDirection north_east = MapDirection.NORTH_EAST;

//        when
        north_west= north_west.next();
        south_west= south_west.next();
        south_east=south_east.next();
        north_east= north_east.next();
        west=west.next();
        north=north.next();
        east=east.next();
        south=south.next();
//        then
        assertEquals(MapDirection.NORTH,north_west );
        assertEquals(MapDirection.EAST, north_east );
        assertEquals(MapDirection.SOUTH, south_east );
        assertEquals(MapDirection.WEST, south_west );
        assertEquals(MapDirection.NORTH_EAST, north );
        assertEquals(MapDirection.SOUTH_WEST, south);
        assertEquals(MapDirection.SOUTH_EAST, east);
        assertEquals(MapDirection.NORTH_WEST, west);
    }
    @Test
    void testToUnitVector() {
//        given
        MapDirection north = MapDirection.NORTH;
        MapDirection north_west = MapDirection.NORTH_WEST;
        MapDirection west = MapDirection.WEST;
        MapDirection south_west = MapDirection.SOUTH_WEST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection south_east = MapDirection.SOUTH_EAST;
        MapDirection east = MapDirection.EAST;
        MapDirection north_east = MapDirection.NORTH_EAST;

//        then
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
//        given
        MapDirection north = MapDirection.NORTH;
        MapDirection north_west = MapDirection.NORTH_WEST;
        MapDirection west = MapDirection.WEST;
        MapDirection south_west = MapDirection.SOUTH_WEST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection south_east = MapDirection.SOUTH_EAST;
        MapDirection east = MapDirection.EAST;
        MapDirection north_east = MapDirection.NORTH_EAST;

//        when
        north = north.opposite();
        north_west= north_west.opposite();
        south_west= south_west.opposite();
        south_east= south_east.opposite();
        east= east.opposite();
        south= south.opposite();
        west= west.opposite();
        north_east= north_east.opposite();

//        then
        assertEquals(MapDirection.NORTH,south );
        assertEquals(MapDirection.SOUTH,north );
        assertEquals(MapDirection.WEST,east );
        assertEquals(MapDirection.EAST,west );
        assertEquals(MapDirection.NORTH_EAST,south_west );
        assertEquals(MapDirection.SOUTH_EAST,north_west );
        assertEquals(MapDirection.NORTH_WEST,south_east );
        assertEquals(MapDirection.SOUTH_WEST,north_east );
    }
}