package com.git_er_done.cmput301f22t06_team_project;

public class Ingredient {

    private String name;
    private String desc;
    private String bestBefore;
    private String location;
    private String units;
    private String category;

    public Ingredient() {

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(String bestBefore) {
        this.bestBefore = bestBefore;
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

    private int amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
