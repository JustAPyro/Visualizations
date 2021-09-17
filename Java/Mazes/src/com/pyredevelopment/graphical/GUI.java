package com.pyredevelopment.graphical;

import com.pyredevelopment.cutility.ResizableCanvas;
import com.pyredevelopment.generationalgorithms.PrimGenerator;
import javafx.animation.AnimationTimer;
import javafx.application.Application; // Required for JFX application
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;

import static java.lang.Thread.sleep;

/**
 * This class is in charge of drawing the wireframe and com.pyredevelopment.graphical.GUI in the associated report
 * (https://docs.google.com/document/d/10b-LSSGvkl0g05j54R10NhtFlJdlgE82eGOzjwQn1sg/edit?usp=sharing)
 * To display and navigate the maze generation
 * This is done using JavaFX and Canvas
 */
public class GUI extends Application
{

    private PrimGenerator prim;             // This is what is going to be handling the algorithm
    private TextManager tm;                 // This is what will be handling updating the text

    private boolean completeFlag = false;   // If this is set to true the GUI will continue calling step until finished
    private boolean stepFlag = false;       // Indicates step to be taken on next draw
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
        titleLabel.setFont(Font.font("Helvetica", 24));
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        header.getChildren().add(titleLabel);

        // Add in the spacer between title and buttons
        Region spacerHead = new Region();
        header.getChildren().add(spacerHead);
        HBox.setHgrow(spacerHead, Priority.ALWAYS);

        // Create and add step button
        Button stepButton = new Button("Next Step");
        stepButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                stepFlag = true;
            }
        });
        header.getChildren().add(stepButton);

        // Create and add finish button
        Button finishButton = new Button("Finish Maze");
        finishButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                // Set complete flag to true to indicate continue drawing
                completeFlag = true;
            }
        });
        header.getChildren().add(finishButton);

        // Create and add skip to end button
        Button skipButton = new Button("Skip to End");
        skipButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                while (prim.nextStep() == false);
            }
        });
        header.getChildren().add(skipButton);



        // Create and add canvas to an HBOX then root
        Canvas mazeCanvas = new ResizableCanvas();
        VBox.setVgrow(mazeCanvas, Priority.ALWAYS);
        root.getChildren().add(mazeCanvas);

        // Save the maze canvas graphics context
        GraphicsContext mgc = mazeCanvas.getGraphicsContext2D();

        // Create and add text canvas to root
        Canvas textCanvas = new Canvas(600, 200);
        GraphicsContext tgc = textCanvas.getGraphicsContext2D();
        root.getChildren().add(textCanvas);

        // Create a text manager and attach it to textCanvas
        tm = new TextManager(textCanvas);
        tm.addText(1, 0, "1. Start with a grid full of walls");
        tm.addText(0, 0, "2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.");
        tm.addText(0, 0, "3. While there are walls in the list:");
        tm.addText(0, 1, "1. Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then:");
        tm.addText(0, 2, "1. Make the wall a passage and mark the unvisited cell as part of the maze");
        tm.addText(0, 2, "2. Add the neighboring walls of the cell to the wall list.");
        tm.addText(0, 1, "2. Remove the wall from the list");

        // Then create a Prim generator and attach it to mazeCanvas
        System.out.println(mazeCanvas.getWidth());
        prim = new PrimGenerator(mazeCanvas, tm);

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

        // ---------------- Now that the basic com.pyredevelopment.graphical.GUI is set up, start the animation loop! -----------
        AnimationTimer timer = new AnimationTimer()
        {
            @Override //overriding the handle function to animation
            public void handle(long now)
            {
                // Clear both canvases at the beginning of every frame
                tgc.clearRect(0, 0, textCanvas.getWidth(), textCanvas.getHeight());

                if (stepFlag || completeFlag)
                {
                    prim.nextStep();
                    stepFlag = false;

                    if (prim.isComplete()) {
                        completeFlag = false;
                    }
                }

                tm.draw();
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