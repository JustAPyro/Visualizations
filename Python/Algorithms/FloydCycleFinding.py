# Implementation of 'Floyd's Cycle Finding' Algorithm, for use finding loops in Linked Lists
# Designed to visually represent the process and make it easier to understand.
# You can find the full pre-programming plan here:
# https://docs.google.com/document/d/1b3BPSL_bV0ifP1_X0TBPuH67O34HTNc--6_wvoQBMV4/edit?usp=sharing
#
# Author: Luke Hanna (Github.com/JustAPyro | PyreDevelopment.com)
# Version: 1.0 | Started 7/28/21 | Updated 7/28/21


import PIL  # Import PIL for image processing
import math  # Import math for some sin/cos utilities
import tkinter.font as font  # Import fonts to adjust some GUI elements
from PIL import Image, ImageTk  # Import ImageTk for use drawing the hare/tortoise
from tkinter import *  # Import Tkinter utilities for GUI

# Declare some constants indices that will be used in the data structure
X = 0
Y = 1
HARE = 2
TORTOISE = 3


# Node for my linked list!
class Node:
    # Constructor
    def __init__(self, data=None, next=None):
        self.data = data  # The data
        self.next = next  # The next node


# My actual linked list class
class LinkedList:

    # Constructor
    def __init__(self):
        self.head = None  # The head node
        self.len = 0  # The length (We're saving to avoid infinite loops)

    # Intentionally create a loop by linked last element to the element as position tieback
    def loop(self, tieback):
        # Oh no! A loop has accidentally been formed
        tieback_node = None  # This is the node where the list is going to loop back in
        count = 0  # This is the count to help us find that node

        current = self.head  # Start with the head
        while current.next:  # And iterate through elements (Note: This will break if loop() is called twice)

            if count is tieback:  # If this is the index we will be tying the end of the list back to
                tieback_node = current  # Save the node here
            current = current.next  # Continue iteration through the list
            count = count + 1  # Increment the count

        # Once we've reached the end set the next node to the tieback node
        current.next = tieback_node

    # Just returns the head of the list
    def get(self):  # I have this here for my own sense of readability and OCD
        return self.head  # Kind of like a lot of these comments

    # Method to insert at the end of the list
    def insert(self, data):

        self.len = self.len + 1  # Increment the length so we have a running count of the list
        newNode = Node(data)  # Create the new node we will be adding

        if self.head:  # If we already have a head
            current = self.head  # Start there
            while current.next:  # and iterate through all the nodes
                current = current.next  # until we reach the end
            current.next = newNode  # then set the last node equal to new node
        else:  # Otherwise, if there's no head
            self.head = newNode  # Just set this equal to the head

    # This returns the number of elements adding, allowing us to for(range()) loop through and avoid infinite loops
    def __len__(self):
        return self.len


# Class to handle all the logic of our algorithm
class LoopIdentifier:

    def __init__(self):
        self.turn = -1  # Declare the turn as -1 since we just initialized and haven't started yet

        self.message = ""  # This is where we will put the message we will draw

        self.circle_size = 100  # This is the size of the circle nodes on GUI
        self.half = self.circle_size / 2  # Just calculating this at the beginning to save space later
        self.canvas = None  # We also track the canvas we will be drawing on

        # Create a linked list and add some data (Data is formatted, x, y, isHare, isTortoise
        self.linked_list = LinkedList()
        self.linked_list.insert([100, 100, True, True, 0])
        self.linked_list.insert([250, 250, False, False, 0])
        self.linked_list.insert([500, 500, False, False, 0])
        self.linked_list.insert([800, 340, False, False, 0])
        self.linked_list.insert([450, 90, False, False, 0])

        self.linked_list.loop(1)  # Force a loop in the list, linking the last element into the element at index 1

        # Load the hare image and resize it
        self.hare_img = PIL.Image.open("..\\Resources\\Images\\hare.png")
        self.hare_img = self.hare_img.resize((64, 50))
        self.hare = ImageTk.PhotoImage(self.hare_img)

        # Load the tortoise image and resize it
        self.tortoise_img = PIL.Image.open("..\\Resources\\Images\\tortoise.png")
        self.tortoise_img = self.tortoise_img.resize((64, 50))
        self.tortoise = ImageTk.PhotoImage(self.tortoise_img)

    # Draw functions handles redrawing the canvas at any point
    def draw(self, target_canvas):
        # Set the canvas to the provided one
        self.canvas = target_canvas

        node = self.linked_list.get()  # Starting with the head of the list
        for i in range(len(self.linked_list)):  # Iterate through the len of list (avoiding infinite loops)

            p = node.data  # p represents a Point on the node- getting it here to save space later
            self._circle(p[X], p[Y])  # Also drawing the circle to represent the node on GUI

            # Note: I could do this without deleting the image each time, but I don't want to bother, TBH
            if p[HARE]:  # If the hare is on this node, delete the last hare and draw a new one
                self.canvas.delete('hare')
                self.canvas.create_image(p[X] - 3, p[Y] - 22, image=self.hare, tags='hare')
            if p[TORTOISE]:  # If the tortoise is on this node, delete the last tortoise and draw a new one
                self.canvas.delete('tortoise')
                self.canvas.create_image(p[X] + 5, p[Y] + 10, image=self.tortoise, tags='tortoise')

            # Move on to the next node
            node = node.next

        # Draw text if relevent
        if len(self.message) > 0:
            message_fnt = font.Font(family="Lucida Grande", size=20)
            self.canvas.create_text(240, self.canvas.winfo_height()-70, text=self.message, font=message_fnt)

        # TODO: For now, the arrows are hard coded in, but that should be relatively easy to fix
        self._arrow(100, 100, 250, 250)
        self._arrow(250, 250, 500, 500)
        self._arrow(500, 500, 800, 340)
        self._arrow(800, 340, 450, 90)
        self._arrow(450, 90, 250, 250)

    # This is the function that's called when you click the step button and handles logic for one step of the alg
    def step_function(self):

        # Each pointer only be moved once/turn so lets make a flag to tell if we've moved it
        moved_hare = False
        moved_tortoise = False

        self.turn = self.turn + 1  # Increment the turn just so we can keep track

        # Then for each point NOTE: This avoids infinite loop by using the len() feature I designed
        node = self.linked_list.get()
        for i in range(len(self.linked_list)):

            # If the tortoise and the hare are both at this point, it's not first turn, and we haven't moved either
            if node.data[TORTOISE] and node.data[HARE] and not self.turn == 0 \
                    and not moved_tortoise and not moved_hare:

                # We know we found a loop! Let the user know
                self.message = "We found a loop!\nWe know this because the hare \nand the tortoise are sharing a node."

                # Highlight this node and redraw
                self.draw(self.canvas)

                # Then return and take no further steps
                return

            # If the hare is at this point
            if node.data[HARE] and not moved_hare:
                # Move it forward by TWO
                node.next.next.data[HARE] = True
                node.data[HARE] = False

                # Redraw the hare and set the flag
                moved_hare = True
                self.draw(self.canvas)

            # If the tortoise is at this point
            if node.data[TORTOISE] and not moved_tortoise:
                # Move it forward by one
                node.next.data[TORTOISE] = True
                node.data[TORTOISE] = False

                # Redraw the flag and set moved_tortoise flag
                moved_tortoise = True
                self.draw(self.canvas)

            # Move to the next element in the list
            node = node.next

    # This is a simple private method to draw a circle, using the center point and defaulting to self.size
    def _circle(self, x, y):
        self.canvas.create_oval(x - self.half, y - self.half, x + self.half, y + self.half)

    # This method just draws the arrows between the circles, it's an annoying bit of math to be honest
    def _arrow(self, ax, ay, head_x, head_y):
        # Note: Thanks to my friend Daniel for his help on this section- Drawing an arrow is harder then it looks

        # Calculate the distance between two points
        distance = math.sqrt((head_x - ax) ** 2 + (head_y - ay) ** 2)

        # recalculate aX/aY based on outer circle - This is the tail of arrow
        tail_x = ((head_x - ax) / (distance + .001)) * self.half + ax
        tail_y = ((head_y - ay) / (distance + .001)) * self.half + ay

        # Recalculate bX/bY - This is the head of the arrow
        head_x = ((ax - head_x) / (distance + .001)) * self.half + head_x
        head_y = ((ay - head_y) / (distance + .001)) * self.half + head_y

        # Calculate where the arrow edge point will be rotated from
        rpx = ((ax - head_x) / (distance + .001)) * self.half + head_x
        rpy = ((ay - head_y) / (distance + .001)) * self.half + head_y

        # Rotate that point into the upper part of the arrow and draw
        ux = math.cos(math.pi / 5) * (rpx - head_x) - math.sin(math.pi / 5) * (rpy - head_y) + head_x
        uy = math.sin(math.pi / 5) * (rpx - head_x) + math.cos(math.pi / 5) * (rpy - head_y) + head_y
        self.canvas.create_line(head_x, head_y, ux, uy)

        # rotate that into the lower part of the arrow and draw
        ux = math.cos(-1 * math.pi / 5) * (rpx - head_x) - math.sin(-1 * math.pi / 5) * (rpy - head_y) + head_x
        uy = math.sin(-1 * math.pi / 5) * (rpx - head_x) + math.cos(-1 * math.pi / 5) * (rpy - head_y) + head_y
        self.canvas.create_line(head_x, head_y, ux, uy)

        # Draw the main body of the arrow
        self.canvas.create_line(tail_x, tail_y, head_x, head_y)


# ======================== GUI SET UP ========================

# Creating a Tkinter window to hold the GUI
root = Tk()

# Creating the object to handle the actual logic
algorithm = LoopIdentifier()

# Some quick overall variables
fnt = "Lucida Grande"
button_font = font.Font(family="Lucida Grande", size=16)

# Set the window to an appropriate geometry and lock it
root.geometry("1000x800")
root.resizable(width=False, height=False)

# Add the title to the window
fontStyle = font.Font(family=fnt, size=16)
Label(root, font=fontStyle, text="Using: Floyd’s Cycle-Finding").place(x=18, y=40)
fontStyle = font.Font(family=fnt, size=20)
Label(root, font=fontStyle, text="Finding a Loop in a Linked List").place(x=18, y=5)

# Add the buttons to the GUI
bStep = Button(root, text="Step", command=algorithm.step_function, font=button_font, width=13).place(x=475, y=15)
Button(root, text="Finish", font=button_font, width=13).place(x=645, y=15)
Button(root, text="Skip To End", font=button_font, width=13).place(x=815, y=15)

# Add the canvas
canvas = Canvas(root, bg="white", width=960, height=625)
canvas.place(x=20, y=75)

# Draw the default list onto the canvas
algorithm.draw(canvas)

# Add footer text
fontStyle = font.Font(family=fnt, size=14)
Label(root, font=fontStyle, justify=LEFT,
      text="Implementation By Luke Hanna\nGithub.com/JustAPyro\nPyreDevelopment.com").place(x=18, y=715)

# Add the footer button and label
Button(root, text="Generate\nNew List", font=button_font, width=15).place(x=795, y=715)
Label(root, font=fontStyle, text="Items:").place(x=700, y=715)
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
