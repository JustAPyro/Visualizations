public class ConnectFour
{

    // Width and height of the board (Standard is 6x7)
    int boardx = 7; int boardy = 6;

    // Declaring the variable for the game board
    int[][] board;

    // Constructor Class
    public ConnectFour() {

        // Initializing the board
        board = new int[boardx][boardy];

    }

    /**
     *
     *  For use printing the gamestate.
     *
     * @return Gamestate representation as a multi-line string
     */
    public String toString() {

        // This string will represent the gamestate
        String boardString = "";

        // Saving the value for the newline element from OS
        String newLine = System.getProperty("line.separator");

        // For each row
        for (int y = 0; y < boardy; y++) {

            // Create an element to store the line
            String line = "";

            // For each column item of each row
            for (int x = 0; x < boardx; x++) {

                // Add the correct value and a space to the line
                line += board[x][y] + " ";

            }

            // add the line to the final string
            boardString += line;

            // and then add the newline element
            boardString += newLine;

        }

        // Return the completed multiline string representing gamestate
        return boardString;
    }

}
