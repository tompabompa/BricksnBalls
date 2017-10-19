package digitman.bricksnballs.domain;

import java.awt.Dimension;

/**
 *
 * @author thomas
 */
public class Bat {

    private Dimension size;
    private Point position;
    private Velocity velocity = new Velocity(0, 0);
    private final int BASE_SPEED = 5;

    public Bat(Dimension size, Point position) {
        this.size = size;
        this.position = position;
    }

    public Dimension getSize() {
        return size;
    }

    public Point move() {
        position = position.move(velocity);
        return position;
    }

    public Point getPosition() {
        return position;
    }

    public void shorten() {
        size = new Dimension(size.width - 4, size.height);
    }
    
    public void setPosition(Point position) {
        this.position = position;
    }

    public void moveRight() {
        velocity = new Velocity(BASE_SPEED, 0);
    }

    public void moveLeft() {
        velocity = new Velocity(-BASE_SPEED, 0);
    }

    public void stop() {
        velocity = new Velocity(0, 0);
    }
}
