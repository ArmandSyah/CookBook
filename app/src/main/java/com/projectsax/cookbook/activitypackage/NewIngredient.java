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

public class NewIngredient extends AppCompatActivity {

    private ArrayList<Ingredient> ingredients;

    private EditText quantity;
    private EditText ingredientName;
    private ListView currentListOfIngredients;
    IngredientArrayListAdapter ingredientArrayListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ingredient);

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

        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityOfIngredient = quantity.getText().toString();
                String nameOfIngredient = ingredientName.getText().toString();
                if(quantityOfIngredient.equals("") || nameOfIngredient.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter the fields above", Toast.LENGTH_SHORT).show();
                }
                else{
                    Ingredient newIngredient = new Ingredient(quantityOfIngredient, nameOfIngredient);
                    ingredients.add(newIngredient);
                    quantity.setText("");
                    ingredientName.setText("");
                    Toast.makeText(getApplicationContext(), "Added new ingredient", Toast.LENGTH_SHORT).show();
                    currentListOfIngredients.setAdapter(ingredientArrayListAdapter);
                }
            }
        });

        resetIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity.setText("");
                ingredientName.setText("");
                ingredients.clear();
                currentListOfIngredients.setAdapter(ingredientArrayListAdapter);
                Toast.makeText(getApplicationContext(), "cleared all ingredients", Toast.LENGTH_SHORT).show();
            }
        });

        finishIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingredients.isEmpty()){
                    Toast.makeText(getApplicationContext(), "add some instructions first or just press the back button", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("ingredientList", new IngredientWrapper(ingredients));
                setResult(RESULT_OK, returnIntent);
                finish();
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
        AlertDialog helpDialog =  new AlertDialog.Builder(NewIngredient.this).create();
        helpDialog.setTitle("How to add Ingredients");
        helpDialog.setMessage("Enter the quantity of the ingredient in the field beside Quantity (You can input any prefered measurement" +
                " here)\n" +
                "\nEnter the name of the ingreident in the field beside ingredient name\n" +
                "\nWhen both fields are full, press the add button, which will display your ingredient on the list\n" +
                "\nIf you messed up in making your list of ingredients, pressing reset button will clear the list\n" +
                "\nWhen you are done, press the finish button");
        helpDialog.setButton(helpDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        helpDialog.show();
    }
}
