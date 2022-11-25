package com.git_er_done.cmput301f22t06_team_project.DBHelperTests;

import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import org.junit.Test;

public class RecipeDBHelperTest {

    @Test
    public void testAddRecipe() {
        Recipe testRecipe = new Recipe("unit test", "Comments", "category", 1, 1);
        RecipeDBHelper.addRecipe(testRecipe);
    }

    @Test
    public void testDeleteRecipe() {
    }

    @Test
    public void testModifyRecipeInDB() {
    }

    @Test
    public void testSetRecipesAdapter() {
    }

    @Test
    public void testSetRecipeIngredientAdapter() {
    }

    @Test
    public void testEventChangeListener() {
    }
}