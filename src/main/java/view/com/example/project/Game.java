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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.com.example.project.Barrier;
import model.com.example.project.Character;

import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
    public static ArrayList<Barrier> barriers = new ArrayList<>(); //список препятствий
    AnimationTimer animation;
    private static boolean isJump;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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

       Image image = new Image("https://i.stack.imgur.com/gv4S8.jpg");
       ImageView img = new ImageView(image);
       img.setFitWidth(900);
       img.setFitHeight(600);

       StackPane stackPane = new StackPane();
       stackPane.getChildren().addAll(img, vBox);

       btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //root.getChildren().removeAll(stackPane);
                Pane rootGame = new Pane();
                Pane paneCharacter = new Pane();
                Character character = new Character();

                //Rectangle imgRectangleCharacter = new Rectangle(20, 408, 50, 100);

                Image background = new Image("https://i.stack.imgur.com/WHu9Z.png"); //фон
                ImageView imgBackground = new ImageView(background);
                imgBackground.setFitWidth(900);
                imgBackground.setFitHeight(600);


                Barrier barrier = new Barrier(50); //создание препятствия(пробное)
                barrier.setTranslateX(400);
                barrier.setTranslateY(408 + 50);
                barriers.add(barrier);
                //Image character = new Image("https://i.stack.imgur.com/Zxfbi.png");// иконка персонажа
                //ImageView imgCharacter = new ImageView(character);
                //imgCharacter.setFitWidth(50);
                //imgCharacter.setFitHeight(100);

                //imgRectangleCharacter.setFill(new ImagePattern(character));

                //imgCharacter.setX(20.0);
                //imgCharacter.setY(408.0);
                //paneCharacter.getChildren().addAll(imgRectangleCharacter);
                paneCharacter.getChildren().add(character);
                paneCharacter.getChildren().add(barrier);
                rootGame.getChildren().addAll(imgBackground, paneCharacter);


                /*StackPane gameStackPane = new StackPane();
                gameStackPane.getChildren().addAll(imgBackground, paneCharacter);*/
                //gameStackPane.setAlignment(imgCharacter, Pos.BOTTOM_LEFT);

                //rootGame.getChildren().add(gameStackPane);

                Scene sceneGame = new Scene(rootGame, 900, 600);
                primaryStage.setScene(sceneGame);


                if (character.getBoundsInParent().intersects(barrier.getBoundsInParent())) {
                    System.exit(0);
                }

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
                        character.moveForward();
                        if (isJump) {
                            character.moveJump();
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


        root.getChildren().add(stackPane);

        Scene scene = new Scene(root, 900, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static boolean isIsJump() {
        return isJump;
    }

    public static void setIsJump(boolean isJump) {
        Game.isJump = isJump;
    }
}
