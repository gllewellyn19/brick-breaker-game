package breakout;

import java.util.List;

/**
 * The purpose of this brick is to end the game when it is hit. It depends on the Level class
 * because it needs access to all the bricks on the current level. This class also depends on the
 * Level class to catch that the game should be ended once all the bricks are gone. This class does
 * not make any assumptions. This class also doesn't increase the score for the bricks it removes.
 * An example of how to use this class is to pass it into the constructor for a level and keep track
 * of its index in the special bricks list of instantiated bricks and make your brick configuration
 * file indicate "numHits:indexInSpecialBricksList" at the spot it wants it in the configuration
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public class GameEndingBrick extends BrickWithSpecialAction{

  /*
   * Destroys all the bricks in the game so that the game is over
   */
  @Override
  protected void doSpecialAction(Level currentLevel) {
    List<Brick> bricks = currentLevel.getBricks();
    for (Brick b : bricks) {
      super.getRoot().getChildren().remove(b.getBrickShape());
    }
    bricks.clear();
  }

}
