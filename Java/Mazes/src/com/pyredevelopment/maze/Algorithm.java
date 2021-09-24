package com.pyredevelopment.maze;

import java.io.File;

public abstract class Algorithm
{

    // - - - - - - - - - - File Storage / IO - - - - - - - - - -

    // Saves the maze that the algorithm is working on (Useful for switching between generators/solvers
    public abstract void saveMaze(File file);

    // Allows you to load a maze specific maze into the algorithm
    public abstract void loadMaze(MazeStructure m);

    // - - - - - - - - - - Algorithm Related Methods - - - - - - - - - -

    // Returns true if algorithm is complete and false if not
    public abstract boolean isComplete();

    // Takes the next step of the algorithm and returns true if it's complete
    public abstract boolean nextStep();

    // - - - - - - - - - -

    // Draws a visual representation of the algorithm
    public void draw()
    {

    }
}
