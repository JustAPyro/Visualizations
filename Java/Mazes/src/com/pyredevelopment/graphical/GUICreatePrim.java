package com.pyredevelopment.graphical;

import com.pyredevelopment.algorithms_generators.GeneratorPrim;
import javafx.stage.Stage;

/**
 * This class represents the visualization of maze generation using a randomized prim's algorithm.
 *
 * @author Luke Hanna (Github.com/JustAPyro / PyreDevelopment.com)
 * @version 1.0 - Updated (9/24/2021)
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
        goalString = "Maze";                        // Complete "Maze" button

        // Create a text manager and attach it to textCanvas
        tm = new TextManager(textCanvas);

        // Then create a Prim generator and attach it to mazeCanvas
        alg = new GeneratorPrim(mazeCanvas, tm);

        // Run the super GUI constructor
        super.start(primaryStage);
    }
}
