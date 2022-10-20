package com.git_er_done.cmput301f22t06_team_project.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ingredient {

    private String name;
    private String desc;
    private LocalDate best_before;
    private String location;
    private String units;
    private String category;
    private Integer amount;

    public Ingredient(String name, String desc, LocalDate best_before, String location, String units,
                      String category, Integer amount ) {
        this.name = name;
        this.desc = desc;
        this.best_before = best_before;
        this.location = location;
        this.units = units;
        this.category = category;
        this.amount = amount;

    }

    /**
     * Quick and dirty generator of arraylist of ingredients for UI testing
     * @return ArrayList of {@link Ingredient} instances
     */
    public static ArrayList<Ingredient> createIngredientList(){
        ArrayList<Ingredient> testIngredients = new ArrayList<Ingredient>();

//        Ingredient apple = new Ingredient("apple", "red apple small", )


        return testIngredients;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getBest_before() {
        return best_before;
    }

    public void setBest_before(LocalDate best_before) {
        this.best_before = best_before;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
