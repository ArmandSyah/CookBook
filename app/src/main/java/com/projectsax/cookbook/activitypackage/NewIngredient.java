package com.projectsax.cookbook.activitypackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.projectsax.cookbook.R;
import com.projectsax.cookbook.adapterpackage.IngredientArrayListAdapter;

import com.projectsax.cookbook.cookbookmodelpackage.*;

import java.util.ArrayList;
/*
    Class: NewIngredient
    This is the class representation of the NewIngredient Activity for the cookbook application.
    This activity is used for making Ingredient objects and storing them in an ArrayList to be passed
    back into the recipe
 */

public class NewIngredient extends AppCompatActivity {

    private ArrayList<Ingredient> ingredients; //ArrayList of Ingredients Objects to store the Ingredients made by the user

    //Components on the screen for NewIngredient activity
    private EditText quantity;
    private EditText ingredientName;
    private ListView currentListOfIngredients;
    private IngredientArrayListAdapter ingredientArrayListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ingredient);

        //Instantialization of Button componenets on screen for NewIngredient activity
        Button addIngredientBtn = (Button) findViewById(R.id.add_ingredient_btn);
        Button resetIngredientBtn = (Button) findViewById(R.id.reset_ingredient_btn);
        Button finishIngredientBtn = (Button) findViewById(R.id.finish_ingredient_btn);
        Button helpBtn = (Button) findViewById(R.id.help_ingredient_btn);

        quantity = (EditText) findViewById(R.id.quantity);
        ingredientName = (EditText) findViewById(R.id.ingredientName);
        currentListOfIngredients = (ListView) findViewById(R.id.currentListOfIngredients);

        IngredientWrapper ingredientWrapper = (IngredientWrapper) getIntent().getSerializableExtra("ingredientList");
        ingredients = ingredientWrapper.getIngredients();

        ingredientArrayListAdapter = new IngredientArrayListAdapter(this, ingredients);
        currentListOfIngredients.setAdapter(ingredientArrayListAdapter);

        //setting a nested onClickListener for addIngredientBtn Button
        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityOfIngredient = quantity.getText().toString(); //Turns user input from quantity textField and toStrings it
                String nameOfIngredient = ingredientName.getText().toString(); //Turns user input from name textField and toStrings it
                //If either field is empty, then it makes a toast pop up and leaves the method
                if(quantityOfIngredient.equals("") || nameOfIngredient.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter the fields above", Toast.LENGTH_SHORT).show();
                }
                else{
                    Ingredient newIngredient = new Ingredient(quantityOfIngredient, nameOfIngredient); //Take input from textfields and turns it into Ingredient object
                    ingredients.add(newIngredient); //add it to ArrayList of Ingredients
                    quantity.setText("");
                    ingredientName.setText("");
                    Toast.makeText(getApplicationContext(), "Added new ingredient", Toast.LENGTH_SHORT).show();
                    currentListOfIngredients.setAdapter(ingredientArrayListAdapter); //Puts the new ingredient into listview
                }
            }
        });

        //setting a nested onClickListener for resetIngredientBtn Button
        resetIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //onClick, it resets and clears everything from textFields to the arrayList of ingredients, if it had any
                quantity.setText("");
                ingredientName.setText("");
                ingredients.clear();
                currentListOfIngredients.setAdapter(ingredientArrayListAdapter);
                Toast.makeText(getApplicationContext(), "cleared all ingredients", Toast.LENGTH_SHORT).show();
            }
        });

        //setting a nested onClickListener for finishIngredientBtn Button
        finishIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingredients.isEmpty()){ //if User clicks when arrayList is empty
                    Toast.makeText(getApplicationContext(), "add some ingredients first or just press the back button", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("ingredientList", new IngredientWrapper(ingredients)); //Adds IngredientWrapper to be passed back to RecipeMaker Activity
                setResult(RESULT_OK, returnIntent);
                finish(); //finishes activity and sends user back to previous activity
            }
        });

        //setting a nested onClickListener for helpBtn Button
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog(); //runs this static function onClick
            }
        });
    }

    //Small function to show a dialog on button press
    protected void helpDialog(){
        AlertDialog helpDialog =  new AlertDialog.Builder(NewIngredient.this).create(); //Set up an android alert dialog
        helpDialog.setTitle("How to add Ingredients");
        //Message to appear in the dialog box
        helpDialog.setMessage("Enter the quantity of the ingredient in the field beside Quantity (You can input any prefered measurement" +
                " here)\n" +
                "\nEnter the name of the ingreident in the field beside ingredient name\n" +
                "\nWhen both fields are full, press the add button, which will display your ingredient on the list\n" +
                "\nIf you messed up in making your list of ingredients, pressing reset button will clear the list\n" +
                "\nWhen you are done, press the finish button");
        //Set an 'OK' button on the alert dialog
        helpDialog.setButton(helpDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        helpDialog.show(); //Make the dialog appear
    }
}
