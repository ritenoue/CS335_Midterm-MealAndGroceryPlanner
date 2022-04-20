//package MealMate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.io.FileReader;

public class MealMate extends JFrame implements ActionListener {
	static JTextField t;
	static JTextField tNoGL;
	static JLabel l;
	
	public static void main(String[] args) {
		MealMate m = new MealMate();
		System.out.println("Welcome to Meal Mate!\nYour grocery helper!\n");
		System.out.println("0: Add pantry items\n1: View pantry items\n2: Remove pantry items");
		System.out.println("3: View recipes");
		System.out.println("4: Create grocery list\n5: View grocery lists\n6: Remove grocery list\n");
		System.out.println("Type the number of the action you would like to take");
		
		//Select action scanner. The user has 50 turns to do what they want.
		Scanner r = new Scanner(System.in);
		String[] response = new String[50];
		
		//iterate through response
		for(int i = 1; i < response.length; i++) {
			//if-else to take the users to each action.
			//get action on the current space in response array
			response[i] = r.nextLine();
			if (response[i].equals("0")) {
				//go to add
				addToPantry();
			}
			else if (response[i].equals("1")) {
				//go to view
				m.viewPantry(false);
			}
			else if (response[i].equals("2")) {
				//go to remove
				m.viewPantry(true);
			}
			else if (response[i].equals("3")) {
				//go to view
				viewRecipe(false);
			}
			else if (response[i].equals("4")) {
				//go to view then create
				makeGroceryList();
			}
			else if (response[i].equals("5")) {
				//go to view
				m.viewGroceryList(false);
			}
			else if (response[i].equals("6")) {
				//go to remove
				m.viewGroceryList(true);
			}
			else {
				break;
			}
			System.out.println("Choose another option or type EXIT to stop.");
		}
		r.close(); // close the scanner. This is the only scanner that can be closed. otherwise it throws an error.
		System.out.println("GoodBye");
		System.exit(0); // Terminates program
	}
	
	public void viewPantry(Boolean remove) {
		JFrame fPantry = new JFrame("View Pantry");
		//print all pantry items
		ArrayList<String> items = new ArrayList<String>();
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		int yLoc = 50;
		try {
			//read pantry file by using scanner
			File p = new File("MealMate/pantry");
			//File p = new File("pantry");
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
		} catch (IOException e) {
			System.out.println(e); // prints error
		}
        
        for (int i = 0; i < labels.size(); i++) {
        	fPantry.add(labels.get(i));
        }
        
        yLoc = yLoc + 30;
        JLabel li = new JLabel("Type what words?");
        li.setBounds(50, yLoc, 250, 20);
        fPantry.add(li);
        
        //textfield
        yLoc = yLoc + 20;
        t = new JTextField(100);
        t.setBounds(50, yLoc, 400, 20);
        fPantry.add(t);
        
        //buttons
        JButton bAdd = new JButton("Add To Pantry");
        JButton bRemove = new JButton("Remove From Pantry");
        yLoc = yLoc+20;
        bAdd.setBounds(50, yLoc, 200, 20);
        bRemove.setBounds(300, yLoc, 200, 20);
        bAdd.setActionCommand("Add to Pantry");
        bRemove.setActionCommand("Remove from Pantry");
        bAdd.addActionListener(this);
        bRemove.addActionListener(this);
        bAdd.setEnabled(true);
        bRemove.setEnabled(true);
        fPantry.add(bAdd);
        fPantry.add(bRemove);
        
        yLoc = yLoc + 50;
        JLabel lba = new JLabel("Or add via file upload.");
        lba.setBounds(50, yLoc, 250, 20);
        fPantry.add(lba);
        JButton bBulk = new JButton("Choose File");
        bBulk.setBounds(50, yLoc+20, 200, 20);
        bBulk.setActionCommand("Choose File");
        bBulk.setEnabled(true);
        bBulk.addActionListener(this);
        fPantry.add(bBulk);
        
        fPantry.setBounds(1000,1000,1000,1000);
		fPantry.getContentPane().setLayout(null);
		fPantry.setLocationRelativeTo(null);
        fPantry.setVisible(true);
	}
	
	public static void bulkAddToPantry() {
		//file upload
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int status = jfc.showOpenDialog(null);
        
        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            jfc.setSelectedFile(selectedFile);
            System.out.println(selectedFile.getParent());
            System.out.println(selectedFile.getName());
			try {
			// Make a list of all current pantry items to check for possible repeats
			String line = "";
			FileReader readPantry = new FileReader ("MealMate/pantry");
			//FileReader readPantry = new FileReader ("pantry");
			Scanner pantryScan = new Scanner (readPantry);
			ArrayList<String> pantryItems = new ArrayList<String>(); // all og pantry items
			while (pantryScan.hasNextLine()){
				line = pantryScan.nextLine();
				line = line.toLowerCase();
				line = line.replaceAll("[\\d]", ""); // remove any numeric symbols
				pantryItems.add(line);
			}
			pantryScan.close();

			// Read new file and get all items
			FileReader fr = new FileReader(selectedFile.getPath());
			Scanner infile = new Scanner (fr);

			ArrayList<String> newPantryItems = new ArrayList<String>(); // get all the new pantry items
			line = ""; // reset line value to use in following loop
			String delimiters = ",\\s*|\\.\\s*"; // execptions to split string
			
			// loop to get all the items from the user input file
			while (infile.hasNextLine()) {
				line = infile.nextLine();
				if (line.isEmpty()){ // gets rid of empty lines
					continue;
				}
				String[] lineSplit = line.split(delimiters, 0); // gets rid of any commas or puncuation
				for(String newLine: lineSplit) {
					line = newLine.toLowerCase();
					newPantryItems.add(line);
				}
			}
			fr.close();
			infile.close();

			FileWriter p = new FileWriter("MealMate/pantry", true);
			//FileWriter p = new FileWriter("pantry", true);
			int count = 0;
			boolean isThere = false;
			String currentOGPantry = "";

			// check for repeats and only add new pantry items
			for (int y=count; y<newPantryItems.size();y++){
				String currentNewPantry = newPantryItems.get(y);
				for(int z=0; z<pantryItems.size(); z++) {
					currentOGPantry = pantryItems.get(z);
					if(currentOGPantry.equals(currentNewPantry)){
						isThere = true;
						break;
					}
				}
				if (isThere == false){
					p.write(currentNewPantry + "\r\n");
				}
				count++;
				isThere = false;
			}
			p.close();
			}
			catch(IOException e){
				System.out.println(e); // prints error
			}
			System.out.println(selectedFile.getName() + " was merged with current pantry file.");
          } else if (status == JFileChooser.CANCEL_OPTION) {
            System.out.println("canceled");
          }
        
	}
	
	public static void addToPantry() {
		try {
			FileWriter p = new FileWriter("MealMate/pantry", true);
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
	}
	
	public static void removeFromPantry(ArrayList<String> in, String item) {
		ArrayList<String> pItems = in;
		
				FileWriter m;
				try {
					m = new FileWriter("MealMate/pantry");
					//m = new FileWriter("pantry");
					m.write("");
					m.close();
				} catch (IOException e1) {

					System.out.println(e1); // prints error
				}
				
				FileWriter p;
				try {
					//find and remove. Using the ArrayList sent in from viewPantry()
					p = new FileWriter("MealMate/pantry", true);
					//p = new FileWriter("pantry", true);
					for (int j = 0; j < pItems.size(); j++) {
						if (item.equals(pItems.get(j))) {
							pItems.remove(j);
						}
						else {
							continue;
						}
					}
					
			//write to file
					for (int l = 0; l < pItems.size(); l++) {
						p.write(pItems.get(l) + "\r\n");
					}
					p.close();
				} catch (IOException e) {
					System.out.println(e); // prints error
				}
			} //end else

	
	public static ArrayList<Recipe> viewRecipe(Boolean gList) {
		JFrame f = new JFrame("View Recipes");
		f.setBounds(1000,1000,1000,1000); 
		f.getContentPane().setLayout(null);
		f.setLocationRelativeTo(null);
        f.setVisible(true);
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		try {
			if (gList == false) {
				JLabel l = new JLabel("These are your available recipes.");
				l.setBounds(50,20, 250,20);
		        f.add(l);
			}
			//add to lists to prepare create objects
			File p = new File("MealMate/recipes");
			//File p = new File("recipes");
			Scanner inFile = new Scanner(p);
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
			int i = 0;
			while (inFile.hasNext()) {
				//even lines are added to names and odd to items
				if (i % 2 == 0) {
					String line = inFile.nextLine();
					names.add(line);
				}
				else {
					String line = inFile.nextLine();
					ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(line.split(",")));
					items.add(ingredients);
				}
				i++;
			}
			inFile.close();
			
			//make objects. print if not making a grocery list and call makeGroceryList if true.
			if (gList == false) {
				int yLoc = 200;
				for (int j = 0; j < names.size(); j++) {
					Recipe r = new Recipe(names.get(j), items.get(j));
					recipes.add(r);
					JLabel li = new JLabel(r.getName());
					li.setBounds(75,yLoc, 250,20);
			        labels.add(li);
					yLoc = yLoc + 20;
					//System.out.println(r.getName());//convert to JLabels
				}
				
				for (int l = 0; l < labels.size(); l++) {
		        	f.add(labels.get(l));
		        }
			}
			else {
				for (int k = 0; k < names.size(); k++) {
					Recipe r = new Recipe(names.get(k), items.get(k));
					recipes.add(r);
				}
				//makeGroceryList(recipes);
			}
		} catch (IOException e) {
			System.out.println(e); // prints error
		}
		return(recipes); //I don't think this does anything
	}
	
	//added add/remove. without functionality
	public void viewGroceryList(Boolean remove) {
		JFrame fGL = new JFrame("View Grocery Lists");
        
        ArrayList<JLabel> labelsTitle = new ArrayList<JLabel>();
        ArrayList<JLabel> labelsIngredient = new ArrayList<JLabel>();
        
		ArrayList<GroceryList> gLists = new ArrayList<GroceryList>();
		try {
			JLabel l = new JLabel("These are your available Grocery Lists.");
			l.setBounds(50,20, 250,20);
	        fGL.add(l);
			//add to lists to prepare create objects
			File p = new File("MealMate/groceryList");
			//File p = new File("groceryList");
			Scanner inFile = new Scanner(p);
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
			int i = 0;
			while (inFile.hasNext()) {
				//Even lines are the titles and odd lines are the grocery items
				if (i % 2 == 0) {
					String line = inFile.nextLine();
					names.add(line);
				}
				else {
					String line = inFile.nextLine();
					line = line.substring(1, line.length( ) -1); //remove one set of brackets.
					ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(line.split(", ")));
					items.add(ingredients);
				}
				i++;
			}
			inFile.close();
			//create grocery list object and add them to an ArrayList and print.
			int yLocTitle = 50;
			int yLocLabs = 0;
			for (int j = 0; j < names.size(); j++) {
				GroceryList r = new GroceryList(names.get(j), items.get(j));
				gLists.add(r);
				//System.out.println(r.getName()); //convert to JLabels
				JLabel li = new JLabel(r.getName());
				li.setBounds(75,yLocTitle, 250,20);
		        labelsTitle.add(li);
		        yLocLabs = yLocTitle + 20;
				yLocTitle = yLocTitle + 100;
				for (int k = 0; k < r.getIngredients().size(); k++) {
					//System.out.println("  " + r.getIngredients().get(k)); //convert to JLabel
					JLabel ll = new JLabel(r.getIngredients().get(k));
					ll.setBounds(100,yLocLabs, 250,20);
			        labelsIngredient.add(ll);
					yLocLabs = yLocLabs + 20;
				} 
			}
			
			for (int k = 0; k < labelsTitle.size(); k++) {
	        	fGL.add(labelsTitle.get(k));
	        	for (int r = 0; r < labelsIngredient.size(); r++) {
	        		fGL.add(labelsIngredient.get(r));
	        	}
	        }
			
			//make list
			yLocLabs = yLocLabs+50;
			JButton bGL = new JButton("Make Grocery List");
			bGL.setBounds(50, yLocLabs, 200, 20);
			bGL.setActionCommand("Make Grocery List");
	        bGL.setEnabled(true);
	        bGL.addActionListener(this);
			fGL.add(bGL);
			
			//remove list
			yLocLabs = yLocLabs+30;
			JLabel lRemove = new JLabel("Type which list you want to delete.");
			lRemove.setBounds(50, yLocLabs, 250, 20);
			fGL.add(lRemove);
			
			yLocLabs = yLocLabs+20;
			tNoGL = new JTextField(100);
			tNoGL.setBounds(50, yLocLabs, 400, 20);
			fGL.add(tNoGL);
			
			yLocLabs = yLocLabs+20;
			JButton bNoGL = new JButton("Delete Grocery List");
			bNoGL.setBounds(50, yLocLabs, 400, 20);
			bNoGL.setActionCommand("Delete Grocery List");
	        bNoGL.setEnabled(true);
	        bNoGL.addActionListener(this);
			fGL.add(bNoGL);
		} catch (IOException e) {
			System.out.println(e); // prints error
		}
		
		fGL.setBounds(1000,1000,1000,1000); 
		fGL.getContentPane().setLayout(null);
		fGL.setLocationRelativeTo(null);
        fGL.setVisible(true);
	}

	public static void makeGroceryList() {
		JFrame fMakeGL = new JFrame("Make Grocery List");
		
		JLabel l1 = new JLabel("Here is where you make your Grocery List.");
		int yLoc = 20;
		l1.setBounds(50,yLoc,250,20);
		fMakeGL.add(l1);
		JLabel l2 = new JLabel("Enter a title for your Grocery List and then each recipe you want.");
		yLoc = yLoc + 20;
		l2.setBounds(50,yLoc,250,20);
		fMakeGL.add(l2);
		
		boolean gList = false;
		
		//print available recipes from the list of recipes code from viewRecipes()
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		try {
			yLoc = yLoc + 20;
			if (gList == false) {
				JLabel l = new JLabel("These are your available recipes.");
				l.setBounds(50,yLoc, 250,20);
		        fMakeGL.add(l);
			}
			//add to lists to prepare create objects
			File p = new File("MealMate/recipes");
			//File p = new File("recipes");
			Scanner inFile = new Scanner(p);
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
			int i = 0;
			while (inFile.hasNext()) {
				//even lines are added to names and odd to items
				if (i % 2 == 0) {
					String line = inFile.nextLine();
					names.add(line);
				}
				else {
					String line = inFile.nextLine();
					ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(line.split(",")));
					items.add(ingredients);
				}
				i++;
			}
			inFile.close();
			
			//make objects. print if not making a grocery list and call makeGroceryList if true.
			if (gList == false) {
				yLoc = 100;
				for (int j = 0; j < names.size(); j++) {
					Recipe r = new Recipe(names.get(j), items.get(j));
					recipes.add(r);
					JLabel li = new JLabel(r.getName());
					li.setBounds(75,yLoc, 250,20);
			        labels.add(li);
					yLoc = yLoc + 20;
				}
				
				for (int l = 0; l < labels.size(); l++) {
		        	fMakeGL.add(labels.get(l));
		        }
			}
			else {
				for (int k = 0; k < names.size(); k++) {
					Recipe r = new Recipe(names.get(k), items.get(k));
					recipes.add(r);
				}
				//makeGroceryList(recipes);
			}
			//Title
			JTextField tName = new JTextField(100);
			yLoc = yLoc+20;
			tName.setBounds(50, yLoc, 400, 20);
			fMakeGL.add(tName);
			yLoc = yLoc+20;
			JLabel lName = new JLabel("Type Grocery List name here.");
			lName.setBounds(50,yLoc,250,20);
			fMakeGL.add(lName);
			
			JTextArea tRecipes = new JTextArea();
			yLoc = yLoc+30;
			tRecipes.setBounds(50,yLoc,400,150);
			fMakeGL.add(tRecipes);
			
			yLoc = yLoc+20;
			JLabel lRecipes = new JLabel("Type desired resipes here.");
			lRecipes.setBounds(50,yLoc,250,20);
			fMakeGL.add(lRecipes);
			
			yLoc = yLoc+20;
			JButton bList = new JButton("Add to Grocery List");
			bList.setBounds(50, yLoc, 400, 20);
			fMakeGL.add(bList);
			
		} catch (IOException e) {
			System.out.println(e); // prints error
		}
		fMakeGL.setBounds(1000,1000,1000,1000); 
		fMakeGL.getContentPane().setLayout(null);
		fMakeGL.setLocationRelativeTo(null);
        fMakeGL.setVisible(true);
		
		//get recipes the user  wants on their grocery list.
		//convert to text area
		//System.out.println("Type what recipe you would like to shop for and press ENTER or type EXIT.");
		//ArrayList<String> toCompare = new ArrayList<String>();
		//Boolean exit = false;
		//Scanner i = new Scanner(System.in);
		//while (exit == false) {
			//String item = i.nextLine();
			//Check to see if the user is finished.
			//if (item.equals("EXIT")) {
				//exit = true;
			//}
			//else {
				//toCompare.add(item);
			//}
		//}
		
		//get pantry and add to ArrayList
		ArrayList<String> pantry = new ArrayList<String>();
		try {
			File p = new File("MealMate/pantry");
			//File p = new File("pantry");
			Scanner inFile = new Scanner(p);
			while (inFile.hasNext()) {
				String line = inFile.nextLine();
				pantry.add(line);
			}
			inFile.close();
		} catch (IOException e) {
			System.out.println(e); // prints error
		}
		
		//compare. Send all the recipes, pantry items, selected recipes, and gList tile.
		//compareRecipeGrocery(recipes, pantry, toCompare, title);
	}
	
	public static void compareRecipeGrocery(ArrayList<Recipe> recipes, ArrayList<String> pantry, ArrayList<String> toCompare, String title) {
		
		//initialize list of grocery list items
		ArrayList<String> groceryItems = new ArrayList<String>();
		
		//iterate toCompare
		for (int i = 0; i < toCompare.size(); i++) {
			//iterate recipes to find the same recipe
			for (int j = 0; j < recipes.size(); j++) {
				if (toCompare.get(i).equals(recipes.get(j).getName())) {
					//compare to pantry. iterate recipe ingredients
					for (int k = 0; k < recipes.get(j).getIngredients().size(); k++) {
						int pItemCount = 0;
						//iterate pantry.
						for (int l = 0; l < pantry.size(); l++) {
							if (!recipes.get(j).getIngredients().get(k).equals(pantry.get(l).toLowerCase())) {
								pItemCount++;
							}
							else {
								continue;
							}

						}
						//add missing ingredients. pItemCount should be the same size as the pantry if 
						//the item needs to be added to the grocery list.
						if (pItemCount == pantry.size()) {
							groceryItems.add(recipes.get(j).getIngredients().get(k));
						}
						else {
							continue;
						}
					}
				}
				else {
					continue;
				}
			}
		}
		
		//send to class constructor and print the groceryList 
		GroceryList gList = new GroceryList(title, groceryItems);
		System.out.println(gList.getName());
		System.out.println(gList.getIngredients());
		
		//save to text file
		saveGroceryList(gList);
	}
	
	//send in gLists
	public static void removeGroceryList(ArrayList<GroceryList> gl, String n) {
		ArrayList<GroceryList> gList = gl;
		String item = n;
	
		//empty groceryList file. because the file will be shorter than before
		FileWriter m;
		try {
			m = new FileWriter("MealMate/groceryList");
			//m = new FileWriter("groceryList");
			m.write("");
			m.close();
		} catch (IOException e1) {
			System.out.println(e1); // prints error
		}
		
		FileWriter p;
		try {
			//find and remove
			p = new FileWriter("MealMate/groceryList", true);
			//p = new FileWriter("groceryList", true);
			for (int j = 0; j < gList.size(); j++) {
				if (item.equals(gList.get(j).getName())) {
					gList.remove(j);
				}
				else {
					continue;
				}
			}
			//write to file
						for (int l = 0; l < gList.size(); l++) {
							saveGroceryList(gList.get(l));
						}	
						p.close();
					} catch (IOException e) {
						System.out.println(e); // prints error
				}		
		}
	
	public static void saveGroceryList(GroceryList gl) {
		try {
			String name = gl.getName();
			ArrayList<String> ingredients = gl.getIngredients();
			FileWriter p = new FileWriter("MealMate/groceryList", true);
			//FileWriter p = new FileWriter("groceryList", true);
			p.write(name + "\r\n");
			p.write(ingredients + "\r\n");
			p.close();
		} catch (IOException e) {
			System.out.println(e); // prints error
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if ("Choose File".equals(e.getActionCommand())) {  
            MealMate.bulkAddToPantry();
        } if ("Add to Pantry".equals(e.getActionCommand())) {
			MealMate.addToPantry();
		} if ("Remove from Pantry".equals(e.getActionCommand())) {
			ArrayList<String> items = new ArrayList<String>();
			ArrayList<JLabel> labels = new ArrayList<JLabel>();
			int yLoc = 50;
			try {
				//read pantry file by using scanner
				File p = new File("MealMate/pantry");
				//File p = new File("pantry");
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
		} if ("Make Grocery List".equals(e.getActionCommand())) {
			MealMate.makeGroceryList();
		} if ("Delete Grocery List".equals(e.getActionCommand())) {
			try {
			ArrayList<GroceryList> gLists = new ArrayList<GroceryList>();
			File p = new File("MealMate/groceryList");
			//File p = new File("groceryList");
			Scanner inFile = new Scanner(p);
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
			int i = 0;
			while (inFile.hasNext()) {
				//Even lines are the titles and odd lines are the grocery items
				if (i % 2 == 0) {
					String line = inFile.nextLine();
					names.add(line);
				}
				else {
					String line = inFile.nextLine();
					line = line.substring(1, line.length( ) -1); //remove one set of brackets.
					ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(line.split(", ")));
					items.add(ingredients);
				}
				i++;
			}
			inFile.close();
			//create grocery list object and add them to an ArrayList and print.
			for (int j = 0; j < names.size(); j++) {
				GroceryList r = new GroceryList(names.get(j), items.get(j));
				gLists.add(r);
			}
			String noGL = tNoGL.getText();
			MealMate.removeGroceryList(gLists, noGL);
			} catch(IOException e1) {
				System.out.println(e1); // prints error
			}
		}
    }
	
} 






