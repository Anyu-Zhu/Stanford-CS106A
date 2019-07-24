/* This program finds real roots of a quadratic equation.
 * 
 * first, create three variables which are a, b, c in equations
 * calculate delta, which is used to decide the number of real roots
 * use three if statement to deal with "no real root", "one real root", "two real roots" situations
 */

import acm.program.*;

public class QuadraticEquation extends ConsoleProgram {
	public void run() {
		println("CS 106A Quadratic Solver!");
		int a = readInt("Enter a: ");
		int b = readInt("Enter b: ");
		int c = readInt("Enter c: ");
		
		double delta = (b*b-4*a*c);
		if (delta < 0) { 
			//situation where there is no root.
			println("No real roots");
		}
		
		if (delta == 0) {
			//situation where there is one root.
			double rootA = -(b/2*a);
			println("One root: "+rootA);
		}
		
		if (delta > 0) {
			//situation where there is two real roots.
			double rootA = (-b-Math.sqrt(delta))/(2*a);
			double rootB = (-b+Math.sqrt(delta))/(2*a);
			println("Two roots: "+rootA+" and "+rootB);
		}
	}
}
