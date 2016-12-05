package com.projectsax.cookbook.adapterpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectsax.cookbook.R;

import java.util.ArrayList;

import com.projectsax.cookbook.cookbookmodelpackage.Recipe;

/*
    Class: ViewRecipeListAdapter
    Adapter class for ListView of Recipes. Inflates an ArrayList of Recipes onto it's respective Listview
    in it's activity
 */

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

        ImageView typeImage = (ImageView) rowView.findViewById(R.id.typeImage);
        TextView recipeName = (TextView) rowView.findViewById(R.id.recipeName);

        Recipe recipe = listOfRecipes.get(position);
        recipeName.setText(recipe.getRecipeName());

        if(recipe.getType().equals("Canadian")){
            typeImage.setImageResource(R.drawable.canada_flag);
        }
        else if(recipe.getType().equals("American")){
            typeImage.setImageResource(R.drawable.america_flag);
        }
        else if(recipe.getType().equals("Mexican")){
            typeImage.setImageResource(R.drawable.mexican_flag);
        }
        else if(recipe.getType().equals("British")){
            typeImage.setImageResource(R.drawable.british_flag);
        }
        else if(recipe.getType().equals("Chinese")){
            typeImage.setImageResource(R.drawable.china_flag);
        }
        else if(recipe.getType().equals("Japanese")){
            typeImage.setImageResource(R.drawable.japan_flag);
        }
        else if(recipe.getType().equals("Columbian")){
            typeImage.setImageResource(R.drawable.columbian_flag);
        }
        else if(recipe.getType().equals("Brazillian")){
            typeImage.setImageResource(R.drawable.brazil_flag);
        }
        else if(recipe.getType().equals("Korean")){
            typeImage.setImageResource(R.drawable.korean_flag);
        }
        else if(recipe.getType().equals("Other")){
            typeImage.setImageResource(R.drawable.unknown_flag);
        }

        return rowView;
    }

    @Override
    public int getCount() {
        return listOfRecipes != null ? listOfRecipes.size() : 0;
    }
}
