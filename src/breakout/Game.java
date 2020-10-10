package breakout;

import breakout.Levels.Level;
import breakout.Levels.LevelOne;
import breakout.Levels.LevelThree;
import breakout.Levels.LevelTwo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The purpose of this main class is that it handles the graphics and basic rules of the breakout
 * game and maintains a current level that handles the specifics on that level. The Game class also
 * has a Collision class that handles the collisions between objects interacting in the scene.
 * The assumptions of the Game class depend a lot on the other classes in the project since it
 * expects them to do all the work of displaying the game and shape interactions. Game depends on
 * the javafx package, and on the Level class to maintain the displays and the Collision class
 * to handle the interactions. An example of how to use this function is to start the breakout game
 * which has the levels implemented in different ways to change the graphics.
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */
public class Game extends Application {

  public static final String TITLE = "Breakout game";
  public static final int FRAME_SIZE = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
  public static final String LOSING_MESSAGE = "You have lost. Press A to play again.";
  public static final String WINNING_MESSAGE = "You have won. Press M to move to next level.";
  public static final double SPEED_DROPPING_BRICK = 1.5;
  public static final int BALL_OFFSET_SIDE_HIT = 5;
  public static final Font DEFAULT_FONT = Font.font("Arial", 20);
  public static final double FRACTION_MAX_BALL_SIZE = 4.0;
  public static final List<Level> LEVELS = List
      .of(new LevelOne(), new LevelTwo(), new LevelThree());
  public static final String HIGH_SCORE_FILE = "highScores/highestScores.txt";

  private int levelNumber = 1;
  private boolean isPaused;
  private boolean gameOver;
  private Stage currentStage;
  private Scene myScene;
  private Level currentLevel;
  private Group root;
  private int currentScore = 0;
  private ShapeCollisionAndMovement collisionChecker;
  private boolean onTransitionScreen;

  /**
   * Initialize what will be displayed and how it will be updated.
   */
  @Override
  public void start(Stage stage) {
    setUpStage(stage);
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /*
   * Sets up the stage size and title
   */
  public void setUpStage(Stage stage) {
    currentStage = stage;
    setupScene(FRAME_SIZE, FRAME_SIZE, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
  }

  /*
   * Create the game's "scene": what shapes will be in the game and their starting properties
   */
  public Scene setupScene(int width, int height, Paint background) {
    root = new Group();
    isPaused = true;
    currentLevel = LEVELS.get(levelNumber - 1).clone();
    currentLevel.initializeLevel(root, currentScore);
    gameOver = !currentLevel.getSetupSuccessful();

    myScene = new Scene(root, width, height, background);
    myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    collisionChecker = new ShapeCollisionAndMovement(currentLevel, root, myScene);
    return myScene;
  }

  /*
   * Handle the game's movements. If updateShapes returns true, means game needs to be reset
   */
  public void step(double elapsedTime) {
    if (!gameOver) {
      if (collisionChecker.updateShapes(elapsedTime, isPaused)) {
        resetGame();
      }
      checkIfMoveToNextLevel();
    }
  }

  /*
   * Checks if the game is over/ won or lost (displays correct message and prompts to continue)
   */
  private void checkIfMoveToNextLevel() {
    if (currentLevel.checkLevelOver()) {
      gameOver = true;
      storeScore(currentLevel.getGameDisplay().getScore());
      if (currentLevel.hasWon()) {
        promptUserPlayAgain(WINNING_MESSAGE);
      } else {
        promptUserPlayAgain(LOSING_MESSAGE);
      }
    }
  }

  /*
   * Tells the user if they won or lost and prompts the user if they want to play again to click A
   */
  private void promptUserPlayAgain(String message) {
    Text gameEndMessage = new Text();
    gameEndMessage.setText(message);
    gameEndMessage.setFont(DEFAULT_FONT);
    gameEndMessage
        .setX(myScene.getWidth() / 2.0 - gameEndMessage.getLayoutBounds().getWidth() / 2.0);
    gameEndMessage.setY(myScene.getHeight() / 2.0);
    root.getChildren().add(gameEndMessage);
  }

  /*
   * Restarts the game by calling start and also sets gameOver back to false so gameplay can resume
   * Resets to what levelNumber is (is parameter to make sure it is being changed)
   */
  private void restartGame(int newLevelNumber) {
    currentScore = 0;
    if (currentLevel.hasWon()) {
      currentScore = currentLevel.getGameDisplay().getScore();
    }
    currentLevel.showTransition(root);
    levelNumber = newLevelNumber;
    gameOver = false;
    setUpStage(currentStage);
    //animation.play();
  }

  /*
   * What to do each time a key is pressed. Can change direction of paddle or use cheat keys
   */
  private void handleKeyInput(KeyCode code) {
    checkKeyPressForPaddleChange(code);
    checkKeyPressForGameFunctionality(code);
    checkKeyPressForBallChange(code);
    checkKeyPressForBrickChange(code);
  }

  /*
   * Checks to see if the user drops a brick with a power up
   */
  private void checkKeyPressForBrickChange(KeyCode code) {
    if (code == KeyCode.P) {
      currentLevel.dropBrickWithPowerUp();
    }
  }

  /*
   * Checks if user increases the ball speed to sets the ball back to its default size
   */
  private void checkKeyPressForBallChange(KeyCode code) {
    if (code == KeyCode.S) {
      currentLevel.getBall().increaseBallSpeed();
    }

    if (code == KeyCode.W) {
      currentLevel.getBall().setBallSizeDefault();
    }
  }

  /*
   * Checks if user resets, restarts, pauses or adds a life and does that action
   */
  private void checkKeyPressForGameFunctionality(KeyCode code) {
    checkKeyPressRestartGame(code);
    if (code == KeyCode.SPACE) {
      pauseOrUnpauseGame();
    }
    if (code == KeyCode.L) {
      currentLevel.addLife(root);
    }

    if (code == KeyCode.D) {
      collisionChecker.removeBrick(currentLevel.getBricks().get(0), currentLevel.getBricks(), root);
    }
    int codeDig = Character.getNumericValue(code.getChar().charAt(0));
    if (code.isDigitKey() && codeDig > 0 && codeDig <= LEVELS.size()) {
      restartGame(codeDig);
    }
  }

  /*
   * Checks to see if the user gives a key press that results in resetting or restarting the game
   */
  private void checkKeyPressRestartGame(KeyCode code) {
    if (code == KeyCode.R) {
      resetGame();
    }

    if (code == KeyCode.M && levelNumber < LEVELS.size() && currentLevel.checkLevelOver()) {
      restartGame(levelNumber + 1);
    }

    if (code == KeyCode.A) {
      levelNumber = 1;
      restartGame(levelNumber);
    }
  }

  /*
   * Checks if user tries to move the paddle or grow the paddle and does that action
   */
  private void checkKeyPressForPaddleChange(KeyCode code) {
    if (!gameOver && code.isArrowKey()) {
      unpauseGameIfPaused();
      currentLevel.checkPaddleMovement(code, myScene.getHeight(), myScene.getWidth());
    }
    if (code == KeyCode.G) {
      currentLevel.getPaddle().increasePaddleSize();
    }
  }

  /*
   * Checks if the game is paused and unpauses it if it is paused by resetting the paddle shift
   * amount and ball speed
   */
  public void unpauseGameIfPaused() {
    if (isPaused) {
      currentLevel.getBall().setBallSpeedDefault();
      currentLevel.getPaddle().setPaddleShiftDefault();
      isPaused = false;
    }
  }

  /*
   * Checks to see if the game is already paused (restarts ball if so) or game is unpaused (stops
   * ball if so). Changes the isPaused boolean too
   */
  private void pauseOrUnpauseGame() {
    if (isPaused) {
      currentLevel.getBall().setBallSpeedDefault();
      currentLevel.getPaddle().setPaddleShiftDefault();
    } else {
      currentLevel.getBall().resetBallSpeed();
      currentLevel.getPaddle().setPaddleShiftZero();
    }
    isPaused = !isPaused;
  }

  /*
   * Stores the current score into a text file
   */
  private void storeScore(int score) {
    if (score != 0) {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(
            "data/" + HIGH_SCORE_FILE, true));
        writer.append(String.valueOf(score)).append("\n");
        writer.close();
      } catch (IOException e) {
        displayWarningMessage(root, "File for high scores not found", Game.FRAME_SIZE,
            Game.FRAME_SIZE);

      }
    }
  }

  /*
   * Resets the ball and the paddle to the center of the screen and makes the speed
   * of the ball 0. Resets the ball and paddle size
   */
  protected void resetGame() {
    currentLevel.getBall().resetBall(myScene.getHeight(), myScene.getWidth(),
        currentLevel.getPaddleShape().getHeight());
    currentLevel.getPaddle().resetPaddle(myScene.getHeight(), myScene.getWidth());
    isPaused = true;
  }

  public void setCurrentLevel(int newLevel) {
    levelNumber = newLevel;
  }

  public Level getCurrentLevel() {
    return currentLevel;
  }

  public Group getRoot() {
    return root;
  }

  /* For testing purposes */
  public Scene getMyScene() {
    return myScene;
  }

  public void setGameOverFalse() { gameOver = false; }

  /*
   * Displays a warning message in the middle of the screen
   */
  public static void displayWarningMessage(Group root, String message, int frameHeight,
      int frameWidth) {
    Text messageDisplay = new Text();
    messageDisplay.setText(message);
    messageDisplay.setFont(Game.DEFAULT_FONT);
    messageDisplay
        .setX(frameWidth / 2.0 - messageDisplay.getLayoutBounds().getWidth() / 2.0);
    messageDisplay.setY(frameHeight / 2.0);
    root.getChildren().add(messageDisplay);
  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
