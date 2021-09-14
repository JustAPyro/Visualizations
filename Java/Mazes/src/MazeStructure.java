import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    int[][] hWalls;     // Represents horizontal maze walls
    int[][] vWalls;     // Represents vertical maze walls
    int[][] cells;    // Represents all maze cell status


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
        hWalls = new int[x-1][y-1];
        vWalls = new int[y-1][x-1];
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
        gc.save();
        gc.setStroke(Color.PINK);
        gc.strokeRect(0, 0, mazeWidth, mazeHeight);
        gc.restore();

        // NOTE: Nested for loops are not ideal, obviously, but in this case we know we're working with relatively few
        // items, so it should be okay for this application

        // Start by drawing the VERTICAL LINES
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {

                gc.strokeLine((x+1)*cellWidth, (y)*cellHeight, (x+1)*cellWidth, (y+1)*cellHeight);

            }
        }

        // then draw the HORIZONTAL LINES
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {

                gc.strokeLine((x)*cellWidth, (y+1)*cellHeight, (x+1)*cellWidth, (y+1)*cellHeight);

            }
        }



    }

}
