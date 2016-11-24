package cookbook;

import org.litepal.crud.DataSupport;

/**
 * Created by Sanda on 2016-11-24.
 */
class RecipeDetails extends DataSupport {
    private int cookTime;
    private int prepTime;
    private String recipeName;
    private String type;
    private String category;

    public RecipeDetails(int cookTime, int prepTime, String recipeName, String type, String category) {
        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.recipeName = recipeName;
        this.type = type;
        this.category = category;
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
}
