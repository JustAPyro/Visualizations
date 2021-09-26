package com.pyredevelopment.algorithms_solvers;

import com.pyredevelopment.algorithms_generators.GeneratorPrim;
import com.pyredevelopment.graphical.TextManager;
import com.pyredevelopment.maze.MazeAlgorithm;
import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * This class represents the algorithmic and procedural method of solving a maze using a Depth First Search
 *
 * @author Luke Hanna (Github.com/JustAPyro / PyreDevelopment.com)
 * @version 1.0 - Built 9/24/2021 - Updated 9/24/21
 */
public class SolverDFS extends MazeAlgorithm
{

    // The size of the maze (in cells, W x H)
    int[] size = {8, 8};

    // The end (Assuming that the end is bottom right for this case
    int[] end = {size[0]-1, size[1]-1};

    // Position of our AI
    int[] positionAI;

    // List of moves we've made, to make backtracking easier
    Stack<Direction> lastMoves;

    // Decision Stack! Used for the AI tree
    private Stack<Character> openStack = new Stack();   // The decisions we may need to revisit
    private Stack<Character> closedStack = new Stack(); // The decisions we've already checked

    private int startASCII = 65;    // ASCII codes to represent the letters we generate as we go through
    char backtrackingToChar = 0;    // 0 If not backtracking, otherwise this is the char we are backtracking to

    // List of all positions that have choices we have labeled
    ArrayList<int[]> labeledPositions = new ArrayList<>();

    // Colors used for the generation of the maze and visualization
    private final Color VISITED = Color.LIGHTBLUE;

    /**
     * Constructor class
     * @param canvas The canvas on which you want the visualization shown
     * @param tm The text manager that will handle the explanation during visulization
     */
    public SolverDFS(Canvas canvas, TextManager tm)
    {
        // Save the provided parameters
        this.canvas = canvas;
        this.tm = tm;

        tm.addText(0, 0, "Open List: []");
        tm.addText(1, 0, "Closed List: []");

        // set maze into a random maze generated using the Randomized Prim's Algorithm
        maze = GeneratorPrim.getRandomMaze(size[0], size[1], canvas);

        // Start the AI's position in the top left
        positionAI = new int[] {0, 0};

        // Initialize the list to store visited cells and moves
        visitedCells = new ArrayList<>();
        lastMoves = new Stack<>();

        // Add 0, 0 to visited cells and color it accordingly
        visitedCells.add(new int[] {0, 0});
        maze.colorCell(0, 0, VISITED);

        // Pass the AI's position matrix into the maze for it to be drawn
        maze.setPositionAI(positionAI);



    }

    @Override
    public void newMazeButton()
    {
        maze = GeneratorPrim.getRandomMaze(size[0], size[1], canvas);
        // Start the AI's position in the top left
        positionAI = new int[] {0, 0};
        // Initialize the list to store visited cells and moves
        lastMoves = new Stack<>();

        maze.colorCell(0, 0, VISITED);

        openStack = new Stack<>();
        closedStack = new Stack<>();

        startASCII = 65;

        // Pass the AI's position matrix into the maze for it to be drawn
        maze.setPositionAI(positionAI);
    }

    @Override
    public boolean isComplete()
    {
        return (Arrays.equals(positionAI, end));
    }

    @Override
    public boolean nextStep()
    {

        // If we're at the finish, don't do anything!
        if (isComplete()) { return true; };

        // Get the possible open directions and remove any moves we JUST made if possible
        ArrayList<Direction> open = maze.getOpenDirections(positionAI);
        if (!lastMoves.isEmpty()) { open.remove(opposite(lastMoves.peek())); }


        if (open.size() == 0 || backtrackingToChar != 0)
        {
            if (backtrackingToChar == 0)
                backtrackingToChar = openStack.peek();

            moveToChar(openStack.peek());
        }
        // If there is only one option, let's do that!
        else if (open.size() == 1)
        {
            // Move and down the only route and then return
            moveAI(open.get(0), false);
            return isComplete();
        }
        // Otherwise, if it's more then 1 option and the decision isn't labeled
        else if (!isLabeled())
        {
            // Label the surroundings and return
            labelSurrounding();
            return isComplete();
        }
        // Otherwise, if it's more then 1 option but it's already labeled
        else if (open.size() > 1 && isLabeled())
        {
            moveAI(open.get(open.size()-1), false);
            return isComplete();
        }


        return false;

    }

    private boolean isLabeled()
    {
        for (int[] label : labeledPositions)
        {
            if (Arrays.equals(label, positionAI))
                return true;
        }
        return false;
    }

    private void labelSurrounding()
    {
        ArrayList<Direction> open = maze.getOpenDirections(positionAI);
        if (!lastMoves.isEmpty()) { open.remove(opposite(lastMoves.peek())); }

        labeledPositions.add(positionAI.clone());

        // For each possible direction
        for (Direction d : open)
        {
            // Get the new position if we move that way
            int[] pos = getNewPosition(positionAI, d);

            // Get the next character we have available to mark with
            char c = getNextChar();

            // Mark it with the next character we're using to represent decisions
            maze.labelChar(pos[0], pos[1], c);

            // Add the label to the stack so we can see
            openStack.push(c);
        }
        tm.updateText(0, getList("Open", openStack));
    }

    private String getList(String start, Stack<Character> characters)
    {
        StringBuilder returnString = new StringBuilder();
        returnString.append(start).append(" = [");
        for (char c : characters)
        {
            returnString.append(c).append(", ");
        }
        returnString.append("]");
        return returnString.toString();
    }

    private char getNextChar()
    {
        char c = (char) startASCII;
        startASCII++;
        return c;
    }

    private int[] getNewPosition(int[] pos, Direction dir)
    {

        int[] newPos = positionAI.clone();
        // Switch statement based on direction
        switch(dir)
        {
            case UP:    newPos[1]--; break;
            case DOWN:  newPos[1]++; break;
            case LEFT:  newPos[0]--; break;
            case RIGHT: newPos[0]++; break;
        }
        return newPos;
    }

    private ArrayList<int[]> getOpenPosition()
    {
        ArrayList<Direction> openDirections = maze.getOpenDirections(positionAI);
        ArrayList<int[]> openPositions = new ArrayList<>();
        for (Direction d : openDirections)
        {
            openPositions.add(getNewPosition(positionAI, d));
        }
        return openPositions;
    }


    /**
     * Returns the opposite of the direction given
     * @param dir Input direction
     * @return Opposite of the input direction
     */
    private Direction opposite(Direction dir)
    {
        // Switch based on provided direction
        switch(dir)
        {
            // Return opposite
            case RIGHT: return Direction.LEFT;
            case LEFT:  return Direction.RIGHT;
            case DOWN:  return Direction.UP;
            case UP:    return Direction.DOWN;
        }

        // If no return was given, throw exception
        throw new IllegalArgumentException("Direction unknown! Only use SolverDFS.opposite() with left/right/up/down");
    }

    /**
     * Takes a step towards the target character
     * @param c The target character you want to move towards
     */
    private void moveToChar(char c)
    {

        // First get the location of the target
        int[] target = maze.getLocationChar(c);

        // Check to see if any of the open positions contain target
        boolean targetNeighbor = false;
        for (int[] pos : getOpenPosition())
        {
            // If the potential neighboring position equals target
            if (Arrays.equals(pos, target))
                // set targetNeighbor to true
                targetNeighbor = true;
        }

        // If one of the open positions around us contains the target
        if (targetNeighbor)
        {
            // Check what directions are open
            ArrayList<Direction> openDirections = maze.getOpenDirections(positionAI);

            // For each of those directions
            for (Direction d : openDirections)
            {
                // Check if the new position moving in that direction is the target
                if (Arrays.equals(getNewPosition(positionAI, d), target))
                {
                    // If so, move there
                    moveAI(d, false);
                    return;

                }

            }
        }

        // Otherwise, we have to back track to get to char
        moveAI(opposite(lastMoves.pop()), true);


    }

    private void moveAI(Direction dir, boolean backtracking)
    {
        // TODO: Maybe some error handling here?

        // Switch statement based on direction
        switch(dir)
        {
            case UP:    positionAI[1]--; break;
            case DOWN:  positionAI[1]++; break;
            case LEFT:  positionAI[0]--; break;
            case RIGHT: positionAI[0]++; break;
        }

        // if the AI is currently on a char label, consume it (Erase and return it)
        char c = maze.consumeLabelChar(positionAI[0], positionAI[1]);

        // if a label was consumed move an item off the openstack to the closed stack
        if (c != 0) { closedStack.push(openStack.pop()); }

        // Update the lists in textmanager
        tm.updateText(0, getList("Open", openStack));
        tm.updateText(1, getList("Closed", closedStack));

        // Add the move to the list of move's we've made if we're not backtracking
        if (!backtracking) { lastMoves.push(dir); }
        if (c == backtrackingToChar) { backtrackingToChar = 0; }

        // Color the cell as whatever color visited is
        maze.colorCell(positionAI, VISITED);

        // redraw the maze
        maze.setRedrawFlag(true);
    }

}


