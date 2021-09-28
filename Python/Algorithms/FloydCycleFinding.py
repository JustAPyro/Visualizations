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

# Set the window to an appropriate geometry and lock it
root.geometry("1000x800")
root.resizable(width=False, height=False)

fontStyle = tkFont.Font(family="Lucida Grande", size=20)
Label(root, font=fontStyle, text="Using: Floyd’s Cycle-Finding").place(x=10, y=40)
Label(root, font=fontStyle, text="Finding a Loop in a Linked List").place(x=10, y=5)




root.mainloop()

