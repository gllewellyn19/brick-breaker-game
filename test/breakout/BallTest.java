package breakout;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for ball
 *
 * @author Grace Llewellyn
 */
class BallTest {

  private Ball myBall;

  @BeforeEach
  public void setup() {
    myBall = new Ball(Color.HOTPINK, Game.FRAME_SIZE / 2,
        Game.FRAME_SIZE - Paddle.PADDLE_HEIGHT - Ball.DEFAULT_BALL_RADIUS - 1,
        Ball.DEFAULT_BALL_RADIUS);
    myBall.initializeBall(new Group());
  }

  @Test
  void increaseBallSizeTest() {
    double previousBallSize = myBall.getBallShape().getRadius();
    myBall.increaseBallSize();

    assertEquals(previousBallSize + Ball.DEFAULT_BALL_SIZE_INC_AMOUNT,
        myBall.getBallShape().getRadius());
  }

  @Test
  void increaseBallSpeedTest() {
    double previousBallSpeed = myBall.getBallSpeed();
    myBall.increaseBallSpeed();

    assertEquals(previousBallSpeed + Ball.DEFAULT_BALL_SPEED_INC_AMOUNT,
        myBall.getBallSpeed());
  }

  @Test
  void defaultBallSpeedTest() {
    myBall.setBallSpeedDefault();
    assertEquals(Ball.DEFAULT_BALL_SPEED, myBall.getBallSpeed());
  }

  @Test
  void defaultBallSizeTest() {
    myBall.setBallSizeDefault();
    assertEquals(Ball.DEFAULT_BALL_RADIUS, myBall.getBallShape().getRadius());
  }

  @Test
  void resetBallTest() {
    myBall.resetBall(Game.FRAME_SIZE, Game.FRAME_SIZE, Paddle.PADDLE_HEIGHT);
    assertEquals(-1, myBall.getBallXDirection());
    assertEquals(-1, myBall.getBallYDirection());
    assertEquals(0, myBall.getBallSpeed());
    assertEquals(Ball.DEFAULT_BALL_RADIUS, myBall.getBallShape().getRadius());
    assertEquals(Game.FRAME_SIZE / 2.0, myBall.getBallShape().getCenterX());
    assertEquals(Game.FRAME_SIZE - Paddle.PADDLE_HEIGHT - myBall.getBallShape().getRadius() - 1,
        myBall.getBallShape().getCenterY());
  }

  @Test
  void setXDirectionTest() {
    myBall.setXDirection(-1);
    assertEquals(-1, myBall.getBallXDirection());
    myBall.setXDirection(2);
    assertEquals(-1, myBall.getBallXDirection());

  }
}