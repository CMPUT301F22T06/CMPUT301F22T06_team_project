package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import com.git_er_done.cmput301f22t06_team_project.models.Meal;

import java.util.ArrayList;

public interface MealPlannerFirebaseCallBack {
    void onCallback(ArrayList<Meal> retrievedMealPlans);
}
