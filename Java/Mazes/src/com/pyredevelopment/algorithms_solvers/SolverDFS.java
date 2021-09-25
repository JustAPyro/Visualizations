package com.pyredevelopment.algorithms_solvers;

import com.pyredevelopment.algorithms_generators.GeneratorPrim;
import com.pyredevelopment.graphical.TextManager;
import com.pyredevelopment.maze.MazeAlgorithm;
import com.pyredevelopment.maze.Wall;
import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import static javafx.scene.AccessibleAttribute.VISITED;

/**
 * This class represents the algorithmic and procedural method of solving a maze using a Depth First Search
 *
 * @author Luke Hanna (Github.com/JustAPyro / PyreDevelopment.com)
 * @version 1.0 - Built 9/24/2021 - Updated 9/24/21
 */
public class SolverDFS extends MazeAlgorithm
{

    // Position of our AI
    int[] positionAI;

    // List of cell's we've visited / moves we've made
    ArrayList<int[]> visitedCells;
    ArrayList<Direction> lastMoves;

    private final Color VISITED = Color.LIGHTBLUE;

    /**
     * Constructor class
     * @param canvas The canvas on which you want the visualization shown
     * @param tm The text manager that will handle the explanation during visulization
     */
    public SolverDFS(Canvas canvas, TextManager tm)
    {
        // Save the provided parameters
        this.canvas = canvas;
        this.tm = tm;

        // set maze into a random maze generated using the Randomized Prim's Algorithm
        maze = GeneratorPrim.getRandomMaze(8, 8, canvas);

        // Start the AI's position in the top left
        positionAI = new int[] {0, 0};

        // Initialize the list to store visited cells and moves
        visitedCells = new ArrayList<>();
        lastMoves = new ArrayList<>();

        // Add 0, 0 to visited cells and color it accordingly
        visitedCells.add(new int[] {0, 0});
        maze.colorCell(0, 0, VISITED);

        // Pass the AI's position matrix into the maze for it to be drawn
        maze.setPositionAI(positionAI);

    }

    @Override
    public void newMazeButton()
    {
        maze = GeneratorPrim.getRandomMaze(8, 8, canvas);
        currentStep = 0;
    }

    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public boolean nextStep()
    {
        // Get the possible open directions
        ArrayList<Direction> open = maze.getOpenDirections(positionAI);



        // If there's only one direction, let's move in that direction
        if (open.size() == 1)
            moveAI(open.get(0));



        return false;
    }

    /**
     * Returns the opposite of the direction given
     * @param dir Input direction
     * @return Opposite of the input direction
     */
    private Direction opposite(Direction dir)
    {
        // Switch based on provided direction
        switch(dir)
        {
            // Return opposite
            case RIGHT: return Direction.LEFT;
            case LEFT:  return Direction.RIGHT;
            case DOWN:  return Direction.UP;
            case UP:    return Direction.DOWN;
        }

        // If no return was given, throw exception
        throw new IllegalArgumentException("Direction unknown! Only use SolverDFS.opposite() with left/right/up/down");
    }

    private void moveAI(Direction dir)
    {
        // TODO: Maybe some error handling here?

        // Switch statement based on direction
        switch(dir)
        {
            case UP:    positionAI[1]--; break;
            case DOWN:  positionAI[1]++; break;
            case LEFT:  positionAI[0]--; break;
            case RIGHT: positionAI[0]++; break;
        }

        // Add the move to the list of move's we've made
        lastMoves.add(dir);

        // Add copy the new position to cell's we've visited
        visitedCells.add(positionAI.clone());

        // Color the cell as whatever color visited is
        maze.colorCell(positionAI, VISITED);

        // redraw the maze
        maze.setRedrawFlag(true);
    }

}


