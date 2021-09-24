package com.pyredevelopment.maze;

import com.pyredevelopment.cutility.CUtility;
import com.pyredevelopment.graphical.TextManager;
import javafx.scene.canvas.Canvas;

import java.io.File;

public abstract class MazeAlgorithm
{

    // This is the current step of the algorithm we are on
    protected int currentStep;

    // This is the text manager that will dictate the algorithms process
    protected TextManager tm;

    // The maze object that the algorithm is working with
    protected MazeStructure maze;

    // The primary canvas the maze is being visualized on to
    protected Canvas canvas;

    // - - - - - - - - - - Maze Related Methods - - - - - - - - - -

    public abstract void newMazeButton();


    /**
     * Allows you to set the maze a given algorithm is working on (Note, if you're working with a .maze file you
     * can just use the loadMaze(File) method instead of this
     * @param maze
     */
    public void setMaze(MazeStructure maze)
    {
        this.maze = maze;
    }

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
     * @param file The maze file you wanted loaded in
     */
    public void loadMaze(File file)
    {
        MazeStructure maze = MazeStructure.loadMaze(file);

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
