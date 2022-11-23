package com.git_er_done.cmput301f22t06_team_project.models.ingredient;

import java.util.ArrayList;

/**
 * Unit represents the physical unit used to measure the amount of an ingredient.
 * User defined parameter with a few hardcoded example options.
 */
public class IngredientUnit {
    private ArrayList<String> units = new ArrayList<>();

    private static final IngredientUnit instance = new IngredientUnit();

    private IngredientUnit(){
        units.add("Add New Unit");
        units.add("g");
        units.add("ml");
        units.add("L");
        units.add("oz");
        units.add("singles");
    }

    public static IngredientUnit getInstance(){
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
