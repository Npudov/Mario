package view.com.example.project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application {
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
        btnQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        root.getChildren().add(stackPane);

        Scene scene = new Scene(root, 900, 600);
        //scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
