from tkinter import *
from MazeStructure import MazeFrame;

# Creating a Tkinter window to hold the GUI
window = Tk()

# Static variables to start with
# TODO: Add a small popup to get these values  from user
dimensions = (15, 25)
size = 30

# Creating an empty frame, passing in the window, dimensions of maze, and size of cell
maze = MazeFrame("Prim's Maze Generation", window, dimensions, size)

window.mainloop()
