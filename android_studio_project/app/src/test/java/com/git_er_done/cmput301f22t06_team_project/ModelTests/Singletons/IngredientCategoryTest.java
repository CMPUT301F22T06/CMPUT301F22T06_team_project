package com.git_er_done.cmput301f22t06_team_project.ModelTests.Singletons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;



public class IngredientCategoryTest {

    @Test
    public void testGetAllCategories() {
        ArrayList<String> initialCategories = new ArrayList<>();
        initialCategories.add("dairy");
        initialCategories.add("fruit");
        initialCategories.add("grain");
        initialCategories.add("lipid");
        initialCategories.add("protein");
        initialCategories.add("spice");
        initialCategories.add("vegetable");
        initialCategories.add("miscellaneous");

        IngredientCategory category = IngredientCategory.getInstance();

        ArrayList<String> fromSingleton = category.getAllIngredientCategories();
        assertEquals(initialCategories, fromSingleton);
    }

    @Test
    public void testAddGetCategory() {
        IngredientCategory category = IngredientCategory.getInstance();

        category.addIngredientCategory("starch");
        // First user-defined index
        assertEquals("starch", category.getIngredientCategoryFromIndex(8));
        // Cleanup
        category.deleteCategory("starch");
    }

    @Test
    public void testDeleteCategory() {
        IngredientCategory category = IngredientCategory.getInstance();

        category.addIngredientCategory("starch");
        assertTrue(category.getAllIngredientCategories().contains("starch"));

        category.deleteCategory("starch");
        assertFalse(category.getAllIngredientCategories().contains("starch"));
    }
}
