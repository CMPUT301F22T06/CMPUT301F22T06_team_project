package com.git_er_done.cmput301f22t06_team_project.models.recipe;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.git_er_done.cmput301f22t06_team_project.dbHelpers.UserDefinedDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import java.util.ArrayList;

/**
 * This class represents a recipe that takes using a list of ingredients of type ingredient.
 * Take list of ingredients, title, comments, category, preptime, servings, and an image.
 * @see {Ingredient} {MealPlan} {RecipeDBHelper}
 * @version 1.8
 */
public class Recipe implements Cloneable {
    private ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
    private String title;
    private String comments;
    private String category;
    private int prep_time;
    private int servings;
    private String image;
    boolean isChecked;

    // No empty constructor since it should never be called anyway


    //Grab singleton arrays for user defined attributes like location and category
    public static ArrayList<String> recipeCategories = UserDefinedDBHelper.getRecipeCategories();

    /**
     * Creates a new Recipe object.
     *
     * @param title     The name of the Recipe
     * @param comments  A brief description of the Recipe
     * @param category  The user-defined category of the Recipe
     * @param prep_time The time it takes to prepare the Recipe
     * @param servings  The number of people this Recipe serves
     */
    public Recipe(String title, String comments, String category, int prep_time, int servings) {
        this.title = title;
        this.comments = comments;
        this.category = category;
        this.prep_time = prep_time;
        this.servings = servings;
    }

    /**
     * Creates a new Recipe object.
     *
     * @param title     The name of the Recipe
     * @param comments  A brief description of the Recipe
     * @param category  The user-defined category of the Recipe
     * @param prep_time The time it takes to prepare the Recipe
     * @param servings  The number of people this Recipe serves
     * @param image     The serialized image of the Recipe.
     */
    public Recipe(String title, String comments, String category, int prep_time, int servings, String image) {
        this.title = title;
        this.comments = comments;
        this.category = category;
        this.prep_time = prep_time;
        this.servings = servings;
        this.image = image;
    }

    /**
     * Returns an {@link ArrayList} of {@link Ingredient}s contained in the Recipe
     *
     * @return The ingredients contained in the recipe
     */
    public ArrayList<Ingredient> getIngredients() {
        return recipeIngredients;
    }

    /**
     * Sets a new list of Ingredients for the recipe, replacing the old list.
     *
     * @param recipeIngredients An {@link ArrayList} of {@link Ingredient}s to be set.
     */
    public void setIngredientsList(ArrayList<Ingredient> recipeIngredients) { //Got rid of this
        this.recipeIngredients = recipeIngredients;
    }

    /**
     * Adds a new Ingredient to the recipe alongside the old ones
     *
     * @param recipeIngredient The new {@link Ingredient} to be added
     */
    public void addIngredient(Ingredient recipeIngredient) { // I changed this
        recipeIngredients.add(recipeIngredient);
    }

    /**
     * Removes the specified {@link Ingredient} if it exists in the Recipe
     *
     * @param recipeIngredient The Ingredient to be removed.
     */
    public void removeIngredient(Ingredient recipeIngredient) {
        if (recipeIngredients.contains(recipeIngredient)) {
            recipeIngredients.remove(recipeIngredient);
        } else {
            throw new IllegalArgumentException("That ingredient does not exist in this recipe!");
        }
    }

    /**
     * Returns the Ingredients associated with the Recipe.
     *
     * @return An {@link ArrayList} of Ingredient objects associated with the Recipe.
     */
    public ArrayList<Ingredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    /**
     * Sets the Ingredients that the Recipe requires.
     *
     * @param recipeIngredients An {@link ArrayList} of Ingredient objects to be set.
     */
    public void setRecipeIngredients(ArrayList<Ingredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    /**
     * Returns the Name of the recipe as a {@link String}
     *
     * @return The title of the Recipe
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new name for the recipe, overwriting the old one.
     *
     * @param title The new name as a {@link String}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the recipe as a {@link String}
     *
     * @return The description of the recipe.
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets a new description for the recipe, overwriting the old one.
     *
     * @param comments The description to be set as a {@link String}
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Returns the category of the recipe as a {@link String}
     *
     * @return The category of the recipe.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets a new category for the recipe, overwriting the old one.
     *
     * @param category The new category of the Recipe as a {@link String}
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the amount of time it takes to make the recipe as an int
     *
     * @return The time the recipe takes to cook
     */
    public Integer getPrep_time() {
        return prep_time;
    }

    /**
     * Sets a new cooking time for the recipe, overwriting the old one.
     *
     * @param prep_time The new cooking time as an int.
     */
    public void setPrep_time(Integer prep_time) {
        this.prep_time = prep_time;
    }

    /**
     * Returns the number of people the recipe can serve
     *
     * @return The number of people that can be fed with the recipe
     */
    public Integer getServings() {
        return servings;
    }

    /**
     * Sets a new number of servings, overwriting the old one
     *
     * @param servings The new number of servings
     */
    public void setServings(Integer servings) {
        this.servings = servings;
    }

    /**
     * Returns the image uri so we can access and show it later
     *
     * @return The image uri
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the new image, overwritting the old one
     *
     * @param image the new image
     */
    public void setImage(String image) {
        this.image = image;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    /**
     * Compares Recipes based on their Title
     * @param o The Recipe to be compared
     * @return True if the recipes have the same title, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) o;
        return title.equalsIgnoreCase(other.getTitle());
    }

    /**
     * Creates a field-for-field copy of the given Recipe.
     *
     * @return A field-for-field copy of the original Recipe.
     */
    @Override
    public Recipe clone() {
        try {
            Recipe clone = (Recipe) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
