package cookbook;

import android.database.sqlite.SQLiteDatabase;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;

public class Cookbook {
    private static Cookbook ourInstance = null;
    private static ArrayList<Recipe> listOfRecipes;
    private SQLiteDatabase cookbookDatabase;

    public static Cookbook getInstance() {
        if(ourInstance == null){
            ourInstance = new Cookbook();
        }
        return ourInstance;
    }

    private Cookbook() {
        cookbookDatabase = LitePal.getDatabase();
        listOfRecipes = (ArrayList<Recipe>) DataSupport.findAll(Recipe.class);
    }

    public void addRecipe(Recipe newRecipe){
        listOfRecipes.add(newRecipe);
        newRecipe.save();
    }

    public ArrayList<Recipe> getListOfRecipes(){
        return listOfRecipes;
    }

    public void deleteRecipe(Recipe deleteThisRecipe){
        if(listOfRecipes.contains(deleteThisRecipe)){
            System.out.println("Trace2");
            int index = listOfRecipes.indexOf(deleteThisRecipe);
            DataSupport.delete(Recipe.class, index);
            listOfRecipes.remove(deleteThisRecipe);
            return;
        }
        else {
            System.out.println("Trace");
            System.out.println(listOfRecipes.contains(deleteThisRecipe));
            return;
        }
    }
}
