package com.projectsax.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.projectsax.cookbook.adapterpackage.ViewRecipeListAdapter;

import java.util.ArrayList;

import cookbook.Cookbook;
import cookbook.Recipe;
import cookbook.RecipeWrapper;

public class ViewRecipeList extends AppCompatActivity {

    private ArrayList<Recipe> currentListOfRecipes;
    private Cookbook cookbook = Cookbook.getInstance();
    private ViewRecipeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_list);

        ListView recipeList = (ListView) findViewById(R.id.listOfRecipes);

        currentListOfRecipes = cookbook.getListOfRecipes();

        adapter = new ViewRecipeListAdapter(this, currentListOfRecipes);
        recipeList.setAdapter(adapter);

        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe openThisRecipe = currentListOfRecipes.get(position);
                RecipeWrapper openRecipeWrapper = new RecipeWrapper(openThisRecipe);
                Intent openRecipeIntent = new Intent(ViewRecipeList.this, ViewSelectedRecipe.class);
                openRecipeIntent.putExtra("recipe", openRecipeWrapper);
                startActivity(openRecipeIntent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        currentListOfRecipes = cookbook.getListOfRecipes();
        adapter.notifyDataSetChanged();
    }
}
