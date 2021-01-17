package sample;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Util {

    public static int[][] resize_arr(int[][] arr, int size) {
        int[][] out = new int[size][size];
        for (int i = 0; i < out.length; i++) {
            for (int j = 0; j < out[i].length; j++) {
                try {
                    out[i][j] = arr[i][j];
                }catch (Exception e){
                    out[i][j] = 0;
                }
            }
        };

        return out;
    }
}
