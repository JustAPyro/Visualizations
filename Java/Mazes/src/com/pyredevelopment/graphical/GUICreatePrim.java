package com.pyredevelopment.graphical;

import com.pyredevelopment.algorithms_generators.GeneratorPrim;
import javafx.stage.Stage;

/**
 * This class represents the visualization of maze generation using a randomized prim's algorithm.
 * Implementation by Luke Hanna (Github.com/JustAPyro / PyreDevelopment.com) on Sept 2021
 */
public class GUICreatePrim extends GUI
{

    /**
     * This is entered when the application is launched, by editing the parent variables, we can make this window
     * an appropriate way to display visualizations of most maze generation or solving techniques.
     *
     * @param primaryStage The main window of the application
     */
    @Override
    public void start(Stage primaryStage)
    {
        // Initialize our maze related GUI
        init();

        // Declare GUI info
        titleString = "Prim's Maze Generation";     // Window title
        headerString = "Prim's Maze Generation";    // Window Header
        goalString = "Maze";                        // Complete ... ? Button

        // Insert the Prim's algorithm text to solve this
        // Create a text manager and attach it to textCanvas
        tm = new TextManager(textCanvas);
        tm.addText(1, 0, "1. Start with a grid full of walls");
        tm.addText(0, 0, "2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.");
        tm.addText(0, 0, "3. While there are walls in the list:");
        tm.addText(0, 1, "1. Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then:");
        tm.addText(0, 2, "1. Make the wall a passage and mark the unvisited cell as part of the maze");
        tm.addText(0, 2, "2. Add the neighboring walls of the cell to the wall list.");
        tm.addText(0, 1, "2. Remove the wall from the list");

        // Then create a Prim generator and attach it to mazeCanvas
        alg = new GeneratorPrim(mazeCanvas, tm);

        // Run the super GUI constructor
        super.start(primaryStage);
    }
}
