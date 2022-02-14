import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class MealMate {
	
	public static void main(String[] args) {
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
				viewPantry(false);
			}
			else if (response[i].equals("2")) {
				//go to remove
				viewPantry(true);
			}
			else if (response[i].equals("3")) {
				//go to view
				viewRecipe(false);
			}
			else if (response[i].equals("4")) {
				//go to view then create
				viewRecipe(true);
			}
			else if (response[i].equals("5")) {
				//go to view
				viewGroceryList(false);
			}
			else if (response[i].equals("6")) {
				//go to remove
				viewGroceryList(true);
			}
			else {
				break;
			}
			System.out.println("Choose another option or type EXIT to stop.");
		}
		r.close(); // close the scanner. This is the only scanner that can be closed. otherwise it throws an error.
		System.out.println("GoodBye");
	}
	
	public static void viewPantry(Boolean remove) {
		//print all pantry items
		ArrayList<String> items = new ArrayList<String>();
		try {
			//read pantry file by using scanner
			File p = new File("pantry");
			Scanner inFile = new Scanner(p);
			//check to make sure the file has text on the next line and print
			while (inFile.hasNext()) {
				String line = inFile.nextLine();
				System.out.println(line);
				items.add(line);
			}
			inFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if the user wants to remove items enter condition else return to main.
		if (remove == true) {
			removeFromPantry(items);
		}
	}
	
	public static void addToPantry() {
		System.out.println("Type the name of the item you would like to add and press ENTER or type EXIT to stop adding pantry items.");
		
		try {
			//read pantry file by using scanner. FileWriter must be set to true to append and not overwrite.
			Boolean exit = false;
			Scanner i = new Scanner(System.in);
			while (exit == false) {
				FileWriter p = new FileWriter("pantry", true);
				String item = i.nextLine();
				//check to see if user wants to exit else write to file.
				if (item.equals("EXIT")) {
					exit = true;
				}
				else {
					p.write(item + "\r\n");
					p.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeFromPantry(ArrayList<String> in) {
		ArrayList<String> pItems = in;
		
		//print grocery lists in viewPantry
		
		//use scanner to get user to input the item to be removed.
		System.out.println("Type which pantry item you would like to remove. You may only remove one at a time.");
		Scanner i = new Scanner(System.in);
		String item = i.nextLine();
		
		//empty groceryList file. This has to happen because the file will be shorter than before.
		FileWriter m;
		try {
			m = new FileWriter("pantry");
			m.write("");
			m.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		FileWriter p;
		try {
			//find and remove. Using the ArrayList sent in from viewPantry()
			p = new FileWriter("pantry", true);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Recipe> viewRecipe(Boolean gList) {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		try {
			System.out.println("These are your available recipes.");
			//add to lists to prepare create objects
			File p = new File("recipes");
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
				for (int j = 0; j < names.size(); j++) {
					Recipe r = new Recipe(names.get(j), items.get(j));
					recipes.add(r);
					System.out.println(r.getName());
				}
			}
			else {
				for (int j = 0; j < names.size(); j++) {
					Recipe r = new Recipe(names.get(j), items.get(j));
					recipes.add(r);
				}
				makeGroceryList(recipes);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(recipes); //I don't think this does anything
	}
	
	public static void makeGroceryList(ArrayList<Recipe> recipes) {
		System.out.println("Then your grocery list will be created by comparing your pantry and the ingredients of the recipe.");
		System.out.println("Make sure your pantry is up to date.");
		
		//User names grocery list in scanner.
		System.out.println("First, enter a name for your grocery list.");
		Scanner n = new Scanner(System.in);
		String title = n.nextLine();
		
		System.out.println("These are your available recipes.");
		
		//print available recipes from the list of recipes received from viewRecipes()
		for (int i = 0; i < recipes.size(); i++) {
			System.out.println(recipes.get(i).getName());
		}
		
		//get recipes the user  wants on their grocery list.
		System.out.println("Type what recipe you would like to shop for and press ENTER or type EXIT.");
		ArrayList<String> toCompare = new ArrayList<String>();
		Boolean exit = false;
		Scanner i = new Scanner(System.in);
		while (exit == false) {
			String item = i.nextLine();
			//Check to see if the user is finished.
			if (item.equals("EXIT")) {
				exit = true;
			}
			else {
				toCompare.add(item);
			}
		}
		
		//get pantry and add to ArrayList
		ArrayList<String> pantry = new ArrayList<String>();
		try {
			File p = new File("pantry");
			Scanner inFile = new Scanner(p);
			while (inFile.hasNext()) {
				String line = inFile.nextLine();
				pantry.add(line);
			}
			inFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//compare. Send all the recipes, pantry items, selected recipes, and gList tile.
		compareRecipeGrocery(recipes, pantry, toCompare, title);
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
	
	public static void viewGroceryList(Boolean remove) {
		ArrayList<GroceryList> gLists = new ArrayList<GroceryList>();
		try {
			System.out.println("These are your available Grocery Lists.");
			//add to lists to prepare create objects
			File p = new File("groceryList");
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
					ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(line.split(",")));
					items.add(ingredients);
				}
				i++;
			}
			inFile.close();
			//create grocery list object and add them to an ArrayList and print.
			for (int j = 0; j < names.size(); j++) {
				GroceryList r = new GroceryList(names.get(j), items.get(j));
				gLists.add(r);
				System.out.println(r.getName());
				for (int l = 0; l < r.getIngredients().size(); l++) {
					System.out.println("  " + r.getIngredients().get(l));
				}
			}
			
			//enter condition if user wants to remove items.
			if (remove == true) {
				removeGroceryList(names, items);
			}
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}	
	}
	
	public static void removeGroceryList(ArrayList<String> n, ArrayList<ArrayList<String>> it) {
		ArrayList<String> names  = n;
		ArrayList<ArrayList<String>> ingredients = it;
		
		//print grocery lists
		System.out.println("Type which grocery list you would like to remove. You may only remove one at a time.");
		Scanner i = new Scanner(System.in);
		String item = i.nextLine();
		
		//empty groceryList file. because the file will be shorter than before
		FileWriter m;
		try {
			m = new FileWriter("groceryList");
			m.write("");
			m.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		FileWriter p;
		try {
			//find and remove
			p = new FileWriter("groceryList", true);
			for (int j = 0; j < names.size(); j++) {
				if (item.equals(names.get(j))) {
					names.remove(j);
					ingredients.remove(j);
				}
				else {
					continue;
				}
			}
			
			//write to file
			for (int l = 0; l < names.size(); l++) {
				p.write(names.get(l) + "\r\n");
				p.write(ingredients.get(l) + "\r\n");
			}
			p.close();
			//i.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveGroceryList(GroceryList gl) {
		try {
			String name = gl.getName();
			ArrayList<String> ingredients = gl.getIngredients();
			FileWriter p = new FileWriter("groceryList", true);
			p.write(name + "\r\n");
			p.write(ingredients + "\r\n");
			p.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
