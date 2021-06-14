# Importing tkinter for GUI building
from tkinter import *

import time

import threading

# Creating our own window class that inherits Frame
class MazeFrame(Frame):
    title = "Maze"



    h_walls = []
    v_walls = []
    cells = []

    def __init__(self, newTitle, window, dimensions, size, step_func):
        # Start by calling the parent Frame initialization
        super().__init__()

        self.seed = (0, 0)
        self.selected_wall = (0, 0, False)
        self.saved_walls = []
        self.saved_cell = ()

        self.color_normal_fill = "#A9D"
        self.color_select_fill = "#3C4"
        self.color_maze_fill = "#F93"

        self.color_normal_stroke = "#101010"
        self.color_select_stroke = "#FF0"
        self.color_saved_stroke = "#F00"

        self.step = 0;
        self.step_func = step_func;

        # for dimensions = (2, 2)
        # Configure the correct number of horizontal and vertical walls
        self.vertical = [[1 for x in range(dimensions[1])] for y in range(dimensions[0]+1)]  # Horizontal
        self.horizontal = [[1 for x in range(dimensions[1]+1)] for y in range(dimensions[0])]  # vertical

        # Create a cell array
        self.cells = [[1 for x in range(dimensions[1])] for y in range(dimensions[0])]

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
        self.canvas = Canvas(self)
        self.draw_update()

        self.canvas.pack(fill=BOTH, expand=1)

        button = Button(self.canvas, text='Next Step', command=self.next)
        button.pack(side=RIGHT)

        finish_button = Button(self.canvas, text="Finish", command=self.call_finish)
        finish_button.pack(side=RIGHT)

        final_button = Button(self.canvas, text="Skip to end", command=self.skip)
        final_button.pack(side=RIGHT)

    def skip(self):
        while True:
            self.step_func(self.step)
            self.step = self.step + 1

            if not self.get_saved_walls() and self.step != 1:
                break

        self.draw_update()

    def finish(self):
        for i in range(5):
            print("hi!")
            self.step_func(self.step)
            self.step += 1
            self.draw_update()
            time.sleep(1)

    def get_saved_walls(self):
        return self.saved_walls

    def set_seed(self, seed):
        self.seed = seed

    def get_seed(self):
        return self.seed

    def get_selected_wall(self):
        return self.selected_wall

    def call_finish(self):
        threading.Thread(target=self.finish).start()

    def next(self):
        self.step_func(self.step)
        self.step+= 1
        self.draw_update()

    def draw_update(self):
        # here is where we draw stuff

        # Fill the cells
        for x in range(len(self.cells)):
            for y in range(len(self.cells[x])):
                if self.cells[x][y] == 0:
                    col = self.color_maze_fill
                elif self.cells[x][y] == 2:
                    col = self.color_select_fill
                elif self.cells[x][y] == 1:
                    col = self.color_normal_fill
                self.canvas.create_rectangle(self.border + x * self.size, self.border + y * self.size,
                                        self.border + (x + 1) * self.size, self.border + (y + 1) * self.size, fill=col, outline='')


        # Draw the vertical cell walls
        for x in range(len(self.vertical)):
            for y in range(len(self.vertical[x])):
                if self.vertical[x][y] == 0:
                    col = self.color_maze_fill
                elif self.vertical[x][y] == 1:
                    col = self.color_normal_stroke
                elif self.vertical[x][y] == 2:
                    col = self.color_select_stroke
                elif self.vertical[x][y] == 3:
                    col = self.color_saved_stroke
                if self.vertical[x][y]:
                    self.canvas.create_line(self.border + x * self.size, self.border + y * self.size,
                                       self.border + x * self.size, self.border + (y + 1) * self.size, fill=col)

        # Draw the horizontal cell walls
        for x in range(len(self.horizontal)):
            for y in range(len(self.horizontal[x])):
                if self.horizontal[x][y] == 0:
                    col = self.color_maze_fill
                elif self.horizontal[x][y] == 1:
                    col = self.color_normal_stroke
                elif self.horizontal[x][y] == 2:
                    col = self.color_select_stroke
                elif self.horizontal[x][y] == 3:
                    col = self.color_saved_stroke
                if self.horizontal[x][y]:
                    self.canvas.create_line(self.border + x * self.size, self.border + y * self.size,
                                       self.border + (x + 1) * self.size, self.border + y * self.size, fill=col)


    def select_cell(self, x, y):
        self.cells[x][y] = 2
        self.draw_update()

    def add_to_maze(self, cell):
        self.cells[cell[0]][cell[1]] = 0
        self.draw_update()

    def get_cell_walls(self, cell):
        # True = vertical | False = horizontal
        # Return the unique cell walls
        return [(cell[0], cell[1], True), (cell[0]+1, cell[1], True), (cell[0], cell[1], False), (cell[0], cell[1]+1, False)]

    def get_wall_cells(self, wall):
        # True = vertical | False = horizontal
        # return the unique cell values
        if (wall[2]):
            return [(wall[0], wall[1]), (wall[0]-1, wall[1])]
        else:
            return [(wall[0], wall[1]), (wall[0], wall[1]-1)]

    def get_wall_status(self, wall):
        if wall[2]:
            return self.vertical[wall[0]][wall[1]]
        else:
            return self.horizontal[wall[0]][wall[1]]

    def get_cell_status(self, cell):
        return self.cells[cell[0]][cell[1]]

    def get_cell(self):
        return self.saved_cell

    def skip_step(self):
        self.step = self.step + 1

    def save_cell(self, cell):
        self.saved_cell = cell

    def save_wall(self, wall):
        # True = vertical | False = horizontal
        self.saved_walls.append(wall)
        if wall[2]:
            self.vertical[wall[0]][wall[1]] = 3
        else:
            self.horizontal[wall[0]][wall[1]] = 3

    def unsave_wall(self, wall):
        # True = vertical | False = horizontal
        self.saved_walls.remove(wall)
        if wall[2]:
            self.vertical[wall[0]][wall[1]] = 1
        else:
            self.horizontal[wall[0]][wall[1]] = 1
        self.draw_update()

    # Selects the wall
    def select_wall(self, wall):
        # True = vertical | False = horizontal
        if (wall[2]):
            self.vertical[wall[0]][wall[1]] = 2
        else:
            self.horizontal[wall[0]][wall[1]] = 2
        self.selected_wall = wall
        self.draw_update()

    def pick_wall(self, wall):
        # True = vertical | False = horizontal
        if (wall[2]):
            self.vertical[wall[0]][wall[1]] = 3
        else:
            self.horizontal[wall[0]][wall[1]] = 3

        self.draw_update()

    def break_wall(self, wall):
        # True = vertical | False = horizontal
        if (wall[2]):
            self.vertical[wall[0]][wall[1]] = 0
        else:
            self.horizontal[wall[0]][wall[1]] = 0

        self.draw_update()