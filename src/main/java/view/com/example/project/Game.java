package view.com.example.project;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.com.example.project.Barrier;
import model.com.example.project.Brick;
import model.com.example.project.MainHero;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Game extends Application {
    private static MediaPlayer mediaPlayer;
    public static ArrayList<Barrier> barriers = new ArrayList<>(); //список препятствий
    public static ArrayList<Brick> bricks = new ArrayList<>();
    private static char[][] array = {{'O', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'B', 'O', 'O', 'F'},
                                    {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'G', 'G', 'G', 'O', 'O', 'G', 'O', 'O', 'G', 'G', 'G', 'O', 'O', 'G', 'G', 'G', 'G', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'G',}};
    private static int deltaX = 0;
    private AnimationTimer animation;
    private static boolean isJump;
    private static int WIDTH = 900;
    private static int HEIGHT = 600;
    private static int score = 0;
    private static boolean gameOver = false;
    private static boolean levelComplete = false;
    private static double finishX = 400 + array[0].length * 50 + MainHero.getStartDeltax();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        music();
        Scene scene;
        primaryStage.setTitle("Mario");
        //primaryStage.setWidth(900);
        //primaryStage.setHeight(600);

        Pane root = new Pane();
        Button btnStart = new Button();
        Button btnQuit = new Button();
        btnStart.setText("Start game");
        btnQuit.setText("Quit game");
        btnStart.setFont(Font.font(20));
        btnQuit.setFont(Font.font(15));

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(btnStart, btnQuit);
        vBox.setAlignment(Pos.CENTER);

        Image image = new Image(getClass().getResourceAsStream("/Mario.png")); //фон главного меню
        ImageView img = new ImageView(image);
        img.setFitWidth(900);
        img.setFitHeight(600);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(img, vBox);

        root.getChildren().add(stackPane);

        scene = new Scene(root, WIDTH, HEIGHT);

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Pane rootGame = new Pane();
                Pane paneCharacter = new Pane();
                MainHero mainHero = new MainHero();



                Image background = new Image(getClass().getResourceAsStream("/background.png")); //фон
                //Image background = new Image("https://i.stack.imgur.com/WHu9Z.png");
                ImageView imgBackground = new ImageView(background);
                imgBackground.setFitWidth(WIDTH);
                imgBackground.setFitHeight(HEIGHT);

                Image supportingBackground = new Image(getClass().getResourceAsStream("/background.png"));
                ImageView imgSupportingBackground = new ImageView(supportingBackground);
                imgSupportingBackground.setFitWidth(WIDTH);
                imgSupportingBackground.setFitHeight(HEIGHT);
                imgSupportingBackground.setX(WIDTH);
                imgSupportingBackground.setY(0);

                //добавляем на сцену препятствия
                addBarriers(paneCharacter);

                paneCharacter.getChildren().add(mainHero);
                rootGame.getChildren().addAll(imgBackground, imgSupportingBackground, paneCharacter);

                Scene sceneGame = new Scene(rootGame, WIDTH, HEIGHT);
                primaryStage.setScene(sceneGame);
                primaryStage.show();

                animation = new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        score++;
                        mainHero.moveForward();
                        if (isJump) {
                            mainHero.moveJump();
                        }
                        mainHero.translateXProperty().addListener((observableValue, oldValue, newValue) -> {
                            int dislocation = newValue.intValue();
                            if (dislocation > 450) {
                                rootGame.setLayoutX(-(dislocation - 450));
                            }
                        });
                        double d = abs(rootGame.getLayoutX());
                        if (d - WIDTH >= imgBackground.getLayoutX()) {
                            imgBackground.setLayoutX(imgBackground.getLayoutX() + WIDTH);
                            imgSupportingBackground.setLayoutX(imgSupportingBackground.getLayoutX() + WIDTH);
                        }
                        primaryStage.setTitle("Mario " + "Score: " + score);

                        if (gameOver) {
                            animation.stop();
                            final int finalScore = score;
                            BorderPane rootGameOver = new BorderPane();
                            Text text = new Text("GAME OVER! " + "YOUR SCORE: " + finalScore);
                            text.setFont(Font.font(30));
                            text.setFill(Color.RED);
                            rootGameOver.setCenter(text);
                            Scene sceneGameOver = new Scene(rootGameOver, WIDTH, HEIGHT);
                            primaryStage.setScene(sceneGameOver);
                        }

                        if (levelComplete) {
                            animation.stop();
                            final int finalScore = score;
                            BorderPane rootLevelComplete = new BorderPane();
                            Text text = new Text("LEVEL COMPLETE! " + "YOUR SCORE: " + finalScore);
                            text.setFont(Font.font(30));
                            text.setFill(Color.GREEN);
                            rootLevelComplete.setCenter(text);
                            Scene sceneLevelComplete = new Scene(rootLevelComplete, WIDTH, HEIGHT);
                            primaryStage.setScene(sceneLevelComplete);
                        }
                    }
                };
                animation.start();

                sceneGame.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode() == KeyCode.SPACE) {
                            isJump = true;
                        }
                    }
                });
            }
        });
        btnQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });




        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBarriers(Pane paneCharacter) {
        for (int i = 0; i < array[0].length; i++) {
            if (array[0][i] == 'B') {
                Barrier barrier = new Barrier(50);
                barriers.add(barrier);
                barrier.setTranslateX(400 + deltaX);
                barrier.setTranslateY(408 + 50);
                paneCharacter.getChildren().add(barrier);
            }
            if (array[1][i] == 'G') {
                Brick brick = new Brick(30);
                bricks.add(brick);
                brick.setTranslateX(400 + deltaX);
                brick.setTranslateY(308 + 30);
                paneCharacter.getChildren().add(brick);
            }
            deltaX += 50;
        }
    }

    private static void music() {
        String file = "src/main/resources/2-running-about-hurry.mp3";
        Media sound = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static boolean isIsJump() {
        return isJump;
    }

    public static void setIsJump(boolean isJump) {
        Game.isJump = isJump;
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void setGameOver(boolean gameOver) {
        Game.gameOver = gameOver;
    }

    public static double getFinishX() {
        return finishX;
    }

    public static boolean isLevelComplete() {
        return levelComplete;
    }

    public static void setLevelComplete(boolean levelComplete) {
        Game.levelComplete = levelComplete;
    }


}
