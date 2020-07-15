package model.com.example.project;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import view.com.example.project.Game;

public class Barrier extends Pane {
    Rectangle rect;
    public int height;
    public Barrier (int height) {
        this.height = height;
        rect = new Rectangle(50, height);
        getChildren().addAll(rect);
    }
}
