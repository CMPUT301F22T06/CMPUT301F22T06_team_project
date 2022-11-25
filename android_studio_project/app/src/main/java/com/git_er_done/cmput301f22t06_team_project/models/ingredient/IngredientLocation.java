package com.git_er_done.cmput301f22t06_team_project.models.ingredient;

import java.util.ArrayList;

/**
 * Location represents the physical spot an ingredient will be stored.
 * User defined parameter with a few hardcoded example options.
 */
public class IngredientLocation {

    private ArrayList<String> locations = new ArrayList<>();

    private static final IngredientLocation instance = new IngredientLocation();

    private IngredientLocation(){
        locations.add("pantry");
        locations.add("fridge");
        locations.add("freezer");
    }

    public static IngredientLocation getInstance(){
        return instance;
    }

    public ArrayList<String> getAllLocations(){
        return locations;
    }

    public String getLocationFromIndex(int index){
        return locations.get(index);
    }

    public void addLocation(String locationToAdd){
        locations.add(locationToAdd);
    }

    public void deleteLocation(String locationToDelete) {locations.remove(locationToDelete);
    }

}
