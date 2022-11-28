package com.git_er_done.cmput301f22t06_team_project.DBHelperTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.git_er_done.cmput301f22t06_team_project.MainActivity;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class IngredientDBHelperTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    public Ingredient makeIngredient(int index) {
        if (index == 1) {
            return new Ingredient("unit test", "T-Bone", LocalDate.now().plusYears(1),
                    "freezer", "singles", "protein", 2);
        }
        else {
            return new Ingredient("unit test", "Sirloin", LocalDate.now().plusMonths(1),
                    "fridge", "oz", "meats", 8);
        }
    }

    @Test
    public void testAddIngredient() {
        IngredientDBHelper.getInstance();
        ArrayList<String> namesFromDB = new ArrayList<>();
        ArrayList<Ingredient> ingFromDB;
        Ingredient mockIngredient = makeIngredient(1);
        IngredientDBHelper.addIngredientToDB(mockIngredient);

        // Allow database time to give data
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ingFromDB = IngredientDBHelper.getIngredientsFromStorage();
        System.out.println("Data from DB:");
        for (int i = 0; i < ingFromDB.size(); i++) {
            System.out.println(ingFromDB.get(i).getName());
            namesFromDB.add(ingFromDB.get(i).getName());
        }
        System.out.println("End of DB data.");

        assertTrue(namesFromDB.contains(mockIngredient.getName()));

        // Cleanup
        IngredientDBHelper.deleteIngredientFromDB(mockIngredient, 0);
    }

    @Test
    public void testDeleteIngredient() {
        IngredientDBHelper.getInstance();
        Ingredient mockIngredient = makeIngredient(1);
        ArrayList<String> namesFromDB = new ArrayList<>();
        ArrayList<Ingredient> ingFromDB;

        IngredientDBHelper.addIngredientToDB(mockIngredient);

        // Allow database time to give data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ingFromDB = IngredientDBHelper.getIngredientsFromStorage();
        for (int i = 0; i < ingFromDB.size(); i++) {
            namesFromDB.add(ingFromDB.get(i).getName());
        }
        // Make sure ingredient was actually added
        assertTrue(namesFromDB.contains(mockIngredient.getName().toLowerCase()));

        IngredientDBHelper.deleteIngredientFromDB(mockIngredient, 0);

        // Give DB time to actually delete the item
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ingFromDB = IngredientDBHelper.getIngredientsFromStorage();
        namesFromDB = new ArrayList<>();
        System.out.println("After clear: " + namesFromDB);
        for (int i = 0; i < ingFromDB.size(); i++) {
            namesFromDB.add(ingFromDB.get(i).getName());
        }

        assertFalse(namesFromDB.contains(mockIngredient.getName().toLowerCase()));

    }

    @Test
    public void testModifyIngredientInDB() {
        IngredientDBHelper.getInstance();
        Ingredient firstIngredient = makeIngredient(1);
        Ingredient secondIngredient = makeIngredient(2);
        ArrayList<Ingredient> ingFromDB;
        int updatedIndex = -1;

        IngredientDBHelper.addIngredientToDB(firstIngredient);

        // Allow database time to give data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        IngredientDBHelper.modifyIngredientInDB(secondIngredient, firstIngredient, 0);

        // Give DB time to update entry
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ingFromDB = IngredientDBHelper.getIngredientsFromStorage();
        for (int i = 0; i < ingFromDB.size(); i++) {
            if (ingFromDB.get(i).getName().equals(secondIngredient.getName())) {
                updatedIndex = i;
                break;
            }
        }

        if (updatedIndex == -1) {
            fail("Ingredient not found");
        }
        assertEquals(secondIngredient.getDesc(), ingFromDB.get(updatedIndex).getDesc());
        assertEquals(secondIngredient.getBestBefore(), ingFromDB.get(updatedIndex).getBestBefore());
        assertEquals(secondIngredient.getLocation(), ingFromDB.get(updatedIndex).getLocation());
        assertEquals(secondIngredient.getUnit(), ingFromDB.get(updatedIndex).getUnit());
        assertEquals(secondIngredient.getCategory(), ingFromDB.get(updatedIndex).getCategory());
        assertEquals(secondIngredient.getAmount(), ingFromDB.get(updatedIndex).getAmount());

        // Cleanup

        IngredientDBHelper.deleteIngredientFromDB(secondIngredient, 0);
    }

}
