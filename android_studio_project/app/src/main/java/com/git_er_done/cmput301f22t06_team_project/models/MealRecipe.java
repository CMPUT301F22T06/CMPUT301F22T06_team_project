package com.git_er_done.cmput301f22t06_team_project.models;

public class MealRecipe {
    private Recipe recipe;
    private Integer servings;

    public MealRecipe(Recipe recipe, Integer servings) {
        this.recipe = recipe;
        this.servings = servings;
    }


    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }
}
