package com.git_er_done.cmput301f22t06_team_project;

import static org.junit.Assert.*;

import com.git_er_done.cmput301f22t06_team_project.models.*;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.*;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.*;

import org.junit.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ModelTests {

    public static Recipe makeRecipe() {
        //Ingredient mockIngredient = new Ingredient();
        return new LunchRecipe("Ham Sandwich", "N/A", "Lunch",
                12, 2);
    }

    public static MealPlan makeMealPlan() {
        return new MealPlan("06-06-2023", 2);
    }

    public static Ingredient makeIngredient() {
        // TODO: Update to new ingredient once merged
        return new Ingredient("Steak", "Tomahawk", LocalDate.of(2023, 1, 23), "Fridge", "singles", "Meat", 2);
    }

    public static RecipeIngredient makeRecipeIngredient() {
        return new RecipeIngredient("Potato", "singles", 2, "Russet");
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
        mockRecipe.addIngredient(mockIngredient);

        ArrayList<RecipeIngredient> retrievedIngredients = mockRecipe.getIngredients();
        assertEquals(mockIngredient, retrievedIngredients.get(0));
    }

    @Test
    public void testMealPlanGetSet() {
        MealPlan mockPlan = makeMealPlan();
        Recipe mockRecipe = makeRecipe();
        mockPlan.addRecipe(mockRecipe);

        // Test getters
        assertEquals("06-06-2023", mockPlan.getPlanned_date());
        assertEquals(2, mockPlan.getPlanned_servings());
        ArrayList<Recipe> retrievedRecipes = mockPlan.getRecipes();
        assertEquals(retrievedRecipes, mockPlan.getRecipes());

        // Test setters
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
        // TODO: Implement once Ingredient is updated
        Ingredient mockIngredient = makeIngredient();

        // Test getters
        assertEquals("Steak", mockIngredient.getName());
        assertEquals("Tomahawk", mockIngredient.getDesc());

        LocalDate testDate = LocalDate.of(2023, 1, 23);
        assertTrue(testDate.isEqual(mockIngredient.getBestBefore()));

        assertEquals("Fridge", mockIngredient.getLocation());
        assertEquals("singles", mockIngredient.getUnit());
        assertEquals("Meat", mockIngredient.getCategory());
        assertEquals((int) 2, (int) mockIngredient.getAmount());

        mockIngredient.setName("T-Bone");
        assertEquals("T-Bone", mockIngredient.getName());
        mockIngredient.setDesc("Steak");
        assertEquals("Steak", mockIngredient.getDesc());

        mockIngredient.setBestBefore(LocalDate.of(2022, 12, 12));
        testDate = LocalDate.of(2022, 12, 12);
        assertTrue(testDate.isEqual(mockIngredient.getBestBefore()));

        mockIngredient.setLocation("Freezer");
        assertEquals("Freezer", mockIngredient.getLocation());
        mockIngredient.setUnit("oz");
        assertEquals("oz", mockIngredient.getUnit());
        mockIngredient.setCategory("Protein");
        assertEquals("Protein", mockIngredient.getCategory());
        mockIngredient.setAmount(12);
        assertEquals((Integer) 12, mockIngredient.getAmount());
    }

    @Test
    public void testRecipeIngredientGetSet() {
        RecipeIngredient mockIngredient = makeRecipeIngredient();

        mockIngredient.setName("Russet");
        assertEquals("Russet", mockIngredient.getName());

        mockIngredient.setComment("Potato");
        assertEquals("Potato", mockIngredient.getComment());

        mockIngredient.setUnits("g");
        assertEquals("g", mockIngredient.getUnits());

        mockIngredient.setAmount(500);
        assertEquals(500, mockIngredient.getAmount());
    }
}
