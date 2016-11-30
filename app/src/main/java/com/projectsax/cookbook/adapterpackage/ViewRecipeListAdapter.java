package com.projectsax.cookbook.adapterpackage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectsax.cookbook.R;

import java.util.ArrayList;

import cookbook.Recipe;

public class ViewRecipeListAdapter extends ArrayAdapter<Recipe> {
    private final Context context;
    private final ArrayList<Recipe> listOfRecipes;

    public ViewRecipeListAdapter(Context context, ArrayList<Recipe> listOfRecipes) {
        super(context, R.layout.activity_view_recipe_list, listOfRecipes);
        this.context = context;
        this.listOfRecipes = listOfRecipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_view_recipe_adapter, parent, false);

        TextView recipeName = (TextView) rowView.findViewById(R.id.recipeName);
        Recipe recipe = listOfRecipes.get(position);
        recipeName.setText(recipe.getRecipeName());

        return rowView;
    }

    @Override
    public int getCount() {
        return listOfRecipes != null ? listOfRecipes.size() : 0;
    }
}
