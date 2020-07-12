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
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.*;


public class Main extends Application {
    public Integer[] chosen = new Integer[22];
    public Integer player_current_pos_row;
    public Integer player_current_pos_column;
    public ArrayList<Integer> emptySpace = new ArrayList<>();
    public ImageView bluePlayer;


    public void setEmptySpace() {
        for (int row = 1; row < 8; row++) {

            for (int col = 1; col < 18; col++) {
                if (Arrays.asList(this.chosen).contains(row*19 + col + 1)) {
                    // do nothing
                } else {
                    // add to emptySpace
                    this.emptySpace.add(row*19 + col + 1);
                }
            }
        }
        System.out.println("********");
        System.out.println(this.emptySpace);
        System.out.println("********");
    }

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

        this.bluePlayer = (ImageView) root.lookup("#blueplayer");


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

        for (Rectangle wall : walls) {
            wall.setFill(Color.BLACK);
        }



        this.player_current_pos_column = 4;
        this.player_current_pos_row = 4;

        Scene scene = new Scene(root, 860, 420);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            // This will get the code for key pressed event.

            step(e);
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public ArrayList<Rectangle> chooseWalls(Rectangle[][] rectangles) {
        Integer[] chosen = new Integer[22];
        /*
        Skipping randomness for now!
        int goOn = 1;
        for (int i = 0; i < 57; i++) {
            goOn = 1;
            while (goOn == 1) {
                Random rnd = new Random();
                int tmp = rnd.nextInt(171);
                if (Arrays.asList(chosen).contains(tmp)) {
                    continue;
                } else {
                    chosen[i] = tmp;
                    goOn = 0;
                }
            }
        }
         */



        chosen[0] = 21; chosen[1] = 40; chosen[2] = 59; chosen[3] = 42; chosen[4] = 61; chosen[5] = 80; chosen[6] = 82; chosen[7] = 101; chosen[8] = 120; chosen[9] = 93; chosen[10] = 92; chosen[11] = 73; chosen[12] = 54; chosen[13] = 47; chosen[14] = 66; chosen[15] = 68; chosen[16] = 87; chosen[17] = 67; chosen[18] = 129; chosen[19] = 148; chosen[18] = 124; chosen[19] = 131; chosen[20] = 145; chosen[21] = 117;

        this.chosen = chosen;
        System.out.println(Arrays.toString(this.chosen));

        setEmptySpace();

        ArrayList<Rectangle> walls = new ArrayList<>();
        for (int i = 0; i < chosen.length; i++) {
            int tile_index = chosen[i];
            int tile_row = (tile_index / 19);
            int tile_column = (tile_index % 19);
            if (tile_column != 0) {
                tile_column = tile_column - 1;
            }

            walls.add(rectangles[tile_row][tile_column]);
        }


        ArrayList<Integer> choosen_arraylist = new ArrayList<>();
        ArrayList<Integer> not_chosen = new ArrayList<>();

        for (int i = 0; i < chosen.length; i++) {
            choosen_arraylist.add(chosen[i]);
        }

        // let us build the not_chosen
        for (int index = 0; index < 171; index++) {
            Boolean is_wall = choosen_arraylist.contains(index);
            if (!is_wall) {
                not_chosen.add(index);
            }
        }
        System.out.println(not_chosen);

        ArrayList<Integer> finalChosen = new ArrayList<>();
        // Now, let's detect the surrounded spaces
        for (int i = 0; i < not_chosen.size(); i++) {
            int tile_index = not_chosen.get(i);
            int tile_row = (tile_index / 19);
            int tile_column = (tile_index % 19);
            if (tile_column != 0) {
                tile_column = tile_column - 1;
            }


            int right = 0;
            int left = 0;
            int above = 0;
            int bellow = 0;

            if (tile_row == 1) {

                if (tile_column == 1) {
                    // to check:
                    // right, bellow

                    right = not_chosen.get(i) + 1;
                    bellow = not_chosen.get(i) + 19;

                    Boolean right_is_wall = choosen_arraylist.contains(right);
                    Boolean bellow_is_wall = choosen_arraylist.contains(bellow);

                    if (right_is_wall && bellow_is_wall) {
                        // This is a wall, change one of them!
                        choosen_arraylist.remove(choosen_arraylist.indexOf(right));
                    }


                } else if (tile_column == 17) {
                    // left, bellow
                    left = not_chosen.get(i) - 1;
                    bellow = not_chosen.get(i) + 19;

                    Boolean left_is_wall = choosen_arraylist.contains(left);
                    Boolean bellow_is_wall = choosen_arraylist.contains(bellow);

                    if (left_is_wall && bellow_is_wall) {
                        choosen_arraylist.remove(choosen_arraylist.indexOf(left));
                    }

                } else {
                    // right, left, bellow
                    right = not_chosen.get(i) + 1;
                    left = not_chosen.get(i) - 1;
                    bellow = not_chosen.get(i) + 19;

                    Boolean left_is_wall = choosen_arraylist.contains(left);
                    Boolean right_is_wall = choosen_arraylist.contains(right);
                    Boolean bellow_is_wall = choosen_arraylist.contains(bellow);

                    if(left_is_wall && right_is_wall && bellow_is_wall) {
                        choosen_arraylist.remove(choosen_arraylist.indexOf(bellow));
                    }
                }
            } else if (tile_row == 7) {
                if (tile_column == 0) {
                    // right, above
                    right = not_chosen.get(i) + 1;
                    above = not_chosen.get(i) - 19;

                    Boolean right_is_wall = choosen_arraylist.contains(right);
                    Boolean above_is_wall = choosen_arraylist.contains(above);

                    if (right_is_wall && above_is_wall) {
                        choosen_arraylist.remove(choosen_arraylist.indexOf(right));
                    }

                } else if (tile_column == 17) {
                    // left, above
                    left = not_chosen.get(i) - 1;
                    above = not_chosen.get(i) - 19;

                    Boolean left_is_wall = choosen_arraylist.contains(left);
                    Boolean above_is_wall = choosen_arraylist.contains(above);

                    if (above_is_wall && left_is_wall) {
                        choosen_arraylist.remove(choosen_arraylist.indexOf(above));
                    }

                } else {
                    // left, right, above
                    left = not_chosen.get(i) - 1;
                    right = not_chosen.get(i) + 1;
                    above = not_chosen.get(i) - 19;

                    Boolean left_is_wall = choosen_arraylist.contains(left);
                    Boolean right_is_wall = choosen_arraylist.contains(right);
                    Boolean above_is_wall = choosen_arraylist.contains(above);

                    if (left_is_wall && right_is_wall && above_is_wall) {
                        choosen_arraylist.remove(choosen_arraylist.indexOf(right));
                    }

                }
            } else if ((tile_column == 1) && ((tile_row != 1) && (tile_row != 7))) {
                // above, bellow, right
                right = not_chosen.get(i) + 1;
                above = not_chosen.get(i) - 19;
                bellow = not_chosen.get(i) + 19;

                Boolean right_is_wall = choosen_arraylist.contains(right);
                Boolean above_is_wall = choosen_arraylist.contains(above);
                Boolean bellow_is_wall = choosen_arraylist.contains(bellow);

                if (right_is_wall && above_is_wall && bellow_is_wall) {
                    choosen_arraylist.remove(choosen_arraylist.indexOf(above));
                }

            } else if ((tile_column == 17) && ((tile_row != 1) && (tile_row != 7))) {
                //above, bellow, left
                left = not_chosen.get(i) - 1;
                above = not_chosen.get(i) - 19;
                bellow = not_chosen.get(i) + 19;

                Boolean left_is_wall = choosen_arraylist.contains(left);
                Boolean above_is_wall = choosen_arraylist.contains(above);
                Boolean bellow_is_wall = choosen_arraylist.contains(bellow);

                if (left_is_wall && above_is_wall && bellow_is_wall) {
                    choosen_arraylist.remove(choosen_arraylist.indexOf(above));
                }
            }
        }

        System.out.println("-----------------------------------------");
        System.out.println(choosen_arraylist);
        System.out.println("-----------------------------------------");




        return walls;
    }

    public void rotatePlayer(ImageView player, String direction) {
        if (direction.equals("right")){
            player.setRotate(90.0);
        } else if (direction.equals("left")) {
            player.setRotate(270.0);
        } else if (direction.equals("up")) {
            player.setRotate(0.0);
        } else if (direction.equals("down")) {
            player.setRotate(180.0);
        }
    }

    public void step(KeyEvent event) {
        Integer playerPositionIndex = (19 * this.player_current_pos_row) + this.player_current_pos_column + 1;

        System.out.println("*************************************************************************");
        System.out.println("emptySpace: ");
        System.out.println(this.emptySpace);
        System.out.println(Arrays.toString(this.chosen));
        System.out.println(playerPositionIndex);


        if (event.getCode() == KeyCode.RIGHT) {

            this.rotatePlayer(this.bluePlayer, "right");




            System.out.println(this.bluePlayer.rotateProperty().floatValue());

            System.out.println(event.getCode());
            if (emptySpace.contains(playerPositionIndex + 1)) {
                this.playCorrectMoveBeep();
                System.out.println("right is: " + Integer.toString(playerPositionIndex + 1));
                System.out.println("Can go right!!!");
                this.player_current_pos_column = this.player_current_pos_column  + 1;
                GridPane.setColumnIndex(this.bluePlayer, this.player_current_pos_column);

            } else {
                this.playWrongMoveBeep();
                System.out.println("Cannot move to right...");

            }
        } else if (event.getCode() == KeyCode.LEFT) {
            this.rotatePlayer(this.bluePlayer, "left");
            System.out.println(event.getCode());
            if (emptySpace.contains(playerPositionIndex - 1)) {
                this.playCorrectMoveBeep();
                System.out.println("Can go left!!!");
                this.player_current_pos_column = this.player_current_pos_column - 1;
                GridPane.setColumnIndex(this.bluePlayer, this.player_current_pos_column);
            } else {
                this.playWrongMoveBeep();
                System.out.println("Cannot move to left...");
            }

        } else if (event.getCode() == KeyCode.DOWN) {
            this.rotatePlayer(this.bluePlayer, "down");
            System.out.println(event.getCode());
            if (emptySpace.contains(playerPositionIndex + 19)) {
                this.playCorrectMoveBeep();
                System.out.println("Can go down!!!");
                this.player_current_pos_row = this.player_current_pos_row + 1;
                GridPane.setRowIndex(this.bluePlayer, this.player_current_pos_row);
            } else {
                this.playWrongMoveBeep();
                System.out.println("Cannot move to down...");
            }

        } else if (event.getCode() == KeyCode.UP) {
            this.rotatePlayer(this.bluePlayer, "up");
            System.out.println(event.getCode());
            if (emptySpace.contains(playerPositionIndex - 19)) {
                this.playCorrectMoveBeep();
                System.out.println("Can go up!!!");
                this.player_current_pos_row = this.player_current_pos_row  - 1;
                GridPane.setRowIndex(this.bluePlayer, this.player_current_pos_row);
            } else {
                this.playWrongMoveBeep();
                System.out.println("Cannot move to up...");

            }

        }
    }

    public void playWrongMoveBeep(){
        Media hit = new Media(new File("src/sample/beep-03.wav").toURI().toString());
        MediaPlayer player = new MediaPlayer(hit);
        player.play();
    }

    public void playCorrectMoveBeep() {
        Media hit = new Media(new File("src/sample/button-3.wav").toURI().toString());
        MediaPlayer player = new MediaPlayer(hit);
        player.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
