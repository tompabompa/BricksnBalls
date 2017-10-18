package digitman.bricksnballs.domain;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thomas
 */
public class Wall {

    private final List<Brick> bricks = new ArrayList<>();

    public Wall() {
        for (int y = 900; y > 700; y -= 25) {
            for (int x = 8; x < 920; x += 99) {
                bricks.add(new Brick(new Dimension(94, 20), new Point(x, y)));
            }
        }
    }

    public List<Brick> getBricks() {
        return bricks;
    }
    
    public HitData getHitData(Point point) {
        return bricks.stream().filter(brick -> brick.isCovering(point)).map(brick
                -> new HitData(brick, true)).findFirst().orElse(new HitData(null, false));
    }
    
    public void remove(Brick brick) {
        bricks.remove(brick);
    }
    
    public boolean isEmpty() {
        return bricks.isEmpty();
    }
}
