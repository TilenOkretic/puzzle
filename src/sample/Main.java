package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {

    public static Scene main_scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Igra!");


        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);


        Label x_size_text = new Label("Number of columns:");
        TextField x_size = new TextField("3");
        x_size.setMaxSize(100, 20);

        Label y_size_text = new Label("Number of rows:");
        TextField y_size = new TextField("3");
        y_size.setMaxSize(100, 20);
        Button new_game = new Button("New Game");
        new_game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!x_size.getText().equals("") && !y_size.getText().equals("")) {
                    GameBoard gameBoard = new GameBoard(primaryStage, Integer.parseInt(x_size.getText()), Integer.parseInt(y_size.getText()));
                    gameBoard.print();
                    primaryStage.setScene(new Scene(gameBoard, 1280, 1280));
                }
            }
        });


        box.getChildren().addAll(x_size_text, x_size);
        box.getChildren().addAll(y_size_text, y_size);

        box.getChildren().add(new_game);

        main_scene = new Scene(box, 1280, 1280);
        primaryStage.setScene(main_scene);

        primaryStage.show();
    }


    public VBox createBasicBoard(Stage primary_stage, int x_size, int y_size) throws IOException {

        VBox box = new VBox();

        File file = new File("./saveFiles/");

        String game_name = "MyGame-" + (file.list() != null ? "" + file.list().length : "" + 0);

        GridPane gameBoard = new GridPane();
        gameBoard.setPadding(new Insets(20));

        create_tiles(x_size, y_size, gameBoard);

        GridPane pane = new GridPane();

        pane.setPadding(new Insets(20));

        Label horizontal = new Label("Horizontal:");
        Label vertical = new Label("Vertical:");

        load_game_values(horizontal, vertical);

        pane.add(horizontal, 0, 0);
        pane.add(vertical, 0, 1);

        box.getChildren().addAll(gameBoard, pane);

        Button save_board = new Button("Save Board");
        save_board.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        Button exit = new Button("Exit Game!");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary_stage.setScene(main_scene);
            }
        });

        box.getChildren().addAll(save_board, exit);

        return box;
    }


    public void load_game_values(Label horizontal, Label vertical) throws IOException {
        BufferedReader fr = new BufferedReader(new FileReader("values.txt"));
        String line = fr.readLine();

        while (line != null) {
            String[] arr = line.split("-");
            if (arr[0].equals("horizontal")) {
                for (int i = 1; i < arr.length; i++) {
                    horizontal.setText(horizontal.getText() + " " + arr[i]);
                }
            } else if (arr[0].equals("vertical")) {
                for (int i = 1; i < arr.length; i++) {
                    vertical.setText(vertical.getText() + " " + arr[i]);
                }
            }
            line = fr.readLine();
        }
    }


    public void create_tiles(int x_size, int y_size, GridPane gameBoard) {
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {

                Rectangle tile = new Rectangle(80, 80);
                TextField field = new OneCharField();

                if (Math.random() * 100 > 12) {
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
                    gameBoard.add(new StackPane(tile, field), j, i);
                } else {
                    gameBoard.add(new StackPane(tile), j, i);
                }

            }
        }
    }


    public void save_game(GridPane gridPane) {


    }


    public static void main(String[] args) {
        launch(args);
    }
}
