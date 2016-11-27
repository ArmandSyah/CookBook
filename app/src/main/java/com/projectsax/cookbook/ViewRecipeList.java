package com.projectsax.cookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.projectsax.cookbook.adapterpackage.ViewRecipeAdapter;

import java.util.ArrayList;

import cookbook.Recipe;
import cookbook.RecipeWrapper;

public class ViewRecipeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_list);

        ListView recipeList = (ListView) findViewById(R.id.listOfRecipes);

        RecipeWrapper recipeWrapper = (RecipeWrapper) getIntent().getSerializableExtra("recipeList");
        ArrayList<Recipe> currentListOfRecipes = recipeWrapper.getRecipeList();

        ViewRecipeAdapter adapter = new ViewRecipeAdapter(this, currentListOfRecipes);
        recipeList.setAdapter(adapter);
    }
}
