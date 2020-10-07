Brick Breaker Game
====

### NOTE: Project is work in progress and instructions on how to run are also a work in progress. For now, code is here to view. 

This project implements the game of Breakout in a creative way that utilizes inheritance hierarchy and abstraction.

Authors: Grace Llewellyn and Priya Rathinavelu. Refactored by Grace Llewellyn alone after the project was submitted in class 

### Running the Program
Main class to run: Game.java

Data files needed: Everything in the data file (includes what is in the subfolders brickColor, brickConfigurations, images, high scores and paddleImages)

Key/Mouse inputs: Right arrow to move paddle to the right, left arrow
to move paddle to the left (and cheat keys, see below). No mouse inputs 

Cheat keys: "R" resets ball/paddle location, "space" pauses/unpauses game, "G" increases the paddle size by the default size increase amount, "1-3" changes the level number, "L" adds a life, "A" restarts the game (everything in default positions and resets the lives, score, etc.), "S" increases the ball size, "W" sets the ball size back to default, "P" drops a brick with a power up for the paddle to catch (does nothing if no bricks with power ups), "D" destroys the first brick (first brick is the left most brick on the top level)

#### Notes on adding new components:
Adding a new level: Create a new level class and that implements the abstract level class and passes all the needed values into initialize (can use Level's version). If have a list or something that needs a deep copy as a local variable of your level, override the clone method to make a deep copy of that variable

Adding a new powerup brick: Create a brick that extends the BrickWithPowerUp class and overrides the destroyBrickWhenPaddleHits function

Adding a new brick with special action: Create a brick that extends the brickWithSpecialAction class and override do special action
