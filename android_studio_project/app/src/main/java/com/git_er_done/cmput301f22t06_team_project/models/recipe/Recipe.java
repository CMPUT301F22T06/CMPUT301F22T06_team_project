package com.git_er_done.cmput301f22t06_team_project.models.recipe;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.git_er_done.cmput301f22t06_team_project.dbHelpers.UserDefinedDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import java.util.ArrayList;


public class Recipe implements Cloneable{
    private ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
    private String title;
    private String comments;
    private String category;
    private int prep_time;
    private int servings;
    private String image;

    // No empty constructor since it should never be called anyway


    //Grab singleton arrays for user defined attributes like location and category
    public static ArrayList<String> recipeCategories = UserDefinedDBHelper.getRecipeCategories();

    /**
     * Creates a new Recipe object.
     * @param title The name of the Recipe
     * @param comments A brief description of the Recipe
     * @param category The user-defined category of the Recipe
     * @param prep_time The time it takes to prepare the Recipe
     * @param servings The number of people this Recipe serves
     */
    public Recipe(String title, String comments, String category, int prep_time, int servings) {
        this.title = title;
        this.comments = comments;
        this.category = category;
        this.prep_time = prep_time;
        this.servings = servings;
    }

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
     * @return The ingredients contained in the recipe
     */
    public ArrayList<Ingredient> getIngredients() {
        return recipeIngredients;
    }

    /**
     * Sets a new list of Ingredients for the recipe, replacing the old list.
     * @param recipeIngredients An {@link ArrayList} of {@link Ingredient}s to be set.
     */
    public void setIngredientsList(ArrayList<Ingredient> recipeIngredients) { //Got rid of this
        this.recipeIngredients = recipeIngredients;
    }

    /**
     * Adds a new Ingredient to the recipe alongside the old ones
     * @param recipeIngredient The new {@link Ingredient} to be added
     */
    public void addIngredient(Ingredient recipeIngredient) { // I changed this
        recipeIngredients.add(recipeIngredient);
    }

    /**
     * Removes the specified {@link Ingredient} if it exists in the Recipe
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
     * Creates an {@link ArrayList} of recipes for UI testing
     * @return An {@link ArrayList} of recipes.
     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public final static ArrayList<Recipe> createRecipeList() {
//        ArrayList<Recipe> testRecipes = new ArrayList<>();
//        // Breakfast
//        Recipe fruit_salad = new Recipe("perfect summer fruit salad", "Perfect for the summer and cooling off.", "vegan", 30, 10);
//        RecipeIngredient fruit_salad_sugar = new RecipeIngredient("sugar", "ml", 83, "Boil in saucepan");
//        RecipeIngredient fruit_salad_strawberry = new RecipeIngredient("strawberry", "ml", 500, "Hulled and sliced");
//        RecipeIngredient fruit_salad_apple = new RecipeIngredient("apple", "singles", 2, "Sliced");
//        RecipeIngredient fruit_salad_vanilla_extract = new RecipeIngredient("vanilla extract", "ml", 5, "Stir in with sugar");
//        RecipeIngredient fruit_salad_pineapple = new RecipeIngredient("pineapple", "singles", 1, "Cut into slices");
//        fruit_salad.addIngredient(fruit_salad_apple);
//        fruit_salad.addIngredient(fruit_salad_sugar);
//        fruit_salad.addIngredient(fruit_salad_strawberry);
//        fruit_salad.addIngredient(fruit_salad_vanilla_extract);
//        fruit_salad.addIngredient(fruit_salad_pineapple);
//
//        Recipe spicy_tuna_poke = new Recipe("spicy tuna poke bowl", "Spicy and refreshing taste of the ocean", "seafood", 15, 2);
//        RecipeIngredient poke_tuna = new RecipeIngredient("tuna", "oz", 2, "Cut into 1/2 inch cubes");
//        RecipeIngredient poke_soy_sauce = new RecipeIngredient("soy sauce", "ml", 30, "Combine with tuna");
//        RecipeIngredient poke_sesame_oil = new RecipeIngredient("sesame oil", "ml", 5, "Combine with tuna");
//        RecipeIngredient poke_rice = new RecipeIngredient("rice", "g", 250, "Cook until soft and fluffy");
//        RecipeIngredient poke_cucumber = new RecipeIngredient("cucumber", "g", 250, "Diced into 1/2 inch cubes");
//        spicy_tuna_poke.addIngredient(poke_tuna);
//        spicy_tuna_poke.addIngredient(poke_soy_sauce);
//        spicy_tuna_poke.addIngredient(poke_sesame_oil);
//        spicy_tuna_poke.addIngredient(poke_rice);
//        spicy_tuna_poke.addIngredient(poke_cucumber);
//
//        // Lunch
//        Recipe fried_rice = new Recipe("easy fried rice", "Super simple and super delicious", "meat", 40, 4);
//        RecipeIngredient fr_rice = new RecipeIngredient("rice", "g", 250, "Must be day old for best results");
//        RecipeIngredient fr_vegetable_oil = new RecipeIngredient("vegetable oil", "ml", 10, "Heat in pan");
//        RecipeIngredient fr_egg = new RecipeIngredient("egg", "singles", 2, "Lightly whisked");
//        RecipeIngredient fr_carrot = new RecipeIngredient("carrot", "singles", 1, "Peeled and grated");
//        RecipeIngredient fr_bacon = new RecipeIngredient("bacon", "singles", 2, "Chopped and sliced");
//        fried_rice.addIngredient(fr_rice);
//        fried_rice.addIngredient(fr_vegetable_oil);
//        fried_rice.addIngredient(fr_egg);
//        fried_rice.addIngredient(fr_carrot);
//        fried_rice.addIngredient(fr_bacon);
//
//        Recipe honey_soy_chicken = new Recipe("honey soy chicken", "Sweet and tangy", "poultry", 165, 4);
//
//        // Dinner
//        Recipe pumpkin_soup = new Recipe("pumpkin soup", "Creamy and perfect for the fall season", "vegetarian", 50, 6);
//        Recipe pad_thai = new Recipe("pad thai", "Best noodle dish around.", "meat", 40, 4);
//
//        // Dessert
//        Recipe vanilla_icecream = new Recipe("vanilla icecream", "Perfect with fresh baked pie.", "dessert", 155, 4);
//
//        Recipe bloody_mary = new Recipe("bloody mary", "When you wanna forget everything.", "drink", 2, 1);
//
//
//        // Initialize the recipes
//        // breakfast
//        testRecipes.add(fruit_salad);
//        testRecipes.add(spicy_tuna_poke);
//
//        // lunch
//        testRecipes.add(fried_rice);
//        testRecipes.add(honey_soy_chicken);
//
//        // dinner
//        testRecipes.add(pumpkin_soup);
//        testRecipes.add(pad_thai);
//
//        // dessert
//        testRecipes.add(bloody_mary);
//        testRecipes.add(vanilla_icecream);
//
//        return testRecipes;
//    }

    public ArrayList<Ingredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<Ingredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    /**
     * Returns the Name of the recipe as a {@link String}
     * @return The title of the Recipe
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new name for the recipe, overwriting the old one.
     * @param title The new name as a {@link String}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the recipe as a {@link String}
     * @return The description of the recipe.
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets a new description for the recipe, overwriting the old one.
     * @param comments The description to be set as a {@link String}
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Returns the category of the recipe as a {@link String}
     * @return The category of the recipe.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets a new category for the recipe, overwriting the old one.
     * @param category The new category of the Recipe as a {@link String}
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *  Returns the amount of time it takes to make the recipe as an int
     * @return The time the recipe takes to cook
     */
    public Integer getPrep_time() {
        return prep_time;
    }

    /**
     * Sets a new cooking time for the recipe, overwriting the old one.
     * @param prep_time The new cooking time as an int.
     */
    public void setPrep_time(Integer prep_time) {
        this.prep_time = prep_time;
    }

    /**
     * Returns the number of people the recipe can serve
     * @return The number of people that can be fed with the recipe
     */
    public Integer getServings() {
        return servings;
    }

    /**
     * Sets a new number of servings, overwriting the old one
     * @param servings The new number of servings
     */
    public void setServings(Integer servings) {
        this.servings = servings;
    }

    /**
     *  Returns the image uri so we can access and show it later
     * @return The image uri
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the new image, overwritting the old one
     * @param image the new image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Required for indexOf call
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) o;
        return title.equalsIgnoreCase(other.getTitle());
    }

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
