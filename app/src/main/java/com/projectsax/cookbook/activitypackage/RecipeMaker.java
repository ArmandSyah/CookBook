package com.projectsax.cookbook.activitypackage;

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

import com.projectsax.cookbook.R;

import java.util.ArrayList;

import com.projectsax.cookbook.cookbookmodelpackage.Ingredient;
import com.projectsax.cookbook.cookbookmodelpackage.IngredientWrapper;
import com.projectsax.cookbook.cookbookmodelpackage.Instruction;
import com.projectsax.cookbook.cookbookmodelpackage.InstructionWrapper;
import com.projectsax.cookbook.cookbookmodelpackage.Recipe;
import com.projectsax.cookbook.cookbookmodelpackage.RecipeWrapper;

/*
    Class: RecipeMaker
    This is the class representation of the RecipeMaker Activity for the cookbook application.
    This activity is used for making or updating Recipe objects.
 */

public class RecipeMaker extends AppCompatActivity {

    private ArrayList<Ingredient> listOfIngredients = new ArrayList<Ingredient>(); //ArrayList of Ingredients Objects to store the Ingredient made by the user
    private ArrayList<Instruction> listOfInstructions = new ArrayList<Instruction>(); //ArrayList of Instructions Objects to store the Instruction made by the user

    //Components on the screen for RecipeMaker activity
    private EditText recipeName;
    private EditText cookTime;
    private EditText prepTime;
    private TextView typeSelected;
    private TextView categorySelected;

    private String flag; //Since this activity is used for making a New Recipe and Editing Recipe, we used a passed flag to determine which action the user is performing.
    private Recipe editThisRecipe; //if user is editting recipe, the recipe to be editted is pulled from the intent and placed here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_recipe);

        recipeName = (EditText) findViewById(R.id.recipeName);
        cookTime = (EditText) findViewById(R.id.cookTime);
        prepTime = (EditText) findViewById(R.id.prepTime);
        typeSelected = (TextView) findViewById(R.id.typeSelected);
        categorySelected = (TextView) findViewById(R.id.categorySelected);

        //Instantialization of Button componenets on screen for RecipeMaker activity
        Button createIngredientBtn = (Button) findViewById(R.id.make_ingredient_btn);
        Button createInstrucitonBtn = (Button) findViewById(R.id.make_instruction_btn);
        Button selectTypeBtn = (Button) findViewById(R.id.select_type);
        Button selectCategoryBtn = (Button) findViewById(R.id.select_category);
        Button resetBtn = (Button) findViewById(R.id.reset_recipe_btn);
        Button finishBtn = (Button) findViewById(R.id.finish_recipe_btn);
        Button helpBtn = (Button) findViewById(R.id.help_recipe_maker_btn);

        flag = getIntent().getStringExtra("flag"); //get's the flag String passed with the intent
        checkFlag(flag); //runs a flag check function to check content of flag String

        //setting a nested onClickListener for createIngredientBtn Button
        createIngredientBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent createIngredientIntent = new Intent(getApplicationContext(), NewIngredient.class); //set up intent for NewIngredient activity
                createIngredientIntent.putExtra("ingredientList", new IngredientWrapper(listOfIngredients)); //put list of ingredient arrayList into intent
                startActivityForResult(createIngredientIntent, 0); //start NewIngredient activity, with request code 0

            }

        });

        //setting a nested onClickListener for createInstrucitonBtn Button
        createInstrucitonBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent createInstructionIntent = new Intent(getApplicationContext(), NewInstruction.class); //set up intent for NewInstruction activity
                createInstructionIntent.putExtra("instructionList", new InstructionWrapper(listOfInstructions)); //put list of instruction arrayList into intent
                startActivityForResult(createInstructionIntent,1); //start NewInstruction activity, with request code 1
            }
        });

        //setting a nested onClickListener for resetInstructionBtn Button
        resetBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) { //onClick, it resets and clears everything from textFields to the arrayLists
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

        //setting a nested onClickListener for selectTypeBtn Button
        selectTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSelector();//runs static typeSelector function onClick
            }
        });

        //setting a nested onClickListener for selectCategoryBtn Button
        selectCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySelector(); //runs static categorySelector function onClick
            }
        });

        //setting a nested onClickListener for finishBtn Button
        finishBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Get all the user inputs from textFields and puts them as strings
                String nameOfRecipe = recipeName.getText().toString();
                String timeToCook = String.valueOf(cookTime.getText().toString());
                String timeToPrep = String.valueOf(prepTime.getText().toString());
                String typeOfRecipe = typeSelected.getText().toString();
                String categoryOfRecipe = categorySelected.getText().toString();

                if(flag.equals("New")) { //If the flag String is 'New', perform these operations
                    if (nameOfRecipe.equals("") || timeToCook.equals("") || timeToPrep.equals("") || typeOfRecipe.equals("none") || categoryOfRecipe.equals("none")
                            || listOfIngredients.isEmpty() || listOfInstructions.isEmpty()) { //Check if any of the fields or lists are empty
                        Toast.makeText(getApplicationContext(), "You are missing some fields. Enter all fields first", Toast.LENGTH_SHORT).show();
                        return;
                    } else { //else if all fields are filled
                        Recipe newRecipe = new Recipe(Integer.parseInt(timeToCook), Integer.parseInt(timeToPrep),
                                nameOfRecipe, typeOfRecipe, categoryOfRecipe, listOfIngredients, listOfInstructions); //Make a Recipe object with the fields and the lists
                        Intent returnIntent = new Intent(); //Make a new Intent to pass back
                        returnIntent.putExtra("recipe", new RecipeWrapper(newRecipe)); //Put recipe object in wrapper and put it into Intent
                        setResult(RESULT_OK, returnIntent);
                        finish(); //finish activity and go back
                    }
                }
                else if(flag.equals("Edit")){ //If the flag String is 'Edit', perform these operations
                    if (nameOfRecipe.equals("") || timeToCook.equals("") || timeToPrep.equals("") || typeOfRecipe.equals("none") || categoryOfRecipe.equals("none")
                            || listOfIngredients.isEmpty() || listOfInstructions.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "You are missing some fields. Enter all fields first", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        editThisRecipe.setCookTime(Integer.parseInt(timeToCook));
                        editThisRecipe.setPrepTime(Integer.parseInt(timeToPrep));
                        editThisRecipe.setType(typeOfRecipe);
                        editThisRecipe.setCategory(categoryOfRecipe);
                        editThisRecipe.setListOfIngredients(listOfIngredients);
                        editThisRecipe.setListOfInstructions(listOfInstructions);
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("recipe", new RecipeWrapper(editThisRecipe));
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }
                }
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog();
            }
        });
    }

    //Function that runs after coming back from an activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) { //if user clicks cancel, simply returns from the function
            return;
        }
        else {
            switch (requestCode) { //else it checks the request code and runs code based off that
                case 0:
                    IngredientWrapper ingredientWrapper = (IngredientWrapper) data.getSerializableExtra("ingredientList"); //gets IngredientWrapper passed in intent
                    listOfIngredients = ingredientWrapper.getIngredients(); //takes listOfIngredients arrayList from wrapper and sets it equal to instance of ingredient list in this activity
                    Toast.makeText(getApplicationContext(), "Finished Adding List of Ingredients to our Recipe", Toast.LENGTH_SHORT).show();
                    return;
                case 1:
                    InstructionWrapper instructionWrapper = (InstructionWrapper) data.getSerializableExtra("instructionList"); //gets InstructionWrapper passed in intent
                    listOfInstructions = instructionWrapper.getInstructions(); //takes listOfInstructions arrayList from wrapper and sets it equal to instance of instruction list in this activity
                    Toast.makeText(getApplicationContext(), "Finish Adding List of Instructions to our Recipe", Toast.LENGTH_SHORT).show();
                    return;
            }
        }

    }

    //Dialog to choose recipe type
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

    //Dialog to choose recipe category
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

    //Function to check the flag passed
    protected void checkFlag(String flag){
        if(flag.equals("New")){ //If flag is 'New', that means a new recipe is gonna be made, and nothing happens
            Toast.makeText(getApplicationContext(), "Ready to make a New Recipe", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(flag.equals("Edit")){ //If flag is 'Edit', that means a recipe is passed here to be editted. All fields will be filled with info of recipe to be editted, to be changed at user discretion
            RecipeWrapper editRecipeWrapper = (RecipeWrapper) getIntent().getSerializableExtra("editableRecipe");
            editThisRecipe = editRecipeWrapper.getRecipe();

            recipeName.setText(editThisRecipe.getRecipeName());
            cookTime.setText(Integer.toString(editThisRecipe.getCookTime()));
            prepTime.setText(Integer.toString(editThisRecipe.getPrepTime()));
            typeSelected.setText(editThisRecipe.getType());
            categorySelected.setText(editThisRecipe.getCategory());
            listOfIngredients = editThisRecipe.getListOfIngredients();
            listOfInstructions = editThisRecipe.getListOfInstructions();

            recipeName.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Ready to Edit Recipe", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //function for help Dialog
    protected void helpDialog(){
        AlertDialog helpDialog =  new AlertDialog.Builder(RecipeMaker.this).create();
        helpDialog.setTitle("How to Create new/Edit Recipe");
        helpDialog.setMessage("Type in Recipe name in the textfield beside 'Enter Recipe Name' \n" +
                "\nEnter in number of minutes for cooktime and preptime (field only takes an Integer) \n" +
                "\nPress the Enter Ingredient Button to open up a new screen where you can add your ingredients \n" +
                "\nPress the Enter Instruction Button to open up a new screen where you can add your instruction \n" +
                "\nPress the select button near type to open up a dialog with list of types you can select\n" +
                "\nPress the select button near category to open up a dialog with list of categories you can select\n" +
                "\nPress Finish Button when you are finished making your recipe (All Fields (including the instruction and ingredients)" +
                " must be entered, otherwise the app will prompt you to fill in the missing fields) \n" +
                "\nPress Reset to clear all the fields (including instruction and ingredients)");
        helpDialog.setButton(helpDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        helpDialog.show();
    }
}
