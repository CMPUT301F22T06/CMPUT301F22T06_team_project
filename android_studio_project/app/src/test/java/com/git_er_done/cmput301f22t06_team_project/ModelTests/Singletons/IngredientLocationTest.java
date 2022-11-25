package com.git_er_done.cmput301f22t06_team_project.ModelTests.Singletons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.git_er_done.cmput301f22t06_team_project.models.ingredient.IngredientLocation;

import org.junit.Test;

import java.util.ArrayList;

public class IngredientLocationTest {

    @Test
    public void testGetAllLocation() {
        IngredientLocation location = IngredientLocation.getInstance();
        ArrayList<String> initialLocations = new ArrayList<>();
        initialLocations.add("Add New Location");
        initialLocations.add("pantry");
        initialLocations.add("fridge");
        initialLocations.add("freezer");

        ArrayList<String> fromSingleton = location.getAllLocations();
        assertEquals(initialLocations, fromSingleton);
    }

    @Test
    public void testAddGetLocation() {
        IngredientLocation location = IngredientLocation.getInstance();

        location.addLocation("cupboard");
        // First user-defined index
        assertEquals("cupboard", location.getLocationFromIndex(4));

        // Cleanup
        location.deleteLocation("cupboard");
    }

    @Test
    public void testDeleteLocation() {
        IngredientLocation location = IngredientLocation.getInstance();

        location.addLocation("cupboard");

        location.deleteLocation("cupboard");
        assertFalse(location.getAllLocations().contains("cupboard"));
    }

}
