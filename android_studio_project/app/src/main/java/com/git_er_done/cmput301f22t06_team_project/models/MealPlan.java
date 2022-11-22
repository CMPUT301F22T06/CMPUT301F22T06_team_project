package com.git_er_done.cmput301f22t06_team_project.models;

import com.git_er_done.cmput301f22t06_team_project.models.Recipe.Recipe;

import java.util.ArrayList;

public class MealPlan {
    // TODO: Make planned_date a localDate
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private String planned_date;
    private int planned_servings;

    /**
     * Creates a new Meal Plan object.
     * @param date The date for the planned meal.
     * @param servings The number of servings required for the planned meal.
     */
    public MealPlan(String date, int servings) {
        planned_date = date;
        planned_servings = servings;
    }

    /**
     * Returns the full list of recipes contained in the {@link MealPlan}
     * @return An {@link ArrayList} of recipes in the meal plan
     */
    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Sets a new list of {@link Recipe}s, overwriting the old one.
     * @param recipes An {@link ArrayList} of Recipes to be set.
     */
    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * Adds a new recipe to the meal plan alongside the ones already contained.
     * @param recipe The {@link Recipe} object to be added to the meal plan.
     */
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    /**
     * Removes a {@link Recipe} from the meal plan, if it is contained in the plan.
     * @param recipe The Recipe to be removed from the meal plan.
     */
    public void removeRecipe(Recipe recipe) {
        if (recipes.contains(recipe)) {
            recipes.remove(recipe);
        } else {
            throw new IllegalArgumentException("That recipe does not exist in this meal plan!");
        }
    }

    /**
     * Returns the planned date of the meal plan as a {@link String}
     * @return The planned date for the meal plan.
     */
    public String getPlanned_date() {
        return planned_date;
    }

    /**
     * Sets a new date for the meal plan, overwriting the old one.
     * @param planned_date The new date for the planned meals
     */
    public void setPlanned_date(String planned_date) {
        this.planned_date = planned_date;
    }

    /**
     * Returns the number of people to be served at this planned meal as an int.
     * @return The number of servings to be prepared
     */
    public int getPlanned_servings() {
        return planned_servings;
    }

    /**
     * Sets a new number of planned servings overwriting the old one.
     * @param planned_servings The new number of required servings as an int.
     */
    public void setPlanned_servings(int planned_servings) {
        this.planned_servings = planned_servings;
    }

}
