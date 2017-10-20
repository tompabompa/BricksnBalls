package digitman.bricksnballs.domain;

/**
 *
 * @author thomas
 */
public class Ball {

    private final int ORIGINAL_BASE_SPEED = 7;
    private final int radius;
    private Point position;
    private Velocity velocity;
    private int baseYSpeed;

    public Ball(int radius, Point position) {
        this.radius = radius;
        this.position = position;
        velocity = new Velocity(0, 0);
        resetSpeed();
    }

    public boolean isStill() {
        return velocity.isStill();
    }

    public void increaseYSpeed() {
        baseYSpeed = (Math.abs(baseYSpeed) + 2) * baseYSpeed / Math.abs(baseYSpeed);
        if (!isStill()) {
            velocity = new Velocity(velocity.x, baseYSpeed);
        }
    }
    
    public final void resetSpeed() {
        baseYSpeed = ORIGINAL_BASE_SPEED;
    }
    
    public void setOff() {
        velocity = new Velocity(2, baseYSpeed);
    }

    public void bounce(Direction where, int... xVelo) {
        if (where == Direction.HORIZONTALLY) {
            velocity = new Velocity(-velocity.x, velocity.y);
        } else if (!isStill()) {
            int x = xVelo.length > 0 ? xVelo[0] : velocity.x;
            while (x == 0) {
                x = Long.valueOf(Math.round(Math.random() * 3.0)).intValue() - 1;
            }
            velocity = new Velocity(x, -velocity.y);
        }
    }

    public Point move() {
        position = position.move(velocity);
        return position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getRadius() {
        return radius;
    }

    public void stop() {
        velocity = new Velocity(0, 0);
    }

    public static enum Direction {
        HORIZONTALLY, VERTICALLY;
    }
}
