package com.git_er_done.cmput301f22t06_team_project.ModelTests.Singletons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.git_er_done.cmput301f22t06_team_project.models.ingredient.IngredientCategory;

import org.junit.Before;
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

        category.addIngredientCategory("grain");
        // First user-defined index
        assertEquals("grain", category.getIngredientCategoryFromIndex(8));
        // Cleanup
        category.deleteCategory("grain");
    }

    @Test
    public void testDeleteCategory() {
        IngredientCategory category = IngredientCategory.getInstance();

        category.addIngredientCategory("grain");

        category.deleteCategory("grain");
        //System.err.println(category.getAllIngredientCategories().contains("grain"));
        //System.err.println(category.getAllIngredientCategories());
        assertFalse(category.getAllIngredientCategories().contains("grain"));
    }
}
