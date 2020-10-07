package breakout;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class LevelThreeTest extends DukeApplicationTest {

  private final Game myGame = new Game();
  private Scene myScene;
  private Circle ballShape;
  private Rectangle paddleShape;

  /**
   * Start special test version of application that does not animate on its own before each test.
   *
   * Automatically called @BeforeEach by TestFX.
   */
  @Override
  public void start (Stage stage) {
    myGame.setCurrentLevel(3);
    myScene = myGame.setupScene(Game.FRAME_SIZE, Game.FRAME_SIZE, Game.BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    ballShape = lookup("#ball").query();
    paddleShape = lookup("#paddle").query();
  }

  @Test
  void checkPaddleAndBallStartingUp() {
    assertEquals(paddleShape.getY(), LevelThree.START_Y_PADDLE);
    assertEquals(ballShape.getCenterY(), LevelThree.START_Y_PADDLE - ballShape.getRadius() - 1);
  }

  @Test
  void checkPaddleMoveUpAndDown() {
    double initialPaddleY = paddleShape.getY();
    press(myScene, KeyCode.SPACE);
    press(myScene, KeyCode.UP);
    assertTrue(initialPaddleY!=paddleShape.getY());
    initialPaddleY = paddleShape.getY();
    press(myScene, KeyCode.DOWN);
    assertTrue(initialPaddleY!=paddleShape.getY());
  }

  @Test
  void checkPaddleStopsHalfWay() {
    paddleShape.setY(myScene.getHeight()/2);
    double initialPaddleY = paddleShape.getY();
    press(myScene, KeyCode.SPACE);
    press(myScene, KeyCode.UP);
    assertEquals(initialPaddleY, paddleShape.getY());
  }

}