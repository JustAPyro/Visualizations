
// Import scanner for getting user data in headless mode
import java.util.Scanner;

public class GUI
{

    public static void headlessGame() {

        // Create a scanner object since we're running a game headless
        Scanner in = new Scanner(System.in);

        // Welcome to the players
        System.out.println("Welcome to Connect4!");

        // Get player one's name
        System.out.print("Player one, please enter your name: ");
        String p1 = in.nextLine();

        // Get player two's name
        System.out.print("Player two, please enter your name: ");
        String p2 = in.nextLine();

        // Create a game object with their names
        ConnectFour game = new ConnectFour(p1, p2);

        System.out.println("Great! Let's play!");

        while(true) {
            // Print the board
            System.out.print(game);

            // Get the desired placement for the play
            System.out.print("Enter where you would like to place your piece: ");
            int placement = in.nextInt();

            // Make the play and then add a little spacing
            game.insert(placement);
            System.out.println();


        }


    }

}
