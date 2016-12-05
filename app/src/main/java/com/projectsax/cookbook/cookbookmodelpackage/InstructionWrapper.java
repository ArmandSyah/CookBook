package com.projectsax.cookbook.cookbookmodelpackage;


import java.io.Serializable;
import java.util.ArrayList;

/*
    Class: InstructionWrapper
    Wrapper class for Instruction object. Used for moving Instructions between activities
 */
public class InstructionWrapper implements Serializable{
    public ArrayList<Instruction> instructions;

    public InstructionWrapper(ArrayList<Instruction> instructions){
        this.instructions = instructions;
    }

    public ArrayList<Instruction> getInstructions(){
        return this.instructions;
    }
}
