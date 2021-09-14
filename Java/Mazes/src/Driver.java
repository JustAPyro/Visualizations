import javafx.application.Application; // Need this to call application.launch

// Launches the Maze
public class Driver
{

    // Main method (Duh)
    public static void main(String[] args)
    {
        // Launch the GUI, passing in the the args and GUI class, as well as the maze generator of choice
        Application.launch(GUI.class, args);

    }

}
