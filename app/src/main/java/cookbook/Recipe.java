package cookbook;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe extends DataSupport implements Serializable {

    private int cookTime;
    private int prepTime;
    private String recipeName;
    private String type;
    private String category;
    private ArrayList<Ingredient> listOfIngredients;
    private ArrayList<Instruction> listOfInstructions;

    public Recipe(int cookTime, int prepTime, String recipeName, String type, String category, ArrayList listOfIngredients, ArrayList listOfInstructions){
        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.recipeName = recipeName;
        this.type = type;
        this.category = category;
        this.listOfIngredients = listOfIngredients;
        this.listOfInstructions = listOfInstructions;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Ingredient> getListOfIngredients() {
        return listOfIngredients;
    }

    public void setListOfIngredients(ArrayList<Ingredient> listOfIngredients) {
        this.listOfIngredients = listOfIngredients;
    }

    public ArrayList<Instruction> getListOfInstructions() {
        return listOfInstructions;
    }

    public void setListOfInstructions(ArrayList<Instruction> listOfInstructions) {
        this.listOfInstructions = listOfInstructions;
    }

    public void addIngredient(String amount, String name){
        Ingredient newIngredient = new Ingredient(amount,name);
        listOfIngredients.add(newIngredient);
    }

    public void editIngredient(Ingredient ingredient, String amount, String name){
        if(!listOfIngredients.contains(ingredient)){
            return;
        }
        int index = listOfIngredients.indexOf(ingredient);
        Ingredient editIngredient = listOfIngredients.get(index);
        editIngredient.setAmount(amount);
        editIngredient.setName(name);
        listOfIngredients.set(index, editIngredient);
    }

    public void deleteIngredient(Ingredient ingredient){
        if(!listOfIngredients.contains(ingredient)){
            return;
        }
        listOfIngredients.remove(ingredient);
    }

    public void addInstruction(int stepNum, String step){
        Instruction newInstruction = new Instruction(stepNum,step);
        listOfInstructions.add(newInstruction);
    }

    public void editInstruction(Instruction instruction, int stepNum, String step){
        if(!listOfIngredients.contains(instruction)){
            return;
        }
        int index = listOfIngredients.indexOf(instruction);
        Instruction editInstruction = listOfInstructions.get(index);
        editInstruction.setStepNum(stepNum);
        editInstruction.setStep(step);
        listOfInstructions.set(index, editInstruction);
    }

    public void deleteInstruction(Instruction instruction){
        if(!listOfIngredients.contains(instruction)){
            return;
        }
        listOfIngredients.remove(instruction);
    }
}
