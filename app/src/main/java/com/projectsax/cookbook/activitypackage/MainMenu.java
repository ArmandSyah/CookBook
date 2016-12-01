package com.projectsax.cookbook.activitypackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.projectsax.cookbook.R;

import java.util.ArrayList;

import com.projectsax.cookbook.cookbookmodelpackage.Cookbook;
import com.projectsax.cookbook.cookbookmodelpackage.Recipe;
import com.projectsax.cookbook.cookbookmodelpackage.RecipeWrapper;

public class MainMenu extends AppCompatActivity {

    private Cookbook cookbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button viewBtn = (Button) findViewById(R.id.view_recipe_btn);
        Button searchBtn = (Button) findViewById(R.id.search_recipe_btn);
        Button newBtn = (Button) findViewById(R.id.new_recipe_btn);
        Button helpBtn = (Button) findViewById(R.id.help_btn);

        cookbook = Cookbook.getInstance();
        viewBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ArrayList<Recipe> recipeList = cookbook.getListOfRecipes();
                Intent viewRecipeListIntent = new Intent(MainMenu.this, ViewRecipeList.class);
                viewRecipeListIntent.putExtra("recipeList", new RecipeWrapper(recipeList));
                startActivity(viewRecipeListIntent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, SearchRecipe.class));
            }
        });

        newBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent newRecipeIntent = new Intent(MainMenu.this, RecipeMaker.class);
                newRecipeIntent.putExtra("flag", "New");
                startActivityForResult(newRecipeIntent,0);
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            return;
        }
        else {
            switch (requestCode) {
                case 0:
                    RecipeWrapper recipeWrapper = (RecipeWrapper) data.getSerializableExtra("recipe");
                    Recipe newRecipe = recipeWrapper.getRecipe();
                    cookbook.addRecipe(newRecipe);
                    Toast.makeText(getApplicationContext(), "Recipe added :)", Toast.LENGTH_SHORT).show();
                    Intent recipeIntent = new Intent(MainMenu.this, ViewSelectedRecipe.class);
                    recipeIntent.putExtra("recipe", recipeWrapper);
                    startActivity(recipeIntent);
                    return;
            }
        }
    }

    protected void helpDialog(){
        AlertDialog helpDialog =  new AlertDialog.Builder(MainMenu.this).create();
        helpDialog.setTitle("How to Use");
        helpDialog.setMessage("To make a New Recipe, press the New Recipe Button. \n" +
                              "\nTo search for a Recipe, press the Search Recipe Button. \n" +
                              "\nTo simply view full list of Recipes, press the View Recipe List Button");
        helpDialog.setButton(helpDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        helpDialog.show();
    }
}
