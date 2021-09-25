package com.pyredevelopment.maze;

public class Maze
{

    private int[][] vWalls;
    private int[][] hWalls;
    private int[][] cells;

    public Maze(int x, int y)
    {
        if (x < 1 || y < 1)
            throw new IllegalArgumentException("Maze must have x, y greater then 0");

        vWalls = new int[x-1][y];
        hWalls = new int[x][y-1];
        cells = new int[x][y];
    }

    public int getCell(Unit unit)
    {
        return getCell(unit.x, unit.y);
    }

    public int getCell(int x, int y)
    {
        if (x < 1 || y < 1)
            throw new IllegalArgumentException("Tried to call getCell() on negative or zero value");

        if (x > cells.length || y > cells[x-1].length)
            throw new IllegalArgumentException("Tried to access a cell off of the maze");

        return cells[x-1][y-1];
    }



    public static void main(String[] args)
    {
        Maze m = new Maze(4, 2);
    }



}
