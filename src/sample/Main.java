package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main extends Application {

    public static Scene main_scene;
    public static Properties app;
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;

    @Override
    public void start(Stage primaryStage) throws Exception {

        load_configs();

        primaryStage.setTitle("Igra!");

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);


        Label x_size_text = new Label("Size of the puzzle::");
        TextField size = new TextField("3");
        size.setMaxSize(100, 20);

        Button new_game = new Button("New Game");
        new_game.setOnAction(event -> {
            if (!size.getText().equals("")) {

                GameBoard gameBoard = new GameBoard(primaryStage, Math.abs(Integer.parseInt(size.getText())));
                //gameBoard.print();

                ScrollPane pane = new ScrollPane();
                pane.setContent(gameBoard);
                primaryStage.setScene(new Scene(pane, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT));
            }
        });

        Button loadGame = new Button("Load Game");

        loadGame.setOnAction(e -> {
                LoadGameBox lgb = new LoadGameBox(primaryStage);
        });


        box.getChildren().addAll(x_size_text, size);

        box.getChildren().addAll(new_game, loadGame);

        main_scene = new Scene(box, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        primaryStage.setScene(main_scene);

        primaryStage.show();
    }


    public void load_configs() throws IOException {
        app = new Properties();
        app.load(new FileInputStream("config/app.cfg"));
        SCREEN_WIDTH = Integer.parseInt(app.getProperty("screen_width"));
        SCREEN_HEIGHT = Integer.parseInt(app.getProperty("screen_height"));
    }



    public static void main(String[] args) {
        launch(args);
    }
}
