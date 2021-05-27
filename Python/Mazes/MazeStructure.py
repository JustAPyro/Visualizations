# Importing tkinter for GUI building
from tkinter import *

# Creating our own window class that inherits Frame
class MazeFrame(Frame):
    title = "Maze"
    def __init__(self, newTitle, window, dimensions, size):
        # Start by calling the parent Frame initialization
        super().__init__()

        # Set a default border
        self.border = 25

        # Set the title to the provided title
        self.title = newTitle

        # Calculate size of window
        win_dimensions = str(dimensions[0] * size + self.border * 2)
        win_dimensions += "x" + str(dimensions[1] * size + self.border * 2)

        # Set the size of the window
        window.geometry(win_dimensions)

        # Save the dimensions of the cells
        self.dimensions = dimensions

        # Save the size of each square
        self.size = size

        # Draw everything on the frame
        self.draw()

    # This function draws everything on the Frame
    def draw(self):
        # Name the window
        self.master.title(self.title)

        # Pack the parent frame into the window
        self.pack(fill=BOTH, expand=1)

        # Create a canvas to draw our maze on
        canvas = Canvas(self)


        # here is where we draw stuff

        # For each horizontal line (add 1 because we need 3 lines to draw 2 boxes)
        for x in range(self.dimensions[0] + 1):
            canvas.create_line(x * self.size + self.border, self.border, x * self.size + self.border, self.border + self.size * self.dimensions[1])

            # For each vertical line
            for y in range(self.dimensions[1] + 1):
                canvas.create_line(self.border, y * self.size + self.border, self.border + self.size * self.dimensions[0], y * self.size + self.border)



        canvas.pack(fill=BOTH, expand=1)

    def move(self):
        self.x += 1;
        self.y += 1;




