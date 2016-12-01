package com.projectsax.cookbook.activitypackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projectsax.cookbook.R;
import com.projectsax.cookbook.adapterpackage.IngredientArrayListAdapter;
import com.projectsax.cookbook.adapterpackage.InstructionArrayListAdapter;

import com.projectsax.cookbook.cookbookmodelpackage.Cookbook;
import com.projectsax.cookbook.cookbookmodelpackage.Recipe;
import com.projectsax.cookbook.cookbookmodelpackage.RecipeWrapper;

public class ViewSelectedRecipe extends AppCompatActivity {

    private Cookbook cookbook = Cookbook.getInstance();

    private RecipeWrapper recipeWrapper;
    private Recipe viewRecipe;

    private TextView recipeName;
    private TextView type;
    private TextView category;
    private TextView prepTime;
    private TextView cookTime;

    private ListView listOfIngredients;
    private ListView listOfInstructions;

    private IngredientArrayListAdapter ingredientArrayListAdapter;
    private InstructionArrayListAdapter instructionArrayListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_recipe);

        Button deleteRecipeBtn = (Button) findViewById(R.id.delete_recipe_btn);
        Button editRecipeBtn = (Button) findViewById(R.id.edit_recipe_btn);
        Button helpBtn = (Button) findViewById(R.id.help_selected_recipe_btn);

        recipeWrapper = (RecipeWrapper) getIntent().getSerializableExtra("recipe");
        viewRecipe = recipeWrapper.getRecipe();

        recipeName = (TextView) findViewById(R.id.recipeName);
        type = (TextView) findViewById(R.id.type);
        category = (TextView) findViewById(R.id.category);
        prepTime = (TextView) findViewById(R.id.prepTime);
        cookTime = (TextView) findViewById(R.id.cookTime);

        listOfIngredients = (ListView) findViewById(R.id.listOfIngredients);
        listOfInstructions = (ListView) findViewById(R.id.listOfInstructions);

        setUp();

        deleteRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cookbook.deleteRecipe(viewRecipe);
                Toast.makeText(getApplicationContext(), "Recipe Deleted", Toast.LENGTH_SHORT).show();
                System.out.println("Size: " + cookbook.size());
                finish();
            }
        });

        editRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editRecipeIntent = new Intent(ViewSelectedRecipe.this, RecipeMaker.class);
                editRecipeIntent.putExtra("flag", "Edit");
                editRecipeIntent.putExtra("editableRecipe", new RecipeWrapper(viewRecipe));
                startActivityForResult(editRecipeIntent, 0);
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
                    Recipe edittedRecipe = recipeWrapper.getRecipe();
                    cookbook.updateRecipe(edittedRecipe);
                    viewRecipe = edittedRecipe;
                    setUp();
            }
        }
    }

    protected void setUp(){
        recipeName.setText(viewRecipe.getRecipeName());
        type.setText(viewRecipe.getType());
        category.setText(viewRecipe.getCategory());
        prepTime.setText(String.valueOf(viewRecipe.getPrepTime()) + " mins");
        cookTime.setText(String.valueOf(viewRecipe.getCookTime()) + " mins");

        ingredientArrayListAdapter = new IngredientArrayListAdapter(this, viewRecipe.getListOfIngredients());
        listOfIngredients.setAdapter(ingredientArrayListAdapter);

        instructionArrayListAdapter = new InstructionArrayListAdapter(this, viewRecipe.getListOfInstructions());
        listOfInstructions.setAdapter(instructionArrayListAdapter);
    }

    protected void helpDialog(){
        AlertDialog helpDialog =  new AlertDialog.Builder(ViewSelectedRecipe.this).create();
        helpDialog.setTitle("You are now viewing your selected Recipe");
        helpDialog.setMessage("This screen shows information about the recipe you selected\n" +
                "\nIf you want to delete it, press the delete button\n" +
                "\nIf you want to edit it, press the edit button");
        helpDialog.setButton(helpDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        helpDialog.show();
    }
}