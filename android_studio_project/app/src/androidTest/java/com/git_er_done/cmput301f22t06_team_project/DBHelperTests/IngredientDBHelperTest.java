package com.git_er_done.cmput301f22t06_team_project.DBHelperTests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.git_er_done.cmput301f22t06_team_project.MainActivity;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class IngredientDBHelperTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

//    public ArrayList<Ingredient> mockIngredientList(){
//        ArrayList<Ingredient> mockIngredientList = new ArrayList<>();
//
//        Ingredient i1 = new Ingredient("Beef", "AAA sirloin", LocalDate.now(),"Pantry", "g", "Protein", 5);
//
//        mockIngredientList.add(i1);
//
//        return mockIngredientList;
//    }

    public Ingredient makeIngredient() {
        return new Ingredient("Steak", "T-Bone", LocalDate.now().plusYears(1),
                "freezer", "singles", "protein", 2);
    }

    @Test
    public void testAddIngredient(){
        IngredientDBHelper.getInstance();
        Ingredient mockIngredient = makeIngredient();
        IngredientDBHelper.addIngredientToDB(mockIngredient);


    }

    @Test
    public void testDeleteIngredient(){

    }

    @Test
    public void testSearchForIngredient(){

    }

    @Test
    public void testGetAllIngredients(){

    }

}
