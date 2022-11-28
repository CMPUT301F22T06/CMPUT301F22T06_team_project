package com.git_er_done.cmput301f22t06_team_project.DBHelperTests;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.git_er_done.cmput301f22t06_team_project.MainActivity;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.UserDefinedDBHelper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class UserDefinedDBHelperTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testIngredientCategories() {
        UserDefinedDBHelper.getInstance();

        UserDefinedDBHelper.addUserDefined("unit test", "ingredientCategory");
        ArrayList<String> catFromDB = UserDefinedDBHelper.getIngredientCategories();
        // Allow database time to give data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(catFromDB.contains("unit test"));

        UserDefinedDBHelper.deleteUserDefined("unit test", "ingredientCategory", 0);
    }

    @Test
    public void testIngredientUnits() {
        UserDefinedDBHelper.getInstance();

        UserDefinedDBHelper.addUserDefined("unit test", "ingredientUnits");
        ArrayList<String> catFromDB = UserDefinedDBHelper.getIngredientUnits();
        // Allow database time to give data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(catFromDB.contains("unit test"));

        UserDefinedDBHelper.deleteUserDefined("unit test", "ingredientUnits", 0);
    }

    @Test
    public void testIngredientLocations() {
        UserDefinedDBHelper.getInstance();

        UserDefinedDBHelper.addUserDefined("unit test", "ingredientLocations");
        ArrayList<String> catFromDB = UserDefinedDBHelper.getIngredientLocations();
        // Allow database time to give data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(catFromDB.contains("unit test"));

        UserDefinedDBHelper.deleteUserDefined("unit test", "ingredientLocations", 0);
    }

    @Test
    public void testRecipeCategory() {
        UserDefinedDBHelper.getInstance();

        UserDefinedDBHelper.addUserDefined("unit test", "recipeCategory");
        ArrayList<String> catFromDB = UserDefinedDBHelper.getRecipeCategories();
        // Allow database time to give data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(catFromDB.contains("unit test"));

        UserDefinedDBHelper.deleteUserDefined("unit test", "recipeCategory", 0);
    }
}
