package view.com.example.project;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import javax.swing.text.html.ImageView;

public class FireballAnimation extends BaseAnimation {
    public FireballAnimation(int width, int height, int count, String fileName) {
        super(width, height, count, fileName);
        setCycleDuration(Duration.millis(1000));
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.EASE_BOTH); /*замедление в начале, затем равномерное движение
                                                 и замедление в конце анимации*/
    }
}
