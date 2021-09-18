package com.pyredevelopment.graphical;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class TextManager
{

    private final GraphicsContext gc; // and associate graphicsContext

    private final ArrayList<String> lines = new ArrayList<>();      // Saves the lines of text
    private final ArrayList<Integer> indent = new ArrayList<>();   // Saves the amount of indent for each line
    private final ArrayList<Integer> index = new ArrayList<>();    // Saves the index we can use to refer to it

    /**
     * Basic constructor
     * @param canvas That you want the text managed on
     */
    public TextManager(Canvas canvas)
    {
        // Set the canvas with the parameter given
        // Canvas we will draw one

        // Then calculate graphics context/width/height so we don't have to later
        this.gc = canvas.getGraphicsContext2D();

    }

    public void selectText(int index)
    {

        for (int i = 0; i < this.index.size(); i++)
        {
            this.index.set(i, 0);
        }
        this.index.set(index, 1);
    }

    /**
     * Method that can be used to add lines of text to the manager
     * @param index The index that you can use to select or modify the text
     * @param indent The amount of indent (Tab) you want applied to the text
     * @param str The string of associated text
     */
    public void addText(int index, int indent, String str)
    {
        lines.add(str);             // Add the string to the lines arrayList
        this.indent.add(indent);    // save the indent
        this.index.add(index);      // And the index
    }

    /**
     * Updates a previously provided string
     * @param index The index of the string you want modified
     * @param str The new string provided
     */
    public void updateText(int index, String str)
    {
        // Set the index to string
        lines.set(index, str);
    }

    /**
     * Method that will draw and manage the graphics of the text
     */
    public void draw()
    {

        // Save the graphics context before we do anything else
        gc.save();

        // For each string of text
        for (int i = 0; i < lines.size(); i++)
        {
            // Check the index to see if it's currently highlighted
            if (index.get(i) == 0)
                gc.setFill(Color.BLACK);
            if (index.get(i) == 1)
                gc.setFill(Color.RED);

            // Draw it, moving down based on i, and indenting based on given parameter
            gc.fillText(lines.get(i), 50+20*indent.get(i), 20*i+25);
        }
    }


}
