package breakout;

/**
 * The purpose of this class is to be the certain brick power up related to increasing the paddle size if
 * the brick is caught by the paddle. It is an extension of the BrickWithPowerUp class. It is dependent on the current
 * level (an extension of the level class) so that it can access that specific level's paddle. By accessing the paddle,
 * this class is also related to the Paddle class because it calls the increasePaddleSize() method found in the
 * Paddle class. This class overrides the destroyBrick method so that it calls the unique powerup when the
 * brick is destroyed. An example of how to use this class is to pass it into the constructor for a level
 * and keep track of its index in the special bricks list of instantiated bricks and make the brick
 * configuration file indicate "numHits:indexInSpecialBricksList" at the spot it wants it in the brick
 * configuration.
 */

public class IncreasePaddleSizeBrick extends BrickWithPowerUp {

  @Override
  public void destroyBrickWhenPaddleHits(Level currentLevel) {
    currentLevel.getPaddle().increasePaddleSize();
  }
}
