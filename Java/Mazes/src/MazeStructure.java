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

    int[] hWalls;     // Represents horizontal maze walls
    int[] vWalls;     // Represents vertical maze walls
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
        hWalls = new int[y-1];
        vWalls = new int[x-1];
        cells = new int[x][y];

        // Save width and height
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;

        // Save the cell number width and height
        width = x;
        height = y;

    }


}
