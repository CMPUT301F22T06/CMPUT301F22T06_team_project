package com.git_er_done.cmput301f22t06_team_project.models.meal;

import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
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
    ArrayList<Recipe> recipes = new ArrayList<>();

    /**
     * List of ingredients in this meal. Does not exactly match ingredients in storage as amount can be adjusted by user.
     */
    ArrayList<Ingredient> ingredients = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

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

    public ArrayList<Ingredient> getOnlyIngredientsFromMeal(){
        return this.ingredients;
    }

    public ArrayList<Ingredient> getAllIngredientsFromMeal() {
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

    public static ArrayList<Meal> createDummyMealList(){
        ArrayList<Meal> dummyMeals = new ArrayList<Meal>();
        ArrayList<Ingredient> ingredients = IngredientDBHelper.getIngredientsFromStorage();
        ArrayList<Recipe> recipes = RecipeDBHelper.getRecipesFromStorage();

        Meal meal1 = new Meal(
                new ArrayList<Recipe>(Arrays.asList(recipes.get(0), recipes.get(1))),
                new ArrayList<Ingredient>(Arrays.asList(ingredients.get(0),ingredients.get(1))),
                LocalDate.now().plus(1, ChronoUnit.DAYS)
        );

        Meal meal2 = new Meal(
                new ArrayList<Recipe>(Arrays.asList(recipes.get(0), recipes.get(1))),
                new ArrayList<Ingredient>(Arrays.asList(ingredients.get(2),ingredients.get(3))),
                LocalDate.now()
        );
        Meal meal3 = new Meal(
                new ArrayList<Recipe>(Arrays.asList(recipes.get(1))),
                new ArrayList<Ingredient>(Arrays.asList(ingredients.get(4),ingredients.get(5))),
                LocalDate.now()
        );

        Meal meal4 = new Meal(
                new ArrayList<Recipe>(Arrays.asList(recipes.get(0))),
                new ArrayList<Ingredient>(Arrays.asList(ingredients.get(6),ingredients.get(7))),
                LocalDate.now().minus(1, ChronoUnit.DAYS)
        );

        dummyMeals.add(meal1);
        dummyMeals.add(meal2);
        dummyMeals.add(meal3);
        dummyMeals.add(meal4);

        return dummyMeals;
    }

}
