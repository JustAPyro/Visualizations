package com.pyredevelopment.maze;

import java.io.Serializable;

/**
 * This class represents a specific wall to be used by maze structure
 * Implements serializable to assist in serializing maze structure
 */
public class Wall implements Serializable
{

    final char o;     // Represents orientation, (Horizontal/vertical/cell)
    final int x;
    final int y;   // Represents x, y position

    // - - - - - - - - - - Constructors - - - - - - - - - -

    // Each wall can be represented by an orientation and and x, y coordinate
    public Wall(char o, int x, int y)
    {
        // Save provided variables
        this.o = o;
        this.x = x;
        this.y = y;
    }

    // - - - - - - - - - - Getters / Setters - - - - - - - - - -

    // Get o (Represents orientation, horizontal, vertical or cell)
    @SuppressWarnings("unused")
    public char getO()
    {
        return o;
    }

    // Get x value
    public int getX()
    {
        return x;
    }

    // Get y value
    public int getY()
    {
        return y;
    }

    public String toString()
    {
        return o + " (" + x + ", " + y + ")";
    }

}
