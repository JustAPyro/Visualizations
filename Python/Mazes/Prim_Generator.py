from tkinter import *
from MazeStructure import MazeFrame
import random

# Creating a Tkinter window to hold the GUI
window = Tk()

# Static variables to start with
# TODO: Add a small popup to get these values  from user
dimensions = (5, 10)
size = 30


# Creating an empty frame, passing in the window, dimensions of maze, and size of cell
maze = MazeFrame("Prim's Maze Generation", window, dimensions, size)




cx, cy = random.randint(0, dimensions[0]-1), random.randint(0, dimensions[1]-1)
maze.select_cell(cx, cy)
maze.add_to_maze((cx, cy))

walls = maze.get_cell_walls((cx, cy))

while walls:
    dw = random.randint(0, len(walls) - 1)

    if (walls[dw][2] is True and walls[dw][0] == 0) \
            or (walls[dw][2] is False and walls[dw][1] == 0) \
            or (walls[dw][2] is True and walls[dw][0] == dimensions[0]) \
            or (walls[dw][2] is False and walls[dw][1] == dimensions[1]):
        walls.pop(dw)
        continue

    destroyCells = maze.get_wall_cells(walls[dw])

    if (maze.get_cell_status(destroyCells[0]) ^ maze.get_cell_status(destroyCells[1])):
        maze.break_wall(walls[dw])

        if (maze.get_cell_status(destroyCells[0])):
            maze.add_to_maze(destroyCells[0])
            walls += maze.get_cell_walls(destroyCells[0])
        else:
            maze.add_to_maze(destroyCells[1])
            walls += maze.get_cell_walls(destroyCells[1])

    walls.pop(dw)














window.mainloop()
