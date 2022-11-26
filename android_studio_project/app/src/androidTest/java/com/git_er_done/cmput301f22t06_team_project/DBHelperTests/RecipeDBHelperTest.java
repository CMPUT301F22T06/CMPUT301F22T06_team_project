package com.git_er_done.cmput301f22t06_team_project.DBHelperTests;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.git_er_done.cmput301f22t06_team_project.MainActivity;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class RecipeDBHelperTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    public Recipe makeRecipe() {
        return new Recipe("unit test", "Comments", "category", 1, 1);
    }

    @Test
    public void testAddRecipe() {
        RecipeDBHelper.getInstance();
        ArrayList<String> nameFromDB = new ArrayList<>();
        ArrayList<Recipe> recFromDB;
        Recipe mockRecipe = makeRecipe();
        RecipeDBHelper.addRecipe(mockRecipe);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recFromDB = RecipeDBHelper.getRecipesFromStorage();
        for (int i = 0; i < recFromDB.size(); i++) {
            nameFromDB.add(recFromDB.get(i).getTitle());
        }

        assertTrue(nameFromDB.contains(mockRecipe.getTitle()));

        // Cleanup
        RecipeDBHelper.deleteRecipe(mockRecipe, 0);

    }

    @Test
    public void testDeleteRecipe() {
    }

    @Test
    public void testModifyRecipeInDB() {
    }
}