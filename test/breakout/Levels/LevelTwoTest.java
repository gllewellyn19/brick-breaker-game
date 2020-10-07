package breakout.Levels;

import static org.junit.jupiter.api.Assertions.*;

import breakout.Game;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests for level two
 *
 * @author Grace Llewellyn
 */
class LevelTwoTest extends DukeApplicationTest {

  private final Game myGame = new Game();
  private Scene myScene;
  private Circle ballShape;
  private Rectangle obstacle;

  @Override
  public void start (Stage stage) {
    myGame.setCurrentLevel(2);
    myGame.setUpStage(stage);
    myScene = myGame.getMyScene();

    ballShape = lookup("#ball").query();
    obstacle = lookup("#obstacleLevelTwo").query();
  }

  @Test
  void checkObstaclePresent() {
    assertTrue(myGame.getRoot().getChildren().contains(obstacle));
  }

  @Test
  void checkObstacleMovingSideWays() {
    double initialXObstacle = obstacle.getX();
    press(myScene, KeyCode.SPACE);
    myGame.step(.5);

    assertTrue(initialXObstacle != obstacle.getX());
  }

  @Test
  void checkObstacleDoesntMoveWhenPaused() {
    double initialXObstacle = obstacle.getX();
    myGame.step(.5);

    assertEquals(initialXObstacle, obstacle.getX());
  }

  @Test
  void checkBounceOffObstacle() {
    ballShape.setCenterX(LevelOne.X_LOCATION_OBSTACLE + obstacle.getWidth() / 2);
    ballShape.setCenterY(LevelOne.Y_LOCATION_OBSTACLE + obstacle.getHeight() + 20);
    press(myScene, KeyCode.SPACE);
    myGame.step(.2);
    myGame.step(.2);
    assertTrue(ballShape.getCenterY() > (obstacle.getY() + obstacle.getHeight()));
  }
}