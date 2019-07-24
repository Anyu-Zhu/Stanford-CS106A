/*This program display a sequence of the input number, and the round will end when result become 1
 * if the boolean input is y, the program will ask you to renew the input, if is n, the program will stop. 
 * 
 * first deal with the case when input is 1
 * then create a boolean to decide whether quit or not.
 * use a while loop containing a if & else loop to calculate the result.
 * add one to integer step after every calculation.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		println("This program computes Hailstone sequence.");
		
		int num = readInt("Enter a number: ");
		
		if (num !=1) {
			calculate(num);
		}else {
			println("It took 0 steps to reach 1.");
		}
		
		boolean runAgain = readBoolean("Run agian? ", "y","n");
		
		while (runAgain) {
			num = readInt("Enter a number: ");
			calculate(num);
			runAgain = readBoolean("Run agian? ", "y","n");
		}
		println("Thank you for using Hailstone!");
		
	}
	
	private void calculate(int num) {
		int steps = 0;
		while (num != 1) {
			// when the input is odd
			if (num%2 == 1) {
				int rst = 3*num + 1;
				println(num + " is odd, so I make 3n+1: " + rst);
				steps++;
				num = rst;
			}else {
				// when the input is even
				int rst = num/2;
				println(num + " is even, so I take half: " + rst);
				steps++;
				num = rst;
			}
		}
		println("It took " + steps + " steps to reach 1 ");
	}
	
}
