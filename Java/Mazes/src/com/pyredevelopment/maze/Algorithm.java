package com.pyredevelopment.maze;

import com.pyredevelopment.cutility.CUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;

public abstract class Algorithm
{

    protected MazeStructure maze;

    // - - - - - - - - - - File Storage / IO - - - - - - - - - -

    // Allows you to save the maze file that the algorithm is working with
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

    // Allows you to load a maze specific maze into the algorithm
    public abstract void loadMaze(MazeStructure m);

    // - - - - - - - - - - Algorithm Related Methods - - - - - - - - - -

    // Returns true if algorithm is complete and false if not
    public abstract boolean isComplete();

    // Takes the next step of the algorithm and returns true if it's complete
    public abstract boolean nextStep();

    // - - - - - - - - - -

    // Draws a visual representation of the algorithm
    public void draw(GraphicsContext gc)
    {
        // Draw the maze!
        maze.draw(gc);
    }
}
