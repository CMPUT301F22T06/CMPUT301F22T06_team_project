package com.git_er_done.cmput301f22t06_team_project.ModelTests.Singletons;

import static org.junit.Assert.assertEquals;

import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeCategory;

import org.junit.Test;

import java.util.ArrayList;

public class RecipeCategoryTest {

    @Test
    public void testGetAllCategories() {
        RecipeCategory category = RecipeCategory.getInstance();

        ArrayList<String> initialCategories = new ArrayList<>();
        initialCategories.add("vegetarian");
        initialCategories.add("vegan");
        initialCategories.add("seafood");
        initialCategories.add("dessert");
        initialCategories.add("meat");
        initialCategories.add("drink");

        assertEquals(initialCategories, category.getAllRecipeCategories());
    }

    @Test
    public void testAddGetCategory() {
        RecipeCategory category = RecipeCategory.getInstance();

        category.addRecipeCategory("beef");
        assertEquals("beef", category.getRecipeCategoryFromIndex(6));
    }
}
