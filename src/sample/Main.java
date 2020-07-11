package sample;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");


        // This is how we address a controller from the fxml file that it was defined in it
        //Rectangle rec00 = (Rectangle) root.lookup("#rec5_8");
        //rec00.setFill(Color.BLUE);

        // Let us store all of the rectangle objects inside a Rectangle array
        Rectangle[][] rectangles = new Rectangle[9][19];

        for (int row = 0; row < 9; row++){
            for (int col = 0; col < 19; col++) {
                System.out.println("=====================");
                System.out.println("row: " + Integer.toString(row) + ", col: " + Integer.toString(col));
                rectangles[row][col] = (Rectangle) root.lookup("#rec" + Integer.toString(row) + "_" + Integer.toString(col));
            }
        }

        // Now, this is how to address the controller:
        // rectangles[8][18].setFill(Color.BLACK);


        // This is how to fade away a rectangle!
        /*
        FadeTransition ft = new FadeTransition(Duration.millis(1000), rectangles[8][18]);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        //ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
         */

        // Randomly choosing the walls (one-third of the rectangles should be walls)
        ArrayList<Rectangle> walls = new ArrayList<Rectangle>();


        // Randomly placing the blue player randomly on the board:
        //Random rnd = new Random();
        //int delayIndex = rnd.nextInt(19);

        walls = chooseWalls(rectangles);
        System.out.println(Arrays.toString(walls.toArray()));




        Scene scene = new Scene(root, 860, 420);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            // This will get the code for key pressed event.
            System.out.println(e.getCode());
            if (e.getCode() == KeyCode.RIGHT) {
                // Do something specifically related to right arrow key press
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public ArrayList<Rectangle> chooseWalls(Rectangle[][] rectangles) {
        Integer[] chosen = new Integer[57];
        int goOn = 1;
        for (int i = 0; i < 57; i++) {
            goOn = 1;
            while (goOn == 1) {
                Random rnd = new Random();
                int tmp = rnd.nextInt(172);
                if (Arrays.asList(chosen).contains(tmp)) {
                    continue;
                } else {
                    chosen[i] = tmp;
                    goOn = 0;
                }
            }
        }
        System.out.println(Arrays.toString(chosen));

        ArrayList<Rectangle> walls = new ArrayList<>();
        for (int i = 0; i < 57; i++) {
            int tile_index = chosen[i];
            int tile_row = (tile_index / 19);
            int tile_column = (tile_index % 19);
            if (tile_column != 0) {
                tile_column = tile_column - 1;
            }

            walls.add(rectangles[tile_row][tile_column]);
        }


        ArrayList<Integer> finalChosen = new ArrayList<>();
        // Now, let's detect the surrounded spaces
        for (int i = 0; i < 57; i++) {
            int tile_index = chosen[i];
            int tile_row = (tile_index / 19);
            int tile_column = (tile_index % 19);
            if (tile_column != 0) {
                tile_column = tile_column - 1;
            }

            if (tile_row == 0) {
                if (tile_column == 0) {
                    // to check:
                    // right, bellow

                } else if (tile_column == 18) {
                    // left, bellow

                } else {
                    // right, left, bellow

                }
            } else if (tile_row == 8) {
                if (tile_column == 0) {
                    // right, above

                } else if (tile_column == 18) {
                    // left, above

                } else {
                    // left, right, above

                }
            } else if ((tile_column == 0) && ((tile_row != 0) && (tile_row != 8))) {

            } else if ((tile_column == 18) && ((tile_row != 0) && (tile_row != 8))) {

            }
        }



        return walls;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
