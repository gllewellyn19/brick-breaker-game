package breakout.Levels;

import static org.junit.jupiter.api.Assertions.*;

import breakout.Game;
import breakout.Paddle;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests for that abstract level class
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */
class LevelTest extends DukeApplicationTest {
  private final Game myGame = new Game();
  // keep created scene to allow mouse and keyboard events
  private Scene myScene;
  private Rectangle paddleShape;
  private Level currentLevel;

  @Override
  public void start (Stage stage) {
    // create game's scene with all shapes in their initial positions and show it
    myScene = myGame.setupScene(Game.FRAME_SIZE, Game.FRAME_SIZE, Game.BACKGROUND);
    stage.setScene(myScene);
    stage.show();
    // find individual items within game by ID (must have been set in your code using setID())
    paddleShape = lookup("#paddle").query();
    currentLevel = myGame.getCurrentLevel();
  }

  @Test
  public void getLevel() {
    //level is 1 when starting game
    assertEquals(1, currentLevel.getLevelNumber());
  }

  @Test
  public void testAddLife() {
    int initialNumLives = currentLevel.getNumLives();
    javafxRun(() -> currentLevel.addLife(currentLevel.getRoot())); //NOTE need an accessor to root
    assertEquals(initialNumLives+1, currentLevel.getNumLives());
  }

  @Test
  public void testCheckLevelOver() {
    //initially game should not be over
    press(myScene, KeyCode.SPACE); // unpause game because starts paused
    assertFalse(currentLevel.checkLevelOver());
  }

  @Test
  public void testHasWon() {
    //initially game should not have level be over
    press(myScene, KeyCode.SPACE); // unpause game because starts paused
    assertFalse(currentLevel.hasWon());
  }


  @Test
  public void testPaddleMovement() {
    // WHEN, move it right by "pressing" the right arrow
    myGame.setGameOverFalse();
    myGame.unpauseGameIfPaused();
    currentLevel.getPaddle().setPaddleShiftDefault();
    press(myScene, KeyCode.RIGHT);
    //check its X coordinate has increased by the proper amount
    Assertions.assertEquals(165 + Paddle.PADDLE_SHIFT_AMOUNT, paddleShape.getX());
    //check if x coordinate has decreased back to center
    press(myScene, KeyCode.LEFT);
    assertEquals(165, paddleShape.getX());
  }

  @Test
  public void testPaddleOutOfBounds() {
    paddleShape.setX(0);
    press(myScene, KeyCode.LEFT);
    assertEquals(0, paddleShape.getX());
  }



}