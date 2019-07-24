/*
 * File: BreakoutExtra.java
 * -------------------
 * Name:Anyu Zhu
 * Section Leader:Peter Hansel
 * 
 * This file will eventually implement the game of Breakout, with some special features added:
 * sound effect, velocity change, display messages, show the score, and adjust the radius of the ball to 5. 
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class BreakoutExtra extends GraphicsProgram {

	// Dimensions of the canvas, in pixels
	// These should be used when setting up the initial size of the game,
	// but in later calculations you should use getWidth() and getHeight()
	// rather than these constants for accurate size information.
	public static final double CANVAS_WIDTH = 420;
	public static final double CANVAS_HEIGHT = 600;

	// Number of bricks in each row
	public static final int NBRICK_COLUMNS = 10;

	// Number of rows of bricks
	public static final int NBRICK_ROWS = 10;

	// Separation between neighboring bricks, in pixels
	public static final double BRICK_SEP = 4;

	// Width of each brick, in pixels
	public static final double BRICK_WIDTH = Math.floor(
			(CANVAS_WIDTH - (NBRICK_COLUMNS + 1.0) * BRICK_SEP) / NBRICK_COLUMNS);

	// Height of each brick, in pixels
	public static final double BRICK_HEIGHT = 8;

	// Offset of the top brick row from the top, in pixels
	public static final double BRICK_Y_OFFSET = 70;

	// Dimensions of the paddle
	public static final double PADDLE_WIDTH = 60;
	public static final double PADDLE_HEIGHT = 10; 

	// Offset of the paddle up from the bottom 
	public static final double PADDLE_Y_OFFSET = 30;

	// Radius of the ball in pixels
	public static final double BALL_RADIUS = 5;

	// The ball's vertical velocity.
	public static final double VELOCITY_Y = 3.0; 

	// The ball's minimum and maximum horizontal velocity; the bounds of the
	// initial random velocity that you should choose (randomly +/-).
	public static final double VELOCITY_X_MIN = 1.0;
	public static final double VELOCITY_X_MAX = 3.0;

	// Animation delay or pause time between ball moves (ms)
	public static final double DELAY = 1000.0 / 60.0;

	// Number of turns 
	public static final int NTURNS = 3;
	
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	
	public void run() {
		
		// Set the window's title bar text
		setTitle("CS 106A Breakout");

		// Set the canvas size.  In your code, remember to ALWAYS use getWidth()
		// and getHeight() to get the screen dimensions, not these constants!
		setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		
		//create the bricks and circle.
		CreatBricks();
		CreatPaddle();
				
		/*
		 * At the beginning of each turn, create the ball, renew the paddleHit to 0;
		 * Set the canvas and display relating messages. 
		 * Give value to the initial velocity of the ball.
		 * Let the ball move according to the condition.
		 */
		
		for (int i = 0; i < NTURNS; i++) {
			
			CreatBall();
			GLabel begin= new GLabel("Click anywhere to start! ");
			add(begin,30,30);
			paddleHit=0;
			
			waitForClick();
			
			// give value to velocity
			vx=rgen.nextDouble(VELOCITY_X_MIN,VELOCITY_X_MAX);
			if (rgen.nextBoolean(0.5)) {
				vx=-vx;
			}
			vy=VELOCITY_Y;
			
			// display score on canvas
			score=new GLabel("Score: "+num);
			add(score,30,50);		
			
			while(!endTurn()) {
				remove(begin);
				BallMove();
				
				// remove all the bricks and win the game.
				if(NumOfBricks == 0) { 
					win=new GLabel("Congratulations! You win !");
					add(win,getWidth()/2 - win.getWidth()/2, getHeight()/2 + 40);
					remove(ball);
					break;
				}
			}
			remove(score);
		}
		
		// When the game end before removing all the bricks.
		GLabel end = new GLabel("End Game");
		add(end,getWidth()/2 - end.getWidth()/2,getHeight()/2);
		
	}
	
	/* 
	 * CreateBricks() is the function to set up all the bricks at the beginning.
	 * c indicates the column number of bricks and r indicates the row number of bricks.
	 * the color of bricks changes every two rows.
	 */
	
	private void CreatBricks(){
		for (int r = 0; r < NBRICK_ROWS; r++) {
			for (int c = 0; c < NBRICK_COLUMNS; c++) {
				double x = BRICK_SEP + c * BRICK_WIDTH + c * BRICK_SEP;
				double y = BRICK_Y_OFFSET + r * BRICK_HEIGHT + r * BRICK_SEP;
				GRect brick = new GRect(x,y, BRICK_WIDTH, BRICK_HEIGHT);
				
				add(brick);
				brick.setFilled(true);
				
				if (r < 2) {
					brick.setColor(Color.RED);
				}
				if (r >= 2 && r < 4) {
					brick.setColor(Color.ORANGE);
				}
				if (r >= 4 && r < 6) {
					brick.setColor(Color.YELLOW);
				}
				if (r >= 6 && r < 8) {
					brick.setColor(Color.GREEN);
				}
				if (r >= 8 && r < 10) {
					brick.setColor(Color.CYAN);
				}
			}
		}	
	}
	
	// Create an instance variable as paddle. 
	private GRect paddle;
	
	/*
	 * CreatePaddle() set up a rectangle in the middle of canvas to be the paddle.
	 * The location of the top left corner is the coordinates of the paddle. 
	 * addMouseListeners() let it move as soon as the mouse click.
	 */
	
	private void CreatPaddle() {
		double x = getWidth()/2 - PADDLE_WIDTH/2;
		double y = getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET;
		
		paddle = new GRect(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		addMouseListeners();
	}
	
	/*
	 * mouseEvent function let the paddle move horizontally according to the direction of mouse.
	 * the if statement makes sure that the paddle won't move beyond the boundary of canvas.
	 */
	
	public void mouseMoved(MouseEvent e) {
		
		double x = paddle.getX();
		double y = paddle.getY();
		GPoint last = new GPoint(x,y);
		if(e.getX() > PADDLE_WIDTH/2 && e.getX() + PADDLE_WIDTH/2 < getWidth()) {
			paddle.move(e.getX() - last.getX() - paddle.getWidth()/2, 0);
		}
	}

	/*
	 * Create instance variables as the ball and its speed (horizontal and vertical)
	 * The horizontal speed is random within a range, so create the RandomGenerator.
	 */
	
	private GOval ball;
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	

	// CreatBall function create the ball in the middle of canvas.
	private void CreatBall() {
		double x = getWidth()/2 - BALL_RADIUS;
		double y = getHeight()/2 - BALL_RADIUS;
		ball = new GOval(x,y,2 * BALL_RADIUS,2 * BALL_RADIUS);
		ball.setFilled(true);
		add(ball);			
	}
	
	/*
	 * Create instance as number of bricks to count the number of bricks removed. Win the game if it becomes 0.
	 * Create instance label score to help display the winning points on screen, it contains a part num.
	 * Create instance num to calculate the winning points. Increase one every time a brick is deleted.
	 * Create instance paddleHit to record the number of times the ball hit the paddle.
	 * Create instance win to display the winning message on screen when all the bricks are removed. 
	 */
	
	private int NumOfBricks = NBRICK_COLUMNS*NBRICK_ROWS;
	private GLabel score;
	private int num = 0;
	private int paddleHit = 0;
	private GLabel win;

	/*
	 * BallMove() defines how the ball moves with vx as horizontal speed, vy as vertical speed in the following situation:
	 * when it touches the left & right boundary of canvas: change direction of vx, vy remain unchanged.
	 * when it touches the top the the canvas, change direction of vy, vx remain unchanged. 
	 * when it touches the bricks: remove the colliding brick, change direction of vy, make changes to the instance variables.
	 * when it touches the paddle, change moving direction based on which side it touches the paddle.
	 */
	
	private void BallMove() {
		ball.move(vx, vy);
			
		//when it touches the left and right boundary of the canvas
		if(ball.getX() > getWidth() - 2 * BALL_RADIUS || ball.getX() <= 0) {
			bounceClip.play();
			vx = -vx;
		}
		
		//when it touches the top of canvas
		if(ball.getY() <= 0) {
			bounceClip.play();
			vy = -vy;
		}

		//get the object which the ball collide with
		GObject collider=getCollidingObject();
		
		/*
		 * When the ball touches the bricks, (exclude the case when the ball touches the score label at the top)
		 * change the direction of vy, vx remained unchanged
		 * remove the brick, the NumOfBricks minus one 
		 * update the score label. 
		 */
		
		if (collider != null && collider!=paddle && collider != score) {
			remove(collider);
			vy = -vy;
			bounceClip.play();
			NumOfBricks--;
			num++;
			remove(score);
			score = new GLabel("Score: "+num);
			add(score, 30,50);
		}
		pause (DELAY);	
		
		/*
		 * When the ball hit the paddle: increase paddHit by 1.
		 * to prevent it sticks with the paddle when it hit the left/right boundary of the paddle:
		 * when it touches somewhere left-hand side of the paddle, change the direction of vx, vy remain unchanged.
		 * when it touches the right-hand side of the paddle, change the direction of vx, vy remain unchanged. 
		 * in other cases, change the direction of vy, vx remain unchanged.
		 * Multiply vx, vy by 2 every 7 times the ball hit the paddle. 
		 */
		
		if (collider == paddle) {	
			//left-hand side of the paddle
			if(ball.getX() <= paddle.getX() - BALL_RADIUS){
				if(vx > 0){
					vx = -vx;
				}
				paddleHit++;
			//right-hand side of the paddle
			}else if(ball.getX() >= paddle.getX() + PADDLE_WIDTH - BALL_RADIUS){
				if(vx < 0){
					vx = -vx;
				}
				paddleHit++;
			//other cases
			}else {			
				vy = -vy;
				paddleHit++;
				bounceClip.play();
			
			// increase the speed of the ball every seven times it hit the paddle.
			if (paddleHit%7==0) {
				vx = 2*vx;
				vy = 2*vy;
				}
			}
		}
	}

	/*
	 * getCollidingObject() returns the object the ball collide with,
	 * check four corners
 	 */
	
	private GObject getCollidingObject() {
		double x = ball.getX();
		double y = ball.getY();	

		// top left corner
		if (getElementAt(x,y) != null) {
			return getElementAt(x,y);
		//top right corner
		}else if (getElementAt(x + 2*BALL_RADIUS,y) != null) {
			return getElementAt(x + 2*BALL_RADIUS,y);
		// bottom left corner
		}else if (getElementAt(x,y + 2*BALL_RADIUS) != null) {
			return getElementAt(x,y + 2*BALL_RADIUS);
		//bottom right corner
		}else if(getElementAt(x + 2*BALL_RADIUS,y + 2*BALL_RADIUS) != null) {
			return getElementAt(x + 2*BALL_RADIUS,y + 2*BALL_RADIUS);
		}else {
			return null;
		}		
	}	
	
	/*
	 * the boolean endTurn() helps check whether we need to end current turn or not. 
	 * end turn situation: the ball touches the bottom.
	 * return true if ball touches the bottom, return false otherwise.
	 */
	
	private boolean endTurn() {
		if(ball.getY() > getHeight()) {
			remove(ball);
		}
		return(ball.getY() > getHeight());
	}
}
