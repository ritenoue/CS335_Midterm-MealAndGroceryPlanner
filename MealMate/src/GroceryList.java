import java.util.ArrayList;

public class GroceryList {
	protected String name;
	protected ArrayList<String> ingredients;
	
	public GroceryList (String n, ArrayList<String> i) {
		name = n;
		ingredients = i;
	}
	
	public String getName() {
		return(name);
	}
	
	public ArrayList<String> getIngredients() {
		return(ingredients);
	}
	
	public String toString() {
		return("Uh idk");
	}
}
