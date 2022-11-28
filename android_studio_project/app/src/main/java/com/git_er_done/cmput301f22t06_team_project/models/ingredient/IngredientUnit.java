package com.git_er_done.cmput301f22t06_team_project.models.ingredient;

import java.util.ArrayList;

/**
 * Unit represents the physical unit used to measure the amount of an ingredient.
 * User defined parameter with a few hardcoded example options.
 */
public class IngredientUnit {
    private ArrayList<String> units = new ArrayList<>();

    private static final IngredientUnit instance = new IngredientUnit();

    /**
     * Private constructor creates the singleton instance of IngredientUnit and populates the
     * default values
     */
    private IngredientUnit() {
        units.add("g");
        units.add("ml");
        units.add("L");
        units.add("oz");
        units.add("singles");
    }

    /**
     * The effective "Constructor". Returns the singleton instance of the class.
     *
     * @return The singleton instance of IngredientUnit.
     */
    public static IngredientUnit getInstance() {
        return instance;
    }

    /**
     * Returns an ArrayList of all currently held units.
     *
     * @return An {@link ArrayList} of {@link String}s with the current units.
     */
    public ArrayList<String> getAllUnits() {
        return units;
    }

    /**
     * Returns the unit stored at the given index.
     *
     * @param index The index of the unit to be retrieved as a primitive int.
     * @return The unit at the input index as a {@link String}.
     */
    public String getUnitFromIndex(int index) {
        return units.get(index);
    }

    /**
     * Adds the input unit to the list of categories.
     *
     * @param locationToAdd The unit to be added to the list as a {@link String}.
     */
    public void addUnit(String locationToAdd) {
        units.add(locationToAdd);
    }

    /**
     * Deletes the given unit from the list.
     *
     * @param unitToDelete The unit to be removed from the list as a {@link String}.
     */
    public void deleteUnit(String unitToDelete) {
        units.remove(unitToDelete);
    }
}
