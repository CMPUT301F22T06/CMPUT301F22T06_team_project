package com.git_er_done.cmput301f22t06_team_project;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private String title;
    private String comments;
    private String category;
    private int prep_time;
    private int servings;

    public Recipe(){

    }

    public ArrayList<Ingredient> get_ingredients() {
        return ingredients;
    }

    public void set_ingredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String get_title() {
        return title;
    }

    public void set_title(String title) {
        this.title = title;
    }

    public String get_comments() {
        return comments;
    }

    public void set_comments(String comments) {
        this.comments = comments;
    }

    public String get_category() {
        return category;
    }

    public void set_category(String category) {
        this.category = category;
    }

    public int get_prep_time() {
        return prep_time;
    }

    public void set_prep_time(int prep_time) {
        this.prep_time = prep_time;
    }

    public int get_servings() {
        return servings;
    }

    public void set_servings(int servings) {
        this.servings = servings;
    }
}
