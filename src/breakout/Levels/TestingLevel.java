package breakout.Levels;


import breakout.Bricks.IncreaseBallSizeBrick;
import breakout.Bricks.IncreaseBallSpeedBrick;
import breakout.Bricks.IncreasePaddleSizeBrick;
import breakout.Game;
import breakout.Levels.Level;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;


public class TestingLevel extends Level {

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
  public void doSpecialFeature(double elapsedTime, boolean isPaused) {

  }
}
