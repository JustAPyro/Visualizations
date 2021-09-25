
package com.pyredevelopment.maze;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest
{

    // - - - - - - - - - - Testing Constructor - - - - - - - - - -

    // negative value provided
    @Test
    void testNegativeX()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
            new Maze(-1, 1);
                });
    }

    // negative value provided
    @Test
    void testNegativeY()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
                    new Maze(1, -1);
                });
    }

    // zero value provided
    @Test
    void testZeroX()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
                    new Maze(0, 1);
                });
    }

    // zero value provided
    @Test
    void testZeroY()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
                    new Maze(1, 0);
                });
    }

    // - - - - - - - - - - Testing Get Cell - - - - - - - - - -

    Maze maze = new Maze(5, 10);

    // Testing edge of array values
    @Test
    void testGetCellEdgeCase()
    {
        assertEquals(maze.getCell(5, 10), 0);
    }

    // Negative X value provided
    @Test
    void testGetCellNegativeX()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
                    maze.getCell(-3, 5);

                });
    }

    // Negative Y value provided
    @Test
    void testGetCellNegativeY()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
                    maze.getCell(5, -3);
                });
    }

    // zero x value provided
    @Test
    void testGetCellZeroX()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
                    maze.getCell(0, 5);

                });
    }

    // zero y value provided
    @Test
    void testGetCellZeroY()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
                    maze.getCell(5, 0);
                });
    }

    // Test Out of bounds upper case X
    @Test
    void testGetCellOutOfBoundsX()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
                    maze.getCell(6, 3);
                });
    }

    // Test Out of bounds upper case Y
    @Test
    void testGetCellOutOfBoundsY()
    {
        assertThrows(IllegalArgumentException.class,
                ()->{
                    maze.getCell(3, 11);
                });
    }



}