package model.com.example.project;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.com.example.project.Game;

public class MainHero extends Pane{
    Rectangle rectangle;
    private static int gravity;
    private static double yPreviousPosition;
    private static double GROUND = 408.0;
    private static double JUMP = 23.0;
    public MainHero() {
        yPreviousPosition = -1;
        setGravity(0);
        rectangle = new Rectangle(50, 100);
        Image character = new Image(getClass().getResourceAsStream("/character.png")); //иконка персонажа
        //Image character = new Image("https://i.stack.imgur.com/Zxfbi.png");
        rectangle.setFill(new ImagePattern(character));
        setTranslateX(20);
        setTranslateY(GROUND);
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
        MainHero.gravity = gravity;
    }

    public void collision() {
        for (Barrier bar : Game.barriers) {
            if (getBoundsInParent().intersects(bar.getBoundsInParent())) {
                Game.setGameOver(true);
                /*System.out.println(getBoundsInParent());
                System.out.println(getBoundsInLocal());
                System.out.println(getLayoutBounds());
                System.out.println(bar.getBoundsInParent());*/
                //System.exit(0);
            }
        }
    }

    public void moveForward() {
        //System.out.println(getBoundsInParent());
        //rectangle.setTranslateX(rectangle.getTranslateX() + 1);
        setTranslateX(getTranslateX() + 4);

        if (!Game.isIsJump() && (getTranslateY() < GROUND)) { //проверка падения вниз при отсутсвии опоры
            double coordinateY = getTranslateY() + 5;
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
                //System.out.println(coordinateY);
                setTranslateY(coordinateY);
            }
        }

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
        double coordinateY = this.getTranslateY() - JUMP + getGravity();
        boolean isGround = false;
        for (Brick brick : Game.bricks) {
            if (getBoundsInParent().intersects(brick.getBoundsInParent())) {
                if (getGravity() <= JUMP) { //летим вверх и столкнулись с кирпичом
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
        //System.out.println(coordinateY);
        System.out.println("gravity=" + getGravity());
        setGravity(getGravity() + 1);
        if (yPreviousPosition <= this.getTranslateY() || isGround) {
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
