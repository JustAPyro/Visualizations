# Importing tkinter for GUI building
from tkinter import *

# Creating our own window class that inherits Frame
class Win(Frame):
    def __init__(self):
        # Start by calling the parent Frame initialization
        super().__init__()

        # Draw everything on the frame
        self.draw()

    # This function draws everything on the Frame
    def draw(self):
        # Name the window
        self.master.title("Maze")

        # Pack the parent frame into the window
        self.pack(fill=BOTH, expand=1)

        # Create a canvas to draw our maze on
        canvas = Canvas(self)

        # here is where we draw stuff
        for i in range(0, 30, 1):


        canvas.pack(fill=BOTH, expand=1)

def main():
    window = Tk()
    win = Win()
    window.mainloop()

main()