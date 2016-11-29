package cookbook;

import com.google.gson.Gson;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Recipe extends DataSupport implements Serializable {

    private long id;
    @Column(ignore = true)
    private final long idRange = 123456789L;

    private int cookTime;
    private int prepTime;
    @Column(unique = true)
    private String recipeName;
    private String type;
    private String category;
    private ArrayList<Ingredient> listOfIngredients;
    private ArrayList<Instruction> listOfInstructions;

    private String listOfIngredientsInJson;
    private String listOfInstructionsInJson;

    public Recipe() {
    }

    public Recipe(int cookTime, int prepTime, String recipeName, String type, String category, ArrayList<Ingredient> listOfIngredients, ArrayList<Instruction> listOfInstructions){
        Gson gson = new Gson();

        Random random = new Random();
        id = (long) (random.nextLong()*idRange);

        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.recipeName = recipeName;
        this.type = type;
        this.category = category;
        this.listOfIngredients = listOfIngredients;
        this.listOfInstructions = listOfInstructions;

        listOfIngredientsInJson = gson.toJson(listOfIngredients);
        listOfInstructionsInJson = gson.toJson(listOfInstructions);
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

    public void setListOfIngredients(ArrayList <Ingredient> listOfIngredients) {
        this.listOfIngredients = listOfIngredients;
    }

    public ArrayList<Instruction> getListOfInstructions() {
        return listOfInstructions;
    }

    public void setListOfInstructions(ArrayList<Instruction> listOfInstructions) {
        this.listOfInstructions = listOfInstructions;
    }

    public String getListOfIngredientsInJson() {
        return listOfIngredientsInJson;
    }

    public void setListOfIngredientsInJson() {
        Gson gson = new Gson();
        listOfIngredientsInJson = gson.toJson(listOfIngredients);
    }

    public String getListOfInstructionsInJson() {
        return listOfInstructionsInJson;
    }

    public void setListOfInstructionsInJson() {
        Gson gson = new Gson();
        listOfInstructionsInJson = gson.toJson(listOfInstructions);
    }

    public long getId() {
        return id;
    }

    /*public void addIngredient(String amount, String name){
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
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (cookTime != recipe.cookTime) return false;
        if (prepTime != recipe.prepTime) return false;
        if (recipeName != null ? !recipeName.equals(recipe.recipeName) : recipe.recipeName != null)
            return false;
        if (type != null ? !type.equals(recipe.type) : recipe.type != null) return false;
        if (category != null ? !category.equals(recipe.category) : recipe.category != null)
            return false;
        if (listOfIngredients != null ? !listOfIngredients.equals(recipe.listOfIngredients) : recipe.listOfIngredients != null)
            return false;
        return listOfInstructions != null ? listOfInstructions.equals(recipe.listOfInstructions) : recipe.listOfInstructions == null;

    }

    @Override
    public int hashCode() {
        int result = cookTime;
        result = 31 * result + prepTime;
        result = 31 * result + (recipeName != null ? recipeName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (listOfIngredients != null ? listOfIngredients.hashCode() : 0);
        result = 31 * result + (listOfInstructions != null ? listOfInstructions.hashCode() : 0);
        return result;
    }
}
