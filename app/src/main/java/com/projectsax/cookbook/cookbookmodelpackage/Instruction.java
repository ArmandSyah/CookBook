package com.projectsax.cookbook.cookbookmodelpackage;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Instruction implements Serializable {

    private int stepNum;
    private String step;

    public Instruction(int stepNum, String step){
        this.stepNum = stepNum;
        this.step = step;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instruction that = (Instruction) o;

        if (stepNum != that.stepNum) return false;
        return step != null ? step.equals(that.step) : that.step == null;

    }

    @Override
    public int hashCode() {
        int result = stepNum;
        result = 31 * result + (step != null ? step.hashCode() : 0);
        return result;
    }
}
