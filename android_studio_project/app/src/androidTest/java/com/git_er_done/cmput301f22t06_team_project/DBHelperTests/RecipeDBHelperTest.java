package com.git_er_done.cmput301f22t06_team_project.DBHelperTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    public Recipe makeRecipe(int index) {
        if (index == 1) {
            return new Recipe("unit test", "Comments", "category", 1, 1);
        } else {
            return new Recipe("unit test", "Other", "other", 10, 10);
        }
    }

    @Test
    public void testAddRecipe() {
        RecipeDBHelper.getInstance();
        ArrayList<String> nameFromDB = new ArrayList<>();
        ArrayList<Recipe> recFromDB;
        Recipe mockRecipe = makeRecipe(1);
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
        RecipeDBHelper.getInstance();
        Recipe mockRecipe = makeRecipe(1);
        ArrayList<String> namesFromDB = new ArrayList<>();
        ArrayList<Recipe> recFromDB;

        RecipeDBHelper.addRecipe(mockRecipe);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recFromDB = RecipeDBHelper.getRecipesFromStorage();
        for (int i = 0; i < recFromDB.size(); i++) {
            namesFromDB.add(recFromDB.get(i).getTitle());
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(namesFromDB.contains(mockRecipe.getTitle()));

        RecipeDBHelper.deleteRecipe(mockRecipe, 0);

        // Allow database time to give data
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recFromDB = RecipeDBHelper.getRecipesFromStorage();
        namesFromDB = new ArrayList<>();
        for (int i = 0; i < recFromDB.size(); i++) {
            namesFromDB.add(recFromDB.get(i).getTitle());
        }

        assertFalse(namesFromDB.contains(mockRecipe.getTitle()));
    }

    @Test
    public void testModifyRecipeInDB() {
        RecipeDBHelper.getInstance();
        Recipe firstRecipe = makeRecipe(1);
        Recipe secondRecipe = makeRecipe(2);
        ArrayList<Recipe> recFromDB;
        int updatedIndex = -1;

        RecipeDBHelper.addRecipe(firstRecipe);

        // Allow database time to give data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecipeDBHelper.modifyRecipeInDB(secondRecipe, firstRecipe, 0);

        // Give DB time to update entry
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recFromDB = RecipeDBHelper.getRecipesFromStorage();
        for (int i = 0; i < recFromDB.size(); i++) {
            if (recFromDB.get(i).getTitle().equals(secondRecipe.getTitle())) {
                updatedIndex = i;
                break;
            }
        }

        if (updatedIndex == -1) {
            fail("Recipe not found!");
        }

        assertEquals(secondRecipe.getTitle(), recFromDB.get(updatedIndex).getTitle());
        assertEquals(secondRecipe.getComments(), recFromDB.get(updatedIndex).getComments());
        assertEquals(secondRecipe.getCategory(), recFromDB.get(updatedIndex).getCategory());
        assertEquals(secondRecipe.getPrep_time(), recFromDB.get(updatedIndex).getPrep_time());
        assertEquals(secondRecipe.getServings(), recFromDB.get(updatedIndex).getServings());

        RecipeDBHelper.deleteRecipe(secondRecipe, 0);
    }
}