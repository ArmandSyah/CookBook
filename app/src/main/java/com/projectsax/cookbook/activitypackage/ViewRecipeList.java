package com.projectsax.cookbook.activitypackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.projectsax.cookbook.R;
import com.projectsax.cookbook.adapterpackage.ViewRecipeListAdapter;

import java.util.ArrayList;

import com.projectsax.cookbook.cookbookmodelpackage.Cookbook;
import com.projectsax.cookbook.cookbookmodelpackage.Recipe;
import com.projectsax.cookbook.cookbookmodelpackage.RecipeWrapper;
/*
    Class: ViewRecipeList
    This is the class representation of the ViewRecipeList Activity for the cookbook application.
    This activity is used for viewing the full list of recipes in the Cookbook.
 */
public class ViewRecipeList extends AppCompatActivity {

    private ArrayList<Recipe> currentListOfRecipes;
    private Cookbook cookbook = Cookbook.getInstance();
    private ViewRecipeListAdapter adapter;

    private Button helpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_list);

        ListView recipeList = (ListView) findViewById(R.id.listOfRecipes);

        helpBtn = (Button) findViewById(R.id.help_view_btn);

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


        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog();
            }
        });
    }

    protected void helpDialog(){
        AlertDialog helpDialog =  new AlertDialog.Builder(ViewRecipeList.this).create();
        helpDialog.setTitle("View Recipe list");
        helpDialog.setMessage("This screen shows all the recipes stored in the cookbook\n" +
                "\nWhen you press on one of the recipes, you open it up");
        helpDialog.setButton(helpDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        helpDialog.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        currentListOfRecipes = cookbook.getListOfRecipes();
        adapter.notifyDataSetChanged();
    }
}
