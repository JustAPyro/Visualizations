package com.pyredevelopment.graphical;

import javafx.stage.Stage;

public class GUISolveDFS extends GUI
{
    /**
     * This is entered when the application is launched
     *
     * @param primaryStage The main window of the application
     */
    @Override
    public void start(Stage primaryStage)
    {
        titleString = "Depth First Maze Solving";
        headerString = "Depth First Maze Solving";
        goalString = "Solve";

        super.start(primaryStage);
    }

}
