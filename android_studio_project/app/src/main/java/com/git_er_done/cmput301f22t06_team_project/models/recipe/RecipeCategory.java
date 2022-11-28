package com.git_er_done.cmput301f22t06_team_project.models.recipe;

import java.util.ArrayList;

/**
 * Category represents the category of a recipe.
 * User defined parameter with a few hardcoded example options.
 */
public class RecipeCategory {

    private ArrayList<String> recipeCategories = new ArrayList<>();

    private static final RecipeCategory instance = new RecipeCategory();

    /**
     * Private constructor creates the singleton instance of RecipeCategory and populates the
     * default values
     */
    private RecipeCategory() {
        recipeCategories.add("vegetarian");
        recipeCategories.add("vegan");
        recipeCategories.add("seafood");
        recipeCategories.add("dessert");
        recipeCategories.add("meat");
        recipeCategories.add("drink");
    }

    /**
     * The effective "Constructor". Returns the singleton instance of the class.
     *
     * @return The singleton instance of RecipeCategory.
     */
    public static RecipeCategory getInstance() {
        return instance;
    }

    /**
     * Returns an ArrayList of all currently held categories.
     *
     * @return An {@link ArrayList} of {@link String}s with the current categories.
     */
    public ArrayList<String> getAllRecipeCategories() {
        return recipeCategories;
    }

    /**
     * Returns the category stored at the given index.
     *
     * @param index The index of the category to be retrieved as a primitive int.
     * @return The category at the input index as a {@link String}.
     */
    public String getRecipeCategoryFromIndex(int index) {
        return recipeCategories.get(index);
    }

    /**
     * Adds the input category to the list of categories.
     *
     * @param categoryToAdd The category to be added to the list as a {@link String}.
     */
    public void addRecipeCategory(String categoryToAdd) {
        recipeCategories.add(categoryToAdd);
    }

    /**
     * Deletes the given category from the list.
     *
     * @param categoryToDelete The category to be removed from the list as a {@link String}.
     */
    public void deleteRecipeCategory(String categoryToDelete) {
        recipeCategories.remove(categoryToDelete);
    }
}
