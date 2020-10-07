package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * This class is responsible for the ball used in the break out game. It handles the shape, size, speed,
 * color, and direction of the ball, as well as how these values should change from different power ups and cheat
 * keys. This class is connected to other classes. For example, a new Ball is created from this class within
 * the Level class, so that each level has a certain ball. Lastly, the collisions class which handles collisions
 * between objects on the screen works with this class as the Ball object needs
 * to update its x and y direction as it bounces off walls/objects in a certain way.
 * For initializing the ball, it assumes that a root
 * will be inputted so that the ball can actually be displayed on the game interface. Also, because the game
 * class handles all of the cheat keys, it interacts with this ball class by calling certain methods. In addition,
 * some parameters of this Ball are assumed to be the default values specified in the class such as the starting
 * radius and speed. To use this class, we can create an instance of the Ball object, like we do in
 * each level class, where we specify the color of the ball, the starting coordinates, and the radius of the ball.
 * To have the ball actually show up on the game, we need to call the initializeBall method. Lastly, when
 * we want to change some parameters of the ball for a power up or certain cheat key, we can call the specific method
 * from this class. In addition, as the ball bounces around and collides with objects on the screen, its
 * x and y direction need to change.
 */

public class Ball {

  public static final int DEFAULT_BALL_RADIUS = 5;
  public static final int DEFAULT_BALL_SPEED = 100;
  public static final int DEFAULT_BALL_SIZE_INC_AMOUNT = 5;
  public static final int DEFAULT_BALL_SPEED_INC_AMOUNT = 30;

  private Circle ballShape;
  private int ballSpeed;
  private final int xLocBall;
  private final int yLocBall;
  private final int radius;
  private final Paint ballColor;
  private int xDirection = -1;
  private int yDirection = -1;

  public Ball(Paint c, int x, int y, int radius) {
    ballColor = c;
    xLocBall = x;
    yLocBall = y;
    this.radius = radius;
  }

  /*
  initializes the Ball object to have the default values and creates the actual ball shape and adds
  it to the root
   */
  void initializeBall(Group root) {
    ballSpeed = DEFAULT_BALL_SPEED;
    ballShape = new Circle(xLocBall, yLocBall, radius);
    ballShape.setFill(ballColor);
    ballShape.setId("ball");
    root.getChildren().add(ballShape);
  }


  protected void increaseBallSize() {
    double newBallRadius = ballShape.getRadius() + DEFAULT_BALL_SIZE_INC_AMOUNT;
    if (newBallRadius > Game.FRAME_SIZE / Game.FRACTION_MAX_BALL_SIZE) {
      newBallRadius = Game.FRAME_SIZE / Game.FRACTION_MAX_BALL_SIZE;
    }
    ballShape.setRadius(newBallRadius);
  }

  /*
   * Resets the ball position and size
   */
  protected void resetBall(double frameHeight, double frameWidth, double paddleHeight) {
    ballShape.setCenterX(frameWidth / 2.0);
    ballShape.setCenterY(frameHeight - paddleHeight - ballShape.getRadius() - 1);
    ballShape.setRadius(DEFAULT_BALL_RADIUS);
    resetBallSpeed();
    xDirection = -1;
    yDirection = -1;
  }

  protected Circle getBallShape() {
    return ballShape;
  }

  protected int getBallSpeed() {
    return ballSpeed;
  }

  protected int getBallXDirection() {
    return xDirection;
  }

  protected int getBallYDirection() {
    return yDirection;
  }

  protected void resetBallSpeed() {
    this.ballSpeed = 0;
  }

  protected void setBallSpeedDefault() {
    this.ballSpeed = DEFAULT_BALL_SPEED;
  }

  protected void setBallSizeDefault() {
    ballShape.setRadius(DEFAULT_BALL_RADIUS);
  }

  protected void increaseBallSpeed() {
    this.ballSpeed = this.ballSpeed + DEFAULT_BALL_SPEED_INC_AMOUNT;
  }

  protected void setXDirection(int newDir) {
    if (checkDirectionValid(newDir)) {
      xDirection = newDir;
    }
  }

  protected void setYDirection(int newDir) {
    if (checkDirectionValid(newDir)) {
      yDirection = newDir;
    }
  }

  protected void switchXDirection() {
    xDirection *= -1;
  }

  protected void switchYDirection() {
    yDirection *= -1;
  }

  /*
   * Make sure direction is only 1 or negative 1
   */
  private boolean checkDirectionValid(int newDir) {
    return newDir == -1 || newDir == 1;
  }

}
