from tkinter import *
from MazeStructure import MazeFrame
import random

# Creating a Tkinter window to hold the GUI
window = Tk()

# Static variables to start with
# TODO: Add a small popup to get these values  from user
dimensions = (5, 5)
size = 30


def prim_step(step):
    # STEP ONE OF PRIM'S ALGORITHM
    if step==0:
        cx, cy = random.randint(0, dimensions[0] - 1), random.randint(0, dimensions[1] - 1)
        maze.select_cell(cx, cy)
        maze.add_to_maze((cx, cy))
        maze.set_seed((cx, cy))

    # STEP TWO OF PRIMS ALGORITHM
    elif step==1:
        walls = maze.get_cell_walls(maze.get_seed())
        for wall in walls:
            maze.save_wall(wall)

    #(AS LONG AS THERE ARE WALLS ITERATE THROUGH THE FOLLOWING)
    elif not maze.get_saved_walls():
        return

    #STEP THREE
    elif (step-2) % 3 == 0:
        dw = random.randint(0, len(maze.get_saved_walls()) - 1)
        maze.select_wall(maze.get_saved_walls()[dw])

    elif (step-3) % 3 == 0:
        wall = maze.get_selected_wall()

        # If the wall is on the border (Checking to see if both cells exist)
        if (wall[2] is True and wall[0] == 0) \
                or (wall[2] is False and wall[1] == 0) \
                or (wall[2] is True and wall[0] == dimensions[0]) \
                or (wall[2] is False and wall[1] == dimensions[1]):
            maze.skip_step()
            maze.unsave_wall(wall) # Remove the wall
            return # If the wall is on the edge of the map and there is no second cell just reutrn
        else:
            destroyCells = maze.get_wall_cells(wall)

            # If only one cell is visited (XOR ^)
            if (maze.get_cell_status(destroyCells[0]) ^ maze.get_cell_status(destroyCells[1])):

                # Mark the new cell as part of the maze
                if (maze.get_cell_status(destroyCells[0])):
                    maze.add_to_maze(destroyCells[0])
                    maze.save_cell(destroyCells[0])
                else:
                    maze.add_to_maze(destroyCells[1])
                    maze.save_cell(destroyCells[1])

                maze.unsave_wall(wall)
                maze.break_wall(wall)

            else:
                maze.unsave_wall(wall)
                maze.skip_step()


    # Step 3 -> 1 -> 2
    elif (step-4) % 3 == 0:
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

# Creating an empty frame, passing in the window, dimensions of maze, and size of cell
maze = MazeFrame("Prim's Maze Generation", window, dimensions, size, prim_step)


window.mainloop()