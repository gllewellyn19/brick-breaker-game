game
====

This project implements the game of Breakout in a creative way.

Name: Priya Rathinavelu & Grace Llewellyn

### Timeline

Start Date: 9/13/20

Finish Date: 9/28/20

Hours Spent: 50 hours

### Resources Used
Class lectures, Piazza, and online sources about reading in files and creating images on shapes, Java oracle  

### Running the Program
For test implementation
Main class: Game.java

Data files needed: Everything in the data file (includes what is in the subfolders brickColor, brickConfigurations, images, high scores and paddleImages)

Key/Mouse inputs: Right arrow to move paddle to the right, left arrow
to move paddle to the left (and cheat keys, see below). No mouse inputs 

Cheat keys: "R" resets ball/paddle location, "space" pauses/unpauses game, "G" increases the paddle size by the default size increase amount, "1-3" changes the level number, "L" adds a life, "A" restarts the game (everything in default positions and resets the lives, score, etc.), "S" increases the ball size, "W" sets the ball size back to default, "P" drops a brick with a power up for the paddle to catch (does nothing if no bricks with power ups), "D" destroys the first brick (first brick is the left most brick on the top level)

Different functionalities of levels:
Level 1: Obstacle in the middle of the screen that you bounce of off. 3 different power ups in this level
Level 2: Moving obstacle in the middle of the screen that you bounce of off. 3 functionally different bricks in this level
Level 3: Paddle can move side to side and up and down. Bricks with power ups and bricks that are functionally different

Known Bugs:

Extra credit:
Design of bricks/ paddle, one more cheat key than required, most features work if you make the screen bigger, did optional


### Notes/Assumptions
Assumptions: List DEFAULT_LOCATIONS_SCORE_LIVES_HIGH_LEVEL in level constructor tells where to put values for the current score, lives, high score and current level in the form [x loc score, y loc score, x loc lives, y loc lives, x high score, y high score, x current level, y current level]. If numbers out of bounds or no numbers are passed in, using default values marked in constant]

Files we left in data will let the game run (no error if not found, but game does not play)

Assumptions about the text file with game configuration: 0 means no brick, anything above a 0 means brick needs that many hits to disappear, spaces between different bricks. Bricks with power ups/ dif functionality indicated by "numHitsBrick:indexInSpecialBricksList" where the special bricks list is a list passed into the level constructor. Works if not square matrix. If try to pass in index for special bricks list that is too large for the actual list of special bricks passed into the constructor, just makes a normal brick in that spot

Hitting a brick that explodes does not cause a positive feedback loop if that brick removes another brick that explodes (we did this on purpose to not get rid of all the bricks at once (unless they hit the gameEnding brick)). We purposely make the score not increase when they hit these bricks because it makes the game more variable so people don't get the same score everytime

Notes on adding new components:
Adding a new level: Create a new level class and that implements the abstract level class and passes all the needed values into initialize (can use Level's version). If have a list or something that needs a deep copy as a local variable of your level, override the clone method to make a deep copy of that variable

Adding a new powerup brick: Create a brick that extends the BrickWithPowerUp class and overrides the destory brick function

Adding a new brick with special action: Create a brick that extends the brickWithSpecialAction class and override do special action

Notes about score:
The score persists when you win the level, but resets if you press A or change the level with cheat keys 

Notes about warnings in Design Coach: Methods that are too complex need to be if else ladder because handling key code input, Methods that are empty are there to show our proposed implementation of the optional and destoryBrick method being empty is a design choice that will be discussed in analysis, Control flow statements "if", "for", "while", "switch" and "try" should not be nested too deeply only lists twice which I don't think is bad, Magic numbers mostly pertain to the default locations for the display variables with is explained in the class comment- default constant variable that makes sure given variable alighs with. Methods should not have too many parameters: This was a major design choice for us because we wanted to have the user chose any many values for themselves as possible (without having too many) to follow open closed design principle- we will go into more depth about this in the analysis. brickMatrix needs to be declared before it is relevant so that the variable setUp successful can be looked at before initializing the values for the loop


### Impressions
Very difficult to make the plan at the beginning with the links given to show possible brick breaker games. Those links showed pretty advanced games with many graphics that I don't think we were able/ had time to do. These links caused us to be overly ambitious on our plan because we thought the design would matter more

