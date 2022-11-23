package com.git_er_done.cmput301f22t06_team_project.models;


import static com.git_er_done.cmput301f22t06_team_project.fragments.MealPlannerFragment.mealMap;

import android.util.Log;

import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipesDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;


/**
 * Meal consists of a list of recipes and ingredients
 */
public class Meal {
    ArrayList<MealRecipe> mealRecipes = new ArrayList<>();
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    LocalDate date;

    public Meal(){

    }

    public final static ArrayList<Meal> createTestMealList() {
        ArrayList<Meal> testMealList = new ArrayList<>();
        ArrayList<Ingredient> testIngredients = IngredientDBHelper.getIngredientsFromDB();
//        while(testIngredients.size() <= 0){
//            testIngredients = IngredientDBHelper.getIngredientsFromDB();
//        }
        ArrayList<Recipe> recipes = RecipesDBHelper.getRecipesFromDB();
        //Create a test meal with the first recipes in the list and the first ingredient
        Meal meal1 = new Meal();
        meal1.addRecipeToMeal(recipes.get(0),2);
        meal1.addIngredientToMeal(testIngredients.get(0));
        meal1.setDate(LocalDate.now());

        //Create a test meal with the last recipe in the list and last ingredient
        Meal meal2 = new Meal();
        meal2.addRecipeToMeal(recipes.get(recipes.size()-1),2);
        meal2.addIngredientToMeal(testIngredients.get(testIngredients.size()-1));
        meal2.setDate(LocalDate.now());

        mealMap.put(meal1.getDate(),meal1);
        mealMap.put(meal2.getDate(), meal2);

        return testMealList;
    }

    public static ArrayList<Meal> getMealsOnDate(LocalDate testDate){
        ArrayList<Meal> res = new ArrayList<>();
        Log.d("MEAL", "getting meals");

        //loop through hashmap of meals, return all meals that match the date argument

//        for(Object date : mealMap.keySet() ){
//            //if the datee matches, loop through the arraylist of meals which is the hashmaps value at that key
//            if(date.equals(testDate.toString())){
//                ArrayList<Meal> meals = (ArrayList<Meal>) mealMap.get(date);
//                assert meals != null;
//                meals.forEach( (n) ->
//                        res.add(n)
//                );
//
//                Log.d("getMealOnDate", "There were: " + res.size() + " meals found!");
//            }
//        }


        //Loop through all items in hashmap and print
        Set<String> setOfKeySet = mealMap.keySet();
        
        for (String key : setOfKeySet){
            
            Log.d("MEAL", key);
        }

        return res;

    }

        /**
         * Return a list of all the ingredients in the meal
         */
    public ArrayList<Ingredient> getIngredientsInMeal(){
        ArrayList<Ingredient> ingredients = new ArrayList();
        //Loop through all recipes in this meal.
            //Retreive instance of each recipe and loop through associated ingredients
            //Append each ingredient and associated amount times the recipe serving size to list
        //append list of ingredients with associated amount to list
        return ingredients;
    }

    public ArrayList<MealRecipe> getMealRecipes() {
        return mealRecipes;
    }

    public void setMealRecipes(ArrayList<MealRecipe> mealRecipes) {
        this.mealRecipes = mealRecipes;
    }

    public void addRecipeToMeal(Recipe recipe, Integer servings){
        MealRecipe mealRecipe = new MealRecipe(recipe, servings);
        mealRecipes.add(mealRecipe);
    }

    public void addIngredientToMeal(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
