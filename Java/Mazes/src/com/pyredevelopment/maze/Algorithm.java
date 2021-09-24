package com.pyredevelopment.maze;

public interface Algorithm
{

    // Allows you to unpack a maze onto the GUI
    abstract void unpack(MazeStructure m);

    // Allows you to pack a maze a maze and return it in a serializable state
    abstract MazeStructure pack();

    // Returns true if algorithm is complete and false if not
    abstract boolean isComplete();

    // Takes the next step of the algorithm and returns true if it's complete
    abstract boolean nextStep();

    // TODO: Figure out what in the world this is for
    abstract void unpack();

    // Draws a visual representation of the algorithm
    abstract void draw();
}
