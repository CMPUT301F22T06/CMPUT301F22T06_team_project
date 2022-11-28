package com.git_er_done.cmput301f22t06_team_project.ModelTests.Singletons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.git_er_done.cmput301f22t06_team_project.models.ingredient.IngredientUnit;

import org.junit.Test;

import java.util.ArrayList;

public class IngredientUnitTest {
    @Test
    public void testGetAllUnits() {
        IngredientUnit unit = IngredientUnit.getInstance();

        ArrayList<String> initialUnits = new ArrayList<>();
        initialUnits.add("g");
        initialUnits.add("ml");
        initialUnits.add("L");
        initialUnits.add("oz");
        initialUnits.add("singles");

        assertEquals(initialUnits, unit.getAllUnits());
    }

    @Test
    public void testAddGetUnit() {
        IngredientUnit unit = IngredientUnit.getInstance();

        unit.addUnit("lbs");
        // First user-defined index
        assertEquals("lbs", unit.getUnitFromIndex(5));
        // Cleanup
        unit.deleteUnit("lbs");
    }

    @Test
    public void testDeleteUnit() {
        IngredientUnit unit = IngredientUnit.getInstance();

        unit.addUnit("lbs");
        unit.deleteUnit("lbs");

        assertFalse(unit.getAllUnits().contains("lbs"));
    }
}
