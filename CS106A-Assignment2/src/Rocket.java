/* This program displays a rocket.
 * define a constant to control the size of the rocket.
 * 
 * first draw the triangle-shaped head by using three for loops inside one for loop.
 * draw the middle line with one for loop to control the number of "=", and "+" on two sides
 * draw the upper part of the body with three for loops inside one for loops
 * the lower part of the body is of same method but reverse order
 * the bottom part is the same shape of the header, repeat drawHeader().
 */

import acm.program.*;

public class Rocket extends ConsoleProgram {
	
	private static final int SIZE = 5;
	public void run() {
		drawHead();
		drawMiddleLine();
		drawUpperBody();
		drawLowerBody();
		drawMiddleLine();
		drawHead(); 
		//head has the same shape with fire
	}
	
	private void drawHead() {
		for (int r=1; r<SIZE+1; r++) {
			print(" ");
			for (int i=0; i<SIZE-r; i++){
				print(" ");
			}
			for (int a=0; a<r; a++) {
				print("/");
			}
			for (int b=0; b<r; b++) {
				print("\\");
			}
			println(" ");
		}
	}
	
	private void drawMiddleLine() {
		print("+");
		for (int i=0; i<SIZE*2; i++) {
			print("=");
		}
		print("+");
		println(" ");
	}
	
	private void drawUpperBody() {
		for (int r=1; r<SIZE+1; r++) {
			print("|");
			for (int i=0; i<SIZE-r; i++) {
				print(".");
			}
			for (int a=0; a<r; a++) {
				print("/\\");
			}
			for (int j=0; j<SIZE-r; j++) {
				print(".");
			}
			print("|");
			println(" ");
		}
	}
	
	private void drawLowerBody() {
		for (int r=1; r<SIZE+1; r++) {
			print("|");
			for (int i=0; i<r-1; i++) {
				print(".");
			}
			for (int a=0; a<SIZE-r+1; a++) {
				print("\\/");
			}
			for (int j=0; j<r-1; j++) {
				print(".");
			}
			print("|");
			println(" ");
		}
	}
}
