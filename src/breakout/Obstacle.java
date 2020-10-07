package breakout;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The purpose of this class is to represent an obstacle on the screen that can be moving. This
 * class has no dependencies. This class assumes that the size of the frame is Game.FRAME_SIZE.
 * An example of how to use this class is to create an instance of it in a level class and then call
 * its move method everytime the game steps
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public class Obstacle {

  private final Rectangle obstacleShape;
  private int xDirection;
  private int yDirection;
  private final int speed;

  public Obstacle(Rectangle shape, int xDir, int yDir, int speed) {
    obstacleShape = shape;
    xDirection = xDir;
    yDirection = yDir;
    this.speed = speed;
  }

  protected Rectangle getObstacleShape() {
    return obstacleShape;
  }

  /*
   * Checks if the obstacle collides with the given ball shape
   */
  protected boolean checkCollisionBall(Circle ballShape) {
    return ballShape.getBoundsInParent().intersects(obstacleShape.getBoundsInParent());
  }

  /*
   * Moves the obstacle, making sure that it bounces off the edges
   */
  protected void move(double elapsedTime, Ball ball) {
    checkIfCollisionObstacle(ball);
    double potentialNewX = calculateNewX(elapsedTime);
    double potentialNewY = calculateNewY(elapsedTime);
    changeDirectionIfNeeded(potentialNewX, potentialNewY);
    obstacleShape.setX(calculateNewX(elapsedTime));
    obstacleShape.setY(calculateNewY(elapsedTime));
  }

  /*
   * Calculates the new x position of the obstacle for the given elapsed time
   */
  private double calculateNewX(double elapsedTime){
    return obstacleShape.getX() + xDirection * speed * elapsedTime;
  }

  /*
   * Calculates the new y position of the obstacle for the given elapsed time
   */
  private double calculateNewY(double elapsedTime){
    return obstacleShape.getY() + yDirection * speed * elapsedTime;
  }

  /*
   * Determines if the obstacle is at the left most or right most side and changes the direction
   * if needed
   */
  private void changeDirectionIfNeeded(double newX, double newY) {
    if (newX <= 0 || (newX + obstacleShape.getWidth()) >= Game.FRAME_SIZE) {
      xDirection*=-1;
    }
    else if (newY <= 0 || (newY + obstacleShape.getHeight()) >= Game.FRAME_SIZE) {
      yDirection *= -1;
    }
  }

  /*
   * Checks to see if there is a collision with the obstacle then bounces the ball correctly
   */
  private void checkIfCollisionObstacle(Ball ball) {
    if (checkCollisionBall(ball.getBallShape())) {
      Collision.determineIfSwitchXOrYFromBallRectangleHit(obstacleShape, ball);
    }
  }

}
