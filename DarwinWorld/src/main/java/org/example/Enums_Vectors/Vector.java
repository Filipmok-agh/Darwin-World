package org.example.Enums_Vectors;

public interface Vector
{
    /**
     * Adds two vectors
     * @param v param to add
     * @return New vector
     */
    Vector2d add(Vector2d v);

    /**
     * Checking if vector is equal to object
     * @param o to compare
     * @return True if object is a Vector and object is equal to vector
     */
    boolean equals(Object o);

    //Creating hashCode
    int hashCode();

    /**
     * Checking if y is in Range
     * @param bottom lowest val to check
     * @param top highest val to check
     * @return True if y is between (inclusive)
     */
    boolean yInRange(int bottom, int top);

    /**
     * Checking if x is over right range
     * @param right value to check
     * @return True if x is greater than right
     */
    boolean xOver(int right);

    /**
     * Checking if x is over right range
     * @param left value to check
     * @return True if x lower than left
     */
    boolean xUnder(int left);
}
