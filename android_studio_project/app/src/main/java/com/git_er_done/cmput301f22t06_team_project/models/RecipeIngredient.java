package com.git_er_done.cmput301f22t06_team_project.models;

public class RecipeIngredient {
    Ingredient ingredient;
    String units;
    int amount;
    String comment;
    public RecipeIngredient(Ingredient ingredient, String units, int amount, String comment) {
        this.ingredient = ingredient;
        this.units = units;
        this.amount = amount;
        this.comment = comment;
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

    public String getName(){
        return ingredient.getName();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
