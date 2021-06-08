public class ConnectFour
{

    // Width and height of the board (Standard is 6x7)
    private final int boardx = 7; private final int boardy = 6;

    // Declaring the variable for the game board
    private int[][] board;

    // Constructor Class
    public ConnectFour() {

        // Initializing the board
        board = new int[boardx][boardy];

    }

    /**
     * Allows you to add pieces to the game board to try to 'Connect Four'
     *
     * @param location The location you want to set the piece (0-start indexed)
     * @param player The player making the move
     * @throws IndexOutOfBoundsException if you try to insert a piece at an invalid point
     */
    public void insert(int location, int player) {

        // First, check if the given location is out of bounds
        if (location < 0 || location > boardx)

            // Throw an IndexOutOfBoundsException
            throw new IndexOutOfBoundsException("Tried to insert piece at invalid point!");

        // Create a variable to track the correct insertion height
        int insertHeight = boardy - 1;

        // Starting from the bottom of the board, as long as there's a piece at insertHeight
        while (board[location][insertHeight] != 0) {

            // Increment insertHeight to move one up
            insertHeight--;

        }

        // Finally, once we have the insert height set that location to belong to the given player
        board[location][insertHeight] = player;

    }

    /**
     *
     *  For use printing the gamestate.
     *
     * @return Gamestate representation as a multi-line string
     */
    @Override
    public String toString() {

        // This string will represent the gamestate
        StringBuilder boardString = new StringBuilder();

        // Saving the value for the newline element from OS
        String newLine = System.getProperty("line.separator");

        // For each row
        for (int y = 0; y < boardy; y++) {

            // Create an element to store the line
            StringBuilder line = new StringBuilder();

            // For each column item of each row
            for (int x = 0; x < boardx; x++) {

                // Add the correct value and a space to the line
                line.append(board[x][y]).append(" ");

            }

            // add the line to the final string
            boardString.append(line);

            // and then add the newline element
            boardString.append(newLine);

        }

        // Return the completed multiline string representing gamestate
        return boardString.toString();
    }

}
