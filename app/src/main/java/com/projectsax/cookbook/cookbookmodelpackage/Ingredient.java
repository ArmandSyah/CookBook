package com.projectsax.cookbook.cookbookmodelpackage;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/*
    Class: Ingredient
    Object representation of an Ingredient of a recipe
    Implements Serializable to be able to Searilize Ingredient Object
 */
public class Ingredient implements Serializable {

    private String amount; //Amount or Quantity of Ingredient
    private String name; //Name of Ingredient

    public Ingredient(String name){
        this.amount = "";
        this.name = name;
    }

    public Ingredient(String amount, String name){
        this.amount = amount;
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    /*
        In order for two ingredients to be equal, all you need for that is that the ingredients are
        spelt the same
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        return name.toUpperCase().equals(that.name.toUpperCase());

    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "amount='" + amount + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
