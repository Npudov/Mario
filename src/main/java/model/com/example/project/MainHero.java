package model.com.example.project;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import view.com.example.project.CoinAnimation;
import view.com.example.project.FireballAnimation;
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
    private static MediaPlayer mediaPlayerFireballs;
    public MainHero() {
        yPreviousPosition = -1;
        setGravity(0);
        rectangle = new Rectangle(50, 100);
        Image character = new Image(getClass().getResourceAsStream("/character.png")); //иконка персонажа
        rectangle.setFill(new ImagePattern(character));
        setTranslateX(START_DELTAX);
        setTranslateY(GROUND_LEVEL);
        getChildren().addAll(rectangle);

        File fileCoin = new File(getClass().getClassLoader().getResource("soundCoins.mp3").getFile());
        Media soundCoin = new Media(fileCoin.toURI().toString());
        mediaPlayerСoins = new MediaPlayer(soundCoin);
        mediaPlayerСoins.setStopTime(Duration.millis(1000));

        File fileFire = new File(getClass().getClassLoader().getResource("fire2.wav").getFile());
        Media soundFire = new Media(fileFire.toURI().toString());
        mediaPlayerFireballs = new MediaPlayer(soundFire);
    }

    public static int getGravity() {
        return gravity;
    }

    public static void setGravity(int gravity) {
        if (gravity < MAX_VALUE_GRAVITY) {
            MainHero.gravity = gravity;
        }
        else {
            MainHero.gravity = (int) MAX_VALUE_GRAVITY;
        }
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
        intersectFireball();
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
                }
                else {
                    coordinateY = brick.getTranslateY() - brick.height - 2;
                }

                isGround = true;
                break;
            }
        }

        if (coordinateY > GROUND_LEVEL) {
            coordinateY = GROUND_LEVEL;
            isGround = true;
        }
        this.setTranslateY(coordinateY);
        setGravity(getGravity() + 1);
        if (isGround) {
            setGravity(0);
            Game.setIsJump(false);
            yPreviousPosition = -1;
        }

    }

    public void intersectCoin() { // проверка взятия монетки
        Iterator<CoinAnimation> coinIterator = Game.coins.iterator();
        while (coinIterator.hasNext()) {
            CoinAnimation nextCoin = coinIterator.next();
            if (getBoundsInParent().intersects(nextCoin.imageView.getBoundsInParent())) {
                soundEffects(mediaPlayerСoins);
                Game.clearCoin(nextCoin.imageView.getId()); //удаляет монетку с определённым идентификатором
                coinIterator.remove();
            }
        }
    }

    public void intersectFireball() { // проверка столкновения с файерболлом
        Iterator<FireballAnimation> fireballIterator = Game.fireballs.iterator();
        while (fireballIterator.hasNext()) {
            FireballAnimation nextFireball = fireballIterator.next();
            if (getBoundsInParent().intersects(nextFireball.imageView.getBoundsInParent())) {
                soundEffects(mediaPlayerFireballs);
                Game.setGameOver(true); //удаляет файерболл с определённым идентификатором
                fireballIterator.remove();
            }
        }
    }

    public void soundEffects(MediaPlayer mediaPlayer) {
        mediaPlayer.seek(Duration.ZERO); //устанавливаем указатель в начало записи
        mediaPlayer.play();
    }

    public static double getStartDeltax() {
        return START_DELTAX;
    }
}
