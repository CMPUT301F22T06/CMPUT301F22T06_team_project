package com.git_er_done.cmput301f22t06_team_project.models.recipe;

import java.util.ArrayList;

public class RecipeCategory{

        private ArrayList<String> recipeCategories = new ArrayList<>();

        private static final RecipeCategory instance = new RecipeCategory();

        private RecipeCategory(){
            recipeCategories.add("Add Category");
            recipeCategories.add("vegetarian");
            recipeCategories.add("vegan");
            recipeCategories.add("seafood");
            recipeCategories.add("dessert");
            recipeCategories.add("meat");
            recipeCategories.add("drink");
        }

        public static RecipeCategory getInstance(){
            return instance;
        }

        public ArrayList<String> getAllRecipeCategories(){
            return recipeCategories;
        }

        public String getRecipeCategoryFromIndex(int index){
            return recipeCategories.get(index);
        }

        public void addRecipeCategory(String locationToAdd){
            recipeCategories.add(locationToAdd);
        }
}
