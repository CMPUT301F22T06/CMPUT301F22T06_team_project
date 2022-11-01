package com.git_er_done.cmput301f22t06_team_project.models;

import com.git_er_done.cmput301f22t06_team_project.models.locationTypes.Freezer;
import com.git_er_done.cmput301f22t06_team_project.models.locationTypes.Fridge;
import com.git_er_done.cmput301f22t06_team_project.models.locationTypes.Pantry;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Location {

    /**
     * toString method overriden so that arrayAdapters will read this name of this given object
     * @return contact_name
     */
    @Override
    public String toString() {
        return locationString;
    }

    public final static ArrayList<Location> createLocationList() {
        ArrayList<Location> testLocations = new ArrayList<>();
        Location pantry = new Pantry();
        Location fridge = new Fridge();
        Location freezer = new Freezer();

        testLocations.add(pantry);
        testLocations.add(fridge);
        testLocations.add(freezer);

        return testLocations;
    };

    public Location location;
    public String locationString;
    public Integer spinnerIndex;

    public Integer getSpinnerIndex(){
        return this.spinnerIndex;
    }

    public Location(){

    }

    //TODO - handle error case if location is not valid
    public Location(String location) {

        if(Objects.equals(location, "pantry")){
            this.location = new Pantry();
        }

        else if(Objects.equals(location, "fridge")){
            this.location = new Fridge();
        }
        else if(Objects.equals(location, "freezer")){
            this.location = new Freezer();
        }

    }

    public String getLocationString() {
        return this.locationString;
    }

    public Location getLocation(){
        return this.location;
    }

    public static Location getLocationFromString(String locationString){
        locationString = locationString.toLowerCase();

        Location result = null;

        switch(locationString){
            case "pantry":
                result = new Pantry();
                break;

            case "fridge":
                result = new Fridge();
                break;

            case "freezer":
                result = new Freezer();
                break;
        }

        return result;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
