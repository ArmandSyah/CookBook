package com.projectsax.cookbook.adapterpackage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projectsax.cookbook.R;

import java.util.ArrayList;

import cookbook.Ingredient;

public class IngredientArrayListAdapter extends ArrayAdapter<Ingredient> {

    private final Context context;
    private ArrayList<Ingredient> ingredients;
    public IngredientArrayListAdapter(Context context, ArrayList<Ingredient> ingredients){
        super(context, R.layout.activity_new_ingredient, ingredients);
        this.context = context;
        this.ingredients = ingredients;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_ingredient_array_list_adapter, parent, false);
        TextView amount = (TextView) rowView.findViewById(R.id.amount);
        TextView ingredientName = (TextView) rowView.findViewById(R.id.ingredient);

        Ingredient i = ingredients.get(position);

        amount.setText(i.getAmount());
        ingredientName.setText(i.getName());
        return rowView;
    }
}
