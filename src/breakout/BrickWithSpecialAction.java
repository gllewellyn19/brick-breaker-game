package breakout;

import javafx.scene.Group;
import javafx.scene.paint.ImagePattern;

/**
 * This is an abstract class that any brick that has a special action should extend. It overrides
 * updateHitToBrick to check for when the brick has no hits remaining and then calls its abstract
 * method doSpecialAction that is implemented by its children. BrickWithSpecialAction assumes that
 * the brick length and number of hits are positive (no runtime error if are negative, but correct
 * goals not achieved). BrickWithSpecialAction also assumes that the root passed in is the root that
 * all the other graphics are being added to. BrickWithSpecialAction depends on the Level class
 * because that is an argument in updateHitToBrick. An example of how to use this class is the
 * Exploding Brick that extends this class and removes all bricks that touch it (can access because
 * has current level) when it is hit
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public abstract class BrickWithSpecialAction extends Brick {

  /*
   * Initializes and creates a brick display with the given parameters
   */
  @Override
  protected void initializeBrick(int hits, ImagePattern brickColor, Group root, int x, int y,
      int brickNumber, int scoreInc, int brickLength) {
    super.initializeBrick(hits, brickColor, root, x, y, brickNumber, scoreInc, brickLength);
  }

  /*
   * Subtracts one from numHitsLeft for the brick and checks if the brick has no hits left If so,
   * returns true so that the brick can be deleted Updates the transparency of the brick based on
   * the hits left
   */
  @Override
  public boolean updateHitToBrick(Level currentLevel) {
    if (super.updateHitToBrick(currentLevel)) {
      doSpecialAction(currentLevel);
      return true;
    }
    return false;
  }

  protected abstract void doSpecialAction(Level currentLevel);
}
