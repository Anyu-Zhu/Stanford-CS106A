/*
 * File: NameSurfer.java
 * ---------------------
 * Name: Anyu Zhu
 * Section Leader: Peter Hansel
 * This program implements the viewer for the baby-name database. 
 * It includes: initialization of the interface; draw backgrounds;
 * and display the points, lines, and labels of all the rank values of names.
 * 
 * ps: the valueOf() method used in this program is known from the Internet.
 */

import acm.graphics.*;
import acm.program.*;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import acm.util.*;

public class NameSurfer extends GraphicsProgram implements NameSurferConstants {
	
	private JTextField nameField;
	private NameSurferDataBase dataBase;
	private ArrayList<NameSurferEntry> arrayList;
	
	/*
	 * Create the interface, add buttons and text field.
	 * make the buttons respond to user's click or 'enter' button.
	 * Read the data base.
	 */
	
	public void init() {
		
		arrayList = new ArrayList<NameSurferEntry>();
		
		add(new JLabel("Name: "), NORTH);
		nameField = new JTextField(TEXT_FIELD_WIDTH);
		add(nameField, NORTH);
		add(new JButton("Graph"), NORTH);
		add(new JButton("Clear"), NORTH);
		nameField.addActionListener(this);
		nameField.setActionCommand("Graph");
		
		dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		addActionListeners();
	}

	/*
	 * This class is responsible for detecting when the buttons are clicked.
	 * If the entry is found in the data base, the entry will be added to the name surfer entry arraylist.
	 * If no matching entry is found, return an error message.
	 * 
	 * The clear command will delete all the things except the background. 
	 */
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == "Graph") {
			String name = nameField.getText();
			NameSurferEntry entry = dataBase.findEntry(name);
			if(entry != null) {
				arrayList.add(entry);
				redraw();
			} else {
				println("No entry found.");
			}
		}
		
		if (command == "Clear") {
			arrayList.clear();
			redraw();
		}
	}
	
	/** 
	 * This class is responsible for detecting when the the canvas
	 * is resized. This method is called on each resize!
	 */
	public void componentResized(ComponentEvent e) { 
		redraw();
	}
	
	/*
	 * redraw() function removes all the things on canvas and draw background again.
	 * use a for loop to go through the name surfer entry array list and display the new name.
	 */
	
	private void redraw() {
		removeAll();
		drawBackground();
		for (int i = 0; i < arrayList.size(); i++) {
			drawEntry(arrayList.get(i));
		}
	}
	
	/*
	 * This function creates the background:
	 * Use a for loop to add lines to represent the decades.
	 * add two lines at top & bottom by adopting the margin size.
	 */
	
	private void drawBackground(){
		double distance = (double) getWidth() / (double) NDECADES;
		for(int i = 0; i < NDECADES; i++) {
			add(new GLine(i * distance, 0, i * distance, getHeight()));
			String years = (1900 + 10 * i) + "";
			GLabel yearLabel = new GLabel(years);
			add(yearLabel, i * distance, getHeight() - GRAPH_MARGIN_SIZE + yearLabel.getHeight());
		}		
		add(new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE));
		add(new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE));
	}
	
	/*
	 * This function display the data of the input name. 
	 * Use a color array to contain four colors, change color every time the input changes.
	 * Use a for loop to go through each rank values of the name.
	 * Use "point" and "newPoint" to construct lines between each rank values.
	 * Then add labels to each point: change the first letter to capitalize;
	 * change 0 to "*"
	 */
	
	private void drawEntry(NameSurferEntry entry) {
		String name = entry.getName();
		Color[] colors = {Color.BLACK, Color.RED, Color.BLUE, Color.MAGENTA};
		Color color = colors[(arrayList.indexOf(entry)) % 4];

		GPoint point = new GPoint(0,0);
		
		for (int i = 0; i < NDECADES; i++) {
			int rank = entry.getRank(i);
			double x = getWidth() * (double) i / NDECADES;
			double y;
			if (rank == 0) {
				y = getHeight() - GRAPH_MARGIN_SIZE;
			} else {
				y = (getHeight() - 2 * GRAPH_MARGIN_SIZE) * (double) rank / (double) MAX_RANK + GRAPH_MARGIN_SIZE;
			}
			
			GPoint newPoint = new GPoint(x, y);
			String rankString = String.valueOf(rank);   
			
			if(i != 0) {
				GLine line = new GLine(point.getX(),point.getY(),newPoint.getX(),newPoint.getY());
				line.setColor(color);
				add(line);
			}
			
			String cap = name.substring(0,1);
			name = cap.toUpperCase() + name.substring(1);
			
			GLabel label;
			if (rankString.equals("0")) {
				label = new GLabel(name + " " + "*", newPoint.getX(),newPoint.getY());
			} else {
				label = new GLabel(name + " " + rankString, newPoint.getX(),newPoint.getY());
			}
			label.setColor(color);
			add(label);
			point = newPoint;
		}
	}
}
