package com.git_er_done.cmput301f22t06_team_project.ModelTests;

import static org.junit.Assert.assertEquals;

import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeIngredient;

import org.junit.Test;

import java.util.ArrayList;

public class RecipeTest {

    public static RecipeIngredient makeRecipeIngredient() {
        return new RecipeIngredient("Potato", "singles", 2, "Russet");
    }

    public static Recipe makeRecipe() {
        //Ingredient mockIngredient = new Ingredient();
        return new Recipe("Ham Sandwich", "N/A", "Lunch",
                12, 2);
    }

    @Test
    // Should be representative for all recipes since they all extend Recipe
    public void testRecipeGetSet() {
        // TODO: Test Ingredient list once new ingredient is merged into main
        Recipe mockRecipe = makeRecipe();
        RecipeIngredient mockIngredient = makeRecipeIngredient();

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

        ArrayList<RecipeIngredient> retrievedIngredients = mockRecipe.getIngredients();
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
