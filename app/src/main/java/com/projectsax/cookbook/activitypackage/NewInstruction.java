package com.projectsax.cookbook.activitypackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.projectsax.cookbook.R;
import com.projectsax.cookbook.adapterpackage.InstructionArrayListAdapter;

import java.util.ArrayList;

import com.projectsax.cookbook.cookbookmodelpackage.Instruction;
import com.projectsax.cookbook.cookbookmodelpackage.InstructionWrapper;

/*
    Class: NewInstruction
    This is the class representation of the NewInstruction Activity for the cookbook application.
    This activity is used for making Instruction objects and storing them in an ArrayList to be passed
    back into the recipe
 */

public class NewInstruction extends AppCompatActivity {

    private ArrayList<Instruction> instructions; //ArrayList of Instructions Objects to store the Instruction made by the user

    //Components on the screen for NewInstruction activity
    private EditText stepNum;
    private EditText step;
    private ListView currentListOfInstructions;
    InstructionArrayListAdapter instructionArrayListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_instruction);

        //Instantialization of Button componenets on screen for NewInstruction activity
        Button addInstructionBtn = (Button) findViewById(R.id.add_instruction_btn);
        Button resetInstructionBtn = (Button) findViewById(R.id.reset_instruction_btn);
        Button finishInstructionBtn = (Button) findViewById(R.id.finish_instruction_btn);
        Button helpBtn = (Button) findViewById(R.id.help_instruction_btn);

        stepNum = (EditText) findViewById(R.id.stepNum);
        step = (EditText) findViewById(R.id.step);
        currentListOfInstructions = (ListView) findViewById(R.id.currentListOfInstructions);

        InstructionWrapper instructionWrapper = (InstructionWrapper) getIntent().getSerializableExtra("instructionList");
        instructions = instructionWrapper.getInstructions();

        instructionArrayListAdapter = new InstructionArrayListAdapter(this, instructions);
        currentListOfInstructions.setAdapter(instructionArrayListAdapter);

        //setting a nested onClickListener for addInstructionBtn Button
        addInstructionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stepNumber = stepNum.getText().toString(); //Turns user input from stepNum textField and toStrings it
                String stepInstruction = step.getText().toString(); //Turns user input from step textField and toStrings it
                //If either field is empty, then it makes a toast pop up and leaves the method
                if(stepNumber.equals("") || stepInstruction.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter the fields above", Toast.LENGTH_SHORT).show();
                }
                else{
                    Instruction newInstruction = new Instruction(Integer.parseInt(stepNumber), stepInstruction); //Take input from textfields and turns it into Instruction object
                    instructions.add(newInstruction); //add it to ArrayList of Instructions
                    stepNum.setText("");
                    step.setText("");
                    Toast.makeText(getApplicationContext(), "Added new instruction", Toast.LENGTH_SHORT).show();
                    currentListOfInstructions.setAdapter(instructionArrayListAdapter); //Puts the new instruction into listview
                }
            }
        });

        //setting a nested onClickListener for resetInstructionBtn Button
        resetInstructionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //onClick, it resets and clears everything from textFields to the arrayList of instructions, if it had any
                stepNum.setText("");
                step.setText("");
                instructions.clear();
                currentListOfInstructions.setAdapter(instructionArrayListAdapter);
                Toast.makeText(getApplicationContext(), "cleared all instructions", Toast.LENGTH_SHORT).show();
            }
        });

        //setting a nested onClickListener for finishIngredientBtn Button
        finishInstructionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instructions.isEmpty()){ //if User clicks when arrayList is empty
                    Toast.makeText(getApplicationContext(), "add some instructions first or just press the back button", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("instructionList", new InstructionWrapper(instructions)); //Adds InstructionWrapper to be passed back to RecipeMaker Activity
                setResult(RESULT_OK, returnIntent);
                finish();//finishes activity and sends user back to previous activity
            }
        });

        //setting a nested onClickListener for helpBtn Button
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog(); //runs this static function onClick
            }
        });
    }

    //Small function to show a dialog on button press
    protected void helpDialog(){//Set up an android alert dialog
        AlertDialog helpDialog =  new AlertDialog.Builder(NewInstruction.this).create(); //Set up an android alert dialog
        helpDialog.setTitle("How to Add Instructions");
        //Message to appear in the dialog box
        helpDialog.setMessage("Enter the step number of the instruction in the field beside stepnum\n" +
                "\nEnter the instruction in the field beside step\n" +
                "\nWhen both fields are full, press the add button, which will display your instruction on the list\n" +
                "\nIf you messed up in making your list of instructions, pressing reset button will clear the list\n" +
                "\nWhen you are done, press the finish button");
        //Set an 'OK' button on the alert dialog
        helpDialog.setButton(helpDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        helpDialog.show(); //Make the dialog appear
    }
}
