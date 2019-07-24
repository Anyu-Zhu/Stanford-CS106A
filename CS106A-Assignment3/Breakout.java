/*
 * File: Breakout.java
 * -------------------
 * Name:Anyu Zhu
 * Section Leader:Peter Hansel
 * 
 * This file will eventually implement the game of Breakout, the original version.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

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
	public static final double BALL_RADIUS = 10;

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
		 * At the beginning of each turn, create the ball, 
		 * Give value to the initial velocity of the ball.
		 * Let the ball move according to the condition.
		 */
		
		for (int i = 0; i < NTURNS; i++) {
			
			CreatBall();
			waitForClick();
			
			// give value to velocity
			vx=rgen.nextDouble(VELOCITY_X_MIN,VELOCITY_X_MAX);
			if (rgen.nextBoolean(0.5)) {
				vx=-vx;
			}
			vy=VELOCITY_Y;		
			
			while(!endTurn()) {
				BallMove();
				
				// remove all the bricks and win the game.
				if(NumOfBricks == 0) { 
					remove(ball);
					break;
				}
			}
		}		
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
	

	/*
	 * CreatBall function create the ball in the middle of canvas.
	 */
	private void CreatBall() {
		double x = getWidth()/2 - BALL_RADIUS;
		double y = getHeight()/2 - BALL_RADIUS;
		ball = new GOval(x,y,2 * BALL_RADIUS,2 * BALL_RADIUS);
		ball.setFilled(true);
		add(ball);			
	}
	
	//Create instance as number of bricks to count the number of bricks removed. Win the game if it becomes 0.	 
	
	private int NumOfBricks = NBRICK_COLUMNS*NBRICK_ROWS;
	
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
			vx = -vx;
		}
		
		//when it touches the top of canvas
		if(ball.getY() <= 0) {
			vy = -vy;
		}

		//get the object which the ball collide with
		GObject collider=getCollidingObject();
		
		/*
		 * When the ball touches the bricks, 
		 * change the direction of vy, vx remained unchanged, remove the brick.
		 */
		
		if (collider != null && collider!=paddle) {
			remove(collider);
			vy = -vy;
			NumOfBricks--;
		}
		pause (DELAY);	
		
		/*
		 * When the ball hit the paddle: 
		 * to prevent it sticks with the paddle when it hit the left/right boundary of the paddle:
		 * when it touches somewhere left-hand side of the paddle, change the direction of vx, vy remain unchanged.
		 * when it touches the right-hand side of the paddle, change the direction of vx, vy remain unchanged. 
		 * in other cases, change the direction of vy, vx remain unchanged.
		 */
		
		if (collider == paddle) {	
			//left-hand side of the paddle
			if(ball.getX() <= paddle.getX() - BALL_RADIUS){
				if(vx > 0){
					vx = -vx;
				}				
			//right-hand side of the paddle
			}else if(ball.getX() >= paddle.getX() + PADDLE_WIDTH - BALL_RADIUS){
				if(vx < 0){
					vx = -vx;
				}
			//other cases
			}else {			
				vy = -vy;
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