package digitman.bricksnballs.domain;

/**
 *
 * @author thomas
 */
public class Velocity {

    public final int x;
    public final int y;

    public Velocity(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean isStill() {
        return x == 0 && y == 0;
    }
}
