package digitman.bricksnballs.domain;

import digitman.bricksnballs.domain.Velocity;

/**
 *
 * @author thomas
 */
public class Point {

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Point move(Velocity velocity) {
        return new Point(x + velocity.x, y + velocity.y);
    }
}
