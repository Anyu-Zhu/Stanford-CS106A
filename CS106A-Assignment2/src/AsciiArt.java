/* This program displays the logo of Deathy Hallows in Harry Potter series
 * 
 * I separate the picture to three parts: header, the circle inside, the bottom line
 * draw the triangle-shaped header with three for loops inside a for loop.
 * The circle inside can only be printed with separate orders.
 * use for loop to control the spaces at the beginning of each line.
 * print out the bottom line with a println()
 */

import acm.program.*;

public class AsciiArt extends ConsoleProgram {
	public void run() {
		println("CS 106A ASCII Art by Anyu Zhu");
		println("Logo of Deathy Hallows in Harry Potter");
		printHeader();
		printCircle();
		printBottomLine();		
	}
	
	private void printHeader() {
		for (int r=1; r<4; r++) {
			print(" ");
			for (int i=0; i<9-r; i++) {
				print(" ");
			}
			print("/");
			
			for (int j=0; j<r-1; j++) {
				print(" ");
			}
			print("|");
			
			for (int j=0; j<r-1; j++) {
				print(" ");
			}
			print("\\");
			
			println(" ");
		}		
		
	}
	
	private void printCircle() {
		for (int i=0;i<6;i++) {
			print(" ");
		}
		print("/.-----.\\");
		println(" ");
		
		for (int i=0;i<5;i++) {
			print(" ");
		}
		print("/\"   |   \"\\");
		println(" ");
		
		for (int i=0; i<4;i++) {
			print(" ");
		}
		print("/|    |    |\\");
		println();
		
		for (int i=0; i<3; i++) {
			print(" ");
		}
		print("/ |    |    | \\");
		println(" ");
		
		for (int i=0;i<2;i++) {
			print(" ");
		}
		print("/   \\   |   /   \\");
		println(" ");
	}
	
	private void printBottomLine() {
		println("  "+"+----'-----'----+");
	}
	

}
