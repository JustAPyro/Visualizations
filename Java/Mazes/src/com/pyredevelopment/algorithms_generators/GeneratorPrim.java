package com.pyredevelopment.algorithms_generators;

import com.pyredevelopment.graphical.TextManager;
import com.pyredevelopment.maze.MazeAlgorithm;
import com.pyredevelopment.maze.MazeStructure;
import com.pyredevelopment.maze.Wall;
import javafx.scene.canvas.Canvas;

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

    private final TextManager tm;         // The text manager that will draw text associated with algorithm
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

        currentStep = 0;

        // Save the provided parameters
        this.canvas = canvas;
        this.tm = tm;

        // Since no com.pyredevelopment.maze.MazeStructure was offered in this constructor, we create a new one
        // TODO: This is broken for non-square mazes
        maze = new MazeStructure(8, 8, canvas);

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
            maze.addToMaze(rx, ry);

            // Get the surrounding walls
            ArrayList<Wall> surround = maze.getSurroundingWalls(rx, ry);

            // Color the provided walls in orange
            maze.colorWall(surround, 10);

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
            maze.colorWall(saved.get(selectedIndex), 11);

            // Get the value of the two cells
            Wall[] cells = maze.getCells(saved.get(selectedIndex));

            // If ONLY (XOR) one of the cells has been visited, then:
            if (maze.getValue(cells[0]) == 1 ^ maze.getValue(cells[1]) == 1)
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
            if (maze.getValue(cells[0]) == 1 ^ maze.getValue(cells[1]) == 1)
            {
                if (maze.getValue(cells[0]) == 1)
                {
                    maze.addToMaze(cells[1].getX(), cells[1].getY());
                    workingWall = cells[1];
                }
                else
                {
                    maze.addToMaze(cells[0].getX(), cells[0].getY());
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
            maze.colorWall(surround, 10);
            saved.addAll(surround);

            tm.selectText(5);
            currentStep++;
        }
        else if (currentStep == 5)
        {
            if (maze.getValue(saved.get(selectedIndex)) == 11)
                maze.colorWall(saved.get(selectedIndex), 10);
            saved.remove(selectedIndex);
            tm.selectText(6);
            currentStep = 1;
        }
        return saved.size() == 0 && currentStep < 2;
    }


}