package digitman.bricksnballs.gui;

import digitman.bricksnballs.domain.Gameboard;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author thomas
 */
public class GameRunner {

    private final Dimension virtualDim = new Dimension(1000, 1000);
    private final Gameboard gameboard;
    long time;
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long currentNanoTime) {
            if (currentNanoTime > time + 10000000) {
                time = currentNanoTime;
                gameboard.refresh();
            }
        }
    };
    private final Scene scene;

    public GameRunner(Scene scene, Canvas canvas) {
        List<String> inputKeys = new ArrayList<>();
        gameboard = new Gameboard(virtualDim, canvas, inputKeys);
        this.scene = scene;
        scene.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            if (!inputKeys.contains(code)) {
                inputKeys.add(code);
                if ("ESCAPE".equals(code)) {
                    System.exit(0);
                }
            }
        });
        scene.setOnKeyReleased((KeyEvent e) -> {
            inputKeys.remove(e.getCode().toString());
        });
    }

    public void run() {
        time = System.nanoTime();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
