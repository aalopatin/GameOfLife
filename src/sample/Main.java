package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.logic.Universe;

public class Main extends Application {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = WIDTH;
    private static int rows = 50;
    private static int columns = rows;
    private static int squareSize = WIDTH / rows;
    private GraphicsContext gc;

    private static Universe universe;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Life");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();
        run(gc);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


    public static void main(String[] args) {
        if (args.length == 1) {
            universe = new Universe(rows, columns, args[0]);
        } else {
            universe = new Universe(rows, columns);
        }
        launch(args);
    }

    private void run(GraphicsContext gc) {
        universe.nextGeneration();
        drawBackground(gc);
    }

    private void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (universe.isLiveCell(i, j)) {
                    gc.setFill(Color.web("000"));
                } else {
                    gc.setFill(Color.web("FFF"));
                }
                gc.fillRect(j * squareSize, i * squareSize, squareSize, squareSize);
            }
        }
    }

}
