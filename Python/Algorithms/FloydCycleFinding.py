# Implementation of 'Floyd's Cycle Finding' Algorithm, for use finding loops in Linked Lists
# Designed to visually represent the process and make it easier to understand.
# You can find the full pre-programming plan here:
# https://docs.google.com/document/d/1b3BPSL_bV0ifP1_X0TBPuH67O34HTNc--6_wvoQBMV4/edit?usp=sharing
#
# Author: Luke Hanna (Github.com/JustAPyro | PyreDevelopment.com)
# Version: 1.0 | Started 7/28/21 | Updated 7/28/21

from tkinter import *  # Import Tkinter utilities for GUI
import tkinter.font as tkFont

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
canvas = Canvas(root, bg="white", width=960, height=625).place(x=20, y=75)

# Add footer text
fontStyle = tkFont.Font(family=fnt, size=14)
Label(root, font=fontStyle, justify=LEFT, text="Implementation By Luke Hanna\nGithub.com/JustAPyro\nPyreDevelopment.com").place(x=18, y=715)

# Add the footer buttons
Button(root, text="Generate\nNew List", font=button_font, width=15).place(x=795, y=715)

Label(root, font=fontStyle, text="Loop?").place(x=600, y=700)






root.mainloop()

