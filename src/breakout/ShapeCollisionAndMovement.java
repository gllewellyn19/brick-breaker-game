package breakout;

import breakout.Bricks.Brick;
import breakout.Levels.Level;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The purpose of this class is to handle all of the collisions and shape movements in our game, whether it is between the
 * ball and the brick, or the ball and the paddle. This class assumes that the level, root, and scene are inputted when creating a
 * version of the collision for each class. This class heavily relies on the elapsed time as it updates
 * the positions of the ball and bricks as time passes in the game. In addition, as certain collisions occur,
 * this class ensures that the objects react accordingly. This class is dependent on the Level class because it
 * takes in the current level to have access to the current level's ball and paddle when looking at the
 * different collisions. This class is responsible for making sure the ball bounces correctly off of items,
 * and also that each brick is registering when it is hit and is removed from the screen
 * when necessary. As a result, this class is also related to the Brick class. To use this class, we can create
 * an instance of Collision, like we do in each level, that takes in the level, root, and scene. As the different
 * interactions are occurring in the updateShapes method, the objects on the screen will move around
 * accordingly.
 */

public class ShapeCollisionAndMovement {

  private final Level currentLevel;
  private final Circle ballShape;
  private final Rectangle paddleShape;
  private final Group root;
  private final Scene myScene;

  public ShapeCollisionAndMovement(Level currentLevel, Group root, Scene myScene) {
    this.ballShape = currentLevel.getBallShape();
    this.paddleShape = currentLevel.getPaddleShape();
    this.currentLevel = currentLevel;
    this.root = root;
    this.myScene = myScene;
  }

  /*
   * Change properties of shapes in small ways to animate them over time. Returns true if the game
   * needs to be reset
   */
  protected boolean updateShapes(double elapsedTime, boolean isPaused) {
    checkBallPaddleCollision();
    checkAllBricksCollision(elapsedTime);
    currentLevel.doSpecialFeature(elapsedTime, isPaused);

    return checkBallWallCollision(ballShape.getCenterX(), ballShape.getCenterY(), isPaused, elapsedTime);
  }

  /*
   * Checks to see if the x or y is out of bounds. Only updates the balls shapes if isPaused is false
   * or ball in bounds. Returns true if the ball goes out of bounds at the bottom and the game needs
   * to be reset (means isPaused is false and didn't update movement because ball went out bottom)
   */
  private boolean checkBallWallCollision(double x, double y, boolean isPaused, double elapsedTime) {
    if (checkCanMoveBallAndWallCollisions(x, y, isPaused)) {
      ballShape.setCenterX(currentLevel.getBall().getNewX(elapsedTime));
      ballShape.setCenterY(currentLevel.getBall().getNewY(elapsedTime));
      return false;
    }
    return !isPaused;
  }

  /*
   * Changes the direction of the ball if needed and returns true if the ball can move in the next
   * step
   */
  private boolean checkCanMoveBallAndWallCollisions(double x, double y, boolean isPaused) {
    checkIfXNeedsToBounce(x);
    return checkYInBounds(y) && !isPaused;
  }

  /*
   * Switches the y direction if the ball hits off the top (returns true) and resets the game
   * and decreases the lives by one if the ball goes off the screen (returns false)
   */
  private boolean checkYInBounds(double y) {
    if (y - ballShape.getRadius() <= 0) {
      currentLevel.getBall().switchYDirection();
    }
    if (y - (2 * ballShape.getRadius()) >= myScene.getHeight()) {
      currentLevel.decreaseLivesByOne();
      return false;
    }
    return true;
  }

  /*
   * Switches the x direction if the ball hits off one of the sides of the screen (hard codes
   * values to avoid ball getting "stuck")
   */
  private void checkIfXNeedsToBounce(double x) {
    Ball b = currentLevel.getBall();
    if (x - ballShape.getRadius() <= 0) {
      b.setXDirection(1);
    } else if (x + ballShape.getRadius() >= myScene.getWidth()) {
      b.setXDirection(-1);
    }
  }

  /*
   * Checks if the falling brick hits the paddle which means to do the power up of that brick
   */
  private boolean checkBrickPaddleCollision(Brick b) {
    if (b.getBrickShape().getBoundsInParent().intersects(paddleShape.getBoundsInParent())) {
      b.destroyBrickWhenPaddleHits(currentLevel);
      return true;
    }
    return false;
  }

  /*
   * Determine if ball hit paddle and needs to bounce back
   */
  private void checkBallPaddleCollision() {
    if (ballShape.getBoundsInParent().intersects(paddleShape.getBoundsInParent())) {
      determineIfSwitchXOrYFromBallRectangleHit(paddleShape, currentLevel.getBall());
    }
  }

  /*
   * Goes through all the bricks of the current level to see if there was a collision with any of
   * the bricks (with the paddle or ball). Removes the brick if necessary
   */
  private void checkAllBricksCollision(double elapsedTime) {
    List<Brick> bricks = currentLevel.getBricks();
    for (int i = 0; i < bricks.size(); i++) {
      /* Only continues dropping the current brick if it wasn't removed when the ball hit it */
      if (!checkBallBrickCollision(bricks, bricks.get(i))) {
        continueDroppingBrickIfHasBeenHit(elapsedTime, bricks, bricks.get(i));
      }
    }
  }

  /*
   * If the brick hit, continues dropping it (if brick goes off screen/ hits paddle, removes and
   * returns true)
   */
  private void continueDroppingBrickIfHasBeenHit(double elapsedTime, List<Brick> bricks, Brick b) {
    if (b.getShouldDrop()) {
      if (b.getBrickShape().getY() > myScene.getHeight() || checkBrickPaddleCollision(b)) {
        removeBrick(b, bricks, root);
      }
      else {
        b.getBrickShape().setY(b.getBrickShape().getY() + Game.SPEED_DROPPING_BRICK *
            currentLevel.getBall().getBallSpeed() * elapsedTime);
      }
    }
  }

  /*
   * Checks if the brick has been hit by ball. If it is, updates the numbers of hits to brick and
   * changes the ball's direction. If the brick reached its max hits, removes the brick from the
   * list of shapes/ graphic (and returns true). Doesn't matter if the ball hits a brick falling
   */
  private boolean checkBallBrickCollision(List<Brick> bricks, Brick b) {
    if (ballShape.getBoundsInParent().intersects(b.getBrickShape().getBoundsInParent()) &&
        !b.getShouldDrop()) {
      currentLevel.getGameDisplay().increaseScore(b.getScoreIncreasePerHit());
      determineIfSwitchXOrYFromBallRectangleHit(b.getBrickShape(), currentLevel.getBall());
      return manageBrickHit(bricks, b);
    }
    return false;
  }

  /*
   * updateHitToBrick returns true if the brick has no more hits remaining, and removes the brick
   * from the scene (unless the brick is a power up, then updateHitToBrick marks the brick as
   * hit so that it falls). Function returns true if the brick is removed
   */
  private boolean manageBrickHit(List<Brick> bricks, Brick b) {
    if (b.updateHitToBrick(currentLevel) && !b.getIsPowerUp()) {
      removeBrick(b, bricks, root);
      return true;
    }
    return false;
  }

  /*
   * Checks to see what side of the rectangle (can be brick or paddle) that the ball collided with
   * and switches the x or y direction accordingly
   */
  public static void determineIfSwitchXOrYFromBallRectangleHit(Rectangle r, Ball b) {
    Circle bShape = b.getBallShape();
    if (checkInRange(r.getX(), r.getX() + r.getWidth(), bShape.getCenterX())) {
      b.switchYDirection();
    } else {
      b.switchXDirection();
      /* Offset new position by BALL_OFFSET_SIDE_HIT to avoid minor errors */
      if (bShape.getCenterX() < r.getX()) {
        bShape.setCenterX(bShape.getCenterX() - Game.BALL_OFFSET_SIDE_HIT);
      } else {
        bShape.setCenterX(bShape.getCenterX() + Game.BALL_OFFSET_SIDE_HIT);
      }
    }
  }

  /*
   * checks if the given target value is in an inclusive range indicated by start and end
   */
  private static boolean checkInRange(double start, double stop, double target) {
    return target >= start && target <= stop;
  }

  /*
   * Removes a brick from the list of bricks and the display
   */
  protected void removeBrick(Brick b, List<Brick> bricks, Group root) {
    if (!bricks.isEmpty()) {
      root.getChildren().remove(b.getBrickShape());
      bricks.remove(b);
    }
  }
}
