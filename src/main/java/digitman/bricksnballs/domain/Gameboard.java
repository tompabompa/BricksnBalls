package digitman.bricksnballs.domain;

import java.awt.Dimension;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 *
 * @author thomas
 */
public class Gameboard {

    private Wall wall = new Wall();
    private final Ball ball;
    private final Dimension virtualDim;
    private final Bat bat;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final List<String> inputKeys;
    private int countdownBeforeReload = 0;
    private int score = 0;
    private int nbrOfBallsLeft = 3;

    public Gameboard(Dimension virtualDim, Canvas canvas, List<String> inputKeys) {
        this.virtualDim = virtualDim;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        this.inputKeys = inputKeys;
        bat = new Bat(new Dimension(virtualDim.width / 10, virtualDim.height / 50), new Point(virtualDim.width / 2, 10));
        ball = new Ball(bat.getSize().height, new Point(bat.getPosition().x, bat.getPosition().y + bat.getSize().height));
    }

    private Point getMappedPoint(Point virtual) {
        return new Point(virtual.x * toInt(canvas.getWidth()) / virtualDim.width, toInt(canvas.getHeight()) - virtual.y * toInt(canvas.getHeight()) / virtualDim.height);
    }

    private Dimension getMappedDimension(Dimension virtual) {
        return new Dimension(virtual.width * toInt(canvas.getWidth()) / virtualDim.width, virtual.height * toInt(canvas.getHeight()) / virtualDim.height);
    }

    private int toInt(double input) {
        return new Integer(Double.toString(input).replace(".0", ""));
    }

    public void refresh() {
        gc.setFill(Paint.valueOf(Color.GRAY.toString()));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawBat();
        drawWall();
        drawBall();
        drawScore();
        drawBallsLeft();
    }

    private void drawBat() {
        resolveBatVelocity();
        if (ball.isStill()) {
            ball.setPosition(new Point(bat.getPosition().x, ball.getPosition().y));
        }
        gc.setFill(Paint.valueOf(Color.RED.toString()));
        Dimension d = getMappedDimension(bat.getSize());
        Point p = getMappedPoint(bat.getPosition());
        gc.setLineWidth(d.height);
        gc.fillRect(p.x - d.width / 2, p.y, d.width, d.height);
    }

    private void resolveBatVelocity() {
        if (bat.move().x <= bat.getSize().width / 2) {
            bat.stop();
            bat.setPosition(new Point(bat.getSize().width / 2 + 1, bat.getPosition().y));
        } else if (bat.getPosition().x >= virtualDim.width - bat.getSize().width / 2) {
            bat.stop();
            bat.setPosition(new Point(virtualDim.width - bat.getSize().width / 2 - 1, bat.getPosition().y));
        } else if (inputKeys.contains("RIGHT")) {
            bat.moveRight();
        } else if (inputKeys.contains("LEFT")) {
            bat.moveLeft();
        } else {
            bat.stop();
        }
    }

    private void drawBall() {
        if (nbrOfBallsLeft > 0) {
            resolveBallVelocity();
            gc.setFill(Paint.valueOf(Color.WHITE.toString()));
            Point position = getMappedPoint(ball.getPosition());
            Dimension radius = getMappedDimension(new Dimension(ball.getRadius(), ball.getRadius()));
            gc.fillOval(position.x, position.y, radius.width, radius.height);
            HitData hitData = wall.getHitData(ball.getPosition());
            if (hitData.isHit()) {
                score += getScore(Achievement.BRICK_HIT);
                wall.remove(hitData.getBrick());
                ball.bounce(Ball.Direction.VERTICALLY);
                if (wall.isEmpty()) {
                    score += getScore(Achievement.WALL_DOWN);
                    countdownBeforeReload = 100;
                }
            }
            if (countdownBeforeReload > 0) {
                countdownBeforeReload--;
                if (countdownBeforeReload == 0) {
                    wall = new Wall();
                    ball.increaseYSpeed();
                    bat.shorten();
                }
            }
        }
    }

    private int getScore(Achievement a) {
        return a == Achievement.BRICK_HIT ? 20 : 500;
    }

    private void drawScore() {
        gc.setFill(Paint.valueOf(Color.BLACK.toString()));
        Point position = getMappedPoint(new Point(20, 950));
        gc.setFont(Font.font(20.0));
        gc.fillText(String.valueOf(score), position.x, position.y);
    }

    private void drawBallsLeft() {
        gc.setFill(Paint.valueOf(Color.LIGHTGRAY.toString()));
        Dimension radius = getMappedDimension(new Dimension(ball.getRadius(), ball.getRadius()));
        for (int i = 1; i < nbrOfBallsLeft; i++) {
            Point position = getMappedPoint(new Point(1000 - i * radius.width * 3, 950));
            gc.fillOval(position.x, position.y, radius.width, radius.height);
        }
    }

    private enum Achievement {
        BRICK_HIT, WALL_DOWN;
    }

    private void resolveBallVelocity() {
        if (inputKeys.contains("S") && ball.isStill() && nbrOfBallsLeft > 0) {
            ball.setOff();
        } else {
            Point position = ball.move();
            if (position.x < 1) {
                ball.bounce(Ball.Direction.HORIZONTALLY);
                ball.setPosition(new Point(1, position.y));
            } else if (position.x > virtualDim.width - ball.getRadius()) {
                ball.bounce(Ball.Direction.HORIZONTALLY);
                ball.setPosition(new Point(virtualDim.width - ball.getRadius(), position.y));
            } else if (position.y < bat.getSize().height + ball.getRadius() && Math.abs(position.x - bat.getPosition().x) < bat.getSize().width / 2) {
                ball.bounce(Ball.Direction.VERTICALLY, (position.x - bat.getPosition().x) / 12);
                ball.setPosition(new Point(position.x, bat.getSize().height + ball.getRadius()));
            } else if (position.y > virtualDim.height) {
                ball.bounce(Ball.Direction.VERTICALLY);
                ball.setPosition(new Point(position.x, virtualDim.height));
            } else if (position.y < 0) {
                ball.stop();
                nbrOfBallsLeft--;
            }
        }
    }

    private void drawWall() {
        gc.setFill(Paint.valueOf(Color.FIREBRICK.toString()));
        wall.getBricks().stream().forEach(brick -> {
            Dimension d = getMappedDimension(brick.getSize());
            Point p = getMappedPoint(brick.getPosition());
            gc.fillRect(p.x, p.y, d.width, d.height);
        });
    }
}
