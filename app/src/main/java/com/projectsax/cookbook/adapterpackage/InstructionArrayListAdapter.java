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

import cookbook.Instruction;

public class InstructionArrayListAdapter extends ArrayAdapter<Instruction> {

    private final Context context;
    private ArrayList<Instruction> instructions;
    public InstructionArrayListAdapter(Context context, ArrayList<Instruction> instructions){
        super(context, R.layout.activity_new_ingredient, instructions);
        this.context = context;
        this.instructions = instructions;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_instruction_array_list_adapter, parent, false);
        TextView stepNum = (TextView) rowView.findViewById(R.id.stepNum);
        TextView step = (TextView) rowView.findViewById(R.id.step);

        Instruction i = instructions.get(position);

        stepNum.setText(Integer.toString(i.getStepNum()));
        step.setText(i.getStep());
        return rowView;
    }
}
