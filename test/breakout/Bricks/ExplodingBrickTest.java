package breakout.Bricks;

import static org.junit.jupiter.api.Assertions.*;

import breakout.Game;
import breakout.Levels.Level;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests for brick that explodes and destroys its neighbors
 *
 * @author Grace Llewellyn
 */
class ExplodingBrickTest extends DukeApplicationTest {

  private final Game myGame = new Game();
  private Scene myScene;
  private Circle ballShape;
  private Level currentLevel;
  private Rectangle paddleShape;

  @Override
  public void start (Stage stage) {
    myGame.setCurrentLevel(2);
    myScene = myGame.setupScene(Game.FRAME_SIZE + 1, Game.FRAME_SIZE + 1, Color.PINK);
    stage.setScene(myScene);
    stage.show();
    currentLevel = myGame.getCurrentLevel();

    ballShape = lookup("#ball").query();
    paddleShape = lookup("#paddle").query();
  }

  @Test
  void brickFunctionTest() {
    int initialAmountOfBricks = currentLevel.getBricks().size();
    ballShape.setCenterX(120);
    ballShape.setCenterY(150);
    paddleShape.setX(130);
    press(myScene, KeyCode.SPACE);
    myGame.step(.5);
    javafxRun(() -> myGame.step(.5));
    assertTrue(initialAmountOfBricks != currentLevel.getBricks().size());
  }

}