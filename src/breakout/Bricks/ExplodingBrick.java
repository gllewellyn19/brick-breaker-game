package breakout.Bricks;

import breakout.Levels.Level;
import java.util.List;

/**
 * The purpose of this brick is to explode the bricks that are touching it when it is hit. It
 * depends on the Level class because it needs to access the bricks on the current level to see
 * which ones are intersecting with it. This class does not make any assumptions. This class also
 * doesn't increase the score for the bricks it removes. An example of how to use this class is to
 * pass it into the constructor for a level and keep track of its index in the special bricks list
 * of instantiated bricks and make your brick configuration file indicate
 * "numHits:indexInSpecialBricksList" at the spot it wants it in the configuration
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public class ExplodingBrick extends BrickWithSpecialAction {

  /*
   * Destroys all the bricks that are touching the current brick
   */
  @Override
  protected void doSpecialAction(Level currentLevel) {
    List<Brick> bricks = currentLevel.getBricks();
    for (int i=0; i < bricks.size(); i++) {
      Brick b = bricks.get(i);
      if (b.getBrickShape().getBoundsInParent().intersects(super.getBrickShape().getBoundsInParent())) {
        bricks.remove(b);
        super.getRoot().getChildren().remove(b.getBrickShape());
        i--;
      }
    }
  }
}
