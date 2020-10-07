package breakout;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The purpose of this abstract class is to handle all the functions of a level such as the number
 * of lives and the displays of the game. This class depends on the ball and paddle class to
 * maintain the speed, size and direction of those shapes. This class also depends on the
 * GameBrickDisplay class to create the display of bricks. This class also depends on the
 * GameDisplay class to do the display of the score, high score, level, and lives. This class
 * assumes that width and height are the width and height of the frame and uses this in positioning
 * the ball and paddle. This class makes no assumptions if the files passed in are not valid and
 * does not let the user play the game if they are not valid. An example of how to use this class is
 * to extend it, implement its abstract methods, then pass unique values into the constructor in
 * order to make the game fun.
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */
public abstract class Level implements Cloneable {

  private int numLives;
  private final int levelNumber;
  private final Ball ball;
  private final Paddle paddle;
  private boolean setupSuccessful;
  private final GameDisplay gameDisplay;
  private final GameBrickDisplay gameBrickDisplay;
  private Group root;

  public Level(int numLives, int level, int width, int height, Paint ballColor, Paint paddleColor,
      String paddleImage, List<Integer> locationsScoreLives, List<Brick> potentialSpecialBricks,
      String brickFileLocation, List<String> brickPatterns) {
    gameDisplay = new GameDisplay(numLives, width, height, locationsScoreLives);
    gameBrickDisplay = new GameBrickDisplay(height, width, potentialSpecialBricks,
        brickFileLocation, brickPatterns);
    this.numLives = numLives;
    this.levelNumber = level;
    ball = new Ball(ballColor, width / 2,
        height - Paddle.PADDLE_HEIGHT - Ball.DEFAULT_BALL_RADIUS - 1,
        Ball.DEFAULT_BALL_RADIUS);
    paddle = new Paddle(paddleColor, paddleImage, width / 2 - Paddle.DEFAULT_PADDLE_LENGTH / 2,
        height - Paddle.PADDLE_HEIGHT, height, width);
  }

  /* Warning, Proposed implementation of optional */
  abstract void initializeLevelScreen();

  /*
   * Initializes the level instance
   */
  void initializeLevel(Group root, int currentScore) {
    this.root = root;
    ball.initializeBall(root);
    paddle.initializePaddle(root);
    gameDisplay.initializeGameDisplay(root, levelNumber, currentScore);
    gameBrickDisplay.initializeGameBrickDisplay(root);
    setupSuccessful = gameBrickDisplay.getSetupSuccessful() && gameDisplay.getSetupSuccessful() &&
        paddle.getSetupSuccessful();
  }

  void showTransition(Group root) {
    gameDisplay.readInTransition(root);
  }

  protected boolean getSetupSuccessful() {
    return setupSuccessful;
  }

  protected int getNumLives(){
    return numLives;
  }

  protected List<Brick> getBricks() {
    return gameBrickDisplay.getBricks();
  }

  protected int getLevelNumber(){
    return levelNumber;
  }

  protected Ball getBall(){
    return ball;
  }

  protected Paddle getPaddle() {
    return paddle;
  }

  protected Group getRoot() { return root; }

  /*
   * returns true if the level is over which means that there are no bricks left in the level
   */
  boolean checkLevelOver(){
    return (gameBrickDisplay.getBricks().isEmpty() && setupSuccessful)|| numLives ==0;
  }

  /*
   * Knows that level is over so no lives or no bricks. Returns true if the user has won which
   * means no bricks are left
   */
  boolean hasWon(){ return gameBrickDisplay.getBricks().isEmpty() && setupSuccessful; }

  Rectangle getPaddleShape(){
    return paddle.getPaddleShape();
  }

  Circle getBallShape(){ return ball.getBallShape(); }

  GameDisplay getGameDisplay() {
    return gameDisplay;
  }

  GameBrickDisplay getGameBrickDisplay() {return gameBrickDisplay;}

  /*
   * decreases the lives variable by one and also removed a heart. When game calls this the next
   * iteration checks if game is over which means checking if no lives left. This means that this
   * can't be called when there isn't any hearts left on the screen
   */
  void decreaseLivesByOne(){
    numLives--;
    gameDisplay.removeLife();
  }

  /*
   * Adds a life to the number of lives variable and to the GUI display
   */
  void addLife(Group root) {
    gameDisplay.addHeart(root, numLives);
    numLives++;
  }

  /*
   * Drop a brick that has a power up
   */
  protected void dropBrickWithPowerUp() {
    for (Brick b : gameBrickDisplay.getBricks()) {
      if (b.getIsPowerUp()) {
        b.setShouldDrop();
        return;
      }
    }
  }

  /*
   * Checks if the paddle needs to be moved to the left or the right
   */
  protected void checkPaddleMovement(KeyCode code, double frameHeight, double frameWidth) {
    if (code == KeyCode.LEFT) {
      double newX = paddle.getPaddleShape().getX() - paddle.getPaddleShift();
      checkPaddleOutOfXBoundsAndMove(newX, newX < 0, 0);
    } else if (code == KeyCode.RIGHT) {
      double newX = paddle.getPaddleShape().getX() + paddle.getPaddleShift();
      checkPaddleOutOfXBoundsAndMove(newX, newX > frameWidth - paddle.getPaddleShape().getWidth(),
          frameWidth - paddle.getPaddleShape().getWidth());
    }
  }


  /*
   * Checks to see if out of bounds based on the given boolean. If out of bounds then sets the new
   * x of the paddle to the edge of the bounds (proceeds normally with newX if in bounds)
   */
  private void checkPaddleOutOfXBoundsAndMove(double newX, boolean outOfBounds, double edgeBounds) {
    if (outOfBounds) {
      paddle.getPaddleShape().setX(edgeBounds);
    } else {
      paddle.getPaddleShape().setX(newX);
    }
  }

  @Override
  public Level clone() {
    Level clone = null;
    try{
      clone = (Level) super.clone();
    } catch(CloneNotSupportedException cns) {
      Game.displayWarningMessage(root, "Cannot copy instance of level", Game.FRAME_SIZE,
          Game.FRAME_SIZE);
    }
    return clone;
  }

  protected abstract void doSpecialFeature(double elapsedTime, boolean isPaused);

}
