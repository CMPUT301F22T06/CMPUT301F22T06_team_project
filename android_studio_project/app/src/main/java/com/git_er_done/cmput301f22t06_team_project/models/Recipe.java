package com.git_er_done.cmput301f22t06_team_project.models;

import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private String title;
    private String comments;
    private String category;
    private int prep_time;
    private int servings;

    public Recipe() {

    }


    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        if (ingredients.contains(ingredient)) {
            ingredients.remove(ingredient);
        } else {
            throw new IllegalArgumentException("That ingredient does not exist in this recipe!");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrep_time() {
        return prep_time;
    }

    public void setPrep_time(int prep_time) {
        this.prep_time = prep_time;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }
}
