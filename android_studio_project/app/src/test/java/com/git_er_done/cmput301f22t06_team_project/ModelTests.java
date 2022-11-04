package com.git_er_done.cmput301f22t06_team_project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.git_er_done.cmput301f22t06_team_project.models.*;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.*;

import org.junit.*;

import java.util.ArrayList;

// TODO: Test new ingredient when merged into main

public class ModelTests {

    public static Recipe makeRecipe() {
        //Ingredient mockIngredient = new Ingredient();
        return new LunchRecipe("Ham Sandwich", "N/A", "Lunch",
                12, 2);
    }

    public static MealPlan makeMealPlan() {
        return new MealPlan("06-06-2023", 2);
    }

    @Test
    // Should be representative for all recipes since they all extend Recipe
    public void testRecipeGetSet() {
        // TODO: Test Ingredient list once new ingredient is merged into main
        Recipe mockRecipe = makeRecipe();

        // Test getters
        assertEquals("Ham Sandwich", mockRecipe.getTitle());
        assertEquals("N/A", mockRecipe.getComments());
        assertEquals("Lunch", mockRecipe.getCategory());
        assertEquals(12, mockRecipe.getPrep_time());
        assertEquals(2, mockRecipe.getServings());

        // Test Setters
        mockRecipe.setTitle("Turkey Sandwich");
        assertEquals("Turkey Sandwich", mockRecipe.getTitle());
        mockRecipe.setComments("A Turkey sandwich with cheese");
        assertEquals("A Turkey sandwich with cheese", mockRecipe.getComments());
        mockRecipe.setCategory("Light Lunch");
        assertEquals("Light Lunch", mockRecipe.getCategory());
        mockRecipe.setPrep_time(3);
        assertEquals(3, mockRecipe.getPrep_time());
        mockRecipe.setServings(1);
        assertEquals(1, mockRecipe.getServings());
    }

    @Test
    public void testMealPlanGetSet() {
        MealPlan mockPlan = makeMealPlan();
        Recipe mockRecipe = makeRecipe();
        mockPlan.addRecipe(mockRecipe);

        assertEquals("06-06-2023", mockPlan.getPlanned_date());
        assertEquals(2, mockPlan.getPlanned_servings());
        ArrayList<Recipe> retrievedRecipes = mockPlan.getRecipes();
        assertEquals(retrievedRecipes, mockPlan.getRecipes());

        ArrayList<Recipe> newRecipes = new ArrayList<>();
        newRecipes.add(mockRecipe);
        mockPlan.setRecipes(newRecipes);

        retrievedRecipes = mockPlan.getRecipes();
        assertEquals(retrievedRecipes, mockPlan.getRecipes());

        mockPlan.setPlanned_date("07-07-2023");
        assertEquals("07-07-2023", mockPlan.getPlanned_date());
        mockPlan.setPlanned_servings(4);
        assertEquals(4, mockPlan.getPlanned_servings());
    }

    @Test
    public void testIngredientGetSet() {
        fail("Not yet implemented!");
    }
}
