from tkinter import *
from MazeStructure import MazeFrame
import random

# Creating a Tkinter window to hold the GUI
window = Tk()

# Static variables to start with
# TODO: Add a small popup to get these values  from user
dimensions = (15, 25)
size = 30

# Creating an empty frame, passing in the window, dimensions of maze, and size of cell
maze = MazeFrame("Prim's Maze Generation", window, dimensions, size)


cx, cy = random.randint(0, dimensions[0]-1), random.randint(0, dimensions[1]-1)
maze.select_cell(cx, cy)
maze.add_to_maze(cx, cy)

maze.select_wall(1, 1, 12)


window.mainloop()
