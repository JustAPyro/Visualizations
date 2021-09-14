import javafx.application.Application; // Required for JFX application
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
        Region spacer = new Region();
        header.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Create and add step button
        Button stepButton = new Button("Next Step");
        header.getChildren().add(stepButton);

        // Create and add finish button
        Button finishButton = new Button("Finish Maze");
        header.getChildren().add(finishButton);

        // Create and set a new JFX scene
        primaryStage.setScene(new Scene(root, 600, 800));

        // Set stage to show
        primaryStage.show();
    }
}
