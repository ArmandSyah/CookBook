package com.projectsax.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNewRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_recipe);

        Button createIngredientBtn = (Button) findViewById(R.id.make_ingredient_btn);
        Button createInstrucitonBtn = (Button) findViewById(R.id.make_instruction_btn);

        createIngredientBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateNewRecipe.this, NewIngredient.class));
            }
        });

        createInstrucitonBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateNewRecipe.this, NewInstruction.class));
            }
        });
    }


}
