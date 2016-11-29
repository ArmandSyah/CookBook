package com.projectsax.cookbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import cookbook.Instruction;
import cookbook.InstructionWrapper;
import cookbook.Recipe;
import cookbook.RecipeWrapper;

public class CreateNewRecipe extends AppCompatActivity {

    private ArrayList<Ingredient> listOfIngredients = new ArrayList<Ingredient>();
    private ArrayList<Instruction> listOfInstructions = new ArrayList<Instruction>();

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
        Button selectTypeBtn = (Button) findViewById(R.id.select_type);
        Button selectCategoryBtn = (Button) findViewById(R.id.select_category);
        Button resetBtn = (Button) findViewById(R.id.reset_recipe_btn);
        Button finishBtn = (Button) findViewById(R.id.finish_recipe_btn);


        createIngredientBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent createIngredientIntent = new Intent(getApplicationContext(), NewIngredient.class);
                createIngredientIntent.putExtra("ingredientList", new IngredientWrapper(listOfIngredients));
                startActivityForResult(createIngredientIntent, 0);

            }

        });

        createInstrucitonBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent createInstructionIntent = new Intent(getApplicationContext(), NewInstruction.class);
                createInstructionIntent.putExtra("instructionList", new InstructionWrapper(listOfInstructions));
                startActivityForResult(createInstructionIntent,1);
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
                listOfInstructions.clear();
                Toast.makeText(getApplicationContext(), "Cleared whole recipe", Toast.LENGTH_SHORT).show();
            }
        });

        selectTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSelector();
            }
        });

        selectCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySelector();
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String nameOfRecipe = recipeName.getText().toString();
                String timeToCook = String.valueOf(cookTime.getText().toString());
                String timeToPrep = String.valueOf(prepTime.getText().toString());
                String typeOfRecipe = typeSelected.getText().toString();
                String categoryOfRecipe = categorySelected.getText().toString();
                if(nameOfRecipe.equals("") || timeToCook.equals("") || timeToPrep.equals("") || typeOfRecipe.equals("none") || categoryOfRecipe.equals("none")
                        || listOfIngredients.isEmpty() || listOfInstructions.isEmpty()){
                    Toast.makeText(getApplicationContext(), "You are missing some fields. Enter all fields first", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Recipe newRecipe = new Recipe(Integer.parseInt(timeToCook), Integer.parseInt(timeToPrep),
                            nameOfRecipe,typeOfRecipe, categoryOfRecipe, listOfIngredients, listOfInstructions);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("recipe", new RecipeWrapper(newRecipe));
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
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
                    IngredientWrapper ingredientWrapper = (IngredientWrapper) data.getSerializableExtra("ingredientList");
                    listOfIngredients = ingredientWrapper.getIngredients();
                    Toast.makeText(getApplicationContext(), "Finished Adding List of Ingredients to our Recipe", Toast.LENGTH_SHORT).show();
                    return;
                case 1:
                    InstructionWrapper instructionWrapper = (InstructionWrapper) data.getSerializableExtra("instructionList");
                    listOfInstructions = instructionWrapper.getInstructions();
                    Toast.makeText(getApplicationContext(), "Finish Adding List of Instructions to our Recipe", Toast.LENGTH_SHORT).show();
                    return;
            }
        }

    }

    protected void typeSelector(){
        final CharSequence[] listOfTypes = {"Canadian", "American", "Mexican", "British", "Chinese", "Japanese", "Columbian",
                "Brazillian", "Korean", "Other"};
        AlertDialog.Builder typeBuilder = new AlertDialog.Builder(this);
        typeBuilder.setTitle("Pick a Type");
        typeBuilder.setItems(listOfTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typeSelected.setText(listOfTypes[which]);
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
                categorySelected.setText(listOfCategories[which]);
                Toast.makeText(getApplicationContext(), "Type changed to " + listOfCategories[which] , Toast.LENGTH_SHORT).show();
                return;
            }
        });
        categoryBuilder.show();
    }

}
