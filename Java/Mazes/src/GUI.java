import javafx.animation.AnimationTimer;
import javafx.application.Application; // Required for JFX application
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is in charge of drawing the wireframe and GUI in the associated report
 * (https://docs.google.com/document/d/10b-LSSGvkl0g05j54R10NhtFlJdlgE82eGOzjwQn1sg/edit?usp=sharing)
 * To display and navigate the maze generation
 * This is done using JavaFX and Canvas
 */
public class GUI extends Application
{

    /**
     * This is enteredwhen the application is launched
     * @param primaryStage The main window of the application
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Title
        primaryStage.setTitle("Prim's Maze Generator");

        // Everything arranges in vertical rows
        VBox root = new VBox();

        // HBox holds (Title    Next Step, Finish)
        HBox header = new HBox();

        // Set some spacing and padding in the header
        header.setPadding(new Insets(10, 10, 10, 10));
        header.setSpacing(10);

        // Add the header to root
        root.getChildren().add(header);

        // Create the title and add it to the header HBox
        Label titleLabel = new Label("Prim's Maze Generation");
        header.getChildren().add(titleLabel);

        // Add in the spacer between title and buttons
        Region spacerHead = new Region();
        header.getChildren().add(spacerHead);
        HBox.setHgrow(spacerHead, Priority.ALWAYS);

        // Create and add step button
        Button stepButton = new Button("Next Step");
        header.getChildren().add(stepButton);

        // Create and add finish button
        Button finishButton = new Button("Finish Maze");
        header.getChildren().add(finishButton);

        // Create and add canvas to an HBOX then root
        Canvas mazeCanvas = new Canvas(500, 500);
        HBox canvasHolder = new HBox();
        canvasHolder.getChildren().add(mazeCanvas);
        canvasHolder.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().add(canvasHolder);

        // Save the maze canvas graphics context
        GraphicsContext mgc = mazeCanvas.getGraphicsContext2D();

        // Then create a Prim generator and attach it to mazeCanvas
        PrimGenerator prim = new PrimGenerator(mazeCanvas);

        // Create and add text canvas to root
        Canvas textCanvas = new Canvas(500, 200);
        root.getChildren().add(textCanvas);

        // Create footer containing (myInfo      Save Maze, Load Maze)
        HBox footer = new HBox();

        // Set padding and spacing on footer
        footer.setPadding(new Insets(10, 10, 10, 10));
        footer.setSpacing(10);

        // Add footer to root
        root.getChildren().add(footer);

        // Create and add Info label
        Label infoLabel = new Label("Implementation by \n Luke Hanna  \n Github.com/JustAPyro");
        footer.getChildren().add(infoLabel);

        // Create and add spacer
        Region spacerFoot = new Region();
        footer.getChildren().add(spacerFoot);
        HBox.setHgrow(spacerFoot, Priority.ALWAYS);

        // Create and add save maze button
        Button saveMaze = new Button("Save Maze");
        footer.getChildren().add(saveMaze);

        // Create and add load maze
        Button loadMaze = new Button("Load Maze");
        footer.getChildren().add(loadMaze);

        // ---------------- Now that the basic GUI is set up, start the animation loop! -----------
        AnimationTimer timer = new AnimationTimer()
        {
            @Override //overriding the handle function to animation
            public void handle(long now)
            {
                // Clear the canvas at the beginning of every frame
                mgc.clearRect(0, 0, mazeCanvas.getWidth(), mazeCanvas.getHeight());
                prim.draw();
            }
        };
        timer.start();

        // Create and set a new JFX scene
        primaryStage.setScene(new Scene(root, 700, 850));

        // Set stage to show
        primaryStage.show();
    }
}
