package com.pyredevelopment.maze;

import com.pyredevelopment.cutility.CUtility;
import com.sun.javafx.scene.traversal.Direction;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static java.lang.Math.min;

/**
 * Designed and implemented by Luke Hanna (Github.com/JustAPyro) on 9/14/2021
 *
 * This class is meant to comprehensively represent a maze structure
 * as well as provide any functionality necessary for the demonstration of maze generation and solving
 * Implements serializable so that the maze object can be saved and restored at will
 */
public class MazeStructure implements Serializable
{

    // This is the position of an AI if one is provided
    private int[] positionAI = null;

    // Set default origin point to 0, 0
    double originX = 0;
    double originY = 0;

    // The total pixel width and height of the maze
    double mazeWidth, mazeHeight;

    // The total set of cells
    final double width;
    final double height;

    // pixel width of cells
    double cellWidth, cellHeight;

    // RedrawFlag indicates if the com.pyredevelopment.graphical.GUI needs to be refreshed
    boolean redrawFlag = true;

    // Lists any character labels you would like drawn on the maze
    private ArrayList<int[]> characterLabels = new ArrayList<>();

    private Canvas canvas;

    int markerKey;
    public int[][] hWalls;     // Represents horizontal maze walls
    public int[][] vWalls;     // Represents vertical maze walls
    int[][] cells;    // Represents all maze cell status

    Wall marker;
    public Hashtable<Integer, Color> colors = new Hashtable<>();


    // Create a list of saved walls
    ArrayList<Wall> saved = new ArrayList<>();

    /**
     * Allows you to create a headless theoretical maze structure
     * @param x
     * @param y
     */
    public MazeStructure(int x, int y)
    {
        // Initialize data storage arrays
        hWalls = new int[x][y-1];
        vWalls = new int[y-1][x];
        cells = new int[x][y];

        width = x;
        height = y;
    }

    /**
     * This create a basic maze structure object to work with
     * @param x The number of units(squares) horizontally in maze
     * @param y The number of units(Squares) vertically in maze
     * @param canvas The canvas that the maze is being drawn on, if applicable
     */
    public MazeStructure(int x, int y, Canvas canvas)
    {


        // Save the canvas
        this.canvas = canvas;

        // Initialize data storage arrays
        vWalls = new int[x-1][y];
        hWalls = new int[x][y-1];
        cells = new int[x][y];

        // Save width and height
        this.mazeWidth = canvas.getWidth();
        this.mazeHeight = canvas.getHeight();

        // Save the cell number width and height
        width = x;
        height = y;

        // Calculate the cell sizes so we don't have to later
        cellWidth = min((mazeWidth+20)/width,(mazeHeight+20)/height);
        cellHeight = min((mazeHeight+20)/height, (mazeWidth+20)/width);

    }

    // - - - - - - - - - - Color Method Stuff - - - - - - - - - -

    /**
     * Gets the key for whatever color is passed in-
     * If there is no key for that color, a new one will be created and returned
     * @param color The color of which you'd like the key
     * @return The key of the passed in color
     */
    public Integer getColorKey(Color color)
    {
        // Declare the return value
        Integer key;

        // If colors doesn't contain key
        if (!colors.containsValue(color))
        {
            // Declare key as the next integer value available
            key = colors.size();

            // Insert the new color with new key
            colors.put(key, color);

            // Return key
            return key;
        }

        // Otherwise, we have to search the hashmap for the key, so for each entry
        for (Map.Entry<Integer, Color> entry : colors.entrySet())
        {
            // Check the value, and if it equals color
            if (entry.getValue().equals(color))
            {
                // Return the key
                return entry.getKey();
            }
        }

        throw new RuntimeException("Something went terribly wrong with the KeyColor hashmap!");

    }

    /**
     * Colors all cells in the maze the provided color
     * @param color The color you'd like the maze filled with
     */
    public void colorAllCells(Color color)
    {
        // Get the key for the color (Or create it if it doesn't exist)
        Integer key = getColorKey(color);

        // Then for each row and column
        for (int row = 0; row < cells.length; row++)
            for (int col = 0; col < cells[row].length; col++)
            {
                // Tag the cell with the correct color key
                cells[row][col] = key;
            }
    }

    /**
     * This will color all walls the provided color
     * @param color The color you would like to set all walls to
     */
    public void colorAllWalls(Color color)
    {
        // get the key (or create if necessary) for the provided color
        int key = getColorKey(color);

        // For each horizontal wall
        for (int row = 0; row < hWalls.length; row++)
        {
            for (int col = 0; col < hWalls[row].length; col++)
            {
                // Set to be equal to the new key
                hWalls[row][col] = key;
            }
        }

        // For each vertical wall
        for (int row = 0; row < vWalls.length; row++)
        {
            for (int col = 0; col < vWalls[row].length; col++)
            {
                // Set to be equal to the new key
                vWalls[row][col] = key;
            }
        }
    }

    /**
     * Colors the provided cell the given color based on key
     * Overloaded colorCell(int, int, int)
     *
     * @param x value of the cell
     * @param y value of the cell
     * @param key key for the desired color
     */
    public void colorCell(int x, int y, int key)
    {
        // Set the cell to desired key, since we have it already
        cells[x][y] = key;
    }

    /**
     * Colors the provided cell the given color
     * Overloaded colorCell(int int Color)
     *
     * @param x value of the cell
     * @param y value of the cell
     * @param color desired color
     */
    public void colorCell(int x, int y, Color color)
    {
        cells[x][y] = getColorKey(color);
    }

    /**
     * Colors the provided cell the given color
     * Overloaded colorCell(int[] Color)
     *
     * @param cell 2 element array containing x, y for the cell
     * @param color The color you would like it filled
     */
    public void colorCell(int[] cell, Color color)
    {
        cells[cell[0]][cell[1]] = getColorKey(color);
    }

    /**
     * Colors the provided wall with the key given
     * @param w The wall you'd like colored
     * @param key The key of the desired color
     */
    private void colorWall(Wall w, int key)
    {
        // Switch to determine if it's a hWall or vWall
        switch (w.o)
        {
            // Assign the key to the correct corresponding wall and then break
            case 'h': hWalls[w.x][w.y] = key; break;
            case 'v': vWalls[w.x][w.y] = key; break;
        }
        // Request the maze to be redrawn
        redrawFlag = true;
    }

    /**
     * Colors the wall the provided color
     * @param w The wall you'd like colored
     * @param color The desired color
     */
    public void colorWall(Wall w, Color color)
    {
        // Get the keyColor or create one if necessary
        int key = getColorKey(color);

        // Call the colorWall function, passing the wall and key
        colorWall(w, key);

    }

    /**
     * Colors an entire ArrayList of walls a given color
     * @param walls ArrayList of walls you'd like colored
     * @param color The desired color
     */
    public void colorWall(ArrayList<Wall> walls, Color color)
    {
        // Start by getting the color key (or creating it if it doesn't exist)
        int key = getColorKey(color);

        // Then for each wall
        for (Wall w : walls)
        {
            // Call the colorWall function, passing in the wall and the key
            colorWall(w, key);
        }
    }

    // - - - - - - - - - - - AI Related stuff! - - - - - - - - - -

    public void setPositionAI(int[] position)
    {
        positionAI = position;
    }

    public ArrayList<Direction> getOpenDirections(int[] position)
    {
        // Create an array to store the open directions
        ArrayList<Direction> open = new ArrayList<Direction>();

        // NOTE: Return values are right->down->left->up, clockwise, starting right- This determines depth first direction
        // Check each wall around provided position and add that direction if possible
        if (position[0] > 0 && vWalls[position[0]-1][position[1]] == -1)
            open.add(Direction.LEFT);
        if (position[1] > 0 && hWalls[position[0]][position[1]-1] == -1)
            open.add(Direction.UP);
        if (vWalls[position[0]][position[1]] == -1)
            open.add(Direction.RIGHT);
        if (hWalls[position[0]][position[1]] == -1)
            open.add(Direction.DOWN);

        // Return possible directions
        return open;
    }

    // - - - - - - - - - - Character Label Methods - - - - - - - - - -
    public void labelChar(int x, int y, char c)
    {
        characterLabels.add(new int[] {x, y, (int) c});
        redrawFlag = true;
    }

    public int[] getLocationChar(char target)
    {
        for (int[] c : characterLabels)
        {
            if (c[2] == target)
                return new int[] {c[0], c[1]};
        }

        return new int[0];
    }

    public char consumeLabelChar(int x, int y)
    {
        for (int[] c : characterLabels)
        {
            if (c[0] == x && c[1] == y)
            {
                characterLabels.remove(c);
                return (char) c[2];
            }
        }
        return 0;
    }

    /**
     * This draws the maze structure graphically using whatever graphics context is provided
     * @param gc The graphics Context you'd like the maze drawn with
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public void draw(GraphicsContext gc)
    {

        // If nothing has updated just don't bother redrawing anything yet
        // Note the use of bitwise OR - if canvas is null we avoid calling canvas.width() and avoid errors
        if (canvas == null || (!redrawFlag & mazeWidth == canvas.getWidth() & mazeHeight == canvas.getHeight()))
            return;

        // Adjust height since something change
        mazeWidth = canvas.getWidth();
        mazeHeight = canvas.getHeight();

        // Since it's possible the positions have changed we:
        // Calculate the scale of everything again
        if (mazeWidth < mazeHeight)
        {
            cellHeight = (mazeWidth-20)/width;
            cellWidth = cellHeight; // Keeping' it square
            originY = (mazeHeight-(cellHeight*height))/2;
            originX = 10;
        }
        else
        {
            cellWidth = (mazeHeight-20)/height;
            //noinspection SuspiciousNameCombination
            cellHeight = cellWidth; // Keeping' it square
            originX = (mazeWidth-(cellWidth*width))/2;
            originY = 10;
        }

        // If something has updated we need to clear the canvas for redraw
        gc.clearRect(0, 0, mazeWidth, mazeHeight);

        // Stroke the borders of the maze before doing anything else
        gc.save();
        gc.setStroke(Color.BLACK);
        gc.strokeRect(originX, originY, cellWidth*width, cellHeight*height);
        gc.restore();

        // NOTE: Nested for loops are not ideal, obviously, but in this case we know we're working with relatively few
        // items, so it should be okay for this application


        for (int rows = 0; rows < cells.length; rows++) {
            for (int col = 0; col < cells[rows].length; col++) {
                gc.setFill(colors.get(cells[rows][col]));
                gc.fillRect(cellWidth*rows+originX, cellHeight*col+originY, cellWidth, cellHeight);

            }
        }



        // Start by drawing the VERTICAL LINES
        for (int row = 0; row < vWalls.length; row++) {
            for (int col = 0; col < vWalls[row].length; col++) {

                // If the wall doesn't exist just continue and don't draw it
                if (vWalls[row][col] == -1)
                    continue;

                // Otherwise use the key of the tile to set the color
                gc.setStroke(colors.get(vWalls[row][col]));

                // And then draw the lines
                gc.strokeLine((row+1)*cellWidth+originX, (col)*cellHeight+originY,
                        (row+1)*cellWidth+originX, (col+1)*cellHeight+originY);

            }
        }


        // then draw the HORIZONTAL LINES
        for (int row = 0; row < hWalls.length; row++) {
            for (int col = 0; col < hWalls[row].length; col++) {

                // If the wall is negative just don't draw it
                if (hWalls[row][col] == -1)
                    continue;

                // Otherwise use the key to get color
                gc.setStroke(colors.get(hWalls[row][col]));

                // Then draw the line
                gc.strokeLine((row)*cellWidth+originX, (col+1)*cellHeight+originY,
                        (row+1)*cellWidth+originX, (col+1)*cellHeight+originY);

            }
        }

        for (int[] label : characterLabels)
        {

            char character = (char) label[2];
            gc.setFont(new Font("ARIEL", 50));
            gc.setFill(Color.BLACK);

            // Set the text to draw center label
            gc.setTextAlign(TextAlignment.CENTER); gc.setTextBaseline(VPos.CENTER);
            gc.fillText(String.valueOf(character), cellWidth*label[0]+originX + (cellWidth/2), cellHeight*label[1]+originY + (cellHeight/2));
        }

        if (positionAI != null)
        {
            gc.save();
            gc.setFill(Color.BLACK);
            gc.fillOval(cellWidth*positionAI[0]+originX+cellWidth/4, cellHeight*positionAI[1]+originY+cellHeight/4, cellWidth/2, cellHeight/2);
            gc.restore();
        }

        redrawFlag = false;

    }

    public void setMarker(int x, int y, Color color)
    {
        marker = new Wall('c', x, y);
        markerKey = getColorKey(color);
    }

    public void setMarker(int x, int y)
    {
        marker = new Wall('c', x, y);
        if (markerKey != -1)
        {
            colorCell(x, y, markerKey);
        }
    }

    public void colorCell(int x, int y)
    {
        cells[x][y] = 2;
        redrawFlag = true;
    }

    public static MazeStructure loadMaze(File file)
    {
        // Load the object from provided files
        Object obj = CUtility.LoadObjectFromFile(file);


        // If the object is truly a maze structure
        if (Objects.requireNonNull(obj).getClass() == MazeStructure.class)
        {

            // Cast and return it
            return (MazeStructure) obj;

        }
        else
        {
            throw new RuntimeException("Error loading file: found " + obj.getClass() + " instead of expected MazeStructure");
        }


    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }


    /**
     * sets the redraw flag for the associated maze
     * @param flag How you would like the flag set
     */
    public void setRedrawFlag(boolean flag)
    {
        // Set flag equal to passed parameters
        redrawFlag = flag;
    }

    /**
     * This function will return the walls surrounding cell x, y
     * @param x The x coordinate of cell
     * @param y The y coordinate of cell
     */
    public ArrayList<Wall> getSurroundingWalls(int x, int y)
    {
        //TODO: Make sure that this won't IndexOutOfBoundsException
        //TODO: FIX THE WEIRD H AXIS BEING SWITCHED HERE

        // Create an arraylist to return
        ArrayList<Wall> ret = new ArrayList<>();

        // Save the new walls to the saved list
        // NOTE: We check to ensure no IndexOutOfBoundsException occurs
        if (x > 0)
            ret.add(new Wall('v', x-1, y));
        if (x < width-1)
            ret.add(new Wall('v', x, y));
        if (y > 0)
            ret.add(new Wall('h', x, y-1));
        if (y < height-1)
            ret.add(new Wall('h', x, y));

        // Return the array list of walls
        return ret;
    }

    /**
     * This will break through a wall in the maze
     * @param w the wall to be broken
     */
    public void breakWall(Wall w)
    {
        if (w.o == 'h')
        {
            hWalls[w.x][w.y] = -1;
        }
        else if (w.o == 'v')
        {
            vWalls[w.x][w.y] = -1;
        }

        // Mark the maze for redrawing
        redrawFlag = true;
    }

    // Returns the value of a provided unit
    public int getValue(Wall w)
    {
        if (w.o == 'v')
        {
            return vWalls[w.x][w.y];
        }
        else if (w.o == 'h')
        {
            return hWalls[w.x][w.y];
        }
        else
        {
            return cells[w.x][w.y];
        }
    }


    /**
     * This returns the value of two cells on either side of a given wall
     * @param w The functioning wall
     * @return An array of the 2 cell integers on either side of w
     */
    public Wall[] getCells(Wall w)
    {
        // Create a return array
        Wall[] ret = new Wall[2];



        // If it's a horizontal wall we return top and bottom cells
        if (w.o == 'h')
        {
            ret[0] = new Wall('c', w.x, w.y);
            ret[1] = new Wall('c', w.x, w.y+1);
        }

        // If it's a vertical wall we return left and right cells
        if (w.o == 'v')
        {
            ret[0] = new Wall('c', w.x, w.y);
            ret[1] = new Wall('c', w.x+1, w.y);
        }

        // return the values
        return ret;
    }

    /**\
     * This colors the walls passed in through the list the provided color
     * @param walls The walls to be colored
     * @param color The index of the color you would like
     */
    public void colorWall(ArrayList<Wall> walls, int color)
    {

        for (Wall w : walls)
        {
            if (w.o == 'h')
            {
                hWalls[w.x][w.y] = color;
            }

            if (w.o == 'v')
            {
                vWalls[w.x][w.y] = color;
            }
        }

        // Flag that we need to redraw based on new values
        redrawFlag = true;

    }

    public ArrayList<Direction> getOpenCellsFrom(int y, int x)
    {
        ArrayList<Direction> open = new ArrayList<Direction>();
        if (y > 0 && hWalls[x][y-1] == -1)
            open.add(Direction.UP);
        if (hWalls[x][y] == -1)
            open.add(Direction.DOWN);
        if (x > 0 && vWalls[x-1][y] == -1)
            open.add(Direction.LEFT);
        if (vWalls[x][y] == -1)
            open.add(Direction.RIGHT);
        return open;
    }

    /**
     * Colors a single wall passed in
     * @param w The wall you would like coloreds
     * @param color The index of desired color
     */
    public void colorWall(Wall w, int color, boolean fd)
    {
        // If it's horizontal change the tag on horizontal walls
        if (w.o == 'h')
        {
            hWalls[w.x][w.y] = color;
        }
        // Otherwise change the coloration on vWalls
        else if (w.o == 'v')
        {
            vWalls[w.x][w.y] = color;
        }


        // Flag that we need to redraw based on new values
        redrawFlag = true;
    }

    // Add to maze given indexes and then redraw
    public void addToMaze(int x, int y)
    {
        // Set the provided cell to part of the maze
        cells[x][y] = 1;

        // Flag that the maze needs to be redrawn
        redrawFlag = true;

    }

    public ArrayList<Direction> getMarkerChoices()
    {
        // TODO: SHoudl these really be indexed like this? Rewrite marker, move logic to SolverDFS
        return getOpenCellsFrom(marker.y, marker.x);
    }

    public void moveMarker(Direction direction)
    {

        int x = marker.getX();
        int y = marker.getY();

        if (direction == Direction.DOWN)
        {
            setMarker(x, y + 1);
        }
        if (direction == Direction.RIGHT)
        {
            setMarker(x+1, y);
        }
        if (direction == Direction.LEFT)
        {
            setMarker(x-1, y);
        }
        if (direction == Direction.UP)
        {
            setMarker(x, y-1);
        }
        redrawFlag = true;
    }

}
