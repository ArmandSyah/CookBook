package com.projectsax.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cookbook.Ingredient;
import cookbook.IngredientWrapper;

public class CreateNewRecipe extends AppCompatActivity {

    private ArrayList<Ingredient> listOfIngredients = new ArrayList<Ingredient>();

    private EditText recipeName;
    private EditText cookTime;
    private EditText prepTime;
    private TextView typeSelected;
    private TextView categorySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_recipe);

        recipeName = (EditText) findViewById(R.id.recipeName);
        cookTime = (EditText) findViewById(R.id.cookTime);
        prepTime = (EditText) findViewById(R.id.prepTime);
        typeSelected = (TextView) findViewById(R.id.typeSelected);
        categorySelected = (TextView) findViewById(R.id.categorySelected);

        Button createIngredientBtn = (Button) findViewById(R.id.make_ingredient_btn);
        Button createInstrucitonBtn = (Button) findViewById(R.id.make_instruction_btn);
        Button resetBtn = (Button) findViewById(R.id.reset_recipe_btn);


        createIngredientBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent createRecipeIntent = new Intent(getApplicationContext(), NewIngredient.class);
                createRecipeIntent.putExtra("ingredientList", new IngredientWrapper(listOfIngredients));
                startActivityForResult(createRecipeIntent, 0);

            }

        });

        createInstrucitonBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateNewRecipe.this, NewInstruction.class));
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                recipeName.setText("");
                cookTime.setText("");
                prepTime.setText("");
                typeSelected.setText("None");
                categorySelected.setText("None");
                listOfIngredients.clear();
                Toast.makeText(getApplicationContext(), "Cleared whole recipe", Toast.LENGTH_SHORT).show();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode) {
            case 0:
                IngredientWrapper iw = (IngredientWrapper) data.getSerializableExtra("ingredientList");
                listOfIngredients = iw.getIngredients();
                Toast.makeText(getApplicationContext(), "Added List of Ingredients to our Recipe", Toast.LENGTH_SHORT).show();

        }
    }

}
