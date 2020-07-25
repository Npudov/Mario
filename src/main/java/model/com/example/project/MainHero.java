package model.com.example.project;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.com.example.project.Game;

public class MainHero extends Pane {
    Rectangle rectangle;
    private static int gravity;
    private static double yPreviousPosition;
    private static double GROUND_LEVEL = 408.0;
    private static double JUMP_DELTA = 23.0;
    private static double START_DELTAX = 20.0;
    public MainHero() {
        yPreviousPosition = -1;
        setGravity(0);
        rectangle = new Rectangle(50, 100);
        Image character = new Image(getClass().getResourceAsStream("/character.png")); //иконка персонажа
        //Image character = new Image("https://i.stack.imgur.com/Zxfbi.png");
        rectangle.setFill(new ImagePattern(character));
        setTranslateX(START_DELTAX);
        setTranslateY(GROUND_LEVEL);
        getChildren().addAll(rectangle);
    }

    public static int getGravity() {
        return gravity;
    }

    public static void setGravity(int gravity) {
        MainHero.gravity = gravity;
    }

    public void collision() {
        for (Barrier bar : Game.barriers) {
            if (getBoundsInParent().intersects(bar.getBoundsInParent())) {
                Game.setGameOver(true);
            }
        }
    }

    public void moveForward() {
        //System.out.println(getBoundsInParent());
        //rectangle.setTranslateX(rectangle.getTranslateX() + 1);
        setTranslateX(getTranslateX() + 4);

        if (!Game.isIsJump() && (getTranslateY() < GROUND_LEVEL)) { //проверка падения вниз при отсутсвии опоры
            double coordinateY = getTranslateY() + 5;
            setTranslateY(coordinateY);
            for (Brick brick : Game.bricks) {
                if (getBoundsInParent().intersects(brick.getBoundsInParent())) {
                    coordinateY = brick.getTranslateY() - this.rectangle.getHeight() - 1;
                    Game.setIsJump(false); //стоим на опоре - можем прыгать
                    //System.out.println("brick=" + brick.getTranslateY());
                    //System.out.println("brick.height=" + brick.height);
                    //System.out.println("this.rectangle.getHeight()=" + this.rectangle.getHeight());
                    break;
                }
            }
            if (getTranslateY() != coordinateY) {
                setTranslateY(coordinateY);
            }
        }

        if (getTranslateX() > Game.getFinishX()) {
            Game.setLevelComplete(true);
        }
        collision();
    }

    public void moveJump() {
        if (yPreviousPosition == -1) {
            yPreviousPosition = this.getTranslateY();
        }
        double coordinateY = this.getTranslateY() - JUMP_DELTA + getGravity();
        //setTranslateY(coordinateY);
        boolean isGround = false;
        for (Brick brick : Game.bricks) {
            if (getBoundsInParent().intersects(brick.getBoundsInParent())) {
                if (getGravity() <= JUMP_DELTA) { //летим вверх и столкнулись с кирпичом
                    coordinateY = brick.getTranslateY() + brick.height + 1;
                    setGravity(23);
                    System.out.println(coordinateY);
                    System.out.println(getGravity());
                    System.out.println("bricks translateY =" + brick.getTranslateY());
                    System.out.println("bricks height =" + brick.getHeight());
                }
                else {
                    coordinateY = brick.getTranslateY() - brick.height - 2;//this.rectangle.getHeight();
                }

                isGround = true;
                break;
            }
        }
        this.setTranslateY(coordinateY);
        System.out.println("gravity=" + getGravity());
        setGravity(getGravity() + 1);
        if (yPreviousPosition <= this.getTranslateY() || isGround) {
            setGravity(0);
            Game.setIsJump(false);
            yPreviousPosition = -1;
        }
    }

    public static double getStartDeltax() {
        return START_DELTAX;
    }
}
