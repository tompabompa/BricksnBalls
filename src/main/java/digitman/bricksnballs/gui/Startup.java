package digitman.bricksnballs.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author thomas
 */
public class Startup extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Bricks'n'Balls");
        StackPane rootPane = new StackPane();
        Scene scene = new Scene(rootPane);
        Canvas canvas = new Canvas(800, 600);
        rootPane.getChildren().add(canvas);
        stage.setScene(scene);
        new GameRunner(scene, canvas).run();
        stage.show();
    }
}
