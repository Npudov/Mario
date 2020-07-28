package view.com.example.project;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class CoinAnimation extends Transition {
    public ImageView imageView;
    private final int count = 6; //количество картинок в спрайте
    private final int height;
    private final int width;

    public CoinAnimation () {
        Image image = new Image(getClass().getResourceAsStream("/coin_10.png"));
        this.imageView = new ImageView(image);
        this.height = 75;
        this.width = 50;
        setCycleDuration(Duration.millis(1000));
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.EASE_BOTH);
    }
    @Override
    protected void interpolate(double v) {
        int index = Math.min((int) Math.floor(v * count), count - 1);
        int x = index * width;
        final int y = 0;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
}