# Implementation of 'Floyd's Cycle Finding' Algorithm, for use finding loops in Linked Lists
# Designed to visually represent the process and make it easier to understand.
# You can find the full pre-programming plan here:
# https://docs.google.com/document/d/1b3BPSL_bV0ifP1_X0TBPuH67O34HTNc--6_wvoQBMV4/edit?usp=sharing
#
# Author: Luke Hanna (Github.com/JustAPyro | PyreDevelopment.com)
# Version: 1.0 | Started 7/28/21 | Updated 7/28/21

from tkinter import *  # Import Tkinter utilities for GUI
import tkinter.font as tkFont
import math

# Class to handle all the logic
class LoopIdentifier:

    def __init__(self):
        self.circle_size = 100
        self.half = self.circle_size/2;
        self.canvas = None
        self.points = [(100, 100), (250, 250), (500, 500), (800, 340), (450, 90)]

    def draw(self, target_canvas):
        self.canvas = target_canvas
        for p in self.points:
            self._circle(p[0], p[1])

        self._arrow(100, 100, 250, 250)
        self._arrow(250, 250, 500, 500)
        self._arrow(500, 500, 800, 340)
        self._arrow(800, 340, 450, 90)
        self._arrow(450, 90, 250, 250)



    def _circle(self, x, y):
        self.canvas.create_oval(x-self.half, y-self.half, x+self.half, y+self.half)

    def _arrow(self, ax, ay, bx, by):

        distance = math.sqrt((bx-ax)**2 + (by-ay)**2)

        # recalculate aX/aY based on outer circle - This is the tail of arrow
        cx = ((bx - ax) / (distance+.001)) * self.half + ax
        cy = ((by - ay) / (distance+.001)) * self.half + ay

        # Recalculate bX/bY - This is the head of the arrow
        bx = ((ax - bx) / (distance+.001)) * self.half + bx
        by = ((ay - by) / (distance+.001)) * self.half + by

        ax = 50*math.sin(30) + bx
        ay = 50*math.cos(30) + by


        self.canvas.create_line(cx, cy, bx, by)
        self.canvas.create_line(bx, by, ax, ay)


# ======================== GUI SET UP ========================

# Create the object to manage the actual logic
algorithm = LoopIdentifier()

# Creating a Tkinter window to hold the GUI
root = Tk()

# Some quick overall variables
fnt = "Lucida Grande"
button_font = tkFont.Font(family="Lucida Grande", size=16)


# Set the window to an appropriate geometry and lock it
root.geometry("1000x800")
root.resizable(width=False, height=False)

# Add the title to the window
fontStyle = tkFont.Font(family=fnt, size=16)
Label(root, font=fontStyle, text="Using: Floyd’s Cycle-Finding").place(x=18, y=40)
fontStyle = tkFont.Font(family=fnt, size=20)
Label(root, font=fontStyle, text="Finding a Loop in a Linked List").place(x=18, y=5)

# Add the buttons to the GUI
bStep = Button(root, text="Step", font=button_font, width=13).place(x=475, y=15)
Button(root, text="Finish", font=button_font, width=13).place(x=645, y=15)
Button(root, text="Skip To End", font=button_font, width=13).place(x=815, y=15)

# Add the canvas
canvas = Canvas(root, bg="white", width=960, height=625)
canvas.place(x=20, y=75)

# Draw the default list onto the canvas
algorithm.draw(canvas)

# Add footer text
fontStyle = tkFont.Font(family=fnt, size=14)
Label(root, font=fontStyle, justify=LEFT, text="Implementation By Luke Hanna\nGithub.com/JustAPyro\nPyreDevelopment.com").place(x=18, y=715)

# Add the footer button and label
Button(root, text="Generate\nNew List", font=button_font, width=15).place(x=795, y=715)
Label(root,  font= fontStyle, text="Items:").place(x=700, y=715)
Label(root, font=fontStyle, text="Loop?").place(x=600, y=715)

# Set up the loop tuple set up
loop_variable = StringVar(root)
LOOP_TUPLE = ("Loop", "No Loop", "Random")
loop_control = OptionMenu(root, loop_variable, *LOOP_TUPLE)
loop_control.config(width=10)
loop_control.place(x=572, y=750)

# Insert entry box
Entry(root).place(x=708, y=750, width=40, height=26)

# Start the GUI loop
root.mainloop()

