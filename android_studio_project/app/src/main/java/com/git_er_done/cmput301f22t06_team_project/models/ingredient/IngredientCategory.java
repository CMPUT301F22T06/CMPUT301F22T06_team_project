package com.git_er_done.cmput301f22t06_team_project.models.ingredient;

import java.util.ArrayList;

/**
 * Category represents the category of an ingredient
 * User defined parameter with a few hardcoded example options.
 */
public class IngredientCategory {

    private ArrayList<String> ingredientCategories = new ArrayList<>();

    private static final IngredientCategory instance = new IngredientCategory();

    /**
     * Private constructor creates the singleton instance of IngredientCategory and populates the
     * default values
     */
    private IngredientCategory() {
        ingredientCategories.add("dairy");
        ingredientCategories.add("fruit");
        ingredientCategories.add("grain");
        ingredientCategories.add("lipid");
        ingredientCategories.add("protein");
        ingredientCategories.add("spice");
        ingredientCategories.add("vegetable");
        ingredientCategories.add("miscellaneous");
    }

    /**
     * The effective "Constructor". Returns the singleton instance of the class.
     *
     * @return The singleton instance of IngredientCategory.
     */
    public static IngredientCategory getInstance() {
        return instance;
    }

    /**
     * Returns an ArrayList of all currently held categories.
     *
     * @return An {@link ArrayList} of {@link String}s with the current categories.
     */
    public ArrayList<String> getAllIngredientCategories() {
        return ingredientCategories;
    }

    /**
     * Returns the category stored at the given index.
     *
     * @param index The index of the category to be retrieved as a primitive int.
     * @return The category at the input index as a {@link String}.
     */
    public String getIngredientCategoryFromIndex(int index) {
        return ingredientCategories.get(index);
    }

    /**
     * Adds the input category to the list of categories.
     *
     * @param categoryToAdd The category to be added to the list as a {@link String}.
     */
    public void addIngredientCategory(String categoryToAdd) {
        ingredientCategories.add(categoryToAdd);
    }

    /**
     * Deletes the given category from the list.
     *
     * @param categoryToDelete The category to be removed from the list as a {@link String}.
     */
    public void deleteCategory(String categoryToDelete) {
        ingredientCategories.remove(categoryToDelete);
    }

}
