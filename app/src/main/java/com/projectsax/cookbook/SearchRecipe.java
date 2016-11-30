package com.projectsax.cookbook;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SearchRecipe extends AppCompatActivity {

    private EditText searchField;
    private TextView searchCategory;
    private TextView searchType;

    private Button searchBtn;
    private Button resetBtn;
    private Button searchCategoryBtn;
    private Button searchTypeBtn;

    private ListView searchRecipeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        searchField = (EditText) findViewById(R.id.searchField);
        searchCategory = (TextView) findViewById(R.id.categorySearch);
        searchType = (TextView) findViewById(R.id.typeSearch);

        searchBtn = (Button) findViewById(R.id.search_btn);
        resetBtn = (Button) findViewById(R.id.reset_btn);
        searchCategoryBtn = (Button) findViewById(R.id.search_category_btn);
        searchTypeBtn = (Button) findViewById(R.id.search_type_btn);

        searchRecipeList = (ListView) findViewById(R.id.searchRecipeList);

        searchCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySelector();
            }
        });

        searchTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSelector();
            }
        });


    }

    protected void typeSelector(){
        final CharSequence[] listOfTypes = {"Canadian", "American", "Mexican", "British", "Chinese", "Japanese", "Columbian",
                "Brazillian", "Korean", "Other"};
        AlertDialog.Builder typeBuilder = new AlertDialog.Builder(this);
        typeBuilder.setTitle("Pick a Type");
        typeBuilder.setItems(listOfTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchCategory.setText(listOfTypes[which]);
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
                searchType.setText(listOfCategories[which]);
                Toast.makeText(getApplicationContext(), "Type changed to " + listOfCategories[which] , Toast.LENGTH_SHORT).show();
                return;
            }
        });
        categoryBuilder.show();
    }
}
