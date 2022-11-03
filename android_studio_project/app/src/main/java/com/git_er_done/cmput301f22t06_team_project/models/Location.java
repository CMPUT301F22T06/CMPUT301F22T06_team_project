package com.git_er_done.cmput301f22t06_team_project.models;

import java.util.ArrayList;
import java.util.Objects;

public class Location {

    private ArrayList<String> locations = new ArrayList<>();

    private static final Location instance = new Location();

    private Location(){
        locations.add("Pantry");
        locations.add("Fridge");
        locations.add("Freezer");
    }

    public static Location getInstance(){
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

}
