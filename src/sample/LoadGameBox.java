package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class LoadGameBox {


    public LoadGameBox(Stage primaryStage) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Load Game");
        window.setMinWidth(256);

        Label label = new Label("Select a saved game:");

        File file = new File("./saveFiles/");
        Button[] lgb = new Button[Objects.requireNonNull(file.list()).length];
        for (int i = 0; i < lgb.length; i++){
            String game = Objects.requireNonNull(file.list())[i];
            lgb[i] = new Button(game);
            lgb[i].setOnAction(e -> {
                try {
                    load_game(primaryStage, game);
                    window.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }

        Button btn = new Button("Exit");
        btn.setOnAction((event) -> window.close());

        VBox box = new VBox(15);
        box.getChildren().add(label);
        box.getChildren().addAll(lgb);
        box.getChildren().add(btn);
        box.setAlignment(Pos.CENTER);

        ScrollPane pane = new ScrollPane();
        pane.setContent(box);
        pane.setPadding(new Insets(0,0,0,58));

        Scene scene = new Scene(pane, 256,512);
        window.setScene(scene);
        window.showAndWait();
    }


    public void load_game(Stage primaryStage, String game_name) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("saveFiles/" + game_name));


        Scanner sc = new Scanner(br);
        int size = Integer.parseInt(br.readLine());


        //load tiles
        String l = sc.nextLine();
        int[][] stile = new int[size][size];
        while (!l.contains(";")){
            for (int i=0; i<stile.length; i++) {
                String[] line = l.split(" ");
                for (int j = 0; j < line.length; j++) {
                    stile[i][j] = Integer.parseInt(line[j]);
                }
                l = sc.nextLine();
            }
        }

        //load solution
        l = sc.nextLine();
        int[][] solution = new int[size][size];
        while (!l.contains(";")){
            for (int i=0; i<solution.length; i++) {
                String[] line = l.split(" ");
                for (int j=0; j<line.length; j++) {
                    solution[i][j] = Integer.parseInt(line[j]);
                }
                l = sc.nextLine();
            }
        }

        //load state
        l = sc.nextLine();
        int[][] state = new int[size][size];
        while (!l.contains(";")){
            for (int i=0; i<state.length; i++) {
                String[] line = l.split(" ");
                for (int j=0; j<line.length; j++) {
                    state[i][j] = Integer.parseInt(line[j]);
                }
                l = sc.nextLine();
            }
        }


        GameBoard gameBoard = new GameBoard(primaryStage, Math.abs(size), solution, stile);
        gameBoard.set_state(state);

        ScrollPane pane = new ScrollPane();
        pane.setContent(gameBoard);
        primaryStage.setScene(new Scene(pane, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT));
    }
}
