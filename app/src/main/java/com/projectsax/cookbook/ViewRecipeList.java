package com.projectsax.cookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ViewRecipeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_list);

        ListView recipeList = (ListView) findViewById(R.id.list_of_recipes);
        String[] recipes = new String[]{
                "Cream Brocolli", "Caesar Salad", "Pumpkin Spice Latte", "empty", "empty", "empty", "empty",
                "empty", "empty", "empty", "empty", "empty"
        };

        ViewRecipeAdapter adapter = new ViewRecipeAdapter(this, recipes);
        recipeList.setAdapter(adapter);
    }
}
