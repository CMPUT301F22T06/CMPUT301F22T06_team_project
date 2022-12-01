package com.git_er_done.cmput301f22t06_team_project.ModelTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.git_er_done.cmput301f22t06_team_project.MainActivity;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;

public class IngredientTest {
    public static Ingredient makeIngredient() {
        // TODO: Update to new ingredient once merged
        return new Ingredient("Steak", "Tomahawk", LocalDate.of(2023, 1, 23), "Fridge", "singles", "Meat", 2);
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testIngredientGetSet() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Ingredient mockIngredient = makeIngredient();

        // Test getters
        assertEquals("Steak", mockIngredient.getName());
        assertEquals("Tomahawk", mockIngredient.getDesc());

        LocalDate testDate = LocalDate.of(2023, 1, 23);
        assertTrue(testDate.isEqual(mockIngredient.getBestBefore()));

        assertEquals("Fridge", mockIngredient.getLocation());
        assertEquals("singles", mockIngredient.getUnit());
        assertEquals("Meat", mockIngredient.getCategory());
        assertEquals(2, (int) mockIngredient.getAmount());

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
}
