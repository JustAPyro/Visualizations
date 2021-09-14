import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class TextManager
{

    private Canvas canvas;      // Canvas we will draw one
    private GraphicsContext gc; // and associate graphicsContext

    private double width, height;   // Width and height of canvas

    private ArrayList<String> lines = new ArrayList<String>();      // Saves the lines of text
    private ArrayList<Integer> indent = new ArrayList<Integer>();   // Saves the amount of indent for each line
    private ArrayList<Integer> index = new ArrayList<Integer>();    // Saves the index we can use to refer to it

    private int selected = 0; // Shows which line of text is currently selected

    /**
     * Basic constructor
     * @param canvas That you want the text managed on
     */
    public TextManager(Canvas canvas)
    {
        // Set the canvas with the parameter given
        this.canvas = canvas;

        // Then calculate graphics context/width/height so we don't have to later
        this.gc = canvas.getGraphicsContext2D();
        width = canvas.getWidth();
        height = canvas.getHeight();
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
     * Method that will draw and manage the graphics of the text
     */
    public void draw()
    {
        // Stroke the outside border of our working panel
        gc.strokeRect(0, 0, width, height);

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
            gc.fillText(lines.get(i), 25+20*indent.get(i), 20*i+25);
        }
    }


}
