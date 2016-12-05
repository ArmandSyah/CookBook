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

/**
 * Class: Cookbook
 *
 * The class representation of the Cookbook that holds all the recipes
 * Implemented with the Singleton Pattern design
 * Utilizes an ArrayList to store recipes, and a SQLite Database for local storage and persistence
 * Uses LitePal Library to deal with SQL operations.
 */
public class Cookbook {
    private Gson gson = new Gson(); //Used for turning Java Objects into JSON and vice-versa
    private static Cookbook ourInstance = null; //Singleton instance of CookBook class
    private static ArrayList<Recipe> listOfRecipes; //All the recipes contained in cookbook
    private SQLiteDatabase cookbookDatabase = LitePal.getDatabase(); //SQL database

    public static Cookbook getInstance() {
        if(ourInstance == null){
            ourInstance = new Cookbook();
        }
        return ourInstance;
    }

    /*
        Constructor for the CookBook class. Everytime user opens app, program will take all recipes saved
        from the SQL database and put it into the arraylist. Since the list of ingredients and instructions
        are saved as JSON strings, another method is used to turn the JSON into their respective
        ArrayLists
    */

    private Cookbook() {
        listOfRecipes = (ArrayList<Recipe>) DataSupport.findAll(Recipe.class);
        getIngredientsAndInstructionsFromJSON(listOfRecipes);
    }

    /*
        Adds a Recipe object to the current ArrayList and then saves the recipe onto the SQL Database
        through a method inherited from the LitePal framework
        @param newRecipe The Recipe object user created from the activites
        @return none
    */
    public void addRecipe(Recipe newRecipe){
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

    /*
        Deletes a Recipe object to the current ArrayList and then deletes the recipe from the SQL Database
        through a method inherited from the LitePal framework
        @param deleteRecipe The Recipe object user mentions from activites
        @return none
    */
    public void deleteRecipe(Recipe deleteThisRecipe){
        if(listOfRecipes.contains(deleteThisRecipe)){
            long recipeId = deleteThisRecipe.getId();
            DataSupport.delete(Recipe.class, recipeId);
            listOfRecipes.remove(deleteThisRecipe);
            deleteThisRecipe = null;
            return;
        }
        else {
            return;
        }
    }

    /*
        Searches for the Recipe through the use of Boolean Operators specified from the activites.
        @param selectedCategory: The Category of recipe the user wants to search for
        @param selectedType: The Type of recipe the user wants to search for
        @param andIngredients: An arrayList of ingredients user specified for searching. Used for finding
                                conjunctive combination of the ingredients (AND)
        @param orIngredients: An arrayList of ingredients user specified for searching. Used for finding
                                disjunctive combination of the ingredients (OR)
        @param notIngredients: An arrayList of ingredients user specified for searching. Used for finding
                                recipes to disclude from the query (NOT)
        @param allListedIngredients: An arrayList of ingredients user specified for searching. Utilized when
                                        user doesn't use Boolean operators for his search
     */
    public ArrayList<Recipe> searchRecipe(String selectedCategory, String selectedType, ArrayList<Ingredient> andIngredients,
                                          ArrayList<Ingredient> orIngredients, ArrayList<Ingredient> notIngredients,
                                          ArrayList<Ingredient> allListedIngredients){

        ArrayList<Recipe> queriedRecipes = new ArrayList<Recipe>(); //ArrayList of recipes to be returned after search complete

        ArrayList<Recipe> foundRecipesByCategory = new ArrayList<Recipe>(); //ArrayList of recipes with same Category
        ArrayList<Recipe> foundRecipesByType = new ArrayList<Recipe>(); //ArrayList of recipes with same Type

        ArrayList<Recipe> foundConjunctiveRecipes = new ArrayList<Recipe>(); //ArrayList of recipes found with ingredients from andIngredients
        ArrayList<Recipe> foundDisjunctiveRecipes = new ArrayList<Recipe>();//ArrayList of recipes found with ingredients from orIngredients
        ArrayList<Recipe> foundDiscludedRecipes = new ArrayList<Recipe>();//ArrayList of recipes found with ingredients from notIngredients

        ArrayList<Recipe> foundGeneralRecipes = new ArrayList<Recipe>(); //ArrayList of recipes found with ingredients from allListedIngredients

        //Method takes recipes from
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

        //Finds recipes from SQL DB with the same selected Category
        if(!selectedCategory.isEmpty()){
           foundRecipesByCategory
                   = (ArrayList<Recipe>) DataSupport.where("category = ?", selectedCategory).find(Recipe.class);

            getIngredientsAndInstructionsFromJSON(foundRecipesByCategory);
        }

        //Finds recipes from SQL DB with the same selected Type
        if(!selectedType.isEmpty()){
            foundRecipesByType
                    = (ArrayList<Recipe>) DataSupport.where("type = ?", selectedType).find(Recipe.class);

           getIngredientsAndInstructionsFromJSON(foundRecipesByType);
        }

        //Finds recipes from the arrayList with ingredients matching the ones in andIngredients
        if(!andIngredients.isEmpty()){
            for (Recipe r : listOfRecipes) {
                ArrayList<Ingredient> ingredientsInCurrentRecipe = r.getListOfIngredients();
                if (ingredientsInCurrentRecipe.containsAll(andIngredients)) {
                    foundConjunctiveRecipes.add(r);
                }
            }
            queriedRecipes.addAll(foundConjunctiveRecipes);
        }

        //Finds recipes from the arrayList with ingredients matching the ones in andIngredients
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

        //Finds recipes from the arrayList with ingredients matching the ones in notIngredients
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


        ArrayList<Recipe> recipesToRemove = new ArrayList<Recipe>();
        if(queriedRecipes.isEmpty()) {
            if (!foundRecipesByCategory.isEmpty() && !foundRecipesByType.isEmpty()) {
                queriedRecipes.addAll(listOfRecipes);
                ArrayList<Recipe> recipeWithBothTypeAndCategory = new ArrayList<Recipe>();
                ArrayList<Recipe> biggerList = foundRecipesByCategory.size() >= foundRecipesByType.size()
                        ? foundRecipesByCategory : foundRecipesByType;
                ArrayList<Recipe> smallerList = foundRecipesByCategory.size() < foundRecipesByType.size()
                        ? foundRecipesByCategory : foundRecipesByType;
                int index = 0;
                while (index < smallerList.size()) {
                    if (biggerList.contains(smallerList.get(index))) {
                        recipeWithBothTypeAndCategory.add(smallerList.get(index));
                    }
                    index++;
                }
                for (Recipe r : queriedRecipes) {
                    if (!recipeWithBothTypeAndCategory.contains(r)) {
                        recipesToRemove.add(r);
                    }
                }
                queriedRecipes.removeAll(recipesToRemove);
            } else if (!foundRecipesByCategory.isEmpty()) {
                queriedRecipes.addAll(listOfRecipes);
                for (Recipe r : queriedRecipes) {
                    if (!foundRecipesByCategory.contains(r)) {
                        recipesToRemove.add(r);
                    }
                }
                queriedRecipes.removeAll(recipesToRemove);
            } else if (!foundRecipesByType.isEmpty()) {
                queriedRecipes.addAll(listOfRecipes);
                for (Recipe r : queriedRecipes) {
                    if (!foundRecipesByType.contains(r)) {
                        recipesToRemove.add(r);
                    }
                }
                queriedRecipes.removeAll(recipesToRemove);
            }
        }
        else{
            if (!foundRecipesByCategory.isEmpty() && !foundRecipesByType.isEmpty()) {
                ArrayList<Recipe> recipeWithBothTypeAndCategory = new ArrayList<Recipe>();
                ArrayList<Recipe> biggerList = foundRecipesByCategory.size() >= foundRecipesByType.size()
                        ? foundRecipesByCategory : foundRecipesByType;
                ArrayList<Recipe> smallerList = foundRecipesByCategory.size() < foundRecipesByType.size()
                        ? foundRecipesByCategory : foundRecipesByType;
                int index = 0;
                while (index < smallerList.size()) {
                    if (biggerList.contains(smallerList.get(index))) {
                        recipeWithBothTypeAndCategory.add(smallerList.get(index));
                    }
                    index++;
                }
                for (Recipe r : queriedRecipes) {
                    if (!recipeWithBothTypeAndCategory.contains(r)) {
                        recipesToRemove.add(r);
                    }
                }
                queriedRecipes.removeAll(recipesToRemove);
            } else if (!foundRecipesByCategory.isEmpty()) {
                for (Recipe r : queriedRecipes) {
                    if (!foundRecipesByCategory.contains(r)) {
                        recipesToRemove.add(r);
                    }
                }
                queriedRecipes.removeAll(recipesToRemove);
            } else if (!foundRecipesByType.isEmpty()) {
                for (Recipe r : queriedRecipes) {
                    if (!foundRecipesByType.contains(r)) {
                        recipesToRemove.add(r);
                    }
                }
                queriedRecipes.removeAll(recipesToRemove);
            }
        }

        if(!foundDiscludedRecipes.isEmpty()) {
          if(queriedRecipes.isEmpty()){
              queriedRecipes.addAll(listOfRecipes);
              queriedRecipes.removeAll(foundDiscludedRecipes);
          }
          else{
              queriedRecipes.removeAll(foundDiscludedRecipes);
          }
        }

        /*
            If the user didn't use any boolean operators for his search query, it just adds all the recipes with
            ingredients he listed
        */
        if(foundConjunctiveRecipes.isEmpty() && foundDisjunctiveRecipes.isEmpty() && foundDiscludedRecipes.isEmpty()){
            queriedRecipes.addAll(foundGeneralRecipes);
        }

        //Use temporaryHolder HashSet to remove any possible duplicates in queriedRecipes
        Set<Recipe> temporaryHolder = new LinkedHashSet<Recipe>();
        temporaryHolder.addAll(queriedRecipes);
        queriedRecipes.clear();
        queriedRecipes.addAll(temporaryHolder);

        return queriedRecipes;
    }

    /*
        If user made any edits to any of the recipes, this method updates the Recipe object in the
        CookBook's listOfRecipes ArrayList as well as the entry in the SQL Db.
     */
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

        edittedRecipe.setListOfIngredientsInJson(); //Since sql doesn't take ArrayLists as input, Ingredients are turned into a JSON string and saved as that instead
        edittedRecipe.setListOfInstructionsInJson(); //Since sql doesn't take ArrayLists as input, Instructions are turned into a JSON string and saved as that instead
        listOfRecipes.set(index, edittedRecipe);
        Recipe updateRecipe = edittedRecipe;
        updateRecipe.update(edittedRecipe.getId()); //Updates entry in sql db
    }

    /*
        When any recipes is pulled from the db, it's JSON String representations of list of ingredients
        and list of instructions are turned into their respective ArrayLists so that they can be properly
        be shown in the recipes
        @param recipes: List of recipes that need their ArrayLists transformed
     */
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
