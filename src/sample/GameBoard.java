package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class GameBoard extends VBox implements Serializable {

    private Stage primary_satge;
    int x_size, y_size;
    String game_name;
    GridPane tile_board;

    int[][] matrix;

    public GameBoard(Stage primary_stage, int x_size, int y_size) {
        this.primary_satge = primary_stage;
        this.x_size = x_size;
        this.y_size = y_size;
        this.tile_board = new GridPane();
        this.matrix = new int[x_size][y_size];

        this.create_tiles();

        File file = new File("./saveFiles/");
        game_name = "MyGame-" + (file.list() != null ? "" + file.list().length : "" + 0);

        tile_board.setPadding(new Insets(20));
        makeLabels();

        extra_buttons();
    }

    public void makeLabels() {
        horizontal();
        vertical();
        Label horizontal = new Label("Horizontal: ");
        horizontal.setPadding(new Insets(20));
        Label vertical = new Label("Vertical: ");
        vertical.setPadding(new Insets(20));
        this.getChildren().addAll(horizontal, vertical);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.horizontal().size(); i++) {
            sb.append(this.horizontal().get(i) + " ");
        }
        horizontal.setText(horizontal.getText() + sb.toString());
        sb = new StringBuilder();
        for (int i = 0; i < this.vertical().size(); i++) {
            sb.append(this.vertical().get(i) + " ");
        }
        vertical.setText(vertical.getText() + sb.toString());
    }

    public void create_tiles() {
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {

                Rectangle tile = new Rectangle(80, 80);
                TextField field = new OneCharField();

                if (Math.random() * 100 > 12) {
                    tile.setFill(Color.WHITE);
                    field.setAlignment(Pos.CENTER);
                    field.setMaxSize(80, 80);
                    field.setBackground(null);

                    this.matrix[i][j] = (int) Math.floor(Math.random() * 9) + 1;
                } else {
                    field = null;
                    tile.setFill(Color.BLACK);
                    this.matrix[i][j] = -1;
                }

                tile.setStroke(Color.BLACK);
                if (field != null) {
                    this.tile_board.add(new StackPane(tile, field), j, i);
                } else {
                    this.tile_board.add(new StackPane(tile), j, i);
                }
            }
        }
        this.getChildren().add(this.tile_board);
    }

    public ArrayList<String> horizontal() {
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            String num = "";
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == -1) {
                    if (!num.equals("")) {
                        arr.add(num);
                    }
                    num = "";
                } else {
                    num += matrix[i][j];
                }
            }
            if (!num.equals("")) {
                arr.add(num);
            }
        }

        return arr;
    }

    public ArrayList<String> vertical() {
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            String num = "";
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[j][i] == -1) {
                    if (!num.equals("")) {
                        arr.add(num);
                    }
                    num = "";
                } else {
                    num += matrix[j][i];
                }
            }
            if (!num.equals("")) {
                arr.add(num);
            }
        }

        return arr;
    }

    public void extra_buttons() {


        // save button

        // check button
        Button check = new Button("Check");
        check.setTranslateX(20);
        check.setOnAction((event) -> {
            if(check_elements() == 0){
                MessageBox box = new MessageBox("Game Over", "You Win!");
                primary_satge.setScene(Main.main_scene);
            };
        });


        // show solution
        Button solution = new Button("Show Solution");
        solution.setTranslateX(20);
        solution.setOnAction((event) -> {
            show_solution();
        });


        // exit game
        Button exit = new Button("Exit");
        exit.setTranslateX(20);
        exit.setOnAction((event) -> {
            primary_satge.setScene(Main.main_scene);
        });

        this.getChildren().addAll(exit, solution, check);

    }

    public void show_solution() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                setElement(i, j, matrix[i][j]);
            }
        }
    }

    public int[] flatten_array() {
        int[] out = new int[x_size * y_size];
        int s = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                out[s] = matrix[i][j];
                s++;
            }
        }

        return out;
    }

    public int check_elements() {

        int[] arr = flatten_array();
        int o = 0;
        for (int i = 0; i < this.tile_board.getChildren().size(); i++) {
            StackPane pane = (StackPane) this.tile_board.getChildren().get(i);
            if (pane.getChildren().size() > 1) {

                TextField tf = (TextField) pane.getChildren().get(1);

                if (tf.getText().equals("") || Integer.parseInt(tf.getText()) != arr[i]) {
                    Rectangle rect = (Rectangle) pane.getChildren().get(0);
                    rect.setFill(Color.RED);
                    o=-1;
                }else {
                    Rectangle rect = (Rectangle) pane.getChildren().get(0);
                    rect.setFill(Color.WHITE);
                    o=0;
                }
            }
        }

        return o;
    }

    public void setElement(int row, int col, int value) {
        StackPane pane = (StackPane) this.tile_board.getChildren().get(x_size * row + col);
        if (pane.getChildren().size() > 1) {
            TextField tf = (TextField) pane.getChildren().get(1);
            tf.setText(String.valueOf(value));
        }
    }

    public String getElement(int row, int col) {
        StackPane pane = (StackPane) this.tile_board.getChildren().get(x_size * row + col);
        if (pane.getChildren().size() > 1) {
            TextField tf = (TextField) pane.getChildren().get(1);
            return tf.getText();
        }

        return "err";
    }

    public void print() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
