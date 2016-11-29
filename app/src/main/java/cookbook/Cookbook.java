package cookbook;

import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Cookbook {
    private Gson gson = new Gson();
    private static Cookbook ourInstance = null;
    private static ArrayList<Recipe> listOfRecipes;
    private SQLiteDatabase cookbookDatabase = LitePal.getDatabase();

    public static Cookbook getInstance() {
        if(ourInstance == null){
            ourInstance = new Cookbook();
        }
        return ourInstance;
    }

    private Cookbook() {
        listOfRecipes = (ArrayList<Recipe>) DataSupport.findAll(Recipe.class);

        for(Recipe r: listOfRecipes){
            String outputArrayListIngredients = r.getListOfIngredientsInJson();
            Type typeIngredient = new TypeToken<ArrayList<Ingredient>>() {}.getType();
            ArrayList<Ingredient> ingredientList = gson.fromJson(outputArrayListIngredients, typeIngredient);
            r.setListOfIngredients(ingredientList);

            String outputArrayListInstructions = r.getListOfInstructionsInJson();
            Type typeInstruction = new TypeToken<ArrayList<Instruction>>() {}.getType();
            ArrayList<Instruction> instructionList = gson.fromJson(outputArrayListInstructions, typeInstruction);
            r.setListOfInstructions(instructionList);
        }
    }

    public void addRecipe(Recipe newRecipe){
        System.out.println("Trace Add");
        listOfRecipes.add(newRecipe);
        newRecipe.save();
        System.out.println(listOfRecipes.indexOf(newRecipe));
    }

    public ArrayList<Recipe> getListOfRecipes(){
        return listOfRecipes;
    }

    public int size(){
        return listOfRecipes.size();
    }

    public void deleteRecipe(Recipe deleteThisRecipe){
        if(listOfRecipes.contains(deleteThisRecipe)){
            System.out.println("Trace2");
            long recipeId = deleteThisRecipe.getId();
            DataSupport.delete(Recipe.class, recipeId);
            listOfRecipes.remove(deleteThisRecipe);
            deleteThisRecipe = null;
            return;
        }
        else {
            System.out.println("Trace");
            System.out.println(listOfRecipes.contains(deleteThisRecipe));
            return;
        }
    }
}
