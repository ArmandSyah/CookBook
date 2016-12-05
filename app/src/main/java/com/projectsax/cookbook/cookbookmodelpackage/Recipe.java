package com.projectsax.cookbook.cookbookmodelpackage;

import com.google.gson.Gson;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/*
    Class: Recipe
    An object representation of a recipe in a cookbook.
    Inherits the DataSupport class from the LitePal framework to simplify sql operations on recipes
    Implements Serializable interface
    GSON Library also used to turn the ArrayLists into it's JSON String representation
 */
public class Recipe extends DataSupport implements Serializable {

    private long id; //ID of the recipe, used for SQL db.

    private int cookTime;//Time it takes to cook meal
    private int prepTime;//time it takes to prepare ingredients for cooking
    @Column(unique = true)
    private String recipeName; //Name of the recipe
    private String type; //Type/Origin of the recipe
    private String category; //Category of the recipe
    private ArrayList<Ingredient> listOfIngredients; //Holds an arraylist of ingredients for the recipe
    private ArrayList<Instruction> listOfInstructions; //Holds an arraylist of instructions for the recipe

    private String listOfIngredientsInJson; //String representation of the ArrayList of Ingredients
    private String listOfInstructionsInJson; //String representation of the ArrayList of Instructions

    public Recipe() {
    }

    public Recipe(int cookTime, int prepTime, String recipeName, String type, String category, ArrayList<Ingredient> listOfIngredients, ArrayList<Instruction> listOfInstructions){
        Gson gson = new Gson();

        Random random = new Random();
        id = (long) (random.nextLong()*123456789L);

        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.recipeName = recipeName;
        this.type = type;
        this.category = category;
        this.listOfIngredients = listOfIngredients;
        this.listOfInstructions = listOfInstructions;

        //
        //Because SQL Databases can't take arrayLists as input for it's tables, those arrayLists
        //are modified into String representations and saved into the database instead. The GSON
        //library provides a way to turn Java Objects into JSON strings.
        //
        listOfIngredientsInJson = gson.toJson(listOfIngredients);
        listOfInstructionsInJson = gson.toJson(listOfInstructions);
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Ingredient> getListOfIngredients() {
        return listOfIngredients;
    }

    public void setListOfIngredients(ArrayList <Ingredient> listOfIngredients) {
        this.listOfIngredients = listOfIngredients;
    }

    public ArrayList<Instruction> getListOfInstructions() {
        return listOfInstructions;
    }

    public void setListOfInstructions(ArrayList<Instruction> listOfInstructions) {
        this.listOfInstructions = listOfInstructions;
    }

    public String getListOfIngredientsInJson() {
        return listOfIngredientsInJson;
    }

    public void setListOfIngredientsInJson() {
        Gson gson = new Gson();
        listOfIngredientsInJson = gson.toJson(listOfIngredients);
    }

    public String getListOfInstructionsInJson() {
        return listOfInstructionsInJson;
    }

    public void setListOfInstructionsInJson() {
        Gson gson = new Gson();
        listOfInstructionsInJson = gson.toJson(listOfInstructions);
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (cookTime != recipe.cookTime) return false;
        if (prepTime != recipe.prepTime) return false;
        if (recipeName != null ? !recipeName.equals(recipe.recipeName) : recipe.recipeName != null)
            return false;
        if (type != null ? !type.equals(recipe.type) : recipe.type != null) return false;
        if (category != null ? !category.equals(recipe.category) : recipe.category != null)
            return false;
        if (listOfIngredients != null ? !listOfIngredients.equals(recipe.listOfIngredients) : recipe.listOfIngredients != null)
            return false;
        return listOfInstructions != null ? listOfInstructions.equals(recipe.listOfInstructions) : recipe.listOfInstructions == null;

    }

    @Override
    public int hashCode() {
        int result = cookTime;
        result = 31 * result + prepTime;
        result = 31 * result + (recipeName != null ? recipeName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (listOfIngredients != null ? listOfIngredients.hashCode() : 0);
        result = 31 * result + (listOfInstructions != null ? listOfInstructions.hashCode() : 0);
        return result;
    }
}
