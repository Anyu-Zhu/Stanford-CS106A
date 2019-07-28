/*
 * File: ParaKarel.java
 * ------------------
 * This program will eventually play the ParaKarel game from Assignment 4
 * Words for guessing are randomly selected from ParaKarelLexicon.txt
 * The player has seven guesses before the game ends.
 * Name: Anyu Zhu
 * Section Leader: Peter Hansel
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class ParaKarel extends ConsoleProgram {

	/***********************************************************
	 *              CONSTANTS                                  *
	 ***********************************************************/
	
	/* The number of guesses in one game of ParaKarel */
	private static final int N_GUESSES = 7;
	/* The width and the height to make the karel image */
	private static final int KAREL_SIZE = 150;
	/* The y-location to display karel */
	private static final int KAREL_Y = 230;
	/* The width and the height to make the parachute image */
	private static final int PARACHUTE_WIDTH = 300;
	private static final int PARACHUTE_HEIGHT = 130;
	/* The y-location to display the parachute */
	private static final int PARACHUTE_Y = 50;
	/* The y-location to display the partially guessed string */
	private static final int PARTIALLY_GUESSED_Y = 430;
	/* The y-location to display the incorrectly guessed letters */
	private static final int INCORRECT_GUESSES_Y = 460;
	/* The fonts of both labels */
	private static final String PARTIALLY_GUESSED_FONT = "Courier-36";
	private static final String INCORRECT_GUESSES_FONT = "Courier-26";
	
	/***********************************************************
	 *              Instance Variables                         *
	 ***********************************************************/
	
	/* An object that can produce pseudo random numbers */
	private RandomGenerator rg = new RandomGenerator();
	
	/* The canvas */
	private GCanvas canvas = new GCanvas();
	
	/* The string which represent the player's final guess result (after 7 turns) */
	private String guessResult;
	
	/* The turns the player have */
	private int n;
	
	/* Lines which connect Karel and parachute, remove one every time the player makes a wrong guess */
	private GLine line1;
	private GLine line2;
	private GLine line3;
	private GLine line4;
	private GLine line5;
	private GLine line6;
	private GLine line7;
	
	/* The Karel robot on canvas */
	private GImage Karel;
	private GImage flipKarel;
	
	/* A label on canvas which displays the user's partially guessed result, keep updating after every turn */
	private GLabel partiallyGuessed;
	/* A label which displays all the incorrect guesses by the user on canvas*/
	private GLabel incorrectGuessed;
	/* A string which contains all the wrong guesses by the user*/
	private String wrongGuesses;
		
	/***********************************************************
	 *                    Methods                              *
	 ***********************************************************/
	
	public void run() {
		println("Welcome to ParaKarel");	
		
		/*
		 * Get a random word and get the length of it.
		 * create a empty string to collect all the wrong guesses.
		 */
		
		String word = getRandomWord(); 
		int len = word.length();
		print("Your word looks like this: ");
		wrongGuesses = "";
		
		/*
		 * print out the initial status of the word (full of "-");
		 * print out the number of turns the user have.
		 */
		
		println(initial(len));
		println("You have " + N_GUESSES + " guesses left.");
		n = N_GUESSES;
		
		/*
		 * initialize the guess result;
		 * use a while loop to use all the turns, 
		 * add two labels on to canvas; 
		 * use if/else statement to deal with right/wrong guesses.
		 * reduce n by 1, remove one line from canvas every time the user make a wrong guess
		 */
		
		guessResult = initial(word.length());
		while (n > 0) {
			String guess = readLine("Your guess: ");
			canvas.remove(partiallyGuessed);
			partiallyGuessed = new GLabel(guessResult);
			partiallyGuessed.setFont(PARTIALLY_GUESSED_FONT);
			canvas.add(partiallyGuessed, canvas.getWidth()/2 - partiallyGuessed.getWidth()/2, PARTIALLY_GUESSED_Y);
			
			if(testWord(guess, word) == true) {
				println("That guess is correct.");
				guessResult = showWord(guess, word);
				println("Your word now looks like this: " + guessResult);
				println("You have " + n + " guesses left.");
				
				canvas.remove(partiallyGuessed);
				partiallyGuessed = new GLabel(guessResult);
				partiallyGuessed.setFont(PARTIALLY_GUESSED_FONT);
				canvas.add(partiallyGuessed, canvas.getWidth()/2 - partiallyGuessed.getWidth()/2, PARTIALLY_GUESSED_Y);
				
			}else {
				println("There are no " + guess + "'s in the word.");
				println("Your word now looks like this: " + guessResult);
				wrongGuesses += guess.toUpperCase();
				n--;
				println("You have " + n + " guesses left.");
				
				canvas.remove(incorrectGuessed);
				incorrectGuessed = new GLabel(wrongGuesses);
				incorrectGuessed.setFont(INCORRECT_GUESSES_FONT);
			    canvas.add(incorrectGuessed, canvas.getWidth()/2 - incorrectGuessed.getWidth()/2, INCORRECT_GUESSES_Y);
			}
			removeLine();
			
			/*
			 * if the player guesses all the words right before using up all the turns, 
			 * change n to -1 to end the while loop.
			 */
			
			if(matchWord(word, guessResult)) {
				n = -1;
			}
		}
		
		/*
		 * after using up all the turns, use if/else statement to test whether the final result matches with the word.
		 * if does not match and n is 0, Karel will flip and fall.
		 */
		
		if (matchWord(guessResult, word)) {
			println("You win.");
			println("The word was: " + word);
		}else {
			println("You're completely hung.");
			println("The word was: " + word);
			
			if (n == 0) {
				flipKarel();
			}
		}
		
	}
	
	/*
	 * init() set up the canvas , it add background, parachute, strings, and word labels to canvas. 
	 */
	
	public void init() {
		add(canvas);
		drawBackground();
		drawParachute();
		addKarel();
		addLine();
		addLabel();
	}
	
	/**
	 * testWord() return a boolean and test whether the input by the user matches with any of the characters of the word.
	 * @param str: the input string 
	 * @param word: the word the player should guess. 
	 * @return boolean: return true if find there is at least one character in the word match with the input, else return false.
	 */
	
	private boolean testWord(String str, String word) {
		int count = 0;
		while (count < word.length()) {
			String i = "" + word.charAt(count);
			if (i.equalsIgnoreCase(str)) {
				count++;
				return true;
			}else {
				count++;
			}
		}
		return false;
	}
	
	/**
	 * initial() function generates the initial status of guess results.
	 * @param n: an integer(should be the length of the word to guess when implemented)
	 * @return a string full of "-", with length of the word.
	 */
	
	private String initial(int n) {
		String rst = "";
		for (int i = 0; i < n; i++) {
			rst += "-";
		}
		return rst;
	}
	
	/**
	 * showWord() function returns the current status of guess result after the user's guess. 
	 * @param str: the user's input 
	 * @param word: the word which the user should guess
	 * @return the result after the user's guess: replace the "-" with the right character. 
	 */
	
	private String showWord(String str, String word) {
		str = str.toUpperCase();
		for (int i = 0; i < word.length(); i++) {
			if(word.charAt(i) == str.charAt(0)) {
				guessResult = guessResult.substring(0,i) + str + guessResult.substring(i + 1);
			}
		}
		return guessResult;
	}
	
	/**
	 * matchWord() function test whether the guess result completely match with the word for guessing.
	 * @param str1: the first string (the player's guess result at the end of the game)
	 * @param str2: the second string (the word which the player should guess out)
	 * @return a boolean: return true if two strings are exactly the same (no matter upper/lower case), 
	 * if not match (the final result still contain "-"), return false
	 */
	
	private boolean matchWord(String str1, String str2) {
		if (str1.equalsIgnoreCase(str2)) {
			return true;
		}
		return false;
	}
	
	/*
	 * drawBackground() add the sky background to the canvas.
	 */
	
	private void drawBackground() {
		GImage bg = new GImage("background.jpg");
		bg.setSize(canvas.getWidth(), canvas.getHeight());
		canvas.add(bg,0,0);
	}
	
	/*
	 * drawParachute() add the parachute to the canvas.
	 */
	
	private void drawParachute() {
		GImage parachute = new GImage("parachute.png");
		parachute.setSize(PARACHUTE_WIDTH, PARACHUTE_HEIGHT);
		canvas.add(parachute, canvas.getWidth()/2 - PARACHUTE_WIDTH/2, PARACHUTE_Y);
	}
	
	/*
	 * addKarel() add a Karel robot below the parachute on the canvas.
	 */
	
	private void addKarel() {
		Karel = new GImage("karel.png");
		Karel.setSize(KAREL_SIZE, KAREL_SIZE);
		canvas.add(Karel, canvas.getWidth()/2 - KAREL_SIZE/2, KAREL_Y);
	}
	
	/*
	 * addLine() add seven lines to connect Karel and the parachute.
	 */
		
	private void addLine() {
		line1 = new GLine(canvas.getWidth()/2 - PARACHUTE_WIDTH/2, PARACHUTE_Y + PARACHUTE_HEIGHT, canvas.getWidth()/2, KAREL_Y);
		line2 = new GLine(canvas.getWidth()/2 - PARACHUTE_WIDTH/2 + PARACHUTE_WIDTH/6, PARACHUTE_Y + PARACHUTE_HEIGHT, canvas.getWidth()/2, KAREL_Y);
		line3 = new GLine(canvas.getWidth()/2 - PARACHUTE_WIDTH/2 + PARACHUTE_WIDTH/3, PARACHUTE_Y + PARACHUTE_HEIGHT, canvas.getWidth()/2, KAREL_Y);
		line4 = new GLine(canvas.getWidth()/2, PARACHUTE_Y + PARACHUTE_HEIGHT, canvas.getWidth()/2, KAREL_Y);
		line5 = new GLine(canvas.getWidth()/2 + PARACHUTE_WIDTH/6, PARACHUTE_Y + PARACHUTE_HEIGHT, canvas.getWidth()/2, KAREL_Y);
		line6 = new GLine(canvas.getWidth()/2 + PARACHUTE_WIDTH/3, PARACHUTE_Y + PARACHUTE_HEIGHT, canvas.getWidth()/2, KAREL_Y);
		line7 = new GLine(canvas.getWidth()/2 + PARACHUTE_WIDTH/2, PARACHUTE_Y + PARACHUTE_HEIGHT, canvas.getWidth()/2, KAREL_Y);
		
		canvas.add(line1);
		canvas.add(line2);
		canvas.add(line3);
		canvas.add(line4);
		canvas.add(line5);
		canvas.add(line6);
		canvas.add(line7);
		
	}
	
	/*
	 * removeLine() traces the turns the player still have, n is the number of chances the player still have.
	 * Remove one line every time the player input a wrong number.
	 */
	
	private void removeLine() {
		if (n == 6) {
			canvas.remove(line1);
		}
		if(n == 5) {
			canvas.remove(line7);
		}
		if(n == 4) {
			canvas.remove(line2);
		}
		if(n == 3) {
			canvas.remove(line6);
		}
		if(n == 2) {
			canvas.remove(line3);
		}
		if(n == 1) {
			canvas.remove(line5);
		}
		if(n == 0) {
			canvas.remove(line4);
		}
	}
	
	/*
	 * flipKarel() removes the original Karel and displays a reversed Karel on canvas.
	 * As the number of lines should be zero when Karel flipped, Karel should drop down slowly to the bottom. 
	 */
	
	private void flipKarel() {
		canvas.remove(Karel);
		flipKarel = new GImage("karelFlipped.png");
		flipKarel.setSize(KAREL_SIZE, KAREL_SIZE);
		canvas.add(flipKarel, canvas.getWidth()/2 - KAREL_SIZE/2, KAREL_Y);
		
		while(!hasKarelFall(flipKarel)) {
			flipKarel.move(0, 4);
			pause(60);
		}
	}
	
	/*
	 * addLabel() adds two label on canvas: the partially-guessed result of the player; 
	 * and all the incorrect guesses by the player. 
	 */
	
	private void addLabel() {
		partiallyGuessed = new GLabel("");
	    canvas.add(partiallyGuessed, canvas.getWidth()/2 - partiallyGuessed.getWidth()/2, PARTIALLY_GUESSED_Y);
	    
	    incorrectGuessed = new GLabel("");
	    canvas.add(incorrectGuessed, canvas.getWidth()/2 - incorrectGuessed.getWidth()/2, INCORRECT_GUESSES_Y);
	}
	
	/**
	 * hasKarelFall() tests whether Karel has fall below the bottom of the canvas. 
	 * @param flipKarel: the falling object.
	 * @return a boolean: return true when Karel fall below the bottom.
	 */
	
	private boolean hasKarelFall(GImage flipKarel) {
		return flipKarel.getY() > getHeight();
	}

	/**
	 * Method: Get Random Word
	 * -------------------------
	 * This method returns a word to use in the ParaKarel game. It randomly selects a word 
	 * from the txt file: ParaKarelLexicon. 
	 */
	
	private String getRandomWord() {
		String str = "";
		
		try {
			Scanner scanner = new Scanner(new File("ParaKarelLexicon.txt"));
			ArrayList<String> wordList = new ArrayList<>();
			
			while (scanner.hasNextLine()) {
				wordList.add(scanner.nextLine());
			}
			scanner.close();
			int wordCount = wordList.size();
			int index = rg.nextInt(wordCount);
			str = wordList.get(index);
		}catch(IOException ex) {
			println("The error is: " + ex);
		}
		return str;
		
	}
	
}
