package breakout.Bricks;

import breakout.Levels.Level;
import javafx.scene.Group;
import javafx.scene.paint.ImagePattern;

/**
 * This is an abstract class that any brick that is a power up should extend. It makes sure at
 * the beginning when initializing the brick to also mark it as a power up brick and it checks
 * for when the brick has no hits remaining and then marks the set should drop variable to
 * true and the graphics in the Collision class deal with dropping the brick and checking to see
 * if it hits the paddle. BrickWithPowerUp assumes that the brick length and number of hits are
 * positive (no runtime error if are negative, but correct goals not achieved). BrickWithPowerUp
 * also assumes that the root passed in is the root that all the other graphics are being added to.
 * BrickWithPowerUp depends on the Level class because that is an argument in updateHitToBrick. An
 * example of how to use this class is the IncreaseBallSizeBrick that extends this class and
 * overrides the destoryBrick method of the Brick class
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public abstract class BrickWithPowerUp extends Brick {

  /*
   * Initializes the brick based on the given information
   */
  @Override
  public void initializeBrick(int hits, ImagePattern brickColor, Group root, int x, int y,
      int brickNumber, int scoreInc, int brickLength) {
    super.initializeBrick(hits, brickColor, root, x, y, brickNumber, scoreInc, brickLength);
    super.setIsPowerUpBrickTrue();
  }

  /**
   * Subtracts one from numHitsLeft for the brick and checks if the brick has no hits left If so,
   * returns true so that the brick can be deleted Updates the transparency of the brick based on
   * the hits left
   */
  @Override
  public boolean updateHitToBrick(Level currentLevel) {
    if (super.updateHitToBrick(currentLevel)) {
      super.setShouldDrop();
      super.getBrickShape().opacityProperty().set((double) 1/ super.getTotalHitsPossible());
      return true;
    }
    return false;
  }

}
