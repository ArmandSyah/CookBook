package com.projectsax.cookbook.cookbookmodelpackage;


import java.io.Serializable;
import java.util.ArrayList;

public class IngredientWrapper implements Serializable {
    private ArrayList<Ingredient> ingredients;

    public IngredientWrapper(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public ArrayList<Ingredient> getIngredients(){
        return this.ingredients;
    }
}