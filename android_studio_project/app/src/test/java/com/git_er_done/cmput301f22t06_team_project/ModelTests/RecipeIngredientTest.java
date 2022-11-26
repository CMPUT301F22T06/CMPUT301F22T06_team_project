package com.git_er_done.cmput301f22t06_team_project.ModelTests;

import static org.junit.Assert.assertEquals;

import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeCategory;

import org.junit.Test;

import java.util.ArrayList;

public class RecipeIngredientTest {

    public static RecipeIngredient makeRecipeIngredient() {
        return new RecipeIngredient("Potato", "singles", 2, "Russet");
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

    @Test
    public void testRecipeCategory() {
        RecipeCategory category = RecipeCategory.getInstance();

        category.addRecipeCategory("milkshake");

        ArrayList<String> allCategories = category.getAllRecipeCategories();

        // First user-defined category index
        assertEquals("milkshake", category.getRecipeCategoryFromIndex(7));
    }
}
