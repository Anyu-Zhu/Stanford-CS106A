/*
 * File: ExtensionKarel.java
 * Karel in this program can locate the center point of the quad
 * and put down one beeper, no matter the size of the quad.
 * This program is an extension of MidPointFinding
 * 
 * Should load with the world where Karel has infinite beepers in its bag
 * 
 */

import stanford.karel.*;

public class FindCenterKarel extends SuperKarel {
	public void run() {
		LocateBottomCenter();
		LocateTopCenter();
		turnLeft();
	}
	
	private void LocateBottomCenter() {
		putBeepersToEnd();
		while(frontIsClear() && noBeepersPresent()) {
			FindMidPoint();
		}
		clearOtherBeepers();
		
		turnAround();
		while(noBeepersPresent()) {
			move();
		}
	}
	
	private void LocateTopCenter(){
		turnRight();
		putColBeepers();
		while(frontIsClear() && noBeepersPresent()) {
			FindMidPoint();
		}
		clearOtherBeepers();
		
		turnAround();
		while(noBeepersPresent()) {
			move();
		}
	}
	
	private void putBeepersToEnd() {
		putBeeper();
		while (frontIsClear()) {
			move();
		}
		turnAround();
		putBeeper();
		move();
	}
	
	private void putColBeepers() {
		while (frontIsClear()) {
			move();
		}
		turnAround();
		putBeeper();
		move();
	}
	
	private void FindMidPoint() {
		while(frontIsClear() && noBeepersPresent()) { 
			move();
		}
		turnAround();
		move();
		if(noBeepersPresent()) {
			putBeeper();
			move();
		}
	}
	
	private void clearOtherBeepers() {
		while(frontIsClear()) {
			pickBeeper();
			move();
		}
		pickBeeper();
		turnAround();
		while(noBeepersPresent()) {
			move();
		}
		move();
		while(frontIsClear()) {
			pickBeeper();
			move();
		}
		pickBeeper();
	}
}