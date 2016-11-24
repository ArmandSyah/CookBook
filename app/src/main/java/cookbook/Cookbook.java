package cookbook;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

public class Cookbook extends DataSupport {
    private static Cookbook ourInstance = new Cookbook();
    private ArrayList<Recipe> listOfRecipes;

    public static Cookbook getInstance() {
        return ourInstance;
    }

    private Cookbook() {

    }

    protected static void addRecipe(Recipe newRecipe){

    }
}
