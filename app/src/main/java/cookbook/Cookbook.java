package cookbook;

import android.database.sqlite.SQLiteDatabase;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;

public class Cookbook extends DataSupport {
    private static Cookbook ourInstance = new Cookbook();
    private static ArrayList<Recipe> listOfRecipes;

    public static Cookbook getInstance() {
        return ourInstance;
    }

    private Cookbook() {
        SQLiteDatabase cookbookDatabase = LitePal.getDatabase();
        listOfRecipes = (ArrayList<Recipe>) DataSupport.findAll(Recipe.class);
    }

    public void addRecipe(Recipe newRecipe){
        listOfRecipes.add(newRecipe);
        newRecipe.save();
    }

    public ArrayList<Recipe> getListOfRecipes(){
        return listOfRecipes;
    }
}
