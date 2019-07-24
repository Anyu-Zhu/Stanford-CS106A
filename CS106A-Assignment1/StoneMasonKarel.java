/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	
	public void run() {
		BUpward();
		ReturnFromTop();
		BDownward();
		ReturnFromBottom();
		BUpward();
		ReturnFromTop();
		BDownward();
		turnLeft();
	}
	private void BUpward() {
		while(noBeepersPresent()) {
			putBeeper();
		}
		turnLeft();
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
			}
			move();
			while(noBeepersPresent()) {
				putBeeper();
			}
		}
	}
	
	private void BDownward() {
		while(noBeepersPresent()) {
			putBeeper();
		}
		turnRight();
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
			}
			move();
			while(noBeepersPresent()) {
				putBeeper();
			}
		}
	}
	
	private void ReturnFromTop() {
		turnRight();
		move();move();move();move();
		if(leftIsClear()) {
			BUpward();
			turnRight();
		}
	}
	
	private void ReturnFromBottom() {
		turnLeft();
		move();move();move();move();
		if(rightIsClear()) {
			BUpward();
			turnLeft();
			BDownward();
		}
	}
}