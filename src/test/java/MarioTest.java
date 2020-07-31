import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.com.example.project.Barrier;
import model.com.example.project.MainHero;
import org.junit.jupiter.api.Test;
import view.com.example.project.CoinAnimation;
import view.com.example.project.Game;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MarioTest {
    @Test
    void setGravity() {
        MainHero.setGravity(20);
        assertEquals(20, MainHero.getGravity());
        MainHero.setGravity(41);
        assertEquals(40, MainHero.getGravity());
    }
    /*@Test
    void collision() {

        MainHero mainHero = new MainHero();
        Barrier barrier = new Barrier(50);
        ArrayList<Barrier> barriers = new ArrayList<>();
        barriers.add(barrier);
        for (Barrier bar : barriers) {
            if (mainHero.getBoundsInParent().intersects(bar.getBoundsInParent())) {
                Game.setGameOver(true);
            }
        }
        assertFalse(Game.isGameOver());
    }*/
    @Test
    void getStartDeltax() {
        assertEquals(20.0, MainHero.getStartDeltax());
    }
    @Test
    void interpolate() {
        CoinAnimation coinAnimation = new CoinAnimation();
        double v = 0.0;
        int index = Math.min((int) Math.floor(v * coinAnimation.getCount()), coinAnimation.getCount() - 1);
        int x = index * coinAnimation.getWidth();
        assertEquals(0.0, index);
        assertEquals(0.0, x);
    }
    @Test
    void setIsJump() {

    }
}
