package model.com.example.project;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Barrier extends Pane {
    Rectangle rect;
    public int height;
    public Barrier (int height) {
        this.height = height;
        rect = new Rectangle(50, height);
        Image image = new Image(getClass().getResourceAsStream("/fire.png"));
        rect.setFill(new ImagePattern(image));
        getChildren().addAll(rect);
    }
}
