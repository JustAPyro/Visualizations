package com.pyredevelopment.drivers;

import com.pyredevelopment.graphical.GUISolveBFS;
import javafx.application.Application;

/**
 * This is a driver for the breadth first search visulization GUI
 *
 * @author Luke Hanna (Github.com/JustAPyro | PyreDevelopment.com)
 * @version 1.0 | Created s{DATE} | Updated s{DATE}
 */
public class BFSDriver
{

    /**
     * Main entry point
     * @param args Arguments
     */
    public static void main(String[] args)
    {
        // call Application.launch to initialized a JFX thread and launch the GUI
        Application.launch(GUISolveBFS.class, args);
    }

}
