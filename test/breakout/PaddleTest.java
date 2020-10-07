package breakout;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for paddle
 *
 * @author Grace Llewellyn
 */
class PaddleTest {

  private Paddle myPaddle;

  @BeforeEach
  public void setup() {
    myPaddle = new Paddle(Color.PLUM, "",
        Game.FRAME_SIZE / 2 - Paddle.DEFAULT_PADDLE_LENGTH / 2,
        Game.FRAME_SIZE - Paddle.PADDLE_HEIGHT, Game.FRAME_SIZE, Game.FRAME_SIZE);
    myPaddle.initializePaddle(new Group());
  }

  @Test
  void increasePaddleSizeTest() {
    double previousPaddleSize = myPaddle.getPaddleShape().getWidth();
    myPaddle.increasePaddleSize();

    assertEquals(previousPaddleSize + Paddle.DEFAULT_PADDLE_SIZE_INCREASE_AMT,
        myPaddle.getPaddleShape().getWidth());
  }

  @Test
  void increasePaddleShiftTest() {
    double previousPaddleShift = myPaddle.getPaddleShift();
    myPaddle.increasePaddleShift();

    assertEquals(previousPaddleShift + Paddle.DEFAULT_PADDLE_SHIFT_INCREASE_AMT,
        myPaddle.getPaddleShift());
  }

  @Test
  void paddleShiftDefaultTest() {
    myPaddle.setPaddleShiftDefault();
    assertEquals(myPaddle.getPaddleShift(), Paddle.PADDLE_SHIFT_AMOUNT);
  }

  @Test
  void paddleSizeDefaultTest() {
    myPaddle.setPaddleSizeDefault();
    assertEquals(myPaddle.getPaddleShape().getWidth(), Paddle.DEFAULT_PADDLE_LENGTH);
  }

  @Test
  void resetPaddleTest() {
    myPaddle.resetPaddle(Game.FRAME_SIZE, Game.FRAME_SIZE);
    Rectangle paddleShape = myPaddle.getPaddleShape();
    assertEquals(Game.FRAME_SIZE / 2.0 - Paddle.DEFAULT_PADDLE_LENGTH / 2.0, paddleShape.getX());
    assertEquals(Game.FRAME_SIZE - Paddle.PADDLE_HEIGHT, paddleShape.getY());
    assertEquals(Paddle.DEFAULT_PADDLE_LENGTH, paddleShape.getWidth());
    assertEquals(Paddle.DEFAULT_PADDLE_SHIFT_INCREASE_AMT, myPaddle.getPaddleShift());
  }

  @Test
  void setPaddleShiftZeroTest() {
    myPaddle.setPaddleShiftZero();
    assertEquals(0, myPaddle.getPaddleShift());
  }

}