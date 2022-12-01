package com.git_er_done.cmput301f22t06_team_project.ModelTests;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.git_er_done.cmput301f22t06_team_project.MainActivity;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class RecipeTest {

    public static Ingredient makeRecipeIngredient() {
        return new Ingredient("potato", "Russet", LocalDate.now().plusMonths(4), "pantry", "singles", "grain", 5);
    }

    public static Recipe makeRecipe() {
        //Ingredient mockIngredient = new Ingredient();
        return new Recipe("Ham Sandwich", "N/A", "Lunch",
                12, 2);
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    // Should be representative for all recipes since they all extend Recipe
    public void testRecipeGetSet() {
        // TODO: Test Ingredient list once new ingredient is merged into main
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Recipe mockRecipe = makeRecipe();
        Ingredient mockIngredient = makeRecipeIngredient();

        // Test getters
        assertEquals("Ham Sandwich", mockRecipe.getTitle());
        assertEquals("N/A", mockRecipe.getComments());
        assertEquals("Lunch", mockRecipe.getCategory());
        assertEquals(12, (int) mockRecipe.getPrep_time());
        assertEquals(2, (int) mockRecipe.getServings());

        // Test Setters
        mockRecipe.setTitle("Turkey Sandwich");
        assertEquals("Turkey Sandwich", mockRecipe.getTitle());
        mockRecipe.setComments("A Turkey sandwich with cheese");
        assertEquals("A Turkey sandwich with cheese", mockRecipe.getComments());
        mockRecipe.setCategory("Light Lunch");
        assertEquals("Light Lunch", mockRecipe.getCategory());
        mockRecipe.setPrep_time(3);
        assertEquals(3, (int) mockRecipe.getPrep_time());
        mockRecipe.setServings(1);
        assertEquals(1, (int) mockRecipe.getServings());
        mockRecipe.addIngredient(mockIngredient);

        ArrayList<Ingredient> retrievedIngredients = mockRecipe.getIngredients();
        assertEquals(mockIngredient, retrievedIngredients.get(0));
    }

    @Test
    public void testRecipeEquals() {
        Recipe firstRecipe = makeRecipe();
        Recipe secondRecipe = makeRecipe();

        // Uses overridden .equals() operator
        assertEquals(firstRecipe, secondRecipe);
    }

    @Test
    public void testRecipeClone() {
        Recipe firstRecipe = makeRecipe();
        Recipe secondRecipe = firstRecipe.clone();

        assertEquals(firstRecipe, secondRecipe);
    }
}
