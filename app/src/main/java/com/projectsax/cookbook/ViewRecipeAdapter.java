package com.projectsax.cookbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewRecipeAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ViewRecipeAdapter(Context context, String[] values) {
        super(context, R.layout.activity_view_recipe_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_view_recipe_adapter, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.recipeName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);

        String s = values[position];
        if (s == null || s.isEmpty() || s.equals("empty")) {
            imageView.setImageResource(R.drawable.ic_logo_00);
        } else {
            imageView.setImageResource(R.drawable.food_logo);
        }
        return rowView;
    }
}
