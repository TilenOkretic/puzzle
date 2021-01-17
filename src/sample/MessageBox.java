package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MessageBox {

    public MessageBox(String title, String message, String button_text) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(256);

        Label label = new Label(message);
        Button btn = new Button(button_text);
        btn.setOnAction((event) -> window.close());

        VBox box = new VBox(10);
        box.getChildren().addAll(label, btn);
        box.setAlignment(Pos.CENTER);

        Scene scene = new Scene(box, 100, 100);
        window.setScene(scene);
        window.showAndWait();
    }
}