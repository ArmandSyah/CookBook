package cookbook;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeWrapper implements Serializable {
    private Recipe recipe;
    private ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

    public RecipeWrapper(Recipe recipe) {
        this.recipe = recipe;
    }

    public RecipeWrapper(ArrayList<Recipe> recipeList){
        this.recipeList = recipeList;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public ArrayList<Recipe> getRecipeList(){
        return recipeList;
    }
}
