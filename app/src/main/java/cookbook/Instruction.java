package cookbook;

import org.litepal.crud.DataSupport;

class Instruction extends DataSupport {

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
}
