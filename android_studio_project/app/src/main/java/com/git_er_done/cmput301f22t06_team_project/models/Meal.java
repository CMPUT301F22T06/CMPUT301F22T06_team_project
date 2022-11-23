package com.git_er_done.cmput301f22t06_team_project.models;

import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe.RecipeIngredient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

//TODO - Allow users to copy a meal to a different date
/**
 *  Meal model class.
 *  Consists of an id, a list of recipes, list of ingredients, and date.
 */
public class Meal {
    /**
     * id for storing the meal in the DB
     */
    UUID id;

    /**
     * List of recipes in this meal. Does not exactly match recipes in storage as serving size can be adjusted by user.
     */
    ArrayList<Recipe> recipes;

    /**
     * List of ingredients in this meal. Does not exactly match ingredients in storage as amount can be adjusted by user.
     */
    ArrayList<Ingredient> ingredients;

    /**
     * Date assigned to this meal.
     */
    LocalDate date;

    /**
     * Constructor creates a meal item and assigns it a new UUID.
     * @param recipes ArrayList of recipe objects for this meal
     * @param ingredients ArrayList of ingredient objects for this meal
     * @param date LocalDate representing the date the meal is assigned
     */
    public Meal(ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredients, LocalDate date) {
        this.id = UUID.randomUUID();
        this.recipes = recipes;
        this.ingredients = ingredients;
        this.date = date;
    }


    public ArrayList<Recipe> getRecipesFromMeal() {
        return recipes;
    }

    public void addRecipeToMeal(Recipe recipeToAdd){
        recipes.add(recipeToAdd);
    }

    public ArrayList<Ingredient> getIngredientsFromMeal() {
        ArrayList<Ingredient> allIngredients = new ArrayList<>();

        //Gather all ingredients from recipes in this meal
        //Loop through each recipe in the meal
        for(int i = 0; i < recipes.size(); i++){
            //Loop through all ingredients in the current recipe
//            ArrayList<RecipeIngredient> ingredientsInRecipe = recipes.get(i).getRecipeIngredients();
//            for(int j = 0; j < ingredientsInRecipe.size(); j++){
//                //TODO - If ingredient is already in the resulting arraylist, increase amount
//            }
        }

        allIngredients.addAll(ingredients);
        return allIngredients;
    }

    public void addIngredientToMeal(Ingredient ingredientToAdd){
        ingredients.add(ingredientToAdd);
    }

}
