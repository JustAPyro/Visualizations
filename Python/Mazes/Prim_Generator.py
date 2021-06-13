# Implementation of Randomized Prim's Algorithm in maze generation, using MazeStructure.
# Designed and Implemented by Luke Hanna (github.com/JustAPyro)
# Project started on May 26, 2021
# Objective: Creating a easily understandable visualization of Prim's Maze generation
#
# Details: Implementation is based on wikipedia's pseudo code (https://bit.ly/3pSH9Z6) as follows:
#
# 1. Start with a grid full of walls
# 2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
# 3. While there are walls in the list:
#     1. Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then:
#         1. Make the wall a passage and mark the unvisted cell as part of the maze.
#         2. Add the neighboring walls of the cell to the wall list.
#     2. Remove the wall from the list


from tkinter import *  # Import Tkinter utilities for GUI
from MazeStructure import MazeFrame  # Import the MazeFrame we'll be using to manipulate
import random  # Import random for use randomly picking walls

# Creating a Tkinter window to hold the GUI
window = Tk()

# Static variables to start with
# TODO: Add a small popup to get these values  from user
dimensions = (5, 5)  # Dimensions of the maze
size = 30  # Size of each grid/maze square


# - - - - - - - - - - - - - - - STEP ONE - - - - - - - - - - - - - - -
# 1. Start with a grid full of walls.
# (This is automatically done by MazeFrame)

def prim_step(step):
    # - - - - - - - - - - - - - - - STEP TWO - - - - - - - - - - - - - - -
    # 1. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.

    # Indicates that it's the first "step" when drawing maze incrementally
    if step == 0:

        # Pick a random cell
        cx, cy = random.randint(0, dimensions[0] - 1), random.randint(0, dimensions[1] - 1)

        # Select that cell
        maze.select_cell(cx, cy)

        # Mark it as part of the maze
        maze.add_to_maze((cx, cy))

        # And save it for use later
        maze.set_seed((cx, cy))

    # Second "step" when drawing maze incrementally
    elif step == 1:

        # Get the walls of the first cell
        walls = maze.get_cell_walls(maze.get_seed())

        # For each of the walls
        for wall in walls:
            # Add the walls of the cell to the wall list
            maze.save_wall(wall)


    # - - - - - - - - - - - - - - - STEP THREE - - - - - - - - - - - - - - -
    # 3. While there are walls in the list

    # If there are no walls in the list
    elif not maze.get_saved_walls():

        # Do nothing
        return


    # - - - - - - - - - - - - - - - STEP THREE ONE - - - - - - - - - - - - - - -
    # 3.1 Pick a random wall from the list. If only one of the two cells that the wall divides is visited then:

    # Next step when drawing incrementally
    elif (step - 2) % 3 == 0:

        # Pick a random number between 0 and number of saved walls
        dw = random.randint(0, len(maze.get_saved_walls()) - 1)

        # Select the random wall from the list
        maze.select_wall(maze.get_saved_walls()[dw])

    # Next step when drawing incrementally
    elif (step - 3) % 3 == 0:

        # Get the random wall
        wall = maze.get_selected_wall()

        # This is an added step that just ensures we don't operate on non-existent cells
        # If the wall is on the border (Checking to see if both cells exist)
        if (wall[2] is True and wall[0] == 0) \
                or (wall[2] is False and wall[1] == 0) \
                or (wall[2] is True and wall[0] == dimensions[0]) \
                or (wall[2] is False and wall[1] == dimensions[1]):
            maze.skip_step()  # Skip this step
            maze.unsave_wall(wall)  # Remove the wall
            return  # If the wall is on the edge of the map and there is no second cell just reutrn

        # Once we know they exist,
        else:

            # Get the cells surrounding the random wall
            destroyCells = maze.get_wall_cells(wall)

            # (If only one of the two cells that the wall divides is visited, then)
            # If only one cell is visited (using XOR ^)
            if (maze.get_cell_status(destroyCells[0]) ^ maze.get_cell_status(destroyCells[1])):

                # - - - - - - - - - - - - - - - STEP THREE ONE ONE - - - - - - - - - - - - - - -
                # 3.1.1 Make the wall a passage and mark the unvisited cell as part of the maze.

                # Depending on which cell is the unvisited cell mark the correct cell as part of the maze, and save it
                if (maze.get_cell_status(destroyCells[0])):
                    maze.add_to_maze(destroyCells[0])
                    maze.save_cell(destroyCells[0])
                else:
                    maze.add_to_maze(destroyCells[1])
                    maze.save_cell(destroyCells[1])

                # Make the wall a passage by removing it from the saved list and breaking the wall
                maze.unsave_wall(wall)
                maze.break_wall(wall)

            # Otherwise if it's not exclusively one of the cells has been visted,
            else:
                maze.unsave_wall(wall)  # Un-save the wall
                maze.skip_step()  # Skip this step (Don't draw it)

    # - - - - - - - - - - - - - - - STEP THREE ONE TWO - - - - - - - - - - - - - - -
    # 3.1.2 Add the neighboring walls of the cell to the wall list

    # Next step when drawing incrementally
    elif (step - 4) % 3 == 0:

        # Get the cell we saved in the previous step
        cell = maze.get_cell()

        # Find the surrounding walls
        surrounding = maze.get_cell_walls(cell)

        # For each surrounding cell wall
        for wall in surrounding:

            # If the wall is active (Not broken/0)
            if maze.get_wall_status(wall) != 0:
                # Add that wall to our list of walls
                maze.save_wall(wall)

    # - - - - - - - - - - - - - - - STEP THREE TWO - - - - - - - - - - - - - - -
    # 3.2 Remove the wall from the list (This actually already occurred when we confirmed it fullfilled requirements.
    # Occurs on line 124


# Creating an empty frame, passing in the window, dimensions of maze, and size of cell
# This serves as step 1, as MazeFrame generates a grid full of walls.
maze = MazeFrame("Prim's Maze Generation", window, dimensions, size, prim_step)

# Show the Tkinter window
window.mainloop()
