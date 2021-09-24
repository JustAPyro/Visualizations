package com.pyredevelopment.maze;

import com.pyredevelopment.cutility.CUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;

public abstract class MazeAlgorithm
{

    // This is the current step of the algorithm we are on
    protected int currentStep;

    // The maze object that the algorithm is working with
    protected MazeStructure maze;

    // The primary canvas the maze is being visualized on to
    protected Canvas canvas;

    // - - - - - - - - - - File Storage / IO - - - - - - - - - -

    /**
     * This allows you to save the mazeStructure that the algorithm is currently working on
     * @param file The file you want the maze saved to
     */
    public void saveMaze(File file)
    {
        // Save the current canvas
        Canvas savedCanvas = maze.getCanvas();

        // Temporarily say canvas null during serialization
        maze.setCanvas(null);

        // Save the file to provided file
        CUtility.WriteObjectToFile(file, maze);

        // Restore the original canvas after file is saved
        maze.setCanvas(savedCanvas);
    }

    /**
     * This allows you to load a maze structure onto the current canvas
     * @param maze The maze you wanted loaded in
     */
    public void loadMaze(MazeStructure maze)
    {
        // Reset to step 0
        currentStep = 0;

        // If provided, unpack the provided maze as well
        this.maze = maze;

        // Reset the canvas after serializations
        this.maze.setCanvas(canvas);

        // Set the canvas to be redrawn to be affected by update
        this.maze.setRedrawFlag(true);
    }

    // - - - - - - - - - - Algorithm Related Methods - - - - - - - - - -

    // Returns true if algorithm is complete and false if not
    public abstract boolean isComplete();

    // Takes the next step of the algorithm and returns true if it's complete
    public abstract boolean nextStep();

    // - - - - - - - - - -

    // Draws a visual representation of the algorithm
    public void draw()
    {
        // Draw the maze!
        maze.draw(canvas.getGraphicsContext2D());
    }
}
