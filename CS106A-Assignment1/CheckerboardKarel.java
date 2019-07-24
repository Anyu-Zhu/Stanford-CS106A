/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	public void run() {
		drawLine();
		while(leftIsClear()) {
			LeftPosition();
			drawLine();
			if(rightIsClear()) {
				RightPosition();
				drawLine();
			}else {
				turnRight();
			}
		}
	}
	private void drawLine() {
		while (frontIsClear()) {
			putBeeper();
			if(frontIsClear()) {
				move();
				if(frontIsClear()) {
					move();
				}
			}
		}
	}
	
	private void LeftPosition() {
		turnLeft();
		move();
		turnLeft();
	}
	
	private void RightPosition() {
		turnRight();
		move();
		turnRight();
	}
}
