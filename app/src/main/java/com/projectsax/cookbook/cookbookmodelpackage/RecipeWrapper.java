package com.projectsax.cookbook.cookbookmodelpackage;

import java.io.Serializable;
import java.util.ArrayList;

/*
    Class: RecipeWrapper
    Wrapper class for the Recipe class
    Used to pass Recipe Object between activities screens
 */
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
