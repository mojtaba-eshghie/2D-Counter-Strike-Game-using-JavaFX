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

    public Integer red_player_one_row;
    public Integer red_player_one_col;

    public Integer red_player_two_row;
    public Integer red_player_two_col;

    public Integer red_player_three_row;
    public Integer red_player_three_col;

    public int red_player_three_lives;
    public int red_player_two_lives;
    public int red_player_one_lives;
    public int blue_player_lives;

    public ImageView redPlayer1;
    public ImageView redPlayer2;
    public ImageView redPlayer3;



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
        this.red_player_three_lives = 3;
        this.red_player_two_lives = 3;
        this.red_player_one_lives = 3;
        this.blue_player_lives = 3;

        this.red_player_one_col = 17;
        this.red_player_one_row = 1;

        this.red_player_two_col = 16;
        this.red_player_two_row = 1;

        this.red_player_three_col = 17;
        this.red_player_three_row = 2;



        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");


        // This is how we address a controller from the fxml file that it was defined in it
        //Rectangle rec00 = (Rectangle) root.lookup("#rec5_8");
        //rec00.setFill(Color.BLUE);

        this.redPlayer1 = (ImageView) root.lookup("#redPlayer1");
        this.redPlayer2 = (ImageView) root.lookup("#redPlayer2");
        this.redPlayer3 = (ImageView) root.lookup("#redPlayer3");

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
            if (e.getCode() == KeyCode.SPACE) {
                this.blueShoots();
            } else {
                step(e);
            }

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
                this.updateEmptySpace();
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
                this.updateEmptySpace();
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
                this.updateEmptySpace();
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
                this.updateEmptySpace();
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

    public void updateEmptySpace() {
        /*
        // Update the emptySpace based on the current position of all of players so that
        // it will not include their positions
        this.emptySpace = new ArrayList<Integer>();
        for (int j = 1; j < 172; j++) {
            if ((j > 18) && (j != 19) && (j != 38) && (j != 57) && (j != 76) && (j != 95) && (j != 114) && (j != 133) && (j < 152) && (j != 37) && (j != 56) && (j != 75) && (j != 94) && (j != 113) && (j != 132) && (j != 151)) {
                this.emptySpace.add(j);
            }

        }


        for (int i = 0; i < this.chosen.length; i++) {
            int place = this.chosen[i];
            int exists = this.emptySpace.indexOf(place);
            if (exists != -1) {
                this.emptySpace.remove(exists);
            }
        }



        Integer bluePlayerPosition = (this.player_current_pos_row * 19) + this.red_player_three_col - 13;
        Integer redPlayerOnePosition = (this.red_player_one_row * 19) + this.red_player_one_col;
        Integer redPlayerTwoPosition = (this.red_player_two_row * 19) + this.red_player_two_col;
        Integer redPlayerThreePosition = (this.red_player_three_row * 19) + this.red_player_three_col;

        System.out.println("    #### blue's new position is: " + Integer.toString(bluePlayerPosition) + " #### (" + Integer.toString(this.player_current_pos_row) + ", " + Integer.toString(this.player_current_pos_column) + ") ");

        int exists = this.emptySpace.indexOf(bluePlayerPosition);
        if (exists != -1) {
            System.out.println(Integer.toString(bluePlayerPosition) + " removed from emptySpace::: it was in " + this.emptySpace.indexOf(bluePlayerPosition));
            this.emptySpace.remove(exists);
        }

        exists = this.emptySpace.indexOf(redPlayerOnePosition);
        if (exists != -1) {
            System.out.println(Integer.toString(redPlayerOnePosition) + " removed from emptySpace::: it was in " + this.emptySpace.indexOf(redPlayerOnePosition));
            this.emptySpace.remove(exists);
        }

        exists = this.emptySpace.indexOf(redPlayerTwoPosition);
        if (exists != -1) {
            System.out.println(Integer.toString(redPlayerTwoPosition) + " removed from emptySpace::: it was in " + this.emptySpace.indexOf(redPlayerTwoPosition));
            this.emptySpace.remove(exists);

        }

        exists = this.emptySpace.indexOf(redPlayerThreePosition);
        if (exists != -1) {
            System.out.println(Integer.toString(redPlayerThreePosition) + " removed from emptySpace::: it was in " + this.emptySpace.indexOf(redPlayerThreePosition));
            this.emptySpace.remove(exists);
        }

         */

    }

    public void blueShoots(){
        this.playShootingSound();
        double rotation = this.bluePlayer.getRotate();
        if (rotation == 90.0) {
            // shoot right

            // first check if there is any reds in the line of sight,
            // then, we can check if there is a wall
            HashMap<ImageView, Integer> inlineTargets = new HashMap<>();

            if (this.red_player_one_row == this.player_current_pos_row) {
                if (this.red_player_one_col >= this.player_current_pos_column) {
                    inlineTargets.put(this.redPlayer1, this.red_player_one_col);
                }
            }
            if (this.red_player_two_row == this.player_current_pos_row) {
                if (this.red_player_two_col >= this.player_current_pos_column) {
                    inlineTargets.put(this.redPlayer2, this.red_player_two_col);
                }
            }
            if (this.red_player_three_row == this.player_current_pos_row) {
                if (this.red_player_three_col >= this.player_current_pos_column) {
                    inlineTargets.put(this.redPlayer3, this.red_player_three_col);
                }
            }

            ImageView nearestTarget = null;
            Integer nearestTargetColumn = null;

            if (inlineTargets.size() >= 1) {

                for (Map.Entry<ImageView, Integer> entry : inlineTargets.entrySet()) {
                    ImageView target = entry.getKey();
                    Integer targetColumn = entry.getValue();

                    if (nearestTarget == null) {
                        nearestTarget = target;
                        nearestTargetColumn = targetColumn;

                    } else {
                        if (targetColumn < nearestTargetColumn) {
                            nearestTargetColumn = targetColumn;
                            nearestTarget = target;
                        }
                    }
                }

                // First check if there is any walls then do this: killRed(nearestTarget);

                Integer rowToWorkWith = 0;
                if (nearestTarget.equals(this.redPlayer1)) {
                    rowToWorkWith = this.red_player_one_row;
                } else if (nearestTarget.equals(this.redPlayer2)) {
                    rowToWorkWith = this.red_player_two_row;
                } else if (nearestTarget.equals(this.redPlayer3)) {
                    rowToWorkWith = this.red_player_three_row;
                }

                // check the this.chosen with the following numbers:
                // There must not be any walls in between these two (exclusive of the boundries themselves)
                Boolean thereIsWallInBetween = false;
                int lower_bound = this.player_current_pos_row * 19 + this.player_current_pos_column;
                int upper_bound = this.player_current_pos_row * 19 + nearestTargetColumn;
                for (int i = 0; i < this.chosen.length; i++) {
                    if ((this.chosen[i] > lower_bound) && (this.chosen[i] < upper_bound)) {
                        thereIsWallInBetween = true;
                    }
                }
                if (!thereIsWallInBetween) {
                    hitRed(nearestTarget);
                }

            }





        } else if (rotation == 180.0) {
            // shoot bellow
            if (this.red_player_one_col == this.player_current_pos_column) {

            }
            if (this.red_player_two_col == this.player_current_pos_column) {

            }
            if (this.red_player_three_col == this.player_current_pos_column) {

            }

        } else if (rotation == 270.0) {
            // shoot left
            if (this.red_player_one_row == this.player_current_pos_row) {

            }
            if (this.red_player_two_row == this.player_current_pos_row) {

            }
            if (this.red_player_three_row == this.player_current_pos_row) {

            }
        } else if (rotation == 0.0) {
            // shout up
            if (this.red_player_one_col == this.player_current_pos_column) {

            }
            if (this.red_player_two_col == this.player_current_pos_column) {

            }
            if (this.red_player_three_col == this.player_current_pos_column) {

            }

        }
    }

    public void updateLivesLabels() {
        /**
         * To be implemented:
         * update the live labels on all of the players alltogether!
         */
    }

    public void killRed(ImageView playerToKill) {
        // This is temporary, I should implement actuall killing code!
        FadeTransition ft = new FadeTransition(Duration.millis(1000), playerToKill);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        //ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

    public void hitRed(ImageView redPlayerToKill) {
        if (redPlayerToKill.equals(this.redPlayer1)) {
            if (this.red_player_one_lives <= 0) {
                killRed(redPlayerToKill);
            } else {
                this.red_player_one_lives = this.red_player_one_lives - 1;
                this.updateLivesLabels();
            }
        } else if (redPlayerToKill.equals(this.redPlayer2)) {
            if (this.red_player_two_lives <= 0) {
                killRed(redPlayerToKill);
            } else {
                this.red_player_two_lives = this.red_player_two_lives - 1;
                this.updateLivesLabels();
            }
        } else if (redPlayerToKill.equals(this.redPlayer3)) {
            if (this.red_player_three_lives <= 0) {
                killRed(redPlayerToKill);
            } else {
                this.red_player_three_lives = this.red_player_three_lives - 1;
                this.updateLivesLabels();
            }
        }
    }

    public void playShootingSound() {
        Media hit = new Media(new File("src/sample/blueshoot.wav").toURI().toString());
        MediaPlayer player = new MediaPlayer(hit);
        player.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
