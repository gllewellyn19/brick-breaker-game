package breakout;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;



public class TestingLevel extends Level{

  public TestingLevel() {
    super(3, 1, Game.FRAME_SIZE, Game.FRAME_SIZE, Color.HOTPINK, Color.PLUM,
        "paddleImages/tentPaddle.jpg", new ArrayList<>(),
        List.of(new IncreasePaddleSizeBrick(), new IncreaseBallSizeBrick(),
            new IncreaseBallSpeedBrick()),
        "brickConfigurations/levelTestBricks.txt",
        List.of("brickIce2.jpg", "brickBranch.jpg", "brickRock.jpg"));
  }

  @Override
  void initializeLevelScreen() {

  }

  @Override
  protected void doSpecialFeature(double elapsedTime, boolean isPaused) {

  }
}
