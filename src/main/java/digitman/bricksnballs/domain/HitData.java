package digitman.bricksnballs.domain;

/**
 *
 * @author thomas
 */
public class HitData {

    private final Brick brick;
    public final boolean sideways;

    public HitData(Brick brick, boolean sideways) {
        this.brick = brick  ;
        this.sideways = sideways;
    }
    
    public boolean isHit() {
        return brick != null;
    }

    public Brick getBrick() {
        return brick;
    }
}
