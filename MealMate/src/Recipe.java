import java.util.ArrayList;

public class Recipe {
	protected String name;
	protected ArrayList<String> ingredients;
	
	public Recipe (String n, ArrayList<String> i) {
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
		return(name);
	}
}
