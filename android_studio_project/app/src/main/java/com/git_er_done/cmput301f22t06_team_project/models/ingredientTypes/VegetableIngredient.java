package com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes;

import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

import java.time.LocalDate;

public class VegetableIngredient extends Ingredient {
    public VegetableIngredient(String name, String desc, LocalDate best_before, String location,
                               String units, String category, Integer amount) {
        super(name, desc, best_before, location, units, category, amount);
    }
}
