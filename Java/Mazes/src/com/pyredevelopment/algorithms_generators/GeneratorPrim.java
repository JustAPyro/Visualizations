package com.pyredevelopment.algorithms_generators;

import com.pyredevelopment.graphical.TextManager;
import com.pyredevelopment.maze.MazeAlgorithm;
import com.pyredevelopment.maze.MazeStructure;
import com.pyredevelopment.maze.Wall;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/*
Implementation of Randomized Prim's Algorithm in maze generation, using com.pyredevelopment.maze.MazeStructure.
Implemented by Luke Hanna (github.com/JustAPyro)
Project started on Sept 13, 2021
Objective: Creating a easily understandable visualization of Prim's Maze generation

         Details: Implementation is based on Wikipedia's pseudo code (https://bit.ly/3pSH9Z6) as follows:

         1. Start with a grid full of walls
         2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
         3. While there are walls in the list:
             1. Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then:
                 1. Make the wall a passage and mark the unvisited cell as part of the maze.
                 2. Add the neighboring walls of the cell to the wall list.
             2. Remove the wall from the list

You can find a write up of the original plans for the project here:
https://docs.google.com/document/d/10b-LSSGvkl0g05j54R10NhtFlJdlgE82eGOzjwQn1sg/edit?usp=sharing
 */
public class GeneratorPrim extends MazeAlgorithm
{

    Color   NOT_IN_MAZE = Color.LIGHTGRAY;
    Color       IN_MAZE = Color.WHITE;
    Color    WALL_SAVED = Color.ORANGE;
    Color WALL_SELECTED = Color.RED;
    Color   WALL_NORMAL = Color.DARKGREY;

    final ArrayList<Wall> saved;          // List of saved walls
    int selectedIndex = 0;          // Working index to track
    Wall workingWall;

    /**
     * Basic constructor for a prim generator
     * @param canvas Requires the canvas you wish it to be drawn/animated on
     * @param tm The text manager you would like updated with canvas
     */
    public GeneratorPrim(Canvas canvas, TextManager tm)
    {

        // Add the text that's associated with the algorithm
        tm.addText(1, 0, "1. Start with a grid full of walls");
        tm.addText(0, 0, "2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.");
        tm.addText(0, 0, "3. While there are walls in the list:");
        tm.addText(0, 1, "1. Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then:");
        tm.addText(0, 2, "1. Make the wall a passage and mark the unvisited cell as part of the maze");
        tm.addText(0, 2, "2. Add the neighboring walls of the cell to the wall list.");
        tm.addText(0, 1, "2. Remove the wall from the list");

        currentStep = 0;

        // Save the provided parameters
        this.canvas = canvas;
        this.tm = tm;

        // Since no com.pyredevelopment.maze.MazeStructure was offered in this constructor, we create a new one
        // TODO: This is broken for non-square mazes
        maze = new MazeStructure(10, 8, canvas);
        maze.colorAllCells(NOT_IN_MAZE);
        maze.colorAllWalls(WALL_NORMAL);

        // Initialized the walls array
        saved = new ArrayList<>();

    }


    /**
     * Packs up the maze structure by removing un-necessary (and un-serializable objects)
     * and returning it. After serializing, if you wish to continue using, call "Unpack()"
     * @return The current maze that the generator is working on
     */
    public MazeStructure pack()
    {

        maze.setCanvas(null);
        return maze;
    }

    /**
     * Determines if the maze is complete or not yet
     * @return True if maze is completed, false otherwise
     */
    @Override
    public boolean isComplete()
    {
        // If there are no walls in the saved list and we're not on the first step
        // The maze must be completed so finish the list
        // Otherwise maze construction still in progress
        return saved.size() == 0 && currentStep > 0;

    }


    /**
     * Takes a step and then redraws the maze
     */
    @Override
    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public boolean nextStep()
    {
        // Step = "Pick a cell, mark it as part of the maze and add walls to wall list
        if (currentStep == 0)
        {
            // Create a random generator
            Random rnd = new Random();


            // Then we pick a random cell, mark it as part of the maze, and adds walls to the list
            int rx = rnd.nextInt(8); int ry = rnd.nextInt(8);

            // Add the random cell to the maze
            maze.colorCell(rx, ry, IN_MAZE);

            // Get the surrounding walls
            ArrayList<Wall> surround = maze.getSurroundingWalls(rx, ry);





            maze.colorWall(surround, WALL_SAVED);


            // Save them to the list
            saved.addAll(surround);

            // Select the next row of text
           tm.selectText(1);

            currentStep++;

        }
        // Step = "While there are walls in the list"
        else if (currentStep == 1)
        {
            tm.updateText(2, "3. While there are walls in the list: (Currently " + saved.size() +" in list)");
            tm.selectText(2);
            currentStep++;
        }
        // Step = Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then:
        else if (currentStep == 2)
        {

            // Create a random generator
            Random rnd = new Random();

            // Pick the index of a random wall
            selectedIndex = rnd.nextInt(saved.size());

            // Color the wall to indicate it's been picked
            maze.colorWall(saved.get(selectedIndex), WALL_SELECTED);

            // Get the value of the two cells
            Wall[] cells = maze.getCells(saved.get(selectedIndex));

            // If ONLY (XOR) one of the cells has been visited ( in the maze ), then:
            if (maze.getValue(cells[0]) == maze.getColorKey(IN_MAZE) ^ maze.getValue(cells[1]) == maze.getColorKey(IN_MAZE))
            {
                tm.updateText(3, "1. Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then: (True)");

            }
            else
            {
                currentStep += 2;
                tm.updateText(3, "1. Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then: (False)");
            }

            tm.selectText(3);
            currentStep++;
        }
        else if (currentStep == 3)
        {
            tm.selectText(4);

            Wall[] cells = maze.getCells(saved.get(selectedIndex));
            if (maze.getValue(cells[0]) == maze.getColorKey(IN_MAZE) ^ maze.getValue(cells[1]) == maze.getColorKey(IN_MAZE))
            {
                if (maze.getValue(cells[0]) == maze.getColorKey(IN_MAZE))
                {
                    maze.colorCell(cells[1].getX(), cells[1].getY(), IN_MAZE);
                    workingWall = cells[1];
                }
                else
                {
                    maze.colorCell(cells[0].getX(), cells[0].getY(), IN_MAZE);
                    workingWall = cells[0];
                }


                maze.breakWall(saved.get(selectedIndex));
            }

            currentStep++;
        }
        else if (currentStep == 4)
        {
            ArrayList<Wall> surround = maze.getSurroundingWalls(workingWall.getX(), workingWall.getY());
            for (int i = 0; i < surround.size(); i++)
            {
                if (maze.getValue(surround.get(i)) == -1)
                {
                    surround.remove(i);
                }
            }

            // Color the provided walls in orange
            maze.colorWall(surround, WALL_SAVED);
            saved.addAll(surround);

            tm.selectText(5);
            currentStep++;
        }
        else if (currentStep == 5)
        {
            if (maze.getValue(saved.get(selectedIndex)) == 11)
                maze.colorWall(saved.get(selectedIndex), 10, false);
            saved.remove(selectedIndex);
            tm.selectText(6);
            currentStep = 1;
        }
        return saved.size() == 0 && currentStep < 2;
    }

    // This performs a quick generation of prims algorithm on any given canvas, passing back the new maze structure
    public static MazeStructure getRandomMaze(int x, int y, Canvas c)
    {
        Color   NOT_IN_MAZE = Color.LIGHTGRAY;
        Color       IN_MAZE = Color.WHITE;
        Color    WALL_SAVED = Color.ORANGE;
        Color WALL_SELECTED = Color.RED;
        Color   WALL_NORMAL = Color.DARKGREY;

        MazeStructure maze = new MazeStructure(x, y, c);
        maze.colorAllCells(NOT_IN_MAZE);
        maze.colorAllWalls(WALL_NORMAL);

        ArrayList<Wall> saved = new ArrayList<Wall>();

        // Get a random cell
        Random rnd = new Random();
        int rx = rnd.nextInt(x); int ry = rnd.nextInt(y);
        maze.colorCell(rx, ry, IN_MAZE);

        ArrayList<Wall> surrounding = maze.getSurroundingWalls(rx, ry);
        maze.colorWall(surrounding, WALL_SELECTED);
        saved.addAll(surrounding);

        while (saved.size() > 0)
        {
            Wall wall = saved.remove(rnd.nextInt(saved.size()));
            Wall[] cells = maze.getCells(wall);

            int visitKey = maze.getColorKey(IN_MAZE);
            // If only one of those cells is visited
            if (maze.getValue(cells[0]) == visitKey ^ maze.getValue(cells[1]) == visitKey)
            {
                maze.breakWall(wall);
                ArrayList<Wall> surroundingWalls = new ArrayList<Wall>();
                if (maze.getValue(cells[0]) == visitKey)
                {
                    maze.colorCell(cells[1].getX(), cells[1].getY());
                    surroundingWalls.addAll(maze.getSurroundingWalls(cells[1].getX(), cells[1].getY()));
                }
                else
                {
                    maze.colorCell(cells[0].getX(), cells[0].getY());
                    surroundingWalls.addAll(maze.getSurroundingWalls(cells[0].getX(), cells[0].getY()));
                }

                surroundingWalls.remove(wall);
                for (int i = 0; i < surroundingWalls.size(); i++)
                {
                    System.out.println(surroundingWalls.get(i) + " vs " + wall);
                    if (surroundingWalls.get(i).equals(wall))
                    {
                        System.out.println("Removed!");
                        surroundingWalls.remove(i);
                    }
                }
                maze.colorWall(surroundingWalls, WALL_SAVED);
                saved.addAll(surroundingWalls);




            }

            saved.remove(wall);
        }




        return maze;
/*
        // While there are walls in the list
        while (saved.size() > 0)
        {
            // Pick a random wall value and remove it, then
            Wall wall = saved.remove(rnd.nextInt(saved.size()));

            // Get the two cells around this wall
            Wall[] cells = maze.getCells(wall);

            // If only one of those cells is visited
            if (maze.getValue(cells[0]) == 1 ^ maze.getValue(cells[1]) == 1)
            {
                maze.breakWall(wall);

                maze.colorCell(cells[0].getX(), cells[0].getY(), IN_MAZE);
                maze.colorCell(cells[1].getX(), cells[1].getY(), IN_MAZE);

                ArrayList<Wall> surroundings = maze.getSurroundingWalls(cells[0].getX(), cells[0].getY());
                maze.colorWall(surroundings, WALL_SAVED);
                surroundings.addAll(maze.getSurroundingWalls(cells[1].getX(), cells[1].getY()));

                for (int i = 0; i < surroundings.size(); i++)
                {
                    if (surroundings.get(i) == wall || saved.contains(surroundings.get(i)))
                    {
                        surroundings.remove(i);
                    }
                }

                saved.addAll(surroundings);
            }
        }

        return maze;
        */

    }

    @Override
    public void newMazeButton()
    {
        setMaze(new MazeStructure(8, 8, canvas));
    }
}
