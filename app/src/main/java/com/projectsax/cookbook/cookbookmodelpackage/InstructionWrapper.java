package com.projectsax.cookbook.cookbookmodelpackage;


import java.io.Serializable;
import java.util.ArrayList;

public class InstructionWrapper implements Serializable{
    public ArrayList<Instruction> instructions;

    public InstructionWrapper(ArrayList<Instruction> instructions){
        this.instructions = instructions;
    }

    public ArrayList<Instruction> getInstructions(){
        return this.instructions;
    }
}
