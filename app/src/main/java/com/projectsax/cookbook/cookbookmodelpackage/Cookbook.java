package com.projectsax.cookbook.cookbookmodelpackage;

import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

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
        getIngredientsAndInstructionsFromJSON(listOfRecipes);
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

    public ArrayList<Recipe> searchRecipe(String selectedCategory, String selectedType, ArrayList<Ingredient> andIngredients,
                                          ArrayList<Ingredient> orIngredients, ArrayList<Ingredient> notIngredients,
                                          ArrayList<Ingredient> allListedIngredients){

        ArrayList<Recipe> queriedRecipes = new ArrayList<Recipe>();

        ArrayList<Recipe> foundRecipesByCategory = new ArrayList<Recipe>();
        ArrayList<Recipe> foundRecipesByType = new ArrayList<Recipe>();

        ArrayList<Recipe> foundConjunctiveRecipes = new ArrayList<Recipe>();
        ArrayList<Recipe> foundDisjunctiveRecipes = new ArrayList<Recipe>();
        ArrayList<Recipe> foundDiscludedRecipes = new ArrayList<Recipe>();

        ArrayList<Recipe> foundGeneralRecipes = new ArrayList<Recipe>();

        if(!allListedIngredients.isEmpty()){
            for(Recipe r: listOfRecipes){
                ArrayList<Ingredient> ingredients = r.getListOfIngredients();
                for(Ingredient i: allListedIngredients){
                    if(ingredients.contains(i)){
                        foundGeneralRecipes.add(r);
                        break;
                    }
                }
            }
        }

        if(!selectedCategory.isEmpty()){
           foundRecipesByCategory
                   = (ArrayList<Recipe>) DataSupport.where("category = ?", selectedCategory).find(Recipe.class);

            getIngredientsAndInstructionsFromJSON(foundRecipesByCategory);
        }
        if(!selectedType.isEmpty()){
            foundRecipesByType
                    = (ArrayList<Recipe>) DataSupport.where("type = ?", selectedType).find(Recipe.class);

           getIngredientsAndInstructionsFromJSON(foundRecipesByType);
        }

        if(!andIngredients.isEmpty()){
            for (Recipe r : listOfRecipes) {
                ArrayList<Ingredient> ingredientsInCurrentRecipe = r.getListOfIngredients();
                if (ingredientsInCurrentRecipe.containsAll(andIngredients)) {
                    System.out.println("Trace true");
                    foundConjunctiveRecipes.add(r);
                }
            }
            queriedRecipes.addAll(foundConjunctiveRecipes);
        }

        if(!orIngredients.isEmpty()){
            for(Recipe r: listOfRecipes){
                ArrayList<Ingredient> ingredientsInCurrentRecipe = r.getListOfIngredients();
                for(Ingredient i: orIngredients){
                    if(ingredientsInCurrentRecipe.contains(i)){
                        foundDisjunctiveRecipes.add(r);
                        break;
                    }
                }
            }
            queriedRecipes.addAll(foundDisjunctiveRecipes);
        }

        if(!notIngredients.isEmpty()){
            for(Recipe r: listOfRecipes){
                ArrayList<Ingredient> ingredientsInCurrentRecipe = r.getListOfIngredients();
                for(Ingredient i: notIngredients){
                    if(ingredientsInCurrentRecipe.contains(i)){
                        foundDiscludedRecipes.add(r);
                        break;
                    }
                }
            }
        }


        if(!foundRecipesByCategory.isEmpty() && !foundRecipesByType.isEmpty()) {
            ArrayList<Recipe> biggerList = foundRecipesByCategory.size() >= foundRecipesByType.size()
                    ? foundRecipesByCategory : foundRecipesByType;
            ArrayList<Recipe> smallerList = foundRecipesByCategory.size() < foundRecipesByType.size()
                    ? foundRecipesByCategory : foundRecipesByType;
            int index = 0;
            while (index < smallerList.size()) {
                if (biggerList.contains(smallerList.get(index))) {
                    queriedRecipes.add(smallerList.get(index));
                }
                index++;
            }
        }
        else{
            queriedRecipes.addAll(foundRecipesByCategory);
            queriedRecipes.addAll(foundRecipesByType);
        }

        if(!foundDiscludedRecipes.isEmpty()) {
          System.out.println("Trace Not");
          System.out.println(!foundDiscludedRecipes.isEmpty());
          if(queriedRecipes.isEmpty()){
              System.out.println("Trace query empty");
              queriedRecipes.addAll(listOfRecipes);
              queriedRecipes.removeAll(foundDiscludedRecipes);
          }
          else{
              System.out.println("Trace query filled sorta");
              for(Recipe r: queriedRecipes){
                  System.out.println("Recipe: " + r.getRecipeName());
              }
              queriedRecipes.removeAll(foundDiscludedRecipes);
          }
        }

        if(foundConjunctiveRecipes.isEmpty() && foundDisjunctiveRecipes.isEmpty() && foundDiscludedRecipes.isEmpty()){
            queriedRecipes.addAll(foundGeneralRecipes);
        }

        Set<Recipe> temporaryHolder = new LinkedHashSet<Recipe>();
        temporaryHolder.addAll(queriedRecipes);
        queriedRecipes.clear();
        queriedRecipes.addAll(temporaryHolder);
        return queriedRecipes;
    }

    public void updateRecipe(Recipe edittedRecipe){
        Boolean foundRecipe = false;
        int index = 0;
        for(Recipe r: listOfRecipes){
            if(edittedRecipe.getRecipeName().equals(r.getRecipeName())){
                foundRecipe = true;
                index = listOfRecipes.indexOf(r);
                break;
            }
        }

        if(!foundRecipe) return;

        edittedRecipe.setListOfIngredientsInJson();
        edittedRecipe.setListOfInstructionsInJson();
        listOfRecipes.set(index, edittedRecipe);
        Recipe updateRecipe = edittedRecipe;
        updateRecipe.update(edittedRecipe.getId());
    }

    protected void getIngredientsAndInstructionsFromJSON(ArrayList<Recipe> recipes){
        for(Recipe r: recipes){
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
}