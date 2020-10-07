package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for Game class.
 *
 * @author Robert C Duvall
 */
public class GameTest extends DukeApplicationTest {
    // create an instance of our game to be able to call in tests (like step())
    private final Game myGame = new Game();
    // keep created scene to allow mouse and keyboard events
    private Scene myScene;
    private Circle ball;
    private Rectangle paddle;
    private Level currentLevel;
    private Rectangle brick;
  //  private int xDir = myGame.xDirection;


    /**
     * Start special test version of application that does not animate on its own before each test.
     *
     * Automatically called @BeforeEach by TestFX.
     */
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


    // Can write regular JUnit tests!
    // check initial configuration values of game items set when scene was created
    @Test
    public void testInitialPositions () {
        checkShapesInStartingPosition();

    }

    private void checkShapesInStartingPosition() {
        assertEquals(200, ball.getCenterX());
        assertEquals(374, ball.getCenterY());
        assertEquals(5, ball.getRadius());

        assertEquals(165, paddle.getX());
        assertEquals(380, paddle.getY());
        assertEquals(70, paddle.getWidth());
        assertEquals(20, paddle.getHeight());
    }


    @Test
    public void testBricksLevelOne(){
        List<Rectangle> allBricks = new ArrayList<>();

        for (int t = 1; t < 22; t ++) { //hardcoded given test file
            brick = lookup("#brick"+t).query();
            allBricks.add(brick);
        }
        //testing some bricks (first brick in a row, last brick, middle brick)
        assertEquals(0, allBricks.get(3).getX());
        assertEquals(20, allBricks.get(3).getY());
        assertEquals(20, allBricks.get(6).getY());
        assertEquals(60, allBricks.get(13).getY());
    }



    @Test
    public void testBallBounceOffCorner(){
        ball.setCenterX(10);
        ball.setCenterY(10);
        press(myScene, KeyCode.SPACE); // unpause game because starts paused
        myGame.step(.0625);
        // because the radius is 5, this ball goes in the corner
        assertEquals(10-.0625*Ball.DEFAULT_BALL_SPEED, ball.getCenterX());
        assertEquals(10-.0625*Ball.DEFAULT_BALL_SPEED, ball.getCenterY());
        myGame.step(.0625);
        assertEquals(10, ball.getCenterX());
        assertEquals(10, ball.getCenterY());
    }

    @Test
    public void testBallBounceOffWall(){
        ball.setCenterX(0);
        ball.setCenterY(200);
        press(myScene, KeyCode.SPACE); // unpause game because starts paused
        myGame.step(1);
        assertTrue(ball.getCenterX()>0);
    }

    @Test
    public void testBallResetOnR(){
        press(myScene, KeyCode.R);
        assertEquals(200, ball.getCenterX());
        assertEquals(374, ball.getCenterY());
    }

    // NOTE ball isn't doing right thing
    @Test
    public void testScoreUpdate() {
        ball.setCenterX(Game.FRAME_SIZE/2);
        ball.setCenterY(Game.FRAME_SIZE/2); //setting ball to about to hit brick
        press(myScene, KeyCode.SPACE); // unpause game because starts paused
        myGame.step(1);
        myGame.step(1);
        assertTrue(currentLevel.getGameDisplay().getScore() > 0);
    }

    @Test
    public void testBallResetWhenGoesOffScreen(){
        ball.setCenterX(5);
        ball.setCenterY(500);
        press(myScene, KeyCode.SPACE);
        // make sure more than one life left
        if (currentLevel.getNumLives() <= 1) {
            press(myScene, KeyCode.L);
        }
        myGame.step(.5);
        assertEquals(200, ball.getCenterX());
        assertEquals(374, ball.getCenterY());
    }

    @Test
    public void testPlayerLosesLifeWhenBallGoesOffScreen(){
        ball.setCenterX(5);
        ball.setCenterY(Game.FRAME_SIZE + 100);
        press(myScene, KeyCode.SPACE);
        // make sure more than one life left
        int prevNumLives = currentLevel.getNumLives();
        if (prevNumLives <= 1) {
            press(myScene, KeyCode.L);
            prevNumLives++;
        }
        myGame.step(.5);
        assertEquals(prevNumLives-1, currentLevel.getNumLives());
    }

    @Test
    public void testGameOverWhenBallGoesOffScreen(){
        ball.setCenterX(5);
        ball.setCenterY(Game.FRAME_SIZE + 100);
        press(myScene, KeyCode.SPACE);
        // make sure more than one life left
        while(currentLevel.getNumLives() > 1) {
            currentLevel.decreaseLivesByOne();
        }
        javafxRun(() -> myGame.step(1));
        assertTrue(currentLevel.checkLevelOver());
    }


    //assumes that going to hit brick that only has one hit (can be power up or not)
    // NOTE make sure not going into bricks and multiple bricks not falling
    @Test
    public void testBrickHits() {
        ball.setCenterX(170);
        ball.setCenterY(110);
        int initialNumBricks = currentLevel.getBricks().size();
        press(myScene, KeyCode.SPACE); // unpause game because starts paused
        javafxRun(() -> myGame.step(.5));
        javafxRun(() -> myGame.step(5));
        assertTrue(initialNumBricks-1 == currentLevel.getBricks().size() ||
            checkBrickWasHit(currentLevel.getBricks()));
    }

    /*
     * Returns true if there was a brick that was hit in the list of bricks and false otherwise
     */
    private boolean checkBrickWasHit(List<Brick> bricks) {
        for (Brick b: bricks) {
            if (b.getShouldDrop()) {
                return true;
            }
        }
        return false;
    }


    /* Check cheat keys */
    @Test
    public void checkCheatKeySpace() {
        ball.setCenterX(200);
        ball.setCenterY(374);
        press(myScene, KeyCode.SPACE);
        myGame.step(1);
        assertTrue(ball.getCenterX()!=200 && ball.getCenterY() != 374);
    }

    @Test
    public void checkCheatKeyL() {
        int prevNumLives = currentLevel.getNumLives();
        press(myScene, KeyCode.L);
        assertEquals(prevNumLives+1, currentLevel.getNumLives());
    }


    @Test
    public void checkCheatKeyG() {
        double prevPaddleSize = currentLevel.getPaddleShape().getWidth();
        press(myScene, KeyCode.G);
        assertEquals(prevPaddleSize + Paddle.DEFAULT_PADDLE_SIZE_INCREASE_AMT,
            currentLevel.getPaddleShape().getWidth());
    }

    @Test
    public void checkCheatKeyP() {
        press(myScene, KeyCode.P);
        assertTrue(checkBrickWasHit(currentLevel.getBricks()));
    }

    @Test
    public void checkCheatKeyS() {
        double prevBallSpeed = currentLevel.getBall().getBallSpeed();
        press(myScene, KeyCode.S);
        assertEquals(prevBallSpeed + Ball.DEFAULT_BALL_SPEED_INC_AMOUNT,
            currentLevel.getBall().getBallSpeed());
    }

    @Test
    public void checkCheatKeyW() {
        press(myScene, KeyCode.W);
        assertEquals(Ball.DEFAULT_BALL_RADIUS,
            currentLevel.getBallShape().getRadius());
    }


    @Test
    public void testWrongFileNameBrickConfiguration() {
        javafxRun(() -> currentLevel.getGameBrickDisplay().createBrickFormation(currentLevel.getRoot(),
            "wrongFile.txt", Game.FRAME_SIZE, Game.FRAME_SIZE));
        assertFalse(currentLevel.getGameBrickDisplay().getSetupSuccessful());
    }

    @Test
    public void testWrongBrickNames() {
        javafxRun(() -> currentLevel.getGameBrickDisplay().createImageFromStringName("noFile.txt",
            currentLevel.getRoot()));
        assertFalse(currentLevel.getGameBrickDisplay().getSetupSuccessful());
    }


    @Test
    public void testAddSpecialBricks() {
        javafxRun(() -> currentLevel.getGameBrickDisplay().addSpecialBrick(2, "test", currentLevel.getRoot(),
        10, 10, 3));
        assertFalse(currentLevel.getGameBrickDisplay().getSetupSuccessful());
    }
}
