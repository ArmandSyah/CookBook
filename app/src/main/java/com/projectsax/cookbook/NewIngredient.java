package com.projectsax.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import cookbook.*;

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

        quantity = (EditText) findViewById(R.id.quantity);
        ingredientName = (EditText) findViewById(R.id.ingredientName);
        currentListOfIngredients = (ListView) findViewById(R.id.currentListOfIngredients);

        IngredientWrapper iw = (IngredientWrapper) getIntent().getSerializableExtra("ingredientList");
        ingredients = iw.getIngredients();

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
                Intent returnIntent = new Intent();
                returnIntent.putExtra("ingredientList", new IngredientWrapper(ingredients));
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
