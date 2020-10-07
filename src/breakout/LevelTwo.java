package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The purpose of this class is to extend the level class and be functionally different than the
 * other levels by having a moving obstacle go across the screen. This depends on the Obstacle
 * class to deal with moving the obstacle and bouncing the direction of the obstacle if necessary.
 * This class depends on some constants from the game class. This class makes no assumptions if the
 * files passed in are not valid and does not let the user play the game if they are not valid. An
 * example of how to use this class it to add it to the list of Levels in the Game class, then when
 * the user gets to that level, copy it from the class, then initialize it to start it.
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public class LevelTwo extends Level {

  public static final int HEIGHT_OBSTACLE = 30;
  public static final int WIDTH_OBSTACLE = 80;
  public static final int Y_LOCATION_OBSTACLE = Game.FRAME_SIZE/2 + HEIGHT_OBSTACLE/2;
  public static final int X_LOCATION_OBSTACLE = Game.FRAME_SIZE/2 - WIDTH_OBSTACLE/2;
  public static final int DEFAULT_SPEED_OBSTACLES = 30;

  private Obstacle movingObstacle;

  public LevelTwo() {
    super(3, 2, Game.FRAME_SIZE, Game.FRAME_SIZE, Color.BLUE, Color.YELLOWGREEN,
        "paddleImages/boatPaddle.jpg", new ArrayList<>(),
        List.of(new ExplodingBrick(), new LevelEndingBrick()),
        "brickConfigurations/levelTwoBricks.txt",
        List.of("brickIce.jpg", "brickBranch.jpg", "brickWood.jpg"));
  }

  @Override
  void initializeLevelScreen() {

  }

  /*
   * Initializes the level and adds a obstacle to the middle of the screen
   */
  @Override
  void initializeLevel(Group root, int currentScore) {
    super.initializeLevel(root, currentScore);
    Rectangle rectForMiddleScreen = new Rectangle(
        X_LOCATION_OBSTACLE, Y_LOCATION_OBSTACLE, WIDTH_OBSTACLE, HEIGHT_OBSTACLE);
    rectForMiddleScreen.setFill(Color.BLACK);
    rectForMiddleScreen.setId("obstacleLevelTwo");
    root.getChildren().add(rectForMiddleScreen);
    movingObstacle = new Obstacle(rectForMiddleScreen, 1, 0, DEFAULT_SPEED_OBSTACLES);
  }

  /*
   * Goes through the obstacles and first sees if collision with, then moves the obstacle
   */
  @Override
  protected void doSpecialFeature(double elapsedTime, boolean isPaused) {
    if (!isPaused) {
      movingObstacle.move(elapsedTime, super.getBall());
    }
  }


}
