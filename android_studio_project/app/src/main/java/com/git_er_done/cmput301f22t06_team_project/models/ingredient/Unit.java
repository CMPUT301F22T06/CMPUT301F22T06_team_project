package com.git_er_done.cmput301f22t06_team_project.models.ingredient;

import java.util.ArrayList;

public class Unit {
    private ArrayList<String> units = new ArrayList<>();

    private static final Unit instance = new Unit();

    private Unit(){
        units.add("Add New Unit");
        units.add("g");
        units.add("ml");
        units.add("L");
        units.add("oz");
        units.add("singles");
    }

    public static Unit getInstance(){
        return instance;
    }

    public ArrayList<String> getAllUnits(){
        return units;
    }

    public String getUnitFromIndex(int index){
        return units.get(index);
    }

    public void addUnit(String locationToAdd){
        units.add(locationToAdd);
    }

    public void deleteUnit(String unitToDelete) {
        units.remove(unitToDelete);
    }
}
