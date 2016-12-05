package com.projectsax.cookbook.cookbookmodelpackage;


import java.io.Serializable;
import java.util.ArrayList;

/*
    Class: IngredientWrapper
    Wrapper class for Ingredient class. Used to store and move Ingredients between activities in app
 */

public class IngredientWrapper implements Serializable {
    private ArrayList<Ingredient> ingredients;

    public IngredientWrapper(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public ArrayList<Ingredient> getIngredients(){
        return this.ingredients;
    }
}
