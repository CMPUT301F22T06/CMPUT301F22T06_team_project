package com.git_er_done.cmput301f22t06_team_project.models.ingredient;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.UserDefinedDBHelper;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class represents an ingredient for the storage, recipe, or mealplan.
 * Takes name, description, bestbefore, location, unit, category, amount, and color
 * @see {Recipe} {MealPlan} {IngredientDBHelper}
 * @version 1
 */

public class Ingredient implements Cloneable{
    private String name;
    private String desc;
    private LocalDate bestBefore;
    private String location;
    private String unit;
    private String category;
    private Integer amount;
    private Integer color;

    private boolean isChecked;

    //Grab singleton arrays for user defined attributes like location and category
    public static ArrayList<String> ingredientLocations = UserDefinedDBHelper.getIngredientLocations();
    public static ArrayList<String> ingredientCategories = UserDefinedDBHelper.getIngredientCategories();
    public static ArrayList<String> ingredientUnits = UserDefinedDBHelper.getIngredientUnits();


    /**
     * Creates a new Ingredient object
     * @param name The name of the ingredient as a {@link String}.
     * @param desc A description of the ingredient as a {@link String}.
     * @param bestBefore The best before date of the ingredient as a {@link LocalDate}.
     * @param location The storage location of the ingredient as a {@link String}.
     * @param unit The unit of measure (UOM) of the ingredient as a {@link String}.
     * @param category The category of the ingredient as a {@link String}.
     * @param amount The stored amount of the ingredient in its UOM as a {@link Integer}.
     */
    public Ingredient(String name, String desc, LocalDate bestBefore, String location, String unit,
                      String category, Integer amount) {
        this.name = name;
        this.desc = desc;
        this.bestBefore = bestBefore;
        this.location = location;
        this.unit = unit;
        this.category = category;
        this.amount = amount;
        this.color = R.drawable.border;
    }

    /**
     * Creates a new Ingredient object with a defined colour.
     *
     * @param name       The name of the ingredient as a {@link String}.
     * @param desc       A description of the ingredient as a {@link String}.
     * @param bestBefore The best before date of the ingredient as a {@link LocalDate}.
     * @param location   The storage location of the ingredient as a {@link String}.
     * @param unit       The unit of measure (UOM) of the ingredient as a {@link String}.
     * @param category   The category of the ingredient as a {@link String}.
     * @param amount     The stored amount of the ingredient in its UOM as a {@link Integer}.
     * @param color      The colour of the displayed count text.
     */
    public Ingredient(String name, String desc, LocalDate bestBefore, String location, String unit, String category, Integer amount, Integer color) {
        this.name = name;
        this.desc = desc;
        this.bestBefore = bestBefore;
        this.location = location;
        this.unit = unit;
        this.category = category;
        this.amount = amount;
        this.color = color;
    }


    /**
     * Returns the description of the Ingredient
     *
     * @return The Ingredient description as a {@link String}.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets a new description for the Ingredient.
     *
     * @param desc The new Ingredient description to be set as a {@link String}.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Returns the best before date of the ingredient.
     *
     * @return The Ingredient best before date as a {@link LocalDate}.
     */
    public LocalDate getBestBefore() {
        return bestBefore;
    }

    /**
     * Sets a new best before date for the Ingredient
     *
     * @param best_before The new Ingredient best before date to be set as a {@link LocalDate}
     */
    public void setBestBefore(LocalDate best_before) {
        this.bestBefore = best_before;
    }

    /**
     * Returns the best before date of the Ingredient as an {@link ArrayList} with the year at
     * index 0, the month at index 1, and the day at index 2.
     *
     * @return The best before date as an {@link ArrayList}.
     */
    public ArrayList<String> getBestBeforeStringArrayList(){
        ArrayList<String> bb4StringArray = new ArrayList<String>();
        String bb4year = Integer.toString(this.bestBefore.getYear());
        String bb4month = Integer.toString(this.bestBefore.getMonthValue());
        String bb4date = Integer.toString(this.bestBefore.getDayOfMonth());
        bb4StringArray.add(bb4year);
        bb4StringArray.add(bb4month);
        bb4StringArray.add(bb4date);
        return bb4StringArray;
    }

    /**
     * Returns the storage location of the Ingredient.
     *
     * @return The Ingredient's storage location as a {@link String}.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets a new storage location for the Ingredient
     *
     * @param location the Ingredient's new storage location as a {@link String}.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the Unit of Measure of the Ingredient
     *
     * @return The UOM of the Ingredient as a {@link String}.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets a new Unit of Measure for the Ingredient.
     *
     * @param unit The new UOM for the Ingredient as a {@link String}.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Returns the Category of the Ingredient.
     *
     * @return The Ingredient's category as a {@link String}.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets a new category for the Ingredient.
     *
     * @param category The new category for the Ingredient as a {@link String}.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the amount of the Ingredient in storage.
     *
     * @return The amount of Ingredients in storage as an {@link Integer}.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets a new storage amount for the Ingredient.
     *
     * @param amount The new amount of the Ingredient in storage as an {@link Integer}.
     */
    public void setAmount(Integer amount) { this.amount = amount; }

    /**
     * Returns the Name of the Ingredient.
     *
     * @return the name of the Ingredient as a {@link String}.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the Ingredient.
     *
     * @param name The new name of the Ingredient as a {@link String}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the colour the amount text is set to display as.
     *
     * @return The hex colour value of the amount text as an {@link Integer}.
     */
    public Integer getColor() {
        return color;
    }

    /**
     * Sets a new colour for the amount text to display as.
     *
     * @param color The new hex colour value as an {@link Integer}.
     */
    public void setColor(int color) {
        this.color = color;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    /**
     * Overwritten equals method is required for indexOf call.
     *
     * @param o The object to be compared.
     * @return True if the Ingredients have the same name, False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ingredient)) {
            return false;
        }
        Ingredient other = (Ingredient) o;
        return name.equalsIgnoreCase(other.getName());
    }

    /**
     * Creates a field-for-field copy of the given Ingredient.
     *
     * @return A field-for-field copy of the original Ingredient.
     */
    @Override
    public Ingredient clone() {
        try {
            Ingredient clone = (Ingredient) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
