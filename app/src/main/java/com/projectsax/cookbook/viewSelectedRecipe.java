package com.projectsax.cookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projectsax.cookbook.adapterpackage.IngredientArrayListAdapter;
import com.projectsax.cookbook.adapterpackage.InstructionArrayListAdapter;

import cookbook.Cookbook;
import cookbook.Recipe;
import cookbook.RecipeWrapper;

public class ViewSelectedRecipe extends AppCompatActivity {

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

        recipeWrapper = (RecipeWrapper) getIntent().getSerializableExtra("recipe");
        viewRecipe = recipeWrapper.getRecipe();

        recipeName = (TextView) findViewById(R.id.recipeName);
        type = (TextView) findViewById(R.id.type);
        category = (TextView) findViewById(R.id.category);
        prepTime = (TextView) findViewById(R.id.prepTime);
        cookTime = (TextView) findViewById(R.id.cookTime);

        listOfIngredients = (ListView) findViewById(R.id.listOfIngredients);
        listOfInstructions = (ListView) findViewById(R.id.listOfInstructions);

        recipeName.setText(viewRecipe.getRecipeName());
        type.setText(viewRecipe.getType());
        category.setText(viewRecipe.getCategory());
        prepTime.setText(String.valueOf(viewRecipe.getPrepTime()));
        cookTime.setText(String.valueOf(viewRecipe.getCookTime()));

        ingredientArrayListAdapter = new IngredientArrayListAdapter(this, viewRecipe.getListOfIngredients());
        listOfIngredients.setAdapter(ingredientArrayListAdapter);

        instructionArrayListAdapter = new InstructionArrayListAdapter(this, viewRecipe.getListOfInstructions());
        listOfInstructions.setAdapter(instructionArrayListAdapter);

        deleteRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cookbook cookbook = Cookbook.getInstance();
                cookbook.deleteRecipe(viewRecipe);
                Toast.makeText(getApplicationContext(), "Recipe Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
