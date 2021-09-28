package com.pyredevelopment.drivers;

import com.pyredevelopment.graphical.GUISolveDFS;
import javafx.application.Application;

/**
 * Driver to launch a Depth First Search maze solver
 * @author Luke Hanna (Github.com/JustAPyro | PyreAmusement.com)
 * @version 1.0 | Designed 9/14/21 | Updated 9/27/21
 */
public class DFSDriver
{

    /**
     * Main program entry points
     * @param args Standard arguments
     */
    public static void main(String[] args)
    {
        // call Application.launch to initialized a JFX thread and launch the GUI
        Application.launch(GUISolveDFS.class, args);
    }

}
