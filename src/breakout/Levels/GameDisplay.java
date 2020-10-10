package breakout.Levels;

import breakout.Game;
import breakout.Paddle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * The purpose of this class is to handle the main displays needed for the game. This includes the display of
 * the number of lives the player has (in the form of heart images), the updating current score while playing, the highest score that the user has
 * ever achieved (with it increasing if the current score is higher), and a display to indicate which
 * level the user is currently playing. This class is not really dependent on other classes, but is needed when calling a new level class so that the level
 * specifics are actually shown. One of the input parameters when instantiating this object is a list of
 * all the locations of where the displays can be shown (specific values shown below). This was done so that when creating the game display for the
 * level, the locations of where different counters are shown can be changed. However, if valid values
 * are not found in that list of locations, default values are set instead. In addition, this class assumes that a root is passed in when initializing
 * so that there is a valid object where all of the different displays can actually be displayed to the user.
 * To use this class, like it is called in level, you can create a new GameDisplay object with the necessary
 * input parameters. After creating the new GameDisplay, it is important to call the intializeGameDisplay
 * method so that all of the different displays appear on the scene.
 *
 * DEFAULT_LOCS_SCORE_LIVES - should be 8 numbers, (x loc score, y loc score, x loc lives, y loc lives,
 * x loc highest score, y loc highest score, x loc level, y loc level)
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public class GameDisplay {

  public static final int SIZE_HEART_FOR_LIVES = 15;
  public static final String HEART_FILE = "images/heart.jpg";
  public static final String SCORE_INDICATOR = "Score: ";
  public static final String LEVEL_INDICATOR = "Level: ";
  public static final String HIGHEST_SCORE_INDICATOR = "High Score: ";
  public static final List<Integer> DEFAULT_LOCATIONS_SCORE_LIVES_HIGH_LEVEL =
      List.of(Game.FRAME_SIZE - 100, Game.FRAME_SIZE - Paddle.PADDLE_HEIGHT - 30,
          0, Game.FRAME_SIZE - Paddle.PADDLE_HEIGHT - 40,
          Game.FRAME_SIZE - 100, Game.FRAME_SIZE - Paddle.PADDLE_HEIGHT - 10,
          0, Game.FRAME_SIZE - Paddle.PADDLE_HEIGHT - 10);
  public static final int INDEX_Y_LIVES = 3;
  public static final String HIGHEST_SCORE_FILE = "highScores/highestScores.txt";

  private int highScore;
  private int totalScore;
  private List<ImageView> heartsForLives;
  private Text scoreDisplay;
  private Text levelDisplay;
  private Text highestScoreDisplay;
  private final List<Integer> locationsScoreLivesHighLevel;
  private boolean setupSuccessful;
  private final int frameHeight;
  private final int frameWidth;

  public GameDisplay(int width, int height, List<Integer> locationsScoreLivesHighLevel) {
    makeDefaultLocationsScoreLivesIfNeeded(locationsScoreLivesHighLevel, List.of(width, height));
    this.locationsScoreLivesHighLevel = new ArrayList<>(locationsScoreLivesHighLevel);
    frameHeight = height;
    frameWidth = width;
    setupSuccessful = true;
  }

  /*
  An initializing method that calls different methods specific to each display for the game that places
  them on the root
   */
  public void initializeGameDisplay(Group root, int levelNumber, int score, int numLives) {
    createScoreDisplay(root, locationsScoreLivesHighLevel.get(0), locationsScoreLivesHighLevel.get(1), score);
    createLivesDisplay(root, numLives, locationsScoreLivesHighLevel.get(2));
    createHighestScoreDisplay(root, locationsScoreLivesHighLevel.get(4), locationsScoreLivesHighLevel.get(5));
    createLevelNumberDisplay(root, levelNumber, locationsScoreLivesHighLevel.get(6),
        locationsScoreLivesHighLevel.get(7));
    totalScore = score;
  }

  public boolean getSetupSuccessful() {
    return setupSuccessful;
  }

  /*
   * If locationsScoreLives is missing an index or has an out of bounds index, uses the default
   * location. Dimensions is a list of [width, height] so even is use the 0th index for the width
   */
  private void makeDefaultLocationsScoreLivesIfNeeded(List<Integer> locationsScoreLives,
      List<Integer> dimensions) {
    int i;
    for (i = 0; i < locationsScoreLives.size(); i++) {
      if (!checkInRangeZeroToMax(dimensions.get(i % 2), locationsScoreLives.get(i))) {
        locationsScoreLives.set(i, DEFAULT_LOCATIONS_SCORE_LIVES_HIGH_LEVEL.get(i));
      }
    }
    while (i < DEFAULT_LOCATIONS_SCORE_LIVES_HIGH_LEVEL.size()) {
      locationsScoreLives.add(DEFAULT_LOCATIONS_SCORE_LIVES_HIGH_LEVEL.get(i));
      i++;
    }
  }

  private boolean checkInRangeZeroToMax(double max, double target) {
    return target >= 0 && target <= max;
  }

  /*
   * Creates the display of numLives hearts in the top left corner
   */
  private void createLivesDisplay(Group root, int numLives, int xLoc) {
    heartsForLives = new ArrayList<>();
    for (int i = 0; i < numLives; i++) {
      createHeart(root, xLoc, locationsScoreLivesHighLevel.get(INDEX_Y_LIVES));
      xLoc += SIZE_HEART_FOR_LIVES;
    }
  }

  /*
   * Adds a heart to the game display next to the hearts already there (if applicable)
   */
  protected void addHeart(Group root, int numLives) {
    createHeart(root, numLives * SIZE_HEART_FOR_LIVES, locationsScoreLivesHighLevel.get(INDEX_Y_LIVES));
  }

  /*
   * Removes the last heart from the image display
   */
  protected void removeLife() {
    ImageView heartToRemove = heartsForLives.remove(heartsForLives.size() - 1);
    heartToRemove.setImage(null);
  }

  /*
   * Creates the heart display on the scene at the given x and y coordinates
   */
  private void createHeart(Group root, int x, int y) {
    try {
      Image image = new Image(HEART_FILE);
      ImageView imageView = new ImageView(image);
      imageView.setX(x);
      imageView.setY(y);
      imageView.setFitHeight(SIZE_HEART_FOR_LIVES);
      imageView.setFitWidth(SIZE_HEART_FOR_LIVES);
      imageView.setPreserveRatio(true);
      root.getChildren().add(imageView);
      heartsForLives.add(imageView);
    } catch (IllegalArgumentException e) {
      Game.displayWarningMessage(root, "File for heart lives not found", frameHeight,
          frameWidth);
      setupSuccessful = false;
    }
  }

/*
A method that increases the score for the score display. It also checks if the current score is higher
than the highest score, and if it is, it updates the high score display
 */
public void increaseScore(int amountToIncreaseBy) {
    if (amountToIncreaseBy > 0) {
      totalScore += amountToIncreaseBy;
      updateScoreDisplay(totalScore);
      if (totalScore > highScore) {
        updateHighScoreDisplay(totalScore);
      }
    }
  }

  public int getScore() {
    return totalScore;
  }

  protected Text getScoreDisplay() {
    return scoreDisplay;
  }

  /*
   * Creates the display of the score
   */
  private void createScoreDisplay(Group root, int x, int y, int currentScore) {
    Text score = new Text();
    score.setX(x);
    score.setY(y);
    scoreDisplay = score;
    root.getChildren().add(scoreDisplay);
    updateScoreDisplay(currentScore);
  }

  /*
   * Creates the display of the level number
   */
  private void createLevelNumberDisplay(Group root, int levelDisplayed, int x, int y) {
    Text levelNum = new Text();
    levelNum.setX(x);
    levelNum.setY(y);
    levelDisplay = levelNum;
    root.getChildren().add(levelDisplay);
    levelDisplay.setText(LEVEL_INDICATOR + levelDisplayed);
  }

  /*
   * Creates the display of the highest score
   */
  private void createHighestScoreDisplay(Group root, int x, int y) {
    Text highScoreValue = new Text();
    highScoreValue.setX(x);
    highScoreValue.setY(y);
    highestScoreDisplay = highScoreValue;
    root.getChildren().add(highestScoreDisplay);
    int highestScore = findHighestScore(root);
    highestScoreDisplay.setText(HIGHEST_SCORE_INDICATOR + highestScore);
  }

  /*
   * Finds the highest score from the file of highest scores (warning, but null pointer is caught
   */
  private int findHighestScore(Group root) {
    int highestValue = 0;
    try {
      File file = new File(getFilePath());
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine();
      while (line != null) {
        highestValue = determineScoreOfLineHigher(highestValue, line);
        line = reader.readLine();
      }
      reader.close();
    } catch (NullPointerException | IOException e) {
      Game.displayWarningMessage(root, "File for high scores not found", frameHeight, frameWidth);
      setupSuccessful = false;
    }
    highScore = highestValue;
    return highestValue;
  }

  /*
   * Finds the file path in specific way that works for running as jar file
   */
  private String getFilePath () {
    int startAbsolute =  (new File("").getAbsolutePath()).indexOf("brick-breaker-game");
    return (new File("").getAbsolutePath()).substring(0, startAbsolute) +
        "/brick-breaker-game/data/"+HIGHEST_SCORE_FILE;
  }

  /*
   * Determines if the score in the current line is higher than the highest score
   */
  private int determineScoreOfLineHigher(int highestValue, String line) {
    if (!line.equals("")) {
      int currentScore = Integer.parseInt(line);
      if (currentScore > highestValue) {
        highestValue = currentScore;
      }
    }
    return highestValue;
  }

  public int getHighScore() {
    return highScore;
  }

  public Text getHighestScoreDisplay() {
    return highestScoreDisplay;
  }

  protected Text getLevelDisplay() {
    return levelDisplay;
  }

  /*
   * updates the score display to what the score currently is
   */
  protected void updateScoreDisplay(int score) {
    scoreDisplay.setText(SCORE_INDICATOR + score);
  }

  protected void updateHighScoreDisplay(int score) {
    highestScoreDisplay.setText(HIGHEST_SCORE_INDICATOR + score);
  }

  protected List<ImageView> getHeartsForLives() {
    return heartsForLives;
  }

  /*
   * Reads in the transition to the root (warning, but null pointer is caught)
   */
  public void readInTransition(Group root) {
    try {
      File file = new File(
          getClass().getClassLoader().getResource("transitionFiles/finishLevel1.txt").getFile());
      BufferedReader reader = new BufferedReader(new FileReader(file));
      root.getChildren().removeAll();
      String line = reader.readLine();
      int count = 1;
      while (line != null) {
        displayGameMessage(root, line, count);
        count ++;
        line = reader.readLine();
      }
    } catch (NullPointerException | IOException e) {
      Game.displayWarningMessage(root, "File for transitions not found", frameHeight,
          frameWidth);
    }
  }

  /*
   * Displays the game message given in line (makes sure to be reading down line)
   */
  private void displayGameMessage(Group root, String line, int count) {
    Text gameEndMessage = new Text();
    gameEndMessage.setText(line);
    gameEndMessage.setFont(Game.DEFAULT_FONT);
    gameEndMessage
        .setX(Game.FRAME_SIZE / 2.0 - gameEndMessage.getLayoutBounds().getWidth() / 2.0);
    gameEndMessage.setY((30)* count);
    root.getChildren().add(gameEndMessage);
  }
}
