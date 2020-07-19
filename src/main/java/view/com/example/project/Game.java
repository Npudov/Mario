package view.com.example.project;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.com.example.project.Barrier;
import model.com.example.project.Brick;
import model.com.example.project.MainHero;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Game extends Application {
    public static ArrayList<Barrier> barriers = new ArrayList<>(); //список препятствий
    public static ArrayList<Brick> bricks = new ArrayList<>();
    public char[][] array = {{'O', 'O', 'O', 'O', 'B', 'O', 'O', 'O', 'B'},
                            {'O', 'O', 'G', 'G', 'G', 'G', 'G', 'O', 'O'}};
    private static int deltaX = 0;
    private AnimationTimer animation;
    private static boolean isJump;
    private static int WIDTH = 900;
    private static int HEIGHT = 600;
    private static int score = 0;
    private static boolean gameOver = false;

    Label scoreLabel = new Label("Score: ");
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        //btnStart.setTranslateX(450.0);
        //btnStart.setTranslateY(250.0);
        //btnQuit.setTranslateX(450.0);
        //btnQuit.setTranslateY(300.0);

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
                //root.getChildren().removeAll(stackPane);
                Pane rootGame = new Pane();
                Pane paneCharacter = new Pane();
                MainHero mainHero = new MainHero();

                //Rectangle imgRectangleCharacter = new Rectangle(20, 408, 50, 100);

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

                /*for (int i = 0; i < 10; i++) {
                    Barrier barrier = new Barrier(50); //создание препятствия(пробное)
                    barrier.setTranslateX(i * 450 + 400);
                    barrier.setTranslateY(408 + 50);
                    barriers.add(barrier);
                    paneCharacter.getChildren().add(barrier);
                }*/
                /*Barrier barrier = new Barrier(50); //создание препятствия(пробное)
                barrier.setTranslateX(400);
                barrier.setTranslateY(408 + 50);
                barriers.add(barrier);*/
                //Image character = new Image("https://i.stack.imgur.com/Zxfbi.png");// иконка персонажа
                //ImageView imgCharacter = new ImageView(character);
                //imgCharacter.setFitWidth(50);
                //imgCharacter.setFitHeight(100);

                //imgRectangleCharacter.setFill(new ImagePattern(character));

                //imgCharacter.setX(20.0);
                //imgCharacter.setY(408.0);
                //paneCharacter.getChildren().addAll(imgRectangleCharacter);
                paneCharacter.getChildren().add(mainHero);
                //paneCharacter.getChildren().add(barrier);
                rootGame.getChildren().addAll(imgBackground, imgSupportingBackground, paneCharacter);


                /*StackPane gameStackPane = new StackPane();
                gameStackPane.getChildren().addAll(imgBackground, paneCharacter);*/
                //gameStackPane.setAlignment(imgCharacter, Pos.BOTTOM_LEFT);

                //rootGame.getChildren().add(gameStackPane);

                Scene sceneGame = new Scene(rootGame, WIDTH, HEIGHT);
                primaryStage.setScene(sceneGame);
                primaryStage.show();


                /*if (character.getBoundsInParent().intersects(barrier.getBoundsInParent())) {
                    gameOver = true;
                    //System.exit(0);
                }*/

                /*if (gameOver) {
                    BorderPane rootGameOver = new BorderPane();
                    Text text = new Text("GAME OVER! " + "YOUR SCORE: " + score);
                    text.setFont(Font.font(30));
                    text.setFill(Color.RED);
                    rootGameOver.setCenter(text);
                    Scene sceneGameOver = new Scene(rootGameOver, WIDTH, HEIGHT);
                    primaryStage.setScene(sceneGameOver);
                }*/

                /*KeyValue x = new KeyValue(imgRectangleCharacter.xProperty(), 100);
                //KeyValue y = new KeyValue(imgRectangleCharacter.yProperty(),0);

                KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), x);

                Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.setAutoReverse(false);
                timeline.getKeyFrames().addAll(keyFrame);
                timeline.play();*/


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
                                //if (abs(rootGame.getLayoutX()) % 900 == 0 && abs(rootGame.getLayoutX()) >= 900) {
                                //    System.out.println(rootGame.getLayoutX());
                                //}
                            }
                        });
                        //System.out.println(rootGame.getLayoutX());
                        //System.out.println(imgBackground.getLayoutX());
                        //System.out.println(imgBackground.xProperty());
                        double d = abs(rootGame.getLayoutX());
                        //if (abs(rootGame.getLayoutX()) % 900 == 0 && abs(rootGame.getLayoutX()) >= 900) {
                        if (d - WIDTH >= imgBackground.getLayoutX()) {
                            /*System.out.println("Условие");
                            System.out.println(d);
                            System.out.println(imgBackground.getLayoutX());
                            //rootGame.setLayoutX(abs(rootGame.getLayoutX()));*/
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

                            //primaryStage.setScene(scene);
                        }
                    }
                };
                animation.start();

                sceneGame.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    //int gravity = 0;
                    //AnimationTimer jump;
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode() == KeyCode.SPACE) {
                            //System.out.println("Мы здесь");
                            isJump = true;

                            //double yPreviousPosition = character.getTranslateY();
                            /*jump = new AnimationTimer() {
                                @Override
                                public void handle(long l) {
                                    character.moveForward();
                                    System.out.println("Мы в handle animation timer");
                                    character.setTranslateY(character.getTranslateY() - 40 + gravity);
                                    gravity += 1;
                                    if (yPreviousPosition <= character.getTranslateY()) {
                                        jump.stop();
                                        gravity = 0;
                                    }
                                }
                            };*/
                            //jump.start();
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

}
