package com.pyredevelopment.maze;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static java.lang.Math.min;

/**
 * Designed and implemented by Luke Hanna (Github.com/JustAPyro) on 9/14/2021
 *
 * This class is meant to comprehensively represent a maze structure
 * as well as provide any functionality necessary for the demonstration of maze generation and solving
 */
public class MazeStructure
{

    // Set default origin point to 0, 0
    double originX = 0;
    double originY = 0;

    // The total pixel width and height of the maze
    double mazeWidth, mazeHeight;

    // The total set of cells
    double width, height;

    // pixel width of cells
    double cellWidth, cellHeight;

    // RedrawFlag indicates if the com.pyredevelopment.graphical.GUI needs to be refereshed
    boolean redrawFlag = true;

    private Canvas canvas;

    int[][] hWalls;     // Represents horizontal maze walls
    int[][] vWalls;     // Represents vertical maze walls
    int[][] cells;    // Represents all maze cell status



    // Create a list of saved walls
    ArrayList<Wall> saved = new ArrayList<Wall>();

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

        System.out.println(canvas.getWidth());
        System.out.println(mazeWidth + " / " + width);

        // Calculate the cell sizes so we don't have to later
        cellWidth = min((mazeWidth+20)/width,(mazeHeight+20)/height);
        cellHeight = min((mazeHeight+20)/height, (mazeWidth+20)/width);
        System.out.println(cellWidth + ", " + cellHeight);

    }

    public void addCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }

    /**
     * This draws the maze structure graphically using whatever graphics context is provided
     * @param gc The graphics Context you'd like the maze drawn with
     */
    public void draw(GraphicsContext gc)
    {

        // If nothing has updated just don't bother redrawing anything yet
        if (redrawFlag == false & mazeWidth == canvas.getWidth() & mazeHeight == canvas.getHeight())
            return;

        // Adjust height since something change
        mazeWidth = canvas.getWidth();
        mazeHeight = canvas.getHeight();

        // Since it's possible the positions have changed we:
        // Calculate the scale of everything again
        if (mazeWidth < mazeHeight)
        {
            cellHeight = (mazeWidth-20)/width;
            cellWidth = cellHeight; // Keepin' it square
            originY = (mazeHeight-(cellHeight*height))/2;
            originX = 10;
        }
        else
        {
            cellWidth = (mazeHeight-20)/height;
            cellHeight = cellWidth; // Keepin' it square
            originX = (mazeWidth-(cellWidth*width))/2;
            originY = 10;
        }

        // If something has updated we need to clear the canvas for redraw
        gc.clearRect(0, 0, mazeWidth, mazeHeight);

        System.out.println("Draw");

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
                gc.fillRect(cellWidth*x+originX, cellHeight*y+originY, cellWidth, cellHeight);

            }
        }

        // Start by drawing the VERTICAL LINES
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {
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

        redrawFlag = false;

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
        ArrayList<Wall> ret = new ArrayList<Wall>();

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

        // If it's a veritcal wall we return left and right cells
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
        System.out.println(walls.size());
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

    /**
     * This method simply returns the number of walls currently saved
     * @return Number of saved walls
     */
    public int savedWallSize()
    {
        // Return the number of saved walls
        return saved.size();
    }

    // Add to maze given indexes and then redraw
    public void addToMaze(int x, int y)
    {
        // Set the provided cell to part of the maze
        cells[x][y] = 1;

        // Flag that the maze needs to be redrawn
        redrawFlag = true;

    }

}