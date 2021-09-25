package com.pyredevelopment.algorithms_solvers;

import com.pyredevelopment.algorithms_generators.GeneratorPrim;
import com.pyredevelopment.graphical.TextManager;
import com.pyredevelopment.maze.MazeAlgorithm;
import com.pyredevelopment.maze.Wall;
import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

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

        Color VISITED = Color.LIGHTBLUE;

        maze = GeneratorPrim.getRandomMaze(8, 8, canvas);
        int visited = maze.setColor(VISITED);


        // We'll start in the top left
        maze.setMarker(0, 0, VISITED);

        //int VISITED = maze.colorCell();

        maze.colorCell(0, 0, VISITED);
        maze.setRedrawFlag(true);
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
        ArrayList<Direction> directionsOpen = maze.getMarkerChoices();
        StringBuilder pos = new StringBuilder();
        for (Direction d : directionsOpen)
        {
            pos.append(d.toString()).append(", ");
        }
        System.out.println(directionsOpen.size() + " valid directions: " + pos);


        if (directionsOpen.size() == 1)
            maze.moveMarker(directionsOpen.get(0));
        return false;
    }
}
