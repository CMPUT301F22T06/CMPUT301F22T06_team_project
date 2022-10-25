package com.git_er_done.cmput301f22t06_team_project.models;

public class VegetarianIngredient extends Ingredient {
    public VegetarianIngredient(String name, String desc, String best_before, String location,
                                String units, String category, Integer amount) {
        super(name, desc, best_before, location, units, category, amount);
        setVegan(false);
        setVegetarian(true);
    }
}
