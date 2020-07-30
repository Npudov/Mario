package model.com.example.project;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import view.com.example.project.CoinAnimation;
import view.com.example.project.Game;

import java.io.File;
import java.util.Iterator;

public class MainHero extends Pane {
    Rectangle rectangle;
    private static int gravity;
    private static double yPreviousPosition;
    private static double GROUND_LEVEL = 408.0;
    private static double JUMP_DELTA = 23.0;
    private static double START_DELTAX = 20.0;
    private static double MAX_VALUE_GRAVITY = 40.0;
    private static MediaPlayer mediaPlayerСoins;
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

        String file = "src/main/resources/soundCoins.mp3";
        Media sound = new Media(new File(file).toURI().toString());
        mediaPlayerСoins = new MediaPlayer(sound);
        mediaPlayerСoins.setStopTime(Duration.millis(1000));
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
        setTranslateX(getTranslateX() + 4);

        if (!Game.isIsJump() && (getTranslateY() < GROUND_LEVEL)) { //проверка падения вниз при отсутсвии опоры
            double coordinateY = getTranslateY() + 5;
            setTranslateY(coordinateY);
            for (Brick brick : Game.bricks) {
                if (getBoundsInParent().intersects(brick.getBoundsInParent())) {
                    coordinateY = brick.getTranslateY() - this.rectangle.getHeight() - 1;
                    Game.setIsJump(false); //стоим на опоре - можем прыгать
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
        intersectCoin();
    }

    public void moveJump() {
        if (yPreviousPosition == -1) {
            yPreviousPosition = this.getTranslateY();
        }
        double coordinateY = this.getTranslateY() - JUMP_DELTA + getGravity();
        this.setTranslateY(coordinateY);
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
                    coordinateY = brick.getTranslateY() - brick.height - 2;
                }

                isGround = true;
                System.out.println("isGround =" + isGround);
                break;
            }
        }
        //System.out.println("coordinateY =" + coordinateY);
        if (coordinateY > GROUND_LEVEL) {
            coordinateY = GROUND_LEVEL;
            isGround = true;
        }
        //System.out.println("coordinateY =" + coordinateY);
        this.setTranslateY(coordinateY);
        if (getGravity() < MAX_VALUE_GRAVITY) {
            setGravity(getGravity() + 1);
        }
        System.out.println("getGravity() =" + getGravity());
        //if (yPreviousPosition <= this.getTranslateY() || isGround) {
        if (isGround) {
            setGravity(0);
            Game.setIsJump(false);
            System.out.println("yPreviousPosition =" + yPreviousPosition);
            System.out.println("this.getTranslateY() =" + this.getTranslateY());
            System.out.println("Game.isIsJump() =" + Game.isIsJump());
            yPreviousPosition = -1;
        }

    }

    public void intersectCoin() { // проверка взятия монетки
        Iterator<CoinAnimation> coinIterator = Game.coins.iterator();
        while (coinIterator.hasNext()) {
            CoinAnimation nextCoin = coinIterator.next();
            if (getBoundsInParent().intersects(nextCoin.imageView.getBoundsInParent())) {
                soundCoin();
                Game.clearCoin(nextCoin.imageView.getId()); //удаляет монетку с определённым идентификатором
                coinIterator.remove();
            }
        }
    }

    public static void soundCoin() {
        mediaPlayerСoins.seek(Duration.ZERO); //устанавливаем указатель в начало записи
        mediaPlayerСoins.play();
    }

    public static double getStartDeltax() {
        return START_DELTAX;
    }
}
