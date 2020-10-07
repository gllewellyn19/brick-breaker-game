## Lab Discussion
### Team 8
### Names Grace Llewellyn (gal16) and Priya Rathinavelu (plr11)


### Issues in Current Code

#### Method or Class
 ##### Design issues
* One of our design issues was that our method that handled the different key code inputs 
 was really long and included many if statements. (Game line 368)
 * Another design issue was that we were calling e.printStackTrace() in a few methods. 
 * Some of our methods were too complex (included long if statement chains)
 * Our code uses a few magic numbers

### Refactoring Plan

 * What are the code's biggest issues?
 In general, we can improve our code by continuing to split up some of our larger methods.

 * Which issues are easy to fix and which are hard?
 The issues with splitting up the methods and magic numbers are really easy to fix. Changing the hierarchy 
 will be more complicated.

 * What are good ways to implement the changes "in place"?
 We want to create a "configuration" class that deals with setting up the bricks within the game. Right now, 
 that code is in the level class, so we can create methods in the level class to extract to our new configuration 
 class. 


### Refactoring Work

 * Issue chosen: Fix and Alternatives
 To change our level constructor that takes in a lot of parameters, we can try to combine some of the parameters
 into a list/collection. 

 * Issue chosen: Fix and Alternatives
 To deal with our long handleKeyCodeInput method, we can split up the if statements by which object/aspect of the 
 game they would be adjusting. 