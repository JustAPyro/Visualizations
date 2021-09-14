import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/*
Implementation of Randomized Prim's Algorithm in maze generation, using MazeStructure.
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
public class PrimGenerator
{

    private Canvas canvas;          // The canvas the PrimGenerator works on primarily
    private GraphicsContext gc;     // The graphics context that is mainly used
    private double width, height;   // Parameters for width/height of the canvas
    private MazeStructure maze;     // The maze structure this generator will work with

    /**
     * Basic constructor for a prim generator
     * @param canvas Requires the canvas you wish it to be drawn/animated on
     */
    public PrimGenerator(Canvas canvas)
    {
        // Save the provided parameters
        this.canvas = canvas;

        // Calculate additional necessary parameters on creation so we don't have to do it later
        gc = canvas.getGraphicsContext2D();
        width = canvas.getWidth();
        height = canvas.getHeight();

        // Since no MazeStructure was offered in this constructor, we create a new one
        maze = new MazeStructure(8, 8, width, height);
    }

    /**
     * Draws the current state of working MazeStructure based on the PrimGenerator State
     */
    public void draw()
    {

        maze.draw(gc);

    }

}
