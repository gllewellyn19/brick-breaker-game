package breakout;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObstacleTest {

  private Obstacle myObstacle;


  @BeforeEach
  public void setup() {
    Rectangle obstacleShape = new Rectangle(0, 0, 20, 20);
    myObstacle = new Obstacle(obstacleShape, 1, 1, 20);
  }

  @Test
  void collisionBallTest() {
    Circle ballShape = new Circle(0, 0, 5);
    assertTrue(myObstacle.checkCollisionBall(ballShape));
  }

  @Test
  void moveTest() {
    double initialX = myObstacle.getObstacleShape().getX();
    double initialY = myObstacle.getObstacleShape().getY();
    Ball b = new Ball(Color.BLACK, 20, 20, 5);
    b.initializeBall(new Group());
    myObstacle.move(1.0, b);
    assertEquals(initialX + 20, myObstacle.getObstacleShape().getX());
    assertEquals(initialY + 20, myObstacle.getObstacleShape().getY());
  }
}