package com.pyredevelopment.graphical;

import com.pyredevelopment.algorithms_solvers.SolverDFS;
import javafx.stage.Stage;

/**
 * This class represents and runs the visulization of solving a mase using Depth First Search
 *
 * @author Luke Hanna (Github.com/JustAPyro / PyreDevelopment.com)
 * @version 1.0 - Updated (9/24/2021)
 */
public class GUISolveDFS extends GUI
{
    /**
     * This is entered when the application is launched and in it we configure the GUI to display our desired
     * algorithm visualization, in this case it's going to be SolverDFS
     *
     * @param primaryStage The main window of the application
     */
    @Override
    public void start(Stage primaryStage)
    {
        // Initialize our maze related GUI
        init();

        // Declare GUI info
        titleString = "Depth First Maze Solving";   // Window title
        headerString = "Depth First Maze Solving";  // Window header
        goalString = "Solve";                       // Complete "Solve" button

        // Insert the DFS text to display text while we solve it
        tm = new TextManager(textCanvas);

        // Insert the DFS algorithm to manage everything
        alg = new SolverDFS(mazeCanvas, tm);

        // Call the super GUI constructor
        super.start(primaryStage);
    }

}
