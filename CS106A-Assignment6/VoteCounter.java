/*
 * File: VoteCounter.java
 * ---------------------
 * A sandcastle program that uses collections to tally votes 
 */

import acm.program.*;
import java.util.*;

public class VoteCounter extends ConsoleProgram {
	public void run() {
		ArrayList<String> votes = new ArrayList<String>();
		votes.add("Zaphod Beeblebrox");
		votes.add("Arthur Dent");
		votes.add("Trillian McMillian");
		votes.add("Zaphod Beeblebrox");
		votes.add("Marvin");
		votes.add("Mr. Zarniwoop");
		votes.add("Trillian McMillian");
		votes.add("Zaphod Beeblebrox");
		printVoteCounts(votes);
	}
	
	/*
	 * Your job is to implement this method according to 
	 * the problem specification. 
	 */
	
	Map<String, Integer> voteCount = new HashMap<String, Integer> ();
	private void printVoteCounts(ArrayList<String> votes) {
		for (String candidate: votes) {
			if(voteCount.containsKey(candidate)) {
				voteCount.put(candidate, voteCount.get(candidate)+1);
			} else {
				voteCount.put(candidate, 1);
			}
		}
		for(String key: voteCount.keySet()) {
			int count = voteCount.get(key);
			println("Votes for " + key + " has count " + count);
		}
	}
}
