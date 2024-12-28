package agh.ics.oop.darwinWorld;

import agh.ics.oop.darwinWorld.Elements.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testAdd()
    {
//        given
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(3,4);

//        when
        Vector2d v3 = v1.add(v2);

//        then
        assertEquals(new Vector2d(4,6),v3);
    }

    @Test
    void doesIsYInRangeWorks()
    {
        //when
        Vector2d v1 = new Vector2d(2,5);

        //then
        assertFalse(v1.IsYInRange(3,4));
        assertFalse(v1.IsYInRange(6,8));

        assertTrue(v1.IsYInRange(3,5));
        assertTrue(v1.IsYInRange(3,6));
        assertTrue(v1.IsYInRange(1,10));
        assertTrue(v1.IsYInRange(1,7));
    }

    @Test
    void testIsXInRange()
    {
        //when
        Vector2d v1 = new Vector2d(2,2);

        //then
        assertTrue(v1.isXGreaterThan(1));
        assertFalse(v1.isXGreaterThan(3));
        assertFalse(v1.isXGreaterThan(2));
    }

    @Test
    void doesIsXLessThanWorks()
    {
        //when
        Vector2d v1 = new Vector2d(2,2);

        //then
        assertTrue(v1.isXLessThan(4));
        assertFalse(v1.isXLessThan(2));
        assertFalse(v1.isXLessThan(1));
    }

    @Test
    void doesEqualsWorks()
    {
//        when
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(3,4);
        int n=5;

//        then
        assertTrue(v1.equals(v2));
        assertFalse(v1.equals(v3));
        assertFalse(v1.equals(n));
        assertTrue(v1.equals(v1));
        assertFalse(v1.equals(null));
    }
}