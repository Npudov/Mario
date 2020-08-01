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

import static org.junit.jupiter.api.Assertions.*;

public class MarioTest {
    @Test
    void setGravity() {
        MainHero.setGravity(20);
        assertEquals(20, MainHero.getGravity());
        MainHero.setGravity(41);
        assertEquals(40, MainHero.getGravity());
    }
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
        Game.setIsJump(false);
        assertFalse(Game.isIsJump());
        Game.setIsJump(true);
        assertTrue(Game.isIsJump());
    }
    @Test
    void setGameOver() {
        Game.setGameOver(false);
        assertFalse(Game.isGameOver());
        Game.setGameOver(true);
        assertTrue(Game.isGameOver());
    }
    @Test
    void setLevelComplete() {
        Game.setLevelComplete(false);
        assertFalse(Game.isLevelComplete());
        Game.setLevelComplete(true);
        assertTrue(Game.isLevelComplete());
    }
}