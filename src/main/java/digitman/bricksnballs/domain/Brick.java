package digitman.bricksnballs.domain;

import java.awt.Dimension;

/**
 *
 * @author thomas
 */
public class Brick {

    private final Dimension size;
    private final Point position;

    public Brick(Dimension size, Point position) {
        this.size = size;
        this.position = position;
    }

    public Dimension getSize() {
        return size;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isCovering(Point point) {
        return point.x >= position.x && point.x <= position.x + size.width
                && point.y >= position.y && point.y <= position.y + size.height;
    }
}
