package com.git_er_done.cmput301f22t06_team_project.models.ingredient;

import java.util.ArrayList;

/**
 * Category represents the category of an ingredient
 * User defined parameter with a few hardcoded example options.
 */
public class IngredientCategory {

    private ArrayList<String> ingredientCategories = new ArrayList<>();

    private static final IngredientCategory instance = new IngredientCategory();

    private IngredientCategory(){
        ingredientCategories.add("dairy";
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

    public void deleteCategory(String categoryToDelete) {
        ingredientCategories.remove(categoryToDelete);
    }

}
