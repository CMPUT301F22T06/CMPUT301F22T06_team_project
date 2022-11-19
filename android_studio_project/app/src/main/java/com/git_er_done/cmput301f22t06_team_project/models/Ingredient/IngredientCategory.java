package com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

import java.util.ArrayList;

public class IngredientCategory {

    private ArrayList<String> ingredientCategories = new ArrayList<>();

    private static final IngredientCategory instance = new IngredientCategory();

    private IngredientCategory(){
        ingredientCategories.add("Add new category");
        ingredientCategories.add("dairy");
        ingredientCategories.add("fruit");
        ingredientCategories.add("grain");
        ingredientCategories.add("lipid");
        ingredientCategories.add("protein");
        ingredientCategories.add("spice");
        ingredientCategories.add("vegetable");
        ingredientCategories.add("miscellaneous");
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
