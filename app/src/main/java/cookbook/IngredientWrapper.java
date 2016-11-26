package cookbook;


import java.io.Serializable;
import java.util.ArrayList;

public class IngredientWrapper implements Serializable {
    public ArrayList<Ingredient> ingredients;

    public IngredientWrapper(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public ArrayList<Ingredient> getIngredients(){
        return this.ingredients;
    }
}
