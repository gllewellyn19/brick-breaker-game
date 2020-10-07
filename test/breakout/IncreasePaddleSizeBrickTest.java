package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class IncreasePaddleSizeBrickTest extends DukeApplicationTest {

  private final Game myGame = new Game();
  private Scene myScene;
  private Circle ballShape;
  private Level currentLevel;
  private Rectangle paddleShape;

  /**
   * Start special test version of application that does not animate on its own before each test.
   *
   * Automatically called @BeforeEach by TestFX.
   */
  @Override
  public void start (Stage stage) {
    myScene = myGame.setupScene(Game.FRAME_SIZE + 1, Game.FRAME_SIZE + 1, Color.PINK);
    stage.setScene(myScene);
    stage.show();
    currentLevel = myGame.getCurrentLevel();

    ballShape = lookup("#ball").query();
    paddleShape = lookup("#paddle").query();
  }

  @Test
  void hitBrickTest() {
    ballShape.setCenterX(150);
    ballShape.setCenterY(150);
    paddleShape.setX(130);
    press(myScene, KeyCode.SPACE);
    myGame.step(.5);
    myGame.step(.5);
    assertTrue(checkBrickWasHit(currentLevel.getBricks()));
  }

  @Test
  void brickFunctionTest() {
    double initialPaddleWidth = paddleShape.getWidth();
    ballShape.setCenterX(150);
    ballShape.setCenterY(150);
    currentLevel.getBall().setXDirection(-1);
    currentLevel.getBall().setYDirection(-1);
    paddleShape.setX(120);
    press(myScene, KeyCode.SPACE);
    myGame.step(.5);
    myGame.step(2);
    javafxRun(() -> myGame.step(.5));
    assertTrue(currentLevel.getPaddleShape().getWidth()!=initialPaddleWidth);
  }

  /*
   * Returns true if there was a brick that was hit in the list of bricks and false otherwise
   */
  private boolean checkBrickWasHit(List<Brick> bricks) {
    for (Brick b: bricks) {
      if (b.getShouldDrop()) {
        return true;
      }
    }
    return false;
  }
}