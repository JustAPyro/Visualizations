package com.pyredevelopment.graphical;

import com.pyredevelopment.algorithms_generators.GeneratorPrim;
import com.pyredevelopment.cutility.ResizableCanvas;
import javafx.stage.Stage;

public class GUICreatePrim extends GUI
{

    /**
     * This is entered when the application is launched
     *
     * @param primaryStage The main window of the application
     */
    @Override
    public void start(Stage primaryStage)
    {
        init();

        titleString = "Prim's Maze Generation";
        headerString = "Prim's Maze Generation";
        goalString = "Maze";

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

        super.start(primaryStage);
    }
}
