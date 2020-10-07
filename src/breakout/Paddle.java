package breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * The purpose of this class is to handle the paddle shape within the game interface. It handles creating
 * the actual paddle as well as setting its default shifting values and size, as well as includes methods
 * that correspond to different power ups and cheat keys that can adjust these parameters. This class assumes
 * that a root is inputted when initializing so that the paddle can actually be displayed on the game interface.
 * This class is dependent on the Game class, because certain methods in this class are associated with
 * the different cheat keys called in the Game class. In addition, when creating a new level, we instantiate
 * a new paddle which is specific to the certain level. To use this class, like we do in level, we indicate
 * the paddle image file so that that pattern is shown on the paddle, as well as the starting x and y
 * positions. When updating the paddle speed or size for a cheat key, that certain method can be called
 * within the game class.
 */

public class Paddle {

  public static final int PADDLE_SHIFT_AMOUNT = 5;
  public static final int DEFAULT_PADDLE_LENGTH = 70; // NOTE make these not passed in
  public static final int PADDLE_HEIGHT = 20;
  public static final int DEFAULT_PADDLE_SIZE_INCREASE_AMT = 10;
  public static final int DEFAULT_PADDLE_SHIFT_INCREASE_AMT = 5;

  private final Paint paddleColor;
  private final String paddleImageFile;
  private final int xLocPaddle;
  private final int yLocPaddle;
  private Rectangle paddleShape;
  private int paddleShift;
  private final int frameHeight;
  private final int frameWidth;
  private boolean setupSuccessful;


  public Paddle(Paint c, String paddleImageFile, int x, int y, int frameHeight, int frameWidth) {
    paddleColor = c;
    this.paddleImageFile = paddleImageFile;
    xLocPaddle = x;
    yLocPaddle = y;
    this.frameHeight = frameHeight;
    this.frameWidth = frameWidth;
    setupSuccessful = true;
  }

  /*
   * Creates the paddle and makes the paddle image equal to the given source if the source is
   * not an empty string. If the paddle image is an empty string, then sets the paddle color
   */
  public void initializePaddle(Group root) {
    paddleShape = new Rectangle(xLocPaddle, yLocPaddle, DEFAULT_PADDLE_LENGTH, PADDLE_HEIGHT);
    if (!paddleImageFile.equals("")) {
      createPaddleImage(root);
    } else {
      paddleShape.setFill(paddleColor);
    }
    paddleShape.setId("paddle");
    root.getChildren().add(paddleShape);
  }

  protected void createPaddleImage(Group root) {
    try {
      Image image = new Image(paddleImageFile);
      ImagePattern imagePattern = new ImagePattern(image);
      paddleShape.setFill(imagePattern);
    } catch (IllegalArgumentException e) {
      Game.displayWarningMessage(root, "File for paddle image not found", frameHeight,
          frameWidth);
      setupSuccessful = false;
    }
  }

  /*
   * changes the size of the paddle
   */
  public void increasePaddleSize() {
    double newPaddleSize = paddleShape.getWidth() + DEFAULT_PADDLE_SIZE_INCREASE_AMT;
    if (newPaddleSize > Game.FRAME_SIZE) {
      newPaddleSize = Game.FRAME_SIZE;
    }
    paddleShape.setWidth(newPaddleSize);
  }

  /*
   * Resets the paddle position and size
   */
  protected void resetPaddle(double frameHeight, double frameWidth) {
    paddleShape.setX(frameWidth / 2.0 - DEFAULT_PADDLE_LENGTH / 2.0);
    paddleShape.setY(frameHeight - paddleShape.getHeight());
    setPaddleSizeDefault();
    setPaddleShiftDefault();
  }

  public boolean getSetupSuccessful() {
    return setupSuccessful;
  }

  protected void setPaddleShiftZero() {
    this.paddleShift = 0;
  }

  protected void increasePaddleShift() {
    this.paddleShift = this.paddleShift + PADDLE_SHIFT_AMOUNT;
  }

  public void setPaddleShiftDefault() {
    this.paddleShift = PADDLE_SHIFT_AMOUNT;
  }

  protected void setPaddleSizeDefault() {
    this.paddleShape.setWidth(DEFAULT_PADDLE_LENGTH);
  }

  public int getPaddleShift() {
    return this.paddleShift;
  }

  public Rectangle getPaddleShape() {
    return paddleShape;
  }

}
