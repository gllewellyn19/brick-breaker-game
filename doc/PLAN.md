# Game Plan
## Grace Llewellyn & Priya Rathinavelu


### Breakout Variation Ideas

### Interesting Existing Game Variations

 * Game 1: One game we liked was Fairy treasure. This version of Breakout was very advanced and included a lot of interesting features that enhance the overall gaming experience. 
           I thought having multiple types of bricks with various colors, shapes, and sizes added to design. In addition, the game having a general fantasy theme makes it more engaging and exciting.


 * Game 2: Another game we liked was Devilish. This was interesting because you don’t just stay in the same spot during the whole project- you get the chance to move around/ up and see different parts of the game. 
           It also adds more room for design because you do not have to have the same background for the entire game. I also like how it makes the game harder because you also have to deal with moving up. 



#### Block Ideas
We would like different blocks to be resemble different "materials" you would find in the landscape of the level.

 * Block 1 : Rocks- rocky surface in rectangular shape

 * Block 2 : Branch- a rectangular section of tree branch

 * Block 3 : Ice- blocks of ice to break through


#### Power Up Ideas

 * Power Up 1 - Making the paddle go faster

 * Power Up 2 - Invisible bricks- makes the bricks invisible for X amount of time 
                              
 * Power Up 3 - Increasing the size of the ball


#### Cheat Key Ideas

* Cheat Key 1: ‘L’- player gets one more life
* Cheat Key 2: ‘G’- size of the paddle grows 
* Cheat Key 3: ‘X’- lasers (shoot lasers out of the paddle)
* Cheat Key 4: ‘R’- reset game to original starting position of ball and paddle


#### Level Descriptions

 * Level 1
   * Block Configuration - Stationary campground/ landscape as the background and a tent as 
  the paddle. Each brick will take a varying number of hits to break
 based on its type (branch least, then ice, then rock takes the most amount of hits to break). 
The bricks would be composed of bricks representing leaves, branches, and tree
 trunks. The arrangement of bricks would appear as multiple trees. 

   * Variation features - We would also have some bricks that look different than the other bricks that give the user special power ups
  when the user breaks that brick. 

 * Level 2
   * Block Configuration - The background of this level would be underwater. The different types of bricks
 would be sand (easiest to break), seaweed (harder to break), and seashells 
(hardest to break).  We would arrange the bricks  to be in the formation of waves (with either blanks or different color bricks in between
 the crests of the waves). We also want to include more seashell bricks to make the game 
 a little harder. 

   * Variation features -  The paddle would be a boat with a flat top.To enhance the gameplay, we want to have a fish swimming through randomly
    (randomly at time and direction) that would block the ball from hitting the brick and the
    ball would reflect off of that object. We would also have random bricks be power ups, 
   but they look the same as the rest of the bricks. 

 * Level 3
   * Block Configuration - The bricks would remain the same as in the first level, except we want to add a new type of brick resembling rock that would
    now be the hardest to break. 

   * Variation features -  To go beyond the gameplay, we want the game to be moving up a background 
   like a hiking path (like [Devilish](https://www.youtube.com/watch?v=nrSrNGpQ-ho])) where you can move the paddle up and down and the
   width of the frame sometimes shrinks because of obstacles you can’t break like a bear. 
   You must break the brick in certain openings to be able to pass through the obstacles 
   to continue on the path to reach the top. The paddle in this level would be a hiking shoe with 
   the flat side facing up. 


### Possible Classes

 * Class 1 - Paddle
   * Purpose - to keep track of the paddle and be able to adjust its size if a power up
   * Method - setter method for indicating the size of the paddle (for level ups)

 * Class 2 - Brick
   * Purpose - to keep track of the individual bricks in each brick formation, can adjust 
   their own values such as number of hits needed to destroy the brick

   * Method - setter method for how many hits left of the brick until it is broken

 * Class 3 - Ball
   * Purpose - store general information about the ball including its size and speed
   which will need to be adjusted for different power ups.

   * Method - setter method changing the size and speed of the ball depending on power ups

 * Class 4 - Level
   * Purpose - An abstract class that has a number of lives and list of bricks 
   and some other instance variables. 

   * Method - Setter method for keeping track of how many lives you have left for your current level 

 * Class 5 - Game
   * Purpose - deals with the main functionality of the game

   * Method - includes setup scene, step, etc (methods to start the animation)
