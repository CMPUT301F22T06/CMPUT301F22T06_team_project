package com.git_er_done.cmput301f22t06_team_project.models.ingredient;

import java.util.ArrayList;

/**
 * Location represents the physical spot an ingredient will be stored.
 * User defined parameter with a few hardcoded example options.
 */
public class IngredientLocation {

    private ArrayList<String> locations = new ArrayList<>();

    private static final IngredientLocation instance = new IngredientLocation();

    /**
     * Private constructor creates the singleton instance of IngredientLocation and populates the
     * default values
     */
    private IngredientLocation() {
        locations.add("pantry");
        locations.add("fridge");
        locations.add("freezer");
    }

    /**
     * The effective "Constructor". Returns the singleton instance of the class.
     *
     * @return The singleton instance of IngredientLocation.
     */
    public static IngredientLocation getInstance() {
        return instance;
    }

    /**
     * Returns an ArrayList of all currently held locations.
     *
     * @return An {@link ArrayList} of {@link String}s with the current locations.
     */
    public ArrayList<String> getAllLocations() {
        return locations;
    }

    /**
     * Returns the category stored at the given index.
     *
     * @param index The index of the location to be retrieved as a primitive int.
     * @return The location at the input index as a {@link String}.
     */
    public String getLocationFromIndex(int index) {
        return locations.get(index);
    }

    /**
     * Adds the input location to the list of categories.
     *
     * @param locationToAdd The location to be added to the list as a {@link String}.
     */
    public void addLocation(String locationToAdd) {
        locations.add(locationToAdd);
    }

    /**
     * Deletes the given location from the list.
     *
     * @param locationToDelete The location to be removed from the list as a {@link String}.
     */
    public void deleteLocation(String locationToDelete) {
        locations.remove(locationToDelete);
    }

}
