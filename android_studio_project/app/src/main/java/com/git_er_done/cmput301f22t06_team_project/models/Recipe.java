package com.git_er_done.cmput301f22t06_team_project.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Recipe {
    private HashMap<Ingredient,ArrayList<String>> ingredients = new HashMap<>();
    private String title;
    private String comments;
    private String category;
    private int prep_time;
    private int servings;
    private boolean isVegetarian;
    private boolean isVegan;

    // No empty constructor since it should never be called anyway

    public Recipe(String title, String comments, String category, int prep_time, int servings,
                  boolean isVegan, boolean isVegetarian) {
        this.title = title;
        this.comments = comments;
        this.category = category;
        this.prep_time = prep_time;
        this.servings = servings;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
    }

    public HashMap<Ingredient, ArrayList<String>> getIngredients() {
        return ingredients;
    }

//    public void setIngredientsList(ArrayList<Ingredient> ingredients,) {
//        this.ingredients = ingredients;
//    }

    public void addIngredient(Ingredient ingredient, ArrayList<String> details) {
        ingredients.put(ingredient,details);
    }

    public void removeIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(ingredient)) {
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

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }
}
