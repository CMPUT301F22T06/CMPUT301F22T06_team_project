package com.git_er_done.cmput301f22t06_team_project.ModelTests;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.git_er_done.cmput301f22t06_team_project.MainActivity;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import java.time.LocalDate;
import java.util.ArrayList;

public class MealTest {
    private final Random rand = new Random();

    public Recipe makeRecipe() {
        return new Recipe("unit test", "test", "grain", rand.nextInt(10), rand.nextInt(10));
    }
    public Ingredient makeIngredient() {
        return new Ingredient("unit test", "test", LocalDate.now().plusMonths(1), "pantry", "oz", "grain", rand.nextInt(10));
    }

    public Meal makeMeal() {
        return new Meal(new ArrayList<>(), new ArrayList<>(), LocalDate.now().plusDays(1));
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddGetIngredients() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Meal mockMeal = makeMeal();
        Ingredient firstIngredient = makeIngredient();
        Ingredient secondIngredient = makeIngredient();
        mockMeal.addIngredientToMeal(firstIngredient);
        mockMeal.addIngredientToMeal(firstIngredient);
        mockMeal.addIngredientToMeal(secondIngredient);

        assertEquals(firstIngredient, mockMeal.getOnlyIngredientsFromMeal().get(0));
        assertEquals(firstIngredient, mockMeal.getOnlyIngredientsFromMeal().get(1));
        assertEquals(secondIngredient, mockMeal.getOnlyIngredientsFromMeal().get(2));
    }

    @Test
    public void testAddGetRecipes() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Meal mockMeal = makeMeal();
        Recipe firstRecipe = makeRecipe();
        Recipe secondRecipe = makeRecipe();
        mockMeal.addRecipeToMeal(firstRecipe);
        mockMeal.addRecipeToMeal(firstRecipe);
        mockMeal.addRecipeToMeal(secondRecipe);

        assertEquals(firstRecipe, mockMeal.getRecipesFromMeal().get(0));
        assertEquals(firstRecipe, mockMeal.getRecipesFromMeal().get(1));
        assertEquals(secondRecipe, mockMeal.getRecipesFromMeal().get(2));
    }

}
