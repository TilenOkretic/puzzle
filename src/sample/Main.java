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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


public class Main extends Application {

    public static Scene main_scene;
    public static Properties app;
    public static double SCREEN_WIDTH, SCREEN_HEIGHT;
    public static int CHANCE;

    @Override
    public void start(Stage primaryStage) {

        load_config();

        primaryStage.setTitle("Keisuke");

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        Label x_size_text = new Label("Size of the puzzle::");
        TextField size = new TextField("3");
        size.setMaxSize(100, 20);

        Button new_game = new Button("New Game");
        new_game.setOnAction(event -> {
            if (!size.getText().equals("")) {

                GameBoard gameBoard = new GameBoard(primaryStage, Math.abs(Integer.parseInt(size.getText())));

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

        primaryStage.setOnCloseRequest(e -> {
            app.setProperty("screen_width", Double.toString((primaryStage.getWidth())));
            app.setProperty("screen_height", Double.toString(primaryStage.getHeight()));
            app.setProperty("chance", Integer.toString(CHANCE));
            store_configs();
        });

        primaryStage.show();
    }


    public void load_config() {
        app = new Properties();
        try {
            app.load(new FileInputStream("./config/app.cfg"));
            SCREEN_WIDTH = Double.parseDouble(app.getProperty("screen_width"));
            SCREEN_HEIGHT = Double.parseDouble(app.getProperty("screen_height"));
            CHANCE = Integer.parseInt(app.getProperty("chance"));
        } catch (IOException e) {
            app.setProperty("screen_width", "800");
            app.setProperty("screen_height", "800");
            app.setProperty("chance", "22");
            store_configs();
            load_config();
        }
    }

    public void store_configs(){
        File f = new File("./config");
        if(!f.isDirectory()){
            f.mkdir();
            File ff = new File("./config/app.cfg");
            try {
                ff.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try
        {
            Path pf = Paths.get("./config/app.cfg");
            Writer writer = Files.newBufferedWriter(pf);
            app.store(writer, "");
            writer.close();
        }
        catch(IOException Ex)
        {
            System.out.println("IO Exception :" +
                    Ex.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
