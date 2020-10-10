package breakout.Levels;

import breakout.Bricks.Brick;
import breakout.Game;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * The purpose of this class is to handle the display of the bricks on the root. It reads in the initial set up
 * of the bricks and properly displays them on the actual root for the user to see. This class also handles updating
 * the color of the brick when it is hit. This class does not really depend on other classes, but it is only instantiated in the level class which allows for the unique
 * pattern of bricks to show up for each level. This class assumes that a string containing the file name
 * for the text file with the brick configuration pattern will be inputted. From this file, the class
 * is able to read in the different values to create different bricks with different hits to break them and
 * various power ups. In addition, this class assumes that a list of image names for the different
 * brick patterns will also be provided. Lastly, this class also takes in a list
 * of potentialSpecialBricks that indicate the different types of special bricks available for the
 * specific configuration. To create this class, like in the level class, a GameBrickDisplay
 * object can be created by inputting the necessary parameters. By being able to indicate different brick patterns,
 * brick configurations, and which power ups are available, the GameBrickDisplay object can help create
 * a unique experience for each level. The initializeGameBrickDisplay method should be called so that the
 * actual brick formation can be created.
 *
 */

public class GameBrickDisplay {

  public static final int DEFAULT_SCORE_INCREASE = 1;

  private List<Brick> bricks;
  private final List<Brick> potentialSpecialBricks;
  private final String brickFileLocation;
  private final List<String> brickPatterns;
  private final int frameHeight;
  private final int frameWidth;
  private boolean setupSuccessful;
  private int brickLengthForGame;

  public GameBrickDisplay(int height, int width, List<Brick> potentialSpecialBricks,
      String brickFileLocation, List<String> brickPatterns) {
    this.potentialSpecialBricks = new ArrayList<>(potentialSpecialBricks);
    this.brickFileLocation = brickFileLocation;
    this.brickPatterns = new ArrayList<>(brickPatterns);
    frameHeight = height;
    frameWidth = width;
    bricks = new ArrayList<>();
    setupSuccessful = true;
    brickLengthForGame = Game.FRAME_SIZE;
  }

  public List<Brick> getBricks() {
    return bricks;
  }

  public boolean getSetupSuccessful() {
    return setupSuccessful;
  }

  public void initializeGameBrickDisplay(Group root) {
    bricks = new ArrayList<>();
    createBrickFormation(root, brickFileLocation, frameHeight, frameWidth);
  }

  /*
   * Creates a matrix representing brick hits from the given file where 0 means no brick
   * (warning, but null pointer caught)
   */
  private List<List<String>> createBrickNumberMatrix(String fileName, Group root, int height,
      int width) {
    List<List<String>> brickMatrix = new ArrayList<>();
    BufferedReader reader;
    try {
      File file = new File(getFilePath(fileName));
      reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine();
      while (line != null) {
        addLineToBrickMatrix(brickMatrix, line);
        line = reader.readLine();
      }
      reader.close();
      return brickMatrix;
    } catch (NullPointerException | IOException e) {
      Game.displayWarningMessage(root, "Brick configuration file cannot be found", height, width);
      setupSuccessful = false;
    }
    brickMatrix.add(new ArrayList<>());
    return brickMatrix;
  }

  /*
   * Finds the file path in specific way that works for running as jar file
   */
  private String getFilePath (String fileName) {
    int startAbsolute =  (new File("").getAbsolutePath()).indexOf("brick-breaker-game");
    return (new File("").getAbsolutePath()).substring(0, startAbsolute) +
        "/brick-breaker-game/data/"+fileName;
  }

  /*
   * Given a brick matrix to add to and string line from the file, puts the bricks numbers into
   * the matrix. Updates the new potential brick length if more bricks in the line which means
   */
  private void addLineToBrickMatrix(List<List<String>> brickMatrix, String line) {
    String[] lineOfBricks = line.split(" ", 0);
    int newPotentialBrickLength = Game.FRAME_SIZE / lineOfBricks.length;
    if (newPotentialBrickLength < brickLengthForGame) {
      brickLengthForGame = newPotentialBrickLength;
    }

    brickMatrix.add(new ArrayList<>(Arrays.asList(lineOfBricks)));
  }


  /*
   * Normally makes the color of the brick equal to its number of lives in the list of colors'
   * index - 1. If not colors in the list of colors, makes the brick black. If brickLives is out of
   * bounds in the brickColors list, makes the color the first color in the bricksColors list
   */
  protected ImagePattern getColorBrick(Group root, int brickLives, List<String> brickPatterns) {
    if (brickPatterns.isEmpty()) {
      return createImageFromStringName("black.png", root);
    }
    if (brickLives - 1 >= brickPatterns.size()) {
      return createImageFromStringName(brickPatterns.get(0), root);
    }
    return createImageFromStringName(brickPatterns.get(brickLives - 1), root);
  }

  /*
   * Returns an image pattern which is created from the given image name
   */
  public ImagePattern createImageFromStringName(String brickPatternName, Group root) {
    try {
      return new ImagePattern(new Image("brickColor/" + brickPatternName));
    } catch (IllegalArgumentException e) {
      setupSuccessful = false;
      Game.displayWarningMessage(root, "File for brick image not found", frameHeight,
          frameWidth);
    }
    return null;
  }

  /*
   * Create the formation of bricks on the screen passed on the passed in file. Doesn't do anything
   * if the file set up was not successful
   */
  public void createBrickFormation(Group root, String brickFileLocation, int frameHeight,
      int frameWidth) {
    List<List<String>> brickMatrix = createBrickNumberMatrix(brickFileLocation, root, frameHeight,
        frameWidth);
    if (!setupSuccessful) {
      return;
    }
    int xIndex = 0;
    int yIndex = 0;
    int currentBrickNumber = 1;
    for (List<String> row : brickMatrix) {
      for (String brickInfo : row) {
        currentBrickNumber = addBrick(root, xIndex, yIndex, currentBrickNumber, brickInfo);
        xIndex += brickLengthForGame;
      }
      yIndex += Brick.BRICK_HEIGHT;
      xIndex = 0;
    }
  }

  /*
   * Creates a new brick object (which displays the brick on screen) and adds it to list of bricks
   */
  private int addBrick(Group root, int xIndex, int yIndex, int currentBrickNumber, String brickInfo) {
    int indexOfColon = brickInfo.indexOf(":");

    if (!brickInfo.startsWith("0")) {
      if (!checkIfNormalBrick(indexOfColon, brickInfo, root, xIndex, yIndex, currentBrickNumber)) {
        addSpecialBrick(indexOfColon, brickInfo, root, xIndex, yIndex, currentBrickNumber);
      }
      currentBrickNumber++;
    }
    return currentBrickNumber;
  }

  /*
   * If brick doesn't start with a 0, no ":", or if ":" last character in string, creates a normal
   * brick and adds it to the list of bricks
   */
  private boolean checkIfNormalBrick(int indexOfColon, String brickInfo, Group root, int xIndex,
      int yIndex, int currentBrickNumber) {
    if (indexOfColon == -1 || indexOfColon == brickInfo.length()) {
      try {
        int brickLives = Integer.parseInt(brickInfo);
        addBrickGivenBrickType(root, brickPatterns, xIndex, yIndex, currentBrickNumber, brickLives,
            new Brick());
        return true;
      } catch (NumberFormatException e) {
        Game.displayWarningMessage(root, "Cannot put letters in brick set up file",
            frameHeight, frameWidth);
        setupSuccessful = false;
      }

    }
    return false;
  }

  /*
   * Adds a special brick to the list of bricks if a number is specified after the colon
   */
  public void addSpecialBrick(int indexOfColon, String brickInfo, Group root, int xIndex,
      int yIndex, int currentBrickNumber) {
    try {
      int specialBrickIndex = Integer
          .parseInt(brickInfo.substring(indexOfColon + 1));
      int brickLives = Integer.parseInt(brickInfo.substring(0, indexOfColon));
      addBrickGivenBrickType(root, brickPatterns, xIndex, yIndex, currentBrickNumber, brickLives,
          checkSpecialBrickIndexInBounds(specialBrickIndex));
    } catch (NumberFormatException e) {
      Game.displayWarningMessage(root, "Cannot put letters in brick set up file",
          frameHeight, frameWidth);
      setupSuccessful = false;
    }
  }

  /*
   * Determines if the special brick index is bigger than the list of potential special bricks
   * (if so, returns a normal brick)
   */
  private Brick checkSpecialBrickIndexInBounds(int specialBrickIndex) {
    if (specialBrickIndex >= potentialSpecialBricks.size()) {
      return new Brick();
    }
    else {
      return potentialSpecialBricks.get(specialBrickIndex).clone();
    }
  }

  /*
   * Adds a brick to the list of bricks after initializing it
   */
  private void addBrickGivenBrickType(Group root, List<String> brickPatterns, int xIndex,
      int yIndex, int currentBrickNumber, int brickLives, Brick newBrick) {
    newBrick.initializeBrick(brickLives, getColorBrick(root, brickLives, brickPatterns), root, xIndex,
        yIndex, currentBrickNumber, DEFAULT_SCORE_INCREASE, brickLengthForGame);
    bricks.add(newBrick);
  }
}
