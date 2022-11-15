package com.git_er_done.cmput301f22t06_team_project.models;

import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.ingredientCategories;
import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.locations;
import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.units;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;

import java.time.LocalDate;
import java.util.ArrayList;

public class ShoppingListIngredient {

    private Ingredient ingredient;
    private int requiredAmount;

//    public static ArrayList<ShoppingListIngredient> testShoppingList = new ArrayList<>();
    public static ArrayList<ShoppingListIngredient> testShoppingList = ShoppingListIngredient.createShoppingList();


    public ShoppingListIngredient(Ingredient ingredient, int requiredAmount) {
        this.ingredient = ingredient;
        this.requiredAmount = requiredAmount;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public final static ArrayList<ShoppingListIngredient> createShoppingList() {
        ArrayList<ShoppingListIngredient> tempShoppingList = new ArrayList<>();

        Ingredient lime = new  Ingredient ("lime", "small green lime", LocalDate.now(), locations.get(2), units.get(0), ingredientCategories.get(1), 4);
        Ingredient yellow_onion = new Ingredient("yellow_onion", "yellow skinned onion", LocalDate.now(), locations.get(0), units.get(3), ingredientCategories.get(6), 4);

        ShoppingListIngredient buyLimes = new ShoppingListIngredient(lime, 5);
        ShoppingListIngredient buyYellowOnions = new ShoppingListIngredient(yellow_onion, 3);

        tempShoppingList.add(buyLimes);
        tempShoppingList.add(buyYellowOnions);

        return tempShoppingList;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getRequiredAmount() {
        return this.requiredAmount;
    }

    public void setRequiredAmount(int requiredAmount) {
        this.requiredAmount = requiredAmount;
    }
}
