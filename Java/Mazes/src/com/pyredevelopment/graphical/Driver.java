package com.pyredevelopment.graphical;

import javafx.application.Application; // Need this to call application.launch

// Launches the Maze
public class Driver
{

    // Main method (Duh)
    public static void main(String[] args)
    {
        // Launch the com.pyredevelopment.graphical.GUI, passing in the the args and com.pyredevelopment.graphical.GUI class, as well as the maze generator of choice
        Application.launch(GUI.class, args);

    }

}
