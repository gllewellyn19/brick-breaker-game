package breakout.Levels;

import breakout.Bricks.ExplodingBrick;
import breakout.Bricks.IncreaseBallSpeedBrick;
import breakout.Bricks.IncreasePaddleSizeBrick;
import breakout.Game;
import breakout.Levels.Level;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * The purpose of this class is to extend the level class and be functionally different than the
 * other levels by having a the paddle be able to go up and down. This class relies on the ball
 * and paddle classes, because it needs to position them a bit off the bottom of the screen to start
 * (to indicate that the user can move up and down). This class depends on some constants from the
 * game class. This class makes no assumptions if the files passed in are not valid and does not let
 * the user play the game if they are not valid. An example of how to use this class it to add it to
 * the list of Levels in the Game class, then when the user gets to that level, copy it from the
 * class, then initialize it to start it.
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public class LevelThree extends Level {

  public static final double START_Y_PADDLE = Game.FRAME_SIZE * .75;


  public LevelThree(){
    super(3, 3, Game.FRAME_SIZE, Game.FRAME_SIZE, Color.GRAY, Color.LIGHTPINK,
        "paddleImages/hikingBootPaddle.jpg", new ArrayList<>(),
        List.of(new IncreasePaddleSizeBrick(), new ExplodingBrick(), new IncreaseBallSpeedBrick()),
        "brickConfigurations/levelThreeBricks.txt",
        List.of("brickIce.jpg", "brickBranch.jpg", "brickWood.jpg", "brickRock.jpg"));
  }

  @Override
  void initializeLevelScreen() {

  }

  /*
   * Initializes the level and puts the paddle not on the bottom of the screen to start (so the user
   * knows they can move the paddle up and down
   */
  @Override
  public void initializeLevel(Group root, int currentScore) {
    super.initializeLevel(root, currentScore);
    super.getPaddleShape().setY(START_Y_PADDLE);
    super.getBallShape().setCenterY(START_Y_PADDLE - super.getBallShape().getRadius() - 1);
  }

  /*
   * Don't need for this function
   */
  @Override
  public void doSpecialFeature(double elapsedTime, boolean isPaused) { }

  /*
   * Checks if the paddle needs to be moved to the left or the right and also up or down.
   * Paddle cannot go higher than half of the screen
   */
  @Override
  public void checkPaddleMovement(KeyCode code, double frameHeight, double frameWidth) {
    super.checkPaddleMovement(code, frameHeight, frameWidth);
    if (code == KeyCode.UP) {
      double newY = super.getPaddleShape().getY() - super.getPaddle().getPaddleShift();
      checkPaddleOutOfYBoundsAndMove(newY, newY < frameHeight / 2, frameHeight / 2);
    } else if (code == KeyCode.DOWN) {
      double newY = super.getPaddleShape().getY() + super.getPaddle().getPaddleShift();
      checkPaddleOutOfYBoundsAndMove(newY, newY > frameHeight - super.getPaddleShape().getHeight(),
          frameHeight - super.getPaddleShape().getHeight());
    }
  }

  /*
   * Checks to see if out of bounds based on the given boolean. If out of bounds then sets the new
   * y of the paddle to the edge of the bounds (proceeds normally with newY if in bounds)
   */
  private void checkPaddleOutOfYBoundsAndMove(double newY, boolean outOfBounds, double edgeBounds) {
    if (outOfBounds) {
      super.getPaddleShape().setY(edgeBounds);
    } else {
      super.getPaddleShape().setY(newY);
    }
  }
}
