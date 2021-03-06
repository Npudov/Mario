package view.com.example.project;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class CoinAnimation extends BaseAnimation {
    public CoinAnimation (int width, int height, int count, String fileName) {
        super(width, height, count, fileName);
        setCycleDuration(Duration.millis(500));
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.EASE_BOTH);
    }
}