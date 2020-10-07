package breakout.Bricks;

import breakout.Levels.Level;

/**
 * This is an abstract class that any brick that has a special action should extend. It overrides
 * updateHitToBrick to check for when the brick has no hits remaining and then calls its abstract
 * method doSpecialAction that is implemented by its children. BrickWithSpecialAction depends on the
 * Level class because that is an argument in updateHitToBrick. An example of how to use this class
 * is the Exploding Brick that extends this class and removes all bricks that touch it (can access
 * because has current level) when it is hit
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public abstract class BrickWithSpecialAction extends Brick {

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
