/*
 * File: BitManipulation.java
 * ------------------
 * A sandcastle warmup for assignment 4
 * This program alters 0s and 1s in a series of bits.
 * 
 * Name: Anyu Zhu
 * Section Leader: Peter Hansel
 */

import acm.program.*;

public class BitManipulation extends ConsoleProgram {

	/**
	 * Method: xor
	 * ---------------
	 * Takes in an input string of 0s and 1s and alters the
	 * String so that all 0s become 1s and all 1s become
	 * 0s. This essentially "flips the bits" which is the 
	 * function of XOR - an important logic gate in CS!
	 * Example usage:
	 * xor("010101") -> "101010"
	 * xor("000111") -> "111000"
	 */
	
	/**
	 * @param input: the input series of bits
	 * @return: the result after changing all 0 to 1 and all 1 to 0
	 */
	
	private String xor(String input) {
		String output = ""; 
		String last = "";
		for (int i = 0; i < input.length(); i++) {
			char a = input.charAt(i);
			if (a != '0') {
				last = "0";
			}
			if (a == '0') {
				last = "1";
			}
			output = output + last;
		}	
		return output;
	}

	/****************************************************
	 *                  STARTER CODE                    *
	 * You can read this code, but you should not edit  *
	 * It (except to add more tests, if you so desire)  *
	 ****************************************************/

	// an instance variable which keeps track of how many tests 
	// have been run.
	private int testIndex = 0;
	
	// This run method executes a barrage of tests.
	public void run() {
		runTest("010101", "101010");
		runTest("000111", "111000");
		runTest("", "");
		runTest("1", "0");
		runTest("000000000000", "111111111111");
	}

	/**
	 * Method: Run Test
	 * ----------------
	 * Takes in an input and an expected output, and checks
	 * if the method altCap produces the correct output! Each
	 * call runs exactly one test.
	 */
	private void runTest(String input, String expectedOutput) {
		// call the altCaps method!
		String output = xor(input);
		
		// print out the results
		println("Test #:   " + testIndex);
		println("Input:    " + input);
		println("Output:   " + output);
		println("Expected: " + expectedOutput);
		
		// don't forget to use .equals when comparing strings
		if(expectedOutput.equals(output)) {
			println("Test passed");
		} else {
			println("Test failed");
		}
		
		// this adds a blank line so each test looks like a
		// paragraph of text
		println("");
		
		// keep track of how many tests have been run
		testIndex++;
	}





}
