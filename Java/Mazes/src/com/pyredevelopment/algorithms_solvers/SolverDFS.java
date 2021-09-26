package com.pyredevelopment.algorithms_solvers;

import com.pyredevelopment.algorithms_generators.GeneratorPrim;
import com.pyredevelopment.graphical.TextManager;
import com.pyredevelopment.maze.MazeAlgorithm;
import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class represents the algorithmic and procedural method of solving a maze using a Depth First Search
 *
 * @author Luke Hanna (Github.com/JustAPyro / PyreDevelopment.com)
 * @version 1.0 - Built 9/24/2021 - Updated 9/24/21
 */
public class SolverDFS extends MazeAlgorithm
{

    // Position of our AI
    int[] positionAI;

    // List of cell's we've visited / moves we've made
    ArrayList<int[]> visitedCells;
    Stack<Direction> lastMoves;

    // Decision Stack!
    private Stack<Character> openStack = new Stack();
    private Stack<Character> closedStack = new Stack();

    // Starting our decision stack with ASCII(65) or A
    private int startASCII = 65;

    boolean labeledDecision = false;

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
        maze = GeneratorPrim.getRandomMaze(8, 8, canvas);

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
        maze = GeneratorPrim.getRandomMaze(8, 8, canvas);
        currentStep = 0;
    }

    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public boolean nextStep()
    {
        // Get the possible open directions
        ArrayList<Direction> open = maze.getOpenDirections(positionAI);

        // If we're at the finish, don't do anything!
        if (isComplete()) { return true; };

        // If we have a last move, we're probably not going to go there
        if (!lastMoves.isEmpty())
        {
            //remove it's opposite from open (Let's not go back)
            Direction lastMove = lastMoves.get(lastMoves.size() - 1);
            open.remove(opposite(lastMove));
        }

        // If there's only one direction
        if (open.size() == 1)
        {
            // Move in that direction and end step
            moveAI(open.get(0), false);
            return false;
        }

        // Otherwise, we have decision (Oh boy!) - Start by labeling each decision

        if (labeledDecision)
        {
            // Move the last move (Which should be the same as top of the stack)
            moveAI(open.get(open.size()-1), false);

            // Set labeledDecision to false to indicate that the next decision is no longer labeled
            labeledDecision = false;

            // Return false to end turn
            return false;
        }

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

        // set labeled decision to true so we know that this decision has been labeled
        labeledDecision = true;



        return false;

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

        // If one of the open positions around us contains the target
        if (getOpenPosition().contains(target))
        {
            // Check what directions are open
            ArrayList<Direction> openDirections = maze.getOpenDirections(positionAI);

            // For each of those directions
            for (Direction d : openDirections)
            {
                // Check if the new position moving in that direction is the target
                if (getNewPosition(positionAI, d) == target)
                    // If so, move there
                    moveAI(d, false);
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

        // Color the cell as whatever color visited is
        maze.colorCell(positionAI, VISITED);

        // redraw the maze
        maze.setRedrawFlag(true);
    }

}


