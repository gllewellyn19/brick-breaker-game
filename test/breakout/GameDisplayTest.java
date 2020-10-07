package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class GameDisplayTest extends DukeApplicationTest {

  private final Game myGame = new Game();
  // keep created scene to allow mouse and keyboard events
  private Scene myScene;
  private Circle ball;
  private Rectangle paddle;
  private Level currentLevel;
  private Rectangle brick;

  @Override
  public void start (Stage stage) {
    // create game's scene with all shapes in their initial positions and show it
    myScene = myGame.setupScene(Game.FRAME_SIZE, Game.FRAME_SIZE, Game.BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    // find individual items within game by ID (must have been set in your code using setID())
    ball = lookup("#ball").query();
    paddle = lookup("#paddle").query();
    currentLevel = myGame.getCurrentLevel();
  }

  @Test
  void testUpdateScoreForDisplay() {
    press(myScene, KeyCode.SPACE); // unpause game because starts paused
    myGame.step(1);
    myGame.step(1);
    myGame.step(1);
    myGame.step(1);
    assertTrue(currentLevel.getGameDisplay().getScore() > 0);
  }

  @Test
  void testFindingHighestScore() throws IOException {
    press(myScene, KeyCode.SPACE); // unpause game because starts paused
    myGame.step(1);
    myGame.step(1);
    myGame.step(1);
    myGame.step(1);
    assertEquals(currentLevel.getGameDisplay().getHighScore(), getHighScore());
  }

  @Test
  void testDisplayHighScore() throws IOException {
      press(myScene, KeyCode.SPACE); // unpause game because starts paused
      myGame.step(1);
      myGame.step(1);
      myGame.step(1);
      myGame.step(1);
      String scoreDisp = currentLevel.getGameDisplay().HIGHEST_SCORE_INDICATOR + getHighScore();
      assertEquals(scoreDisp, currentLevel.getGameDisplay().getHighestScoreDisplay().getText());
  }

  private int getHighScore() throws IOException {
    int score = 0;
    File file = new File(
        getClass().getClassLoader().getResource("highScores/highestScores.txt").getFile());
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line = reader.readLine();
    while (line != null) {
      if (!line.equals("")) {
        int currentScore = Integer.parseInt(line);
        if (currentScore > score) {
          score = currentScore;
        }
      }
      line = reader.readLine();
    }
    return score;
  }

  @Test
  void testUpdatingScoreDisplay() {
    press(myScene, KeyCode.SPACE); // unpause game because starts paused
    myGame.step(1);
    myGame.step(1);
    myGame.step(1);
    myGame.step(1);
    int currentScore = myGame.getCurrentLevel().getGameDisplay().getScore();
    String scoreDisp = myGame.getCurrentLevel().getGameDisplay().SCORE_INDICATOR + currentScore;
    assertEquals(myGame.getCurrentLevel().getGameDisplay().getScoreDisplay().getText(), scoreDisp);
  }

  @Test
  void testIncreaseScore() {
    press(myScene, KeyCode.SPACE); // unpause game because starts paused
    int initialScore = myGame.getCurrentLevel().getGameDisplay().getScore();
    myGame.getCurrentLevel().getGameDisplay().increaseScore(5);
    int increasedScore = myGame.getCurrentLevel().getGameDisplay().getScore();
    assertTrue(increasedScore > initialScore);
  }

  @Test
  void testAddHeart() { //NOTE need getter method for hearts
    press(myScene, KeyCode.SPACE); // unpause game because starts paused
    int initialNumberHearts = currentLevel.getGameDisplay().getHeartsForLives().size();
    javafxRun(() -> currentLevel.getGameDisplay().addHeart(currentLevel.getRoot(), 1));
    int increasedNumberHearts = currentLevel.getGameDisplay().getHeartsForLives().size();
    assertEquals(increasedNumberHearts, initialNumberHearts+1);
  }

  @Test
  void testRemoveHeart() {
    int initialNumberHearts = currentLevel.getGameDisplay().getHeartsForLives().size();
    myGame.getCurrentLevel().getGameDisplay().removeLife();
    int decreasedNumberHearts = currentLevel.getGameDisplay().getHeartsForLives().size();
    assertEquals(initialNumberHearts-1, decreasedNumberHearts);
  }

  @Test
  void testLevelDisplay() {
    String scoreDisp = myGame.getCurrentLevel().getGameDisplay().LEVEL_INDICATOR + currentLevel.getLevelNumber();
    assertEquals(scoreDisp, currentLevel.getGameDisplay().getLevelDisplay().getText() );
  }



}