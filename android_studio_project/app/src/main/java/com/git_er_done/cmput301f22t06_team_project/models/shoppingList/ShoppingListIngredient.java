package com.git_er_done.cmput301f22t06_team_project.models.shoppingList;

import static com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient.ingredientCategories;
import static com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient.ingredientLocations;
import static com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient.ingredientUnits;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import java.time.LocalDate;
import java.util.ArrayList;


public class ShoppingListIngredient {

    private Ingredient ingredient;
    private int requiredAmount;

    //    public static ArrayList<ShoppingListIngredient> testShoppingList = new ArrayList<>();
    public static ArrayList<ShoppingListIngredient> testShoppingList = ShoppingListIngredient.createShoppingList();

    /**
     * Creates a new ShoppingListIngredient with the given Ingredient and required amount.
     *
     * @param ingredient     The Ingredient to be added to the shopping list.
     * @param requiredAmount The amount of the Ingredient required as a primitive int.
     */
    public ShoppingListIngredient(Ingredient ingredient, int requiredAmount) {
        this.ingredient = ingredient;
        this.requiredAmount = requiredAmount;
    }

    /** Creates a mock shopping list for UI testing
     * @return A mock shopping list for UI testing.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public final static ArrayList<ShoppingListIngredient> createShoppingList() {
        ArrayList<ShoppingListIngredient> tempShoppingList = new ArrayList<>();

        Ingredient lime = new Ingredient("lime", "small green lime", LocalDate.now(), ingredientLocations.get(2), ingredientUnits.get(0), ingredientCategories.get(1), 4);
        Ingredient yellow_onion = new Ingredient("yellow_onion", "aa yellow skinned onion", LocalDate.now(), ingredientLocations.get(0), ingredientUnits.get(3), ingredientCategories.get(0), 4);

        ShoppingListIngredient buyLimes = new ShoppingListIngredient(lime, 5);
        ShoppingListIngredient buyYellowOnions = new ShoppingListIngredient(yellow_onion, 3);

        tempShoppingList.add(buyLimes);
        tempShoppingList.add(buyYellowOnions);

        return tempShoppingList;
    }

    /** Returns the Ingredient object associated with the ShoppingListIngredient.
     * @return The Ingredient object associated with the ShoppingListIngredient.
     */
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    /** Sets a new Ingredient to be associated with the ShoppingListIngredient
     * @param ingredient The new Ingredient.
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /** Returns the amount of the Ingredient required for the Meal Plan.
     * @return The amount of the Ingredient required by the Meal Plan as a primitive int.
     */
    public int getRequiredAmount() {
        return this.requiredAmount;
    }

    /** Sets a new required amount of the ingredient.
     * @param requiredAmount The new required amount as a primitive int.
     */
    public void setRequiredAmount(int requiredAmount) {
        this.requiredAmount = requiredAmount;
    }
}
