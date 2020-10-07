package breakout.Levels;

import static org.junit.jupiter.api.Assertions.*;

import breakout.Game;
import breakout.Levels.LevelOne;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests for level one
 *
 * @author Grace Llewellyn
 */
class LevelOneTest extends DukeApplicationTest {

  private final Game myGame = new Game();
  private Scene myScene;
  private Circle ballShape;
  private Rectangle obstacle;

  @Override
  public void start (Stage stage) {
    myScene = myGame.setupScene(Game.FRAME_SIZE + 1, Game.FRAME_SIZE + 1, Color.PINK);
    stage.setScene(myScene);
    stage.show();

    ballShape = lookup("#ball").query();
    obstacle = lookup("#obstacleLevelOne").query();
  }

  @Test
  void checkObstaclePresent() {
    assertTrue(myGame.getRoot().getChildren().contains(obstacle));
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

  @Test
  void checkBounceOffObstacleCorner() {
    ballShape.setCenterX(LevelOne.X_LOCATION_OBSTACLE + obstacle.getWidth() + ballShape.getRadius() + 5);
    ballShape.setCenterY(LevelOne.Y_LOCATION_OBSTACLE + obstacle.getHeight() + ballShape.getRadius() + 5);
    press(myScene, KeyCode.SPACE);
    myGame.step(.1);
    myGame.step(.1);
    assertTrue(ballShape.getCenterY() > (obstacle.getY() + obstacle.getHeight()));
  }

}