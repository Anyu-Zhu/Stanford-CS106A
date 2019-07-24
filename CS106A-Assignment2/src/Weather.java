/* This program can return the highest, lowest, average temperatures, and the number of cold days.
 * first create a constant to define the value to quit.
 * use if statements inside while loops to deal with input temperatures.
 */

import acm.program.*;

public class Weather extends ConsoleProgram {
	
	private static final int QUIT_VALUE = -1;
	
	public void run() {
		
		println("CS 106A 'Weather Master 4000'! ");
		
		int temp = readInt("Next temperature (or -1 to quit)?");
		double sumTemp = 0;
		int coldDays=0;
		int Highest=temp;
		int Lowest=temp;
		int days=0;
		
		while(temp != QUIT_VALUE) {
			
			//sum of all temperatures
			sumTemp += temp;
			
			//calculate number of cold days
			days++;
			
			if(temp <50) {
				coldDays++;
			}
			
			//replace highest/lowest temperature if the next input is higher/lower
			if (temp > Highest) {
				Highest=temp;
			} else if (temp < Lowest) {
				Lowest=temp;
			}
			
			//whether to quit
			temp = readInt("Next temperature (or -1 to quit)?");
		}
		
		if (days>0) {
			
			//calculate average temperature
			double avg = sumTemp/days;
			
			println("Highest temperature = "+ Highest);
			println("Lowest temperature = "+ Lowest);
			println("Average = "+ avg);
			println(coldDays+" cold days.");
		}else {
			
			// deal with no input case
			println("No temperatures were entered.");
		}
	}
}
