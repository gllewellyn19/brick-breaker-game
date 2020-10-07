package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The purpose of this level is to create an instance of the abstract Level class that represents
 * the first level in our breakout game. Its specifications, namely the image name for the paddle,
 * the list of brick patterns, and the name of the brick configuration file, allow for a unique experience
 * for this level. This class depends on the breakout package. More importantly, to make this class have
 * a unique feature, it is dependent on the Obstacle class (which creates an obstacle in the screen, making
 * the game play just a little bit more difficult for the user). This obstacle is stationary and in the center
 * of the screen. In order to make sure that each level implementation from the abstract Level class is unique,
 * there are certain methods that should be overridden. To actually see this level
 * in the game play, the LevelOne object is assumed to be included in the list of levels found in the main
 * Game class. In this LevelOne class, the intializeLevel method
 * is overriden so that the root also adds the obstacle mentioned above into the screen. The doSpecialFeature
 * method is also overriden so that the ball appropriately interacts with the objects (bounces off correctly
 * when colliding). To use this class, we first include an instance of LevelOne into the list of levels in Game, and
 * then use the abstract Level constructor to create this specific object representing LevelOne. The two methods
 * mentioned above should be overriden to create the stationary obstacle unique to the level one game
 * play experience.
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public class LevelOne extends Level {

  public static final int HEIGHT_OBSTACLE = 30;
  public static final int WIDTH_OBSTACLE = 80;
  public static final int Y_LOCATION_OBSTACLE = Game.FRAME_SIZE / 2 + HEIGHT_OBSTACLE / 2;
  public static final int X_LOCATION_OBSTACLE = Game.FRAME_SIZE / 2 - WIDTH_OBSTACLE / 2;

  private Obstacle obstacle;

  public LevelOne() {
    super(3, 1, Game.FRAME_SIZE, Game.FRAME_SIZE, Color.HOTPINK, Color.PLUM,
        "paddleImages/tentPaddle.jpg", new ArrayList<>(),
        List.of(new IncreasePaddleSizeBrick(), new IncreaseBallSizeBrick(),
            new IncreaseBallSpeedBrick()),
        "brickConfigurations/levelOneBricks.txt",
        List.of("brickIce2.jpg", "brickBranch.jpg", "brickRock.jpg"));
  }

  @Override
  void initializeLevelScreen() {

  }

  /*
   * Initializes the level and adds a obstacle to the middle of the screen (speed and direction 0,
   * so doesn't move)
   */
  @Override
  void initializeLevel(Group root, int currentScore) {
    super.initializeLevel(root, currentScore);
    Rectangle rectForMiddleScreen = new Rectangle(
        X_LOCATION_OBSTACLE, Y_LOCATION_OBSTACLE, WIDTH_OBSTACLE, HEIGHT_OBSTACLE);
    rectForMiddleScreen.setFill(Color.BLACK);
    rectForMiddleScreen.setId("obstacleLevelOne");
    root.getChildren().add(rectForMiddleScreen);
    obstacle = new Obstacle(rectForMiddleScreen, 0, 0, 0);
  }

  /*
   * Imitates a hit off of an obstacle in the middle of the screen
   */
  @Override
  protected void doSpecialFeature(double elapsedTime, boolean isPaused) {
    if (!isPaused && obstacle.checkCollisionBall(super.getBallShape())) {
      Collision.determineIfSwitchXOrYFromBallRectangleHit(obstacle.getObstacleShape(),
          super.getBall());
    }
  }

}
