package view.com.example.project;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BaseAnimation extends Transition {
    public ImageView imageView;
    private final int count; //количество картинок в спрайте
    private final int height;
    private final int width;
    public BaseAnimation(int width, int height, int count, String fileName) {
        Image image = new Image(getClass().getResourceAsStream(fileName));
        this.imageView = new ImageView(image);
        this.width = width;
        this.height = height;
        this.count = count;
    }

    @Override
    protected void interpolate(double v) {
        int index = Math.min((int) Math.floor(v * count), count - 1);
        int x = index * width;
        final int y = 0;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }

    public int getCount() {
        return count;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
