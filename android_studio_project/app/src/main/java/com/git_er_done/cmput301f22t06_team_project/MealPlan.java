package com.git_er_done.cmput301f22t06_team_project;

import java.util.ArrayList;

public class MealPlan {
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private String planned_date;
    private int planned_servings;

    public MealPlan(String date, int servings) {
        planned_date = date;
        planned_servings = servings;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        if (recipes.contains(recipe)) {
            recipes.remove(recipe);
        } else {
            throw new IllegalArgumentException("That recipe does not exist in this meal plan!");
        }
    }

    public String getPlanned_date() {
        return planned_date;
    }

    public void setPlanned_date(String planned_date) {
        this.planned_date = planned_date;
    }

    public int getPlanned_servings() {
        return planned_servings;
    }

    public void setPlanned_servings(int planned_servings) {
        this.planned_servings = planned_servings;
    }

}
