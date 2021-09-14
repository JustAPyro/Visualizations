import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Designed and implemented by Luke Hanna (Github.com/JustAPyro) on 9/14/2021
 *
 * This class is meant to comprehensively represent a maze structure
 * as well as provide any functionality necessary for the demonstration of maze generation and solving
 */
public class MazeStructure
{

    // Set default origin point to 0, 0
    int originX = 0;
    int originY = 0;

    // The total pixel width and height of the maze
    double mazeWidth, mazeHeight;

    // The total set of cells
    int width, height;

    // pixel width of cells
    double cellWidth, cellHeight;

    // RedrawFlag indicates if the GUI needs to be refereshed
    boolean redrawFlag = true;

    int[][] hWalls;     // Represents horizontal maze walls
    int[][] vWalls;     // Represents vertical maze walls
    int[][] cells;    // Represents all maze cell status

    /**
     * This class represents a specific wall to be used by maze structure
     */
    private class Wall
    {
        char o;
        int x, y;

        // Each wall can be represented by an orientation and and x, y coordinate
        public Wall(char o, int x, int y)
        {
            // Save provided variables
            this.o = o;
            this.x = x;
            this.y = y;
        }
    }

    // Create a list of saved walls
    ArrayList<Wall> saved = new ArrayList<Wall>();

    /**
     * This create a basic maze structure object to work with
     * @param x The number of units(squares) horizontally in maze
     * @param y The number of units(Squares) vertically in maze
     * @param mazeWidth the width (in pixels) of space horizontally to be used
     * @param mazeHeight the height (in pixels) of vertical space to be used
     */
    public MazeStructure(int x, int y, double mazeWidth, double mazeHeight)
    {

        // Initialize data storage arrays
        hWalls = new int[x][y-1];
        vWalls = new int[y-1][x];
        cells = new int[x][y];

        // Save width and height
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;

        // Save the cell number width and height
        width = x;
        height = y;

        // Calculate the cell sizes so we don't have to later
        cellWidth = mazeWidth/width;
        cellHeight = mazeHeight/height;

        hWalls[1][1]=-1;
    }

    /**
     * This draws the maze structure graphically using whatever graphics context is provided
     * @param gc The graphics Context you'd like the maze drawn with
     */
    public void draw(GraphicsContext gc)
    {

        // If nothing has updated just don't bother redrawing anything yet
        if (redrawFlag == false)
            return;

        // If something has updated we need to clear the canvas for redraw
        gc.clearRect(0, 0, mazeWidth, mazeHeight);



        gc.save();
        gc.setStroke(Color.PINK);
        gc.strokeRect(0, 0, mazeWidth, mazeHeight);
        gc.restore();

        // NOTE: Nested for loops are not ideal, obviously, but in this case we know we're working with relatively few
        // items, so it should be okay for this application



        // then draw the CELLS
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (cells[x][y] == 0)
                    gc.setFill(Color.DARKGRAY);
                else if (cells[x][y] == 1)
                    gc.setFill(Color.WHITE);

                gc.fillRect(cellWidth*x, cellHeight*y, cellWidth, cellHeight);

            }
        }

        // Start by drawing the VERTICAL LINES
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {
                if (vWalls[x][y] == 0)
                    gc.setStroke(Color.BLACK);
                else if (vWalls[x][y] == 10)
                    gc.setStroke(Color.ORANGE);

                gc.strokeLine((x+1)*cellWidth, (y)*cellHeight, (x+1)*cellWidth, (y+1)*cellHeight);

            }
        }

        // then draw the HORIZONTAL LINES
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                if (hWalls[x][y] == 0)
                    gc.setStroke(Color.BLACK);
                else if (hWalls[x][y] == 10)
                    gc.setStroke(Color.ORANGE);

                gc.strokeLine((x)*cellWidth, (y+1)*cellHeight, (x+1)*cellWidth, (y+1)*cellHeight);

            }
        }

        redrawFlag = false;

    }

    /**
     * This function will save the walls surrounding a cell to an internal list
     * @param x The x coordinate of cell
     * @param y The y coordinate of cell
     */
    public void saveSurroundingWalls(int x, int y)
    {
        //TODO: Make sure that this won't IndexOutOfBoundsException

        // For 1, 1
        hWalls[x][y-1] = 10;
        hWalls[x][y] = 10;
        vWalls[x-1][y] = 10;
        vWalls[x][y] = 10;

        // Save the new walls to the saved list
        saved.add(new Wall('h', x, y-1));
        saved.add(new Wall('h', x, y));
        saved.add(new Wall('v', x-1, y));
        saved.add(new Wall('v', x, y));

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
