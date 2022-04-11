package MealMate;

//Java program to create a blank text
//field of definite number of columns.
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import javax.swing.*;
import java.io.FileReader;

import javax.swing.*;
public class TextFileDO extends JFrame implements ActionListener {
	// JTextField
	static JTextField t;

	// JFrame
	static JFrame f;

	// JButton
	static JButton b;
	
	static JButton b2;

	// label to display text
	static JLabel l;

	// default constructor
	TextFileDO()
	{
	}

	// main class
	public static void main(String[] args)
	{
		// create a new frame to store text field and button
		f = new JFrame("textfield");

		// create a label to display text
		l = new JLabel("nothing entered");

		// create a new button
		b = new JButton("add");
		b2 = new JButton("remove");

		// create a object of the text class
		TextFileDO te = new TextFileDO();
		TextFileDO te2 = new TextFileDO();

		// addActionListener to button
		b.addActionListener(te);
		b2.addActionListener(te2);

		// create a object of JTextField with 16 columns
		t = new JTextField(16);

		// create a panel to add buttons and textfield
		JPanel p = new JPanel();

		// add buttons and textfield to panel
		p.add(t);
		p.add(b);
		p.add(l);
		p.add(b2);

		// add panel to frame
		f.add(p);

		// set the size of frame
		f.setSize(300, 300);

		f.show();
	}

	// if the button is pressed
	public void actionPerformed(ActionEvent e)
	{
		String s = e.getActionCommand();
		if (s.equals("add")) {
			try {
				FileWriter p = new FileWriter("pantry", true);
				String item = t.getText();
			//check to see if user wants to exit else write to file.
			
				p.write(item + "\r\n");
				p.close();
				l.setText(t.getText());

			// set the text of field to blank
				t.setText(" ");
			} catch (IOException e1) {
				System.out.println(e1); // prints error
			}

		} if (s.equals("remove")) {
			ArrayList<String> items = new ArrayList<String>();
			ArrayList<JLabel> labels = new ArrayList<JLabel>();
			int yLoc = 50;
			try {
				//read pantry file by using scanner
				File p = new File("pantry");
				Scanner inFile = new Scanner(p);
				//check to make sure the file has text on the next line and print
				while (inFile.hasNext()) {
					String line = inFile.nextLine();
					JLabel l = new JLabel(line);
					l.setBounds(50,yLoc, 250,20);
			        labels.add(l);
					//System.out.println(line);
					items.add(line);
					yLoc = yLoc + 20;
				}
				inFile.close();
			} catch (IOException e1) {
				System.out.println(e1); // prints error
			}
	        
			MealMate.removeFromPantry(items, t.getText());
			t.setText(" ");
		}
	}
}
