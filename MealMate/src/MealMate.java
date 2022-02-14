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
		
		Scanner r = new Scanner(System.in);
		String[] response = new String[50];
		
		Boolean s = true;
		for(int i = 1; i < response.length; i++) {
			//if-else to take the users to each action.
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
		r.close();
		System.out.println("GoodBye");
	}
	
	public static void viewPantry(Boolean remove) {
		ArrayList<String> items = new ArrayList<String>();
		try {
			File p = new File("pantry");
			Scanner inFile = new Scanner(p);
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
		if (remove == true) {
			removeFromPantry(items);
		}
	}
	
	public static void addToPantry() {
		System.out.println("Type the name of the item you would like to add and press ENTER or type EXIT to stop adding pantry items.");
		
		try {
			Boolean exit = false;
			Scanner i = new Scanner(System.in);
			while (exit == false) {
				FileWriter p = new FileWriter("pantry", true);
				String item = i.nextLine();
				if (item.equals("EXIT")) {
					exit = true;
				}
				else {
					p.write(item + "\r\n");
					p.close();
				}
			}
			//i.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeFromPantry(ArrayList<String> in) {
		ArrayList<String> pItems = in;
		
		//print grocery lists
		System.out.println("Type which pantry item you would like to remove. You may only remove one at a time.");
		Scanner i = new Scanner(System.in);
		String item = i.nextLine();
		
		//empty groceryList file
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
			//find and remove
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
			//i.close();
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
				if (i % 2 == 0) {
					String line = inFile.nextLine();
					//System.out.println(line);
					names.add(line);
				}
				else {
					String line = inFile.nextLine();
					ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(line.split(",")));
					//System.out.println(ingredients);
					items.add(ingredients);
				}
				i++;
			}
			inFile.close();
			
			//make objects
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
			//Recipe r = new Recipe(names[0], items.get(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(recipes);
	}
	
	public static void makeGroceryList(ArrayList<Recipe> recipes) {
		System.out.println("Then your grocery list will be created by comparing your pantry and the ingredients of the recipe.");
		System.out.println("Make sure your pantry is up to date.");
		
		System.out.println("First, enter a name for your grocery list.");
		Scanner n = new Scanner(System.in);
		String title = n.nextLine();
		
		System.out.println("These are your available recipes.");
		
		//print available recipes
		for (int i = 0; i < recipes.size(); i++) {
			System.out.println(recipes.get(i).getName());
		}
		
		//get recipes
		System.out.println("Type what recipe you would like to shop for and press ENTER or type EXIT.");
		ArrayList<String> toCompare = new ArrayList<String>();
		Boolean exit = false;
		Scanner i = new Scanner(System.in);
		while (exit == false) {
			String item = i.nextLine();
			if (item.equals("EXIT")) {
				exit = true;
			}
			else {
				toCompare.add(item);
			}
		}
		//i.close();
		
		//get pantry
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
		
		//compare
		compareRecipeGrocery(recipes, pantry, toCompare, title);
	}
	
	public static void compareRecipeGrocery(ArrayList<Recipe> recipes, ArrayList<String> pantry, ArrayList<String> toCompare, String title) {
		//System.out.println(recipes.get(0).getName());
		//System.out.println(pantry);
		//System.out.println(toCompare);
		
		//initialize list
		ArrayList<String> groceryItems = new ArrayList<String>();
		
		//iterate toCompare
		for (int i = 0; i < toCompare.size(); i++) {
			//iterate recipes to find the same recipe
			for (int j = 0; j < recipes.size(); j++) {
				if (toCompare.get(i).equals(recipes.get(j).getName())) {
					//compare to pantry. iterate recipe ingredients
					for (int k = 0; k < recipes.get(j).getIngredients().size(); k++) {
						int pItemCount = 0;
						//iterate pantry
						for (int l = 0; l < pantry.size(); l++) {
							if (!recipes.get(j).getIngredients().get(k).equals(pantry.get(l).toLowerCase())) {
								pItemCount++;
							}
							else {
								continue;
							}

						}
						//add missing ingredients
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
		
		//send to class constructor
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
				if (i % 2 == 0) {
					String line = inFile.nextLine();
					//System.out.println(line);
					names.add(line);
				}
				else {
					String line = inFile.nextLine();
					line = line.substring(1, line.length( ) -1);
					ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(line.split(",")));
					items.add(ingredients);
				}
				i++;
			}
			inFile.close();
			for (int j = 0; j < names.size(); j++) {
				GroceryList r = new GroceryList(names.get(j), items.get(j));
				gLists.add(r);
				System.out.println(r.getName());
				for (int l = 0; l < r.getIngredients().size(); l++) {
					System.out.println("  " + r.getIngredients().get(l));
				}
			}
			
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
		
		//empty groceryList file
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
