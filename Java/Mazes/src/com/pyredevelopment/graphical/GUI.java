package com.pyredevelopment.graphical;

import com.pyredevelopment.cutility.ResizableCanvas;
import com.pyredevelopment.maze.MazeAlgorithm;
import com.pyredevelopment.maze.MazeStructure;
import javafx.animation.AnimationTimer;
import javafx.application.Application; // Required for JFX application
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;


/**
 * This class is in charge of drawing the wireframe and com.pyredevelopment.graphical.GUI in the associated report
 * (https://docs.google.com/document/d/10b-LSSGvkl0g05j54R10NhtFlJdlgE82eGOzjwQn1sg/edit?usp=sharing)
 * To display and navigate the maze generation. Primarily done using JavaFX and Canvas, and is designed
 * to be a parent class to any Maze Generation/Solving GUI's
 *
 * @author Luke Hanna (Github.com/JustAPyro / PyreDevelopment.com)
 * @version 1.1 - Updated 9/24/2021
 */
public abstract class GUI extends Application
{


    // These two protected values are initialized in the init function and can be ignored
    protected Canvas mazeCanvas;    // Canvas on which maze is visualized
    protected Canvas textCanvas;    // Canvas on which text is updated about algorithm

    //======================================== IMPORTANT ========================================
    // These variables must all be initialized before calling Super() in subclass Start function
    // If it is not, an exception will be thrown at the beginning of launch TODO: Reimplement that
    protected String titleString;       // Title of the window
    protected String headerString;      // Title of the window header
    protected String goalString;        // Complete ... ? Button
    protected MazeAlgorithm alg;        // The algorithm being visualized
    protected TextManager tm;           // The text manager handling text drawing


    // These values are private and will be handled by individual method/functions call, can effectively be ignored
    private boolean completeFlag = false;   // If this is set to true the GUI will continue calling step until finished
    private boolean stepFlag = false;       // Indicates step to be taken on next draw

    // This initializes the GUI before creating an maze or alg related items
    public void init()
    {
        mazeCanvas = new ResizableCanvas(); // Create the maze canvas
        textCanvas = new ResizableCanvas(); // Create the text canvas
    }

    /**
     * This is entered when the application is launched
     * @param primaryStage The main window of the application
     */
    @Override
    public void start(Stage primaryStage)
    {

        // Title
        primaryStage.setTitle(titleString);

        // Everything arranges in vertical rows
        VBox root = new VBox();

        // HBox holds (Title    Next Step, Finish)
        HBox header = new HBox();

        // Set some spacing and padding in the header
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER);

        // Add the header to root
        root.getChildren().add(header);

        // Create the title and add it to the header HBox
        Label titleLabel = new Label(headerString);
        titleLabel.setFont(Font.font("Helvetica", 24));
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        header.getChildren().add(titleLabel);

        // Add in the spacer between title and buttons
        Region spacerHead = new Region();
        header.getChildren().add(spacerHead);
        HBox.setHgrow(spacerHead, Priority.ALWAYS);

        // Create and add step button as well as set pref size
        Button stepButton = new Button("Next Step");
        stepButton.setPrefSize(100, 30);
        stepButton.setOnAction(event -> stepFlag = true);
        header.getChildren().add(stepButton);

        // Create and add finish button
        Button finishButton = new Button("Finish " + goalString);
        finishButton.setPrefSize(100, 30);
        finishButton.setOnAction(event -> {
            // Set complete flag to true to indicate continue drawing
            completeFlag = true;
        });
        header.getChildren().add(finishButton);

        // Create and add skip to end button
        Button skipButton = new Button("Skip to End");
        skipButton.setPrefSize(100, 30);
        skipButton.setOnAction(event -> {
            while (!alg.isComplete())
            {
                // While the algorithm isn't complete call nextStep function
                alg.nextStep();
            }
        });
        header.getChildren().add(skipButton);

        // Add the maze canvas to the root
        VBox.setVgrow(mazeCanvas, Priority.ALWAYS);
        root.getChildren().add(mazeCanvas);

        // Create and add text canvas to root
        GraphicsContext tgc = textCanvas.getGraphicsContext2D();
        textCanvas.resize(0, 175);
        root.getChildren().add(textCanvas);

        // Create footer containing (myInfo      Save Maze, Load Maze)
        HBox footer = new HBox();

        // Set padding and spacing on footer
        footer.setPadding(new Insets(10, 20, 10, 20));
        footer.setAlignment(Pos.CENTER);
        footer.setSpacing(10);

        // Add footer to root
        root.getChildren().add(footer);

        // Create and add Info label
        Label infoLabel = new Label("Implementation by Luke Hanna\nGithub.com/JustAPyro | PyreDevelopment.com");
        infoLabel.setFont(Font.font("Helvetica", 14));
        footer.getChildren().add(infoLabel);

        // Create and add spacer
        Region spacerFoot = new Region();
        footer.getChildren().add(spacerFoot);
        HBox.setHgrow(spacerFoot, Priority.ALWAYS);

        // Create and add new maze button
        Button newMaze = new Button("New Maze");
        newMaze.setOnAction(event -> alg.newMazeButton());
        newMaze.setPrefSize(100, 30);
        footer.getChildren().add(newMaze);

        // Create and add save maze button
        Button saveMaze = new Button("Save Maze");
        saveMaze.setOnAction(event -> {

            // Create a new file Chooser
            FileChooser fileChooser = new FileChooser();

            // Set the title to save
            fileChooser.setTitle("Save");

            // Set default to .maze
            fileChooser.setInitialFileName("*.maze");

            // Show all files or just .maze files
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*"),
                    new FileChooser.ExtensionFilter("Mazes", "*.maze"));

            // Get the file they want to save to
            File file = fileChooser.showSaveDialog(primaryStage);

            // Save the maze portion to working file
            alg.saveMaze(file);
        });
        saveMaze.setPrefSize(100, 30);
        footer.getChildren().add(saveMaze);

        // Create and add load maze
        Button loadMaze = new Button("Load Maze");
        loadMaze.setOnAction(event -> {
            // Create a new file Chooser
            FileChooser fileChooser = new FileChooser();

            // Set the title to save
            fileChooser.setTitle("Load");

            // Set default to .maze
            fileChooser.setInitialFileName("*.maze");

            // Show all files or just .maze files
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Mazes", "*.maze"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));

            // Get the file they want to save to
            File file = fileChooser.showOpenDialog(primaryStage);

            // Load the maze from provided file
            alg.loadMaze(file);
        });
        loadMaze.setPrefSize(100, 30);
        footer.getChildren().add(loadMaze);

        // ---------------- Now that the basic com.pyredevelopment.graphical.GUI is set up, start the animation loop! -----------
        AnimationTimer timer = new AnimationTimer()
        {
            @Override //overriding the handle function to animation
            public void handle(long now)
            {

                // Clear the text canvas every frame
                tgc.clearRect(0, 0, textCanvas.getWidth(), textCanvas.getHeight());

                // if we are stepping further into the algorithm
                if (stepFlag || completeFlag)
                {
                    // Take the next step and set the stepFlag to be false
                    alg.nextStep();
                    stepFlag = false;

                    // If the algorithm is complete set completeFlag to false
                    if (alg.isComplete()) {
                        completeFlag = false;
                    }
                }

                // Draw the text manager
                 tm.draw();

                // Draw the algorithm
                alg.draw();
            }
        };
        // Start the animation timer (This causes the above code to loop)
        timer.start();

        // Create and set a new JFX scene
        primaryStage.setScene(new Scene(root, 700, 850));

        // Set stage to show
        primaryStage.show();
    }

}
