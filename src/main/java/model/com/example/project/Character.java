package model.com.example.project;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.com.example.project.Game;

public class Character extends Pane{
    Rectangle rectangle;
    private static int gravity;
    private static double yPreviousPosition;
    public Character () {
        yPreviousPosition = -1;
        setGravity(0);
        rectangle = new Rectangle(50, 100);
        Image character = new Image("https://i.stack.imgur.com/Zxfbi.png");//иконка персонажа
        rectangle.setFill(new ImagePattern(character));
        setTranslateX(20);
        setTranslateY(408);
        getChildren().addAll(rectangle);
        /*new AnimationTimer() {
            @Override
            public void handle(long now) {
                rectangle.setTranslateX(rectangle.getTranslateX() + 2);
                collision();
            }
        }.start();*/

    }

    public static int getGravity() {
        return gravity;
    }

    public static void setGravity(int gravity) {
        Character.gravity = gravity;
    }

    public void collision() {
        for (Barrier bar : Game.barriers) {
            if (getBoundsInParent().intersects(bar.getBoundsInParent())) {
                System.out.println(getBoundsInParent());
                System.out.println(getBoundsInLocal());
                System.out.println(getLayoutBounds());
                System.out.println(bar.getBoundsInParent());
                System.exit(0);
            }
        }
    }

    public void moveForward() {
        //System.out.println(getBoundsInParent());
        //rectangle.setTranslateX(rectangle.getTranslateX() + 1);
        setTranslateX(getTranslateX() + 2);
        //System.out.println(rectangle.getWidth());
        //rectangle.setX(rectangle.getX() + 1);
        //rectangle.setWidth(50);
        //rectangle.relocate(getLayoutX() + 1, getLayoutY());
        //rectangle.setTranslateX(0);
        //System.out.println(getBoundsInParent());
               collision();
    }

    public void moveJump() {
        if (yPreviousPosition == -1) {
            yPreviousPosition = this.getTranslateY();
        }
        this.setTranslateY(this.getTranslateY() - 20 + getGravity());
        setGravity(getGravity() + 1);
        if (yPreviousPosition <= this.getTranslateY()) {
            setGravity(0);
            Game.setIsJump(false);
            yPreviousPosition = -1;
        }
    }

    //Image character = new Image("https://i.stack.imgur.com/Zxfbi.png");// иконка персонажа
    //ImageView imgCharacter = new ImageView(character);
    //imgCharacter.setFitWidth(50);
    //imgCharacter.setFitHeight(100);

    //imgCharacter.setX(20.0);
    //imgCharacter.setY(408.0);
}
