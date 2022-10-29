package com.git_er_done.cmput301f22t06_team_project.models;

public abstract class Location {
    String location;

    public Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
