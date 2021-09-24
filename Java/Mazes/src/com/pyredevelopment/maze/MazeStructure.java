package com.pyredevelopment.maze;

import com.pyredevelopment.cutility.CUtility;
import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

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

    private Canvas canvas;

    final int[][] hWalls;     // Represents horizontal maze walls
    final int[][] vWalls;     // Represents vertical maze walls
    final int[][] cells;    // Represents all maze cell status

    Wall marker;



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
        hWalls = new int[x][y-1];
        vWalls = new int[y-1][x];
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

        // then draw the CELLS
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (cells[x][y] == 0)
                    gc.setFill(Color.LIGHTGRAY);
                else if (cells[x][y] == 1)
                    gc.setFill(Color.WHITE);
                else if (cells[x][y] == 2)
                    gc.setFill(Color.LIGHTGREEN);
                gc.fillRect(cellWidth*x+originX, cellHeight*y+originY, cellWidth, cellHeight);

            }
        }

        // Start by drawing the VERTICAL LINES
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height - 1; x++) {
                if (vWalls[x][y] == 0)
                    gc.setStroke(Color.DARKGREY);
                else if (vWalls[x][y] == 10)
                    gc.setStroke(Color.ORANGE);
                else if (vWalls[x][y] == 11)
                    gc.setStroke(Color.RED);
                else if (vWalls[x][y] == 13)
                    gc.setStroke(Color.BLACK);
                else if (vWalls[x][y] == -1)
                    continue;

                gc.strokeLine((x+1)*cellWidth+originX, (y)*cellHeight+originY,
                        (x+1)*cellWidth+originX, (y+1)*cellHeight+originY);

            }
        }

        // then draw the HORIZONTAL LINES
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                if (hWalls[x][y] == 0)
                    gc.setStroke(Color.DARKGREY);
                else if (hWalls[x][y] == 10)
                    gc.setStroke(Color.ORANGE);
                else if (hWalls[x][y] == 11)
                    gc.setStroke(Color.RED);
                else if (hWalls[x][y] == 13)
                    gc.setStroke(Color.BLACK);
                else if (hWalls[x][y] == -1)
                    continue;

                gc.strokeLine((x)*cellWidth+originX, (y+1)*cellHeight+originY,
                        (x+1)*cellWidth+originX, (y+1)*cellHeight+originY);

            }
        }

        if (marker != null)
        {
            gc.save();
            gc.setFill(Color.BLACK);
            gc.fillOval(cellWidth*marker.getX()+originX+cellWidth/4, cellHeight*marker.getY()+originY+cellHeight/4, cellWidth/2, cellHeight/2);
            gc.restore();
        }

        redrawFlag = false;

    }

    public void setMarker(int x, int y)
    {
        marker = new Wall('c', x, y);
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
    public void colorWall(Wall w, int color)
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
