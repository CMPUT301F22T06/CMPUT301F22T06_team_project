package com.git_er_done.cmput301f22t06_team_project.models;

public class RecipeIngredient {
    Ingredient ingredient;
    String units;
    int amount;

    public RecipeIngredient(Ingredient ingredient, String units, int amount) {
        this.ingredient = ingredient;
        this.units = units;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
<<<<<<< HEAD

    public String getName(){
        return ingredient.getName();
    }
=======
>>>>>>> RecipeAbstraction
}
