package view.com.example.project;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.Arrays;

import static java.lang.Math.abs;

public class Game extends Application {
    private static MediaPlayer mediaPlayer;
    private static Scene sceneGame;
    public static ArrayList<Barrier> barriers = new ArrayList<>(); //список препятствий
    public static ArrayList<Brick> bricks = new ArrayList<>();
    //private static char[][] array = {{'O', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'B', 'O', 'O', 'B', 'O', 'O', 'O', 'B', 'B', 'O', 'O', 'O', 'O', 'O', 'O', 'B', 'O', 'O', 'F'},
    //                                {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'G', 'O', 'O', 'G', 'G', 'G', 'O', 'O', 'G', 'G', 'G', 'G', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'G','O', 'O', 'O', 'G', 'G', 'O', 'O', 'G', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}};

    private ArrayList<String>  levelOneBarriers = new ArrayList(Arrays
            .asList("OOOOBOOOBOOOBOOOBOOOOOOOBOOOOOOOOBOOOBOOBOOOBBOOOOOOBOOOF",
                    "OOOOOOOOOOOOOOOOOOOGOOGGGOOGGGGOOOOOOOOOOGOOOGGOOGOOOOOOO"));
    private ArrayList<String>  levelTwoBarriers = new ArrayList(Arrays
            .asList("OOOOBOOOOOOOBOOOBOOOOOOOBOOOBOOOOOOOOOOOBOOOOBOOOOBOOOOOOF",
                    "OOOOOOOOGOOOOOOOOOOOOOOOOOOGGGOOOOOOOOOOOGOGOGGOOOOOOOOOOO"));
    private static ArrayList<String> arrays = new ArrayList<>();
    private static int deltaX = 0;
    private AnimationTimer animation;
    private static boolean isJump;
    private static int WIDTH = 900;
    private static int HEIGHT = 600;
    private static int score = 0;
    private static int level;
    private static boolean gameOver = false;
    private static boolean levelComplete = false;
    private static double finishX = 0; //400 + array[0].length * 50 + MainHero.getStartDeltax();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //arrays.add("OOOOBOOOBOOOBOOOBOOOOOOOBOOOOOOOOBOOOBOOBOOOBBOOOOOOBOOOF");
        //arrays.add("OOOOOOOOOOOOOOOOOOOGOOGGGOOGGGGOOOOOOOOOOGOOOGGOOGOOOOOOO");
        setLevel(1);
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

                sceneGame = new Scene(rootGame, WIDTH, HEIGHT);
                System.out.println("primaryStage.setScene(sceneGame)");
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
                            gameOver = false;
                            final int finalScore = score;
                            Label label = new Label("GAME OVER! " + "YOUR SCORE: " + finalScore);
                            label.setFont(Font.font(50));
                            label.setTextFill(Color.RED);
                            Button btnOk = new Button("OK!");
                            btnOk.setFont(Font.font(50));
                            StackPane.setAlignment(label, Pos.TOP_CENTER);
                            StackPane.setMargin(label, new Insets(10, 0, 0, 0));

                            StackPane rootGameOver = new StackPane(label, btnOk);

                            Scene sceneGameOver = new Scene(rootGameOver, WIDTH, HEIGHT);
                            System.out.println("primaryStage.setScene(sceneGameOver)");
                            primaryStage.setScene(sceneGameOver);
                            btnOk.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    score = 0;
                                    primaryStage.setScene(scene);
                                }
                            });

                            //primaryStage.show();
                        }

                        if (levelComplete) {
                            animation.stop();
                            levelComplete = false;
                            final int finalScore = score;
                            Label text = new Label("LEVEL COMPLETE! " + "YOUR SCORE: " + finalScore);
                            text.setFont(Font.font(30));
                            text.setTextFill(Color.GREEN);

                            Button btnOk = new Button("OK!");
                            btnOk.setFont(Font.font(50));
                            StackPane.setAlignment(text, Pos.TOP_CENTER);
                            StackPane.setMargin(text, new Insets(10, 0, 0, 0));

                            StackPane rootLevelComplete = new StackPane(text, btnOk);

                            Scene sceneLevelComplete = new Scene(rootLevelComplete, WIDTH, HEIGHT);
                            primaryStage.setScene(sceneLevelComplete);
                            btnOk.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    score = 0;
                                    primaryStage.setScene(scene);
                                    if (level == 2) {
                                        setLevel(1);
                                    }
                                    else {
                                        setLevel(2);
                                    }
                                }
                            });
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



        System.out.println("primaryStage.setScene(scene)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setLevel(int level) {
        Game.level = level;
        switch (level) {
            case 1:
                arrays.addAll(levelOneBarriers);
                finishX = 400 + levelOneBarriers.get(0).length() * 50 + MainHero.getStartDeltax();
                break;
            case 2:
                arrays.addAll(levelTwoBarriers);
                finishX = 400 + levelTwoBarriers.get(0).length() * 50 + MainHero.getStartDeltax();
                break;
            default:
                throw new IllegalArgumentException("Invalid level number");
        }
    }

    /*private void _addBarriers(Pane paneCharacter) {
        for (int j=0; j < array.length; j++) {
            deltaX = 0;
            for (int i = 0; i < array[0].length; i++) {
                double pointLevelY = 0;
                //задаем координату Y для соответствующего уровня
                if (j == 0) {
                    pointLevelY = 408;
                } else {
                    pointLevelY = 308;
                }
                if (array[j][i] == 'B') {
                    Barrier barrier = new Barrier(50);
                    barriers.add(barrier);
                    barrier.setTranslateX(400 + deltaX);
                    barrier.setTranslateY(pointLevelY + 50);
                    paneCharacter.getChildren().add(barrier);
                }
                if (array[j][i] == 'G') {
                    Brick brick = new Brick(30);
                    bricks.add(brick);
                    brick.setTranslateX(400 + deltaX);
                    brick.setTranslateY(pointLevelY + 30);
                    paneCharacter.getChildren().add(brick);
                }
                deltaX += 50;
            }
        }
    }*/

    private void addBarriers(Pane paneCharacter) {
        for (int i = 0; i < arrays.size(); i++) {
            deltaX = 0;
            char[] chars = arrays.get(i).toCharArray();
            for (char element: chars) {
                double pointLevelY = 0;
                //задаем координату Y для соответствующего уровня
                if (i == 0) {
                    pointLevelY = 408;
                } else {
                    pointLevelY = 308;
                }
                if (element == 'B') {
                    Barrier barrier = new Barrier(50);
                    barriers.add(barrier);
                    barrier.setTranslateX(400 + deltaX);
                    barrier.setTranslateY(pointLevelY + 50);
                    paneCharacter.getChildren().add(barrier);
                }

                if (element == 'G') {
                    Brick brick = new Brick(30);
                    bricks.add(brick);
                    brick.setTranslateX(400 + deltaX);
                    brick.setTranslateY(pointLevelY + 30);
                    paneCharacter.getChildren().add(brick);
                }
                deltaX += 50;
            }
        }
    }

    private static void music() {
        String file = "src/main/resources/2-running-about-hurry.mp3";
        Media sound = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(Timeline.INDEFINITE);
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
