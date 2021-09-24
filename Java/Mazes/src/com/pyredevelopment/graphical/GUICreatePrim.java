package com.pyredevelopment.graphical;

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
        titleString = "Prim's Maze Generation";
        headerString = "Prim's Maze Generation";
        goalString = "Maze";


        super.start(primaryStage);
    }
}
