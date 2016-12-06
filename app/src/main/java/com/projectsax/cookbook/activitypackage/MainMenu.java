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
/*
    Class: MainMenu
    This is the class representation of the Main Menu Activity for the cookbook application.
    Includes buttons that take the user to different activities in the program.
 */

public class MainMenu extends AppCompatActivity {

    private Cookbook cookbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //These are the Buttons instantialized and to be used for the program
        Button viewBtn = (Button) findViewById(R.id.view_recipe_btn);
        Button searchBtn = (Button) findViewById(R.id.search_recipe_btn);
        Button newBtn = (Button) findViewById(R.id.new_recipe_btn);
        Button helpBtn = (Button) findViewById(R.id.help_btn);

        cookbook = Cookbook.getInstance(); //Getting the singleton instance of Cookbook

        //Setting a nested onClickListener for the viewBtn Button
        viewBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ArrayList<Recipe> recipeList = cookbook.getListOfRecipes(); //Get the list of recipes from the cookbook singleton
                Intent viewRecipeListIntent = new Intent(MainMenu.this, ViewRecipeList.class); //Making the intent

                //Passing the list of recipes to another activity with the RecipeWrapper class
                viewRecipeListIntent.putExtra("recipeList", new RecipeWrapper(recipeList));
                startActivity(viewRecipeListIntent); //start next activity
            }
        });

        //Setting a nested onClickListener for the searchBtn Button
        searchBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, SearchRecipe.class));
            }
        });

        //Setting a nested onClickListener for the newBtn Button
        newBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent newRecipeIntent = new Intent(MainMenu.this, RecipeMaker.class);
                newRecipeIntent.putExtra("flag", "New"); //Adding a string 'New' to the intent
                startActivityForResult(newRecipeIntent,0); //start next activity, for result
            }
        });

        //Setting a nested onClickListener for the helpBtn Button
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog(); //when you click, it runs this static function
            }
        });
    }

    //This code runs when the program comes to this screen after performing another activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //if user clicks cancel, simply returns from the function
        if(resultCode == RESULT_CANCELED) {
            return;
        }
        //else it checks the request code and runs code based off that
        else {
            switch (requestCode) {
                case 0:
                    RecipeWrapper recipeWrapper = (RecipeWrapper) data.getSerializableExtra("recipe"); //Gets the RecipeWrapper attached to the intent
                    Recipe newRecipe = recipeWrapper.getRecipe(); //Gets the recipe in the recipe wrapper
                    cookbook.addRecipe(newRecipe);
                    Toast.makeText(getApplicationContext(), "Recipe added :)", Toast.LENGTH_SHORT).show(); //Makes a toast pop-up
                    Intent recipeIntent = new Intent(MainMenu.this, ViewSelectedRecipe.class); //Make a new intent connecting to the viewRecipe screen
                    recipeIntent.putExtra("recipe", recipeWrapper); //putting the RecipeWrapper to be pased to the next activity
                    startActivity(recipeIntent);
                    return;
            }
        }
    }

    //Small function to show a dialog on button press
    protected void helpDialog(){
        AlertDialog helpDialog =  new AlertDialog.Builder(MainMenu.this).create(); //Set up an android alert dialog
        helpDialog.setTitle("How to Use");
        //Message to appear in the dialog box
        helpDialog.setMessage("To make a New Recipe, press the New Recipe Button. \n" +
                              "\nTo search for a Recipe, press the Search Recipe Button. \n" +
                              "\nTo simply view full list of Recipes, press the View Recipe List Button");
        //Set an 'OK' button on the alert dialog
        helpDialog.setButton(helpDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //close dialog onClick
                    }
                });
        helpDialog.show(); //Make the dialog appear
    }
}
