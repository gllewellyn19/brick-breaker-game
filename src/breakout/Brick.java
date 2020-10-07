package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * The purpose of this class is to represent a brick object and include the graphics of creating the
 * brick in initialize brick. This function assumes that the arguments passed into initializeBrick
 * are valid. When an ImagePattern is created, it checks to see if the image exists, so Brick does
 * not need to worry about that. Brick assumes that the brick length and number of hits are
 * positive (no runtime error if are negative, but correct goals not achieved). Brick also assumes
 * that the root passed in is the root that all the other graphics are being added to. Brick
 * depends on the level class, because the destroyBrick/ updateHitToBrick function takes in a
 * level object. You use this class by instantiating then initializing and adding to a list of
 * bricks that are placed on the screen for breakout game.
 *
 * @author Grace Llewellyn and Priya Rathinavelu
 */

public class Brick implements Cloneable{

  public static final int BRICK_HEIGHT = 20;

  private int numHitsLeft;
  private int totalHitsPossible;
  private Rectangle brickShape;
  private int scoreIncreasePerHit;
  private boolean isPowerUpBrick;
  private boolean shouldDrop;
  private Group root;

  public Brick() {
    isPowerUpBrick = false;
    brickShape = null;
    totalHitsPossible = 0;
    scoreIncreasePerHit = 0;
    root = null;
  }

  /*
   * Initializes the brick based on the given information
   */
  protected void initializeBrick(int hits, ImagePattern brickColor, Group root, int x, int y,
      int brickNumber, int scoreInc, int brickLength) {
    numHitsLeft = hits;
    totalHitsPossible = hits;
    brickShape = new Rectangle(x, y, brickLength, BRICK_HEIGHT);
    brickShape.setStroke(Color.BLACK);
    brickShape.setFill(brickColor);
    brickShape.setId("brick" + brickNumber);
    root.getChildren().add(brickShape);
    scoreIncreasePerHit = scoreInc;
    shouldDrop = false;
    this.root = root;
  }

  /* for subclass of brick */
  protected Group getRoot() {
    return root;
  }

  protected void setIsPowerUpBrickTrue() {
    isPowerUpBrick = true;
  }

  protected int getTotalHitsPossible() {
    return totalHitsPossible;
  }

  /**
   * Subtracts one from numHitsLeft for the brick and checks if the brick has no hits left If so,
   * returns true so that the brick can be deleted Updates the transparency of the brick based on
   * the hits left
   */
  public boolean updateHitToBrick(Level currentLevel) {
    numHitsLeft--;
    brickShape.opacityProperty().set((double) numHitsLeft / totalHitsPossible);
    return numHitsLeft == 0;
  }

  public void setShouldDrop() {
    this.shouldDrop = true;
  }

  public boolean getShouldDrop() {
    return this.shouldDrop;
  }

  public boolean getIsPowerUp() {
    return isPowerUpBrick;
  }

  public Rectangle getBrickShape() {
    return brickShape;
  }

  public int getScoreIncreasePerHit() {
    return scoreIncreasePerHit;
  }

  public void destroyBrickWhenPaddleHits(Level currentLevel) { }

  @Override
  public Brick clone() {
    Brick clone = null;
    try{
      clone = (Brick) super.clone();
      } catch(CloneNotSupportedException cns) {
        Game.displayWarningMessage(root, "Cannot copy instance of brick", Game.FRAME_SIZE,
            Game.FRAME_SIZE);
      }
    return clone;
  }

}
