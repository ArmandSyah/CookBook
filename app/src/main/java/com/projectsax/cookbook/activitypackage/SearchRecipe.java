package com.projectsax.cookbook.activitypackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projectsax.cookbook.R;
import com.projectsax.cookbook.adapterpackage.ViewRecipeListAdapter;

import java.util.ArrayList;

import com.projectsax.cookbook.cookbookmodelpackage.Cookbook;
import com.projectsax.cookbook.cookbookmodelpackage.Ingredient;
import com.projectsax.cookbook.cookbookmodelpackage.Recipe;
import com.projectsax.cookbook.cookbookmodelpackage.RecipeWrapper;

public class SearchRecipe extends AppCompatActivity {

    private ArrayList<Recipe> querriedRecipes;
    private Cookbook cookbook = Cookbook.getInstance();

    private EditText searchField;
    private TextView searchCategory;
    private TextView searchType;

    private Button searchBtn;
    private Button resetBtn;
    private Button searchCategoryBtn;
    private Button searchTypeBtn;
    private Button helpBtn;

    private ListView searchRecipeList;
    private ViewRecipeListAdapter viewRecipeListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        querriedRecipes = cookbook.getListOfRecipes();
        searchField = (EditText) findViewById(R.id.searchField);
        searchCategory = (TextView) findViewById(R.id.categorySearch);
        searchType = (TextView) findViewById(R.id.typeSearch);

        searchBtn = (Button) findViewById(R.id.search_btn);
        resetBtn = (Button) findViewById(R.id.reset_btn);
        searchCategoryBtn = (Button) findViewById(R.id.search_category_btn);
        searchTypeBtn = (Button) findViewById(R.id.search_type_btn);
        helpBtn = (Button) findViewById(R.id.help_search_btn);

        searchRecipeList = (ListView) findViewById(R.id.searchRecipeList);

        viewRecipeListAdapter = new ViewRecipeListAdapter(getApplicationContext(), cookbook.getListOfRecipes());
        searchRecipeList.setAdapter(viewRecipeListAdapter);

        searchRecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe openThisRecipe = querriedRecipes.get(position);
                RecipeWrapper openRecipeWrapper = new RecipeWrapper(openThisRecipe);
                Intent openRecipeIntent = new Intent(SearchRecipe.this, ViewSelectedRecipe.class);
                openRecipeIntent.putExtra("recipe", openRecipeWrapper);
                startActivity(openRecipeIntent);
            }
        });

        searchCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySelector();
            }
        });

        searchTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSelector();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(querriedRecipes.size() > 0) {
                    querriedRecipes = cookbook.getListOfRecipes();
                    viewRecipeListAdapter = new ViewRecipeListAdapter(getApplicationContext(), cookbook.getListOfRecipes());
                    searchRecipeList.setAdapter(viewRecipeListAdapter);
                    viewRecipeListAdapter.notifyDataSetChanged();
                }
                String searchByCategory = searchCategory.getText().toString();
                String searchByType = searchType.getText().toString();
                String searchBar = searchField.getText().toString();
                String[] ingredientSearchQuery = searchBar.split(" ");

                ArrayList<Ingredient> allListedIngredients = new ArrayList<Ingredient>();
                ArrayList<Ingredient> andIngredients = new ArrayList<Ingredient>();
                ArrayList<Ingredient> orIngredients = new ArrayList<Ingredient>();
                ArrayList<Ingredient> notIngredients = new ArrayList<Ingredient>();

                for (int i = 0; i < ingredientSearchQuery.length; i++) {
                    if (ingredientSearchQuery[i].toUpperCase().equals("AND")) {
                        Ingredient ingredientNameBefore = new Ingredient(ingredientSearchQuery[i - 1]);
                        Ingredient ingredientNameAfter = new Ingredient(ingredientSearchQuery[i + 1]);
                        if(!orIngredients.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Don't use both And & Or Booleans, pick 1" , Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            if (!andIngredients.contains(ingredientNameBefore)) {
                                andIngredients.add(ingredientNameBefore);
                            }
                            if (!andIngredients.contains(ingredientNameAfter)) {
                                andIngredients.add(ingredientNameAfter);
                            }
                        }
                    }
                    else if (ingredientSearchQuery[i].toUpperCase().equals("OR")) {
                        Ingredient ingredientNameBefore = new Ingredient(ingredientSearchQuery[i - 1]);
                        Ingredient ingredientNameAfter = new Ingredient(ingredientSearchQuery[i + 1]);
                        if(!andIngredients.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Don't use both And & Or Booleans, pick 1" , Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            if (!orIngredients.contains(ingredientNameBefore)) {
                                orIngredients.add(ingredientNameBefore);
                            }
                            if (!orIngredients.contains(ingredientNameAfter)) {
                                orIngredients.add(ingredientNameAfter);
                            }
                        }
                    }
                    else if (ingredientSearchQuery[i].toUpperCase().equals("NOT")) {
                        Ingredient ingredientNameAfter = new Ingredient(ingredientSearchQuery[i + 1]);
                        if (!notIngredients.contains(ingredientNameAfter)) {
                            notIngredients.add(ingredientNameAfter);
                        }
                    }
                    else{
                        Ingredient ingredientName = new Ingredient(ingredientSearchQuery[i]);
                        allListedIngredients.add(ingredientName);
                    }
                }

                querriedRecipes = cookbook.searchRecipe(searchByCategory, searchByType,
                        andIngredients, orIngredients, notIngredients, allListedIngredients);
                viewRecipeListAdapter = new ViewRecipeListAdapter(getApplicationContext(), querriedRecipes);
                searchRecipeList.setAdapter(viewRecipeListAdapter);
                viewRecipeListAdapter.notifyDataSetChanged();

            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clearing the whole screen, feel free to try again", Toast.LENGTH_SHORT).show();
                searchCategory.setText("");
                searchType.setText("");
                searchField.setText("");
                querriedRecipes = cookbook.getListOfRecipes();
                viewRecipeListAdapter = new ViewRecipeListAdapter(getApplicationContext(), cookbook.getListOfRecipes());
                searchRecipeList.setAdapter(viewRecipeListAdapter);
                viewRecipeListAdapter.notifyDataSetChanged();
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog();
            }
        });
    }

    protected void typeSelector(){
        final CharSequence[] listOfTypes = {"Canadian", "American", "Mexican", "British", "Chinese", "Japanese", "Columbian",
                "Brazillian", "Korean", "Other"};
        AlertDialog.Builder typeBuilder = new AlertDialog.Builder(this);
        typeBuilder.setTitle("Pick a type");
        typeBuilder.setItems(listOfTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchType.setText(listOfTypes[which]);
                Toast.makeText(getApplicationContext(), "Type changed to " + listOfTypes[which] , Toast.LENGTH_SHORT).show();
                return;
            }
        });
        typeBuilder.show();
    }

    protected void categorySelector(){
        final CharSequence[] listOfCategories = {"Breakfast", "Lunch", "Dinner", "Snack", "Desert", "Other"};
        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(this);
        categoryBuilder.setTitle("Pick a Category");
        categoryBuilder.setItems(listOfCategories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchCategory.setText(listOfCategories[which]);
                Toast.makeText(getApplicationContext(), "Type changed to " + listOfCategories[which] , Toast.LENGTH_SHORT).show();
                return;
            }
        });
        categoryBuilder.show();
    }

    protected void helpDialog(){
        AlertDialog helpDialog =  new AlertDialog.Builder(SearchRecipe.this).create();
        helpDialog.setTitle("How to Search for Recipes");
        helpDialog.setMessage("This screen is for searching for Recipes\n" +
                "\nYou can search by type, category or list of ingredients\n" +
                "\nTo search by type or category, press their respective buttons to open up another dialog containing lists of each\n" +
                "\nTo search by ingredients, you may type in the name of the ingredients into the search field\n" +
                "\nYou can choose to use Boolean operator or not in your search:\n" +
                "\nEx: 'Tomatoes and Onion' Searches for recipes that INCLUDES both Tomatoes and Onion\n" +
                "\nEx: 'Tomatoes or Onion' Searches for recipes that has EITHER Tomatoes or Onion\n" +
                "\nEx: 'Not Tomatoes' Searches for recipes that don't have Tomatoes in them\n" +
                "\nDon't mix And & Or booleans in the search query\n" +
                "\nIf you choose not to use boolean operators in your search, that's fine, the program will simply find recipes that match" +
                " your list of given ingredients\n" +
                "\nWhen you are done searching, press the search button to have the querries appear\n" +
                "\nIf you messed up your searches, just press Reset button to clear everything and start over");
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
        searchCategory.setText("");
        searchType.setText("");
        searchField.setText("");
        querriedRecipes = cookbook.getListOfRecipes();
        viewRecipeListAdapter = new ViewRecipeListAdapter(getApplicationContext(), cookbook.getListOfRecipes());
        searchRecipeList.setAdapter(viewRecipeListAdapter);
        viewRecipeListAdapter.notifyDataSetChanged();
    }
}
