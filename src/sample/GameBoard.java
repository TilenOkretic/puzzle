package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class GameBoard extends VBox implements Serializable {

    private final Stage primary_satge;
    int size;
    String game_name;
    GridPane tile_board;

    int[][] matrix;

    public GameBoard(Stage primary_stage, int size) {
        this.primary_satge = primary_stage;
        this.size = size;
        this.tile_board = new GridPane();
        this.matrix = new int[size][size];

        this.create_tiles();

        File file = new File("./saveFiles/");
        game_name = "MyGame-" + (file.list() != null ? "" + Objects.requireNonNull(file.list()).length : "" + 0);

        tile_board.setPadding(new Insets(20));
        makeLabels();

        extra_buttons();

    }

    public GameBoard(Stage primary_stage, int size, int[][] matrix, int[][] stile) {
        this.primary_satge = primary_stage;
        this.size = size;
        this.tile_board = new GridPane();
        this.matrix = matrix;

        load_tiles(stile);

        File file = new File("./saveFiles/");
        game_name = "MyGame-" + (file.list() != null ? "" + Objects.requireNonNull(file.list()).length : "" + 0);

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

    public void load_tiles(int[][] stile) {
        for (int i = 0; i < stile.length; i++) {
            for (int j = 0; j < stile[i].length; j++) {

                Rectangle tile = new Rectangle(80, 80);
                TextField field = new OneCharField();

                if (stile[i][j] == 0) {
                    tile.setFill(Color.WHITE);
                    field.setAlignment(Pos.CENTER);
                    field.setMaxSize(80, 80);
                    field.setBackground(null);
                } else {
                    field = null;
                    tile.setFill(Color.BLACK);
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

    public void resize_board(int[][] board) {

        this.size = board.length;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == size - 1 || j == size - 1) {
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
        }
    }

    public void create_tiles() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

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

        VBox first = new VBox(10);
        HBox layout = new HBox(10);
        VBox last = new VBox(10);

        Button save = new Button("Save");
        save.setTranslateX(20);
        save.setOnAction((event) -> {
            try {
                save_game();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        // check button
        Button check = new Button("Check");
        check.setTranslateX(20);
        check.setOnAction((event) -> {
            if (check_elements() == 0) {
                MessageBox box = new MessageBox("Game Over", "You Win!", "New Game");
                primary_satge.setScene(Main.main_scene);
            }
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

        // TODO: endelss mode

        first.getChildren().add(save);
        layout.getChildren().addAll(check, solution);
        last.getChildren().addAll(exit);
        this.getChildren().addAll(first, layout, last);
    }

    public void show_solution() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                setElement(i, j, matrix[i][j]);
            }
        }
    }

    public int[] flatten_array() {
        int[] out = new int[size * size];
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
                    o = -1;
                } else {
                    Rectangle rect = (Rectangle) pane.getChildren().get(0);
                    rect.setFill(Color.WHITE);
                    o = 0;
                }
            }
        }

        return o;
    }


    public void setElement(int i, int j, int value) {
        StackPane pane = (StackPane) this.tile_board.getChildren().get(i*size+j);
        if (pane.getChildren().size() > 1) {
            TextField tf = (TextField) pane.getChildren().get(1);
            tf.setText(String.valueOf(value));
        }
    }

    public String getElement(int i, int j) {
        StackPane pane = (StackPane) this.tile_board.getChildren().get(i*size+j);
        if (pane.getChildren().size() > 1) {
            TextField tf = (TextField) pane.getChildren().get(1);
            return tf.getText();
        }

        return "err";
    }

    public TextField getFullElement(int row, int col) {
        StackPane pane = (StackPane) this.tile_board.getChildren().get(size * row + col);
        if (pane.getChildren().size() > 1) {
            TextField tf = (TextField) pane.getChildren().get(1);
            return tf;
        }

        return null;
    }

    public void set_state(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != -1) {
                    setElement(i, j, state[i][j]);
                }
            }
        }
    }

    public void print() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    public void save_game() throws IOException {

        PrintWriter pw = new PrintWriter(new FileWriter("saveFiles/" + this.game_name));

        pw.println(this.size);

        // save tile color
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == -1) {
                    pw.print(1 + " ");
                } else {
                    pw.print(0 + " ");
                }
            }
            pw.println();
        }

        pw.println("; ; ;");


        //save game solution
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                pw.print(matrix[i][j] + " ");
            }
            pw.println();
        }

        pw.println("; ; ;");

        //save state
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (getElement(i, j).equals("") || getElement(i, j).equals("err")) {
                    pw.print(-1 + " ");
                } else {
                    pw.print(getElement(i, j) + " ");
                }
            }
            pw.println();
        }

        pw.println("; ; ;");

        pw.close();
        MessageBox box = new MessageBox("Success", "Game Saved!", "Exit");
    }

}
