# Importing tkinter for GUI building
from tkinter import *

# Creating our own window class that inherits Frame
class MazeFrame(Frame):
    title = "Maze"

    h_walls = []
    v_walls = []
    cells = []

    def __init__(self, newTitle, window, dimensions, size):
        # Start by calling the parent Frame initialization
        super().__init__()

        self.color_normal_fill = "#A9D"
        self.color_select_fill = "#3C4"
        self.color_maze_fill = "#F93"

        self.color_normal_stroke = "#101010"
        self.color_select_stroke = "#FF0"

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


    def pressButton(self):
        self.eraseRects()
        self.myGame.takeAStep()
        self.drawRects()

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
                if self.vertical[x][y]:
                    self.canvas.create_line(self.border + x * self.size, self.border + y * self.size,
                                       self.border + x * self.size, self.border + (y + 1) * self.size, fill=col)

        # Draw the horizontal cell walls
        for x in range(len(self.horizontal)):
            for y in range(len(self.horizontal[x])):
                if self.horizontal[x][y] == 0:
                    col = self.color_maze_fill
                if self.horizontal[x][y] == 1:
                    col = self.color_normal_stroke
                elif self.horizontal[x][y] == 2:
                    col = self.color_select_stroke
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

    def get_cell_status(self, cell):
        return self.cells[cell[0]][cell[1]]

    # Selects the wall
    def select_wall(self, wall):
        # True = vertical | False = horizontal
        if (wall[2]):
            self.vertical[wall[0]][wall[1]] = 2
        else:
            self.horizontal[wall[0]][wall[1]] = 2

        self.draw_update()

    def break_wall(self, wall):
        # True = vertical | False = horizontal
        if (wall[2]):
            self.vertical[wall[0]][wall[1]] = 0
        else:
            self.horizontal[wall[0]][wall[1]] = 0

        self.draw_update()








