package com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

import java.util.ArrayList;

public class IngredientCategory {

    private ArrayList<String> ingredientCategories = new ArrayList<>();

    private static final IngredientCategory instance = new IngredientCategory();

    private IngredientCategory(){
        ingredientCategories.add("Dairy");
        ingredientCategories.add("Fruit");
        ingredientCategories.add("Grain");
        ingredientCategories.add("Lipid");
        ingredientCategories.add("Protein");
        ingredientCategories.add("Spice");
        ingredientCategories.add("Vegetable");
        ingredientCategories.add("Miscellaneous");
    }

    public static IngredientCategory getInstance(){
        return instance;
    }

    public ArrayList<String> getAllIngredientCategories(){
        return ingredientCategories;
    }

    public String getIngredientCategoryFromIndex(int index){
        return ingredientCategories.get(index);
    }

    public void addIngredientCategory(String locationToAdd){
        ingredientCategories.add(locationToAdd);
    }
}
