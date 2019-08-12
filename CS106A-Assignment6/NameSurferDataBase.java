/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.*;
import java.util.*;
import java.io.*;

public class NameSurferDataBase implements NameSurferConstants {

	private HashMap<String, int[]> database = new HashMap<String, int[]> ();
	
	
	/**
	 * Constructor: NameSurferDataBase(filename)
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file.  The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	
	public NameSurferDataBase(String filename) {
		try {
			Scanner file = new Scanner(new File(filename));
			while(file.hasNextLine()) {
				String line = file.nextLine();
				String[] lineData = line.split(" ");
				int[] ranks = new int[NDECADES];
				for (int i = 0; i < NDECADES; i++) {
					int rank = Integer.parseInt(lineData[i + 1]);
					ranks[i] = rank;
				}
				database.put(lineData[0].toLowerCase(), ranks);
			}
			file.close();
		}catch(IOException ex) {
			System.out.println("error opening file");
		}
	}

	/**
	 * Public Method: findEntry(name)
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists. If the name does not appear in the database, this
	 * method returns null.
	 */
	
	public NameSurferEntry findEntry(String name) {
		name = name.toLowerCase();
		if(database.containsKey(name)) {
			String nameSurferString = name;
			for(int i = 0; i < NDECADES; i++) {
				int rank = database.get(name)[i];
				String rankString = " " + rank;
				nameSurferString += rankString;
			}
			NameSurferEntry nameSurferEntry = new NameSurferEntry(nameSurferString);
			return nameSurferEntry;
		} else {
			return null;
		}
	}
}

