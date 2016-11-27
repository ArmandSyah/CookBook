package com.projectsax.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.projectsax.cookbook.adapterpackage.InstructionArrayListAdapter;

import java.util.ArrayList;

import cookbook.Instruction;
import cookbook.InstructionWrapper;

public class NewInstruction extends AppCompatActivity {

    private ArrayList<Instruction> instructions;

    private EditText stepNum;
    private EditText step;
    private ListView currentListOfInstructions;
    InstructionArrayListAdapter instructionArrayListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_instruction);

        Button addInstructionBtn = (Button) findViewById(R.id.add_instruction_btn);
        Button resetInstructionBtn = (Button) findViewById(R.id.reset_instruction_btn);
        Button finishInstructionBtn = (Button) findViewById(R.id.finish_instruction_btn);

        stepNum = (EditText) findViewById(R.id.stepNum);
        step = (EditText) findViewById(R.id.step);
        currentListOfInstructions = (ListView) findViewById(R.id.currentListOfInstructions);

        InstructionWrapper instructionWrapper = (InstructionWrapper) getIntent().getSerializableExtra("instructionList");
        instructions = instructionWrapper.getInstructions();

        instructionArrayListAdapter = new InstructionArrayListAdapter(this, instructions);
        currentListOfInstructions.setAdapter(instructionArrayListAdapter);

        addInstructionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stepNumber = stepNum.getText().toString();
                String stepInstruction = step.getText().toString();
                if(stepNumber.equals("") || stepInstruction.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter the fields above", Toast.LENGTH_SHORT).show();
                }
                else{
                    Instruction newInstruction = new Instruction(Integer.parseInt(stepNumber), stepInstruction);
                    instructions.add(newInstruction);
                    stepNum.setText("");
                    step.setText("");
                    Toast.makeText(getApplicationContext(), "Added new instruction", Toast.LENGTH_SHORT).show();
                    currentListOfInstructions.setAdapter(instructionArrayListAdapter);
                }
            }
        });

        resetInstructionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepNum.setText("");
                step.setText("");
                instructions.clear();
                currentListOfInstructions.setAdapter(instructionArrayListAdapter);
                Toast.makeText(getApplicationContext(), "cleared all instructions", Toast.LENGTH_SHORT).show();
            }
        });

        finishInstructionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instructions.isEmpty()){
                    Toast.makeText(getApplicationContext(), "add some instructions first or just press the back button", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("instructionList", new InstructionWrapper(instructions));
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
