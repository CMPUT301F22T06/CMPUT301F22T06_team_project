package com.git_er_done.cmput301f22t06_team_project.models;

public class RecipeIngredient {
    String name;
    String units;
    int amount;
    String comment;

    public RecipeIngredient(String name, String units, int amount, String comment) {
        this.name = name;
        this.units = units;
        this.amount = amount;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
