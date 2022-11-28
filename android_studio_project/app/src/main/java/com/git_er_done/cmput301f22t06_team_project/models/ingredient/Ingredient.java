package com.git_er_done.cmput301f22t06_team_project.models.ingredient;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.git_er_done.cmput301f22t06_team_project.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ingredient implements Cloneable{
    private String name;
    private String desc;
    private LocalDate bestBefore;
    private String location;
    private String unit;
    private String category;
    private Integer amount;
    private Integer color;

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public final static ArrayList<Ingredient> createIngredientList(){
        ArrayList<Ingredient> testIngredients = new ArrayList<>();

        Ingredient lime = new  Ingredient ("lime", "small green lime", LocalDate.now(), ingredientLocations.get(2), ingredientUnits.get(0), ingredientCategories.get(1), 4);
        Ingredient yellow_onion = new Ingredient("yellow_onion", "yellow skinned onion", LocalDate.now(), ingredientLocations.get(0), ingredientUnits.get(3), ingredientCategories.get(6), 4);

        // Fruits
//        Ingredient lime = new  FruitIngredient ("lime", "small green lime", LocalDate.now(), locations.get(0), "Singles", "Fruit", 4);
//        Ingredient apple = new  FruitIngredient ("apple", "red apple small", LocalDate.now(), locations.get(0), "Singles", "Fruit", 4);
//        Ingredient banana = new  FruitIngredient ("banana", "curved yellow banana", LocalDate.now(), locations.get(0), "Singles", "Fruit", 4);
//        Ingredient frozen_strawberries = new  FruitIngredient ("frozen strawberries", "small red seeded frozen fruit", LocalDate.now(), locations.get(0), "g", "Fruit", 1000);
//
//        // Veggies
//        Ingredient yellow_onion = new VegetableIngredient("yellow_onion", "yellow skinned onion", LocalDate.now(), locations.get(0), "Singles", "Veggie", 4);
//        Ingredient tomato_paste = new VegetableIngredient("tomato paste", "Thick and creamy tomato paste", LocalDate.now(), locations.get(0), "ml", "Veggie", 250);
//        Ingredient tomato = new VegetableIngredient("tomato", "Bright red tomato", LocalDate.now(), locations.get(1), "g", "Veggie", 500);
//        Ingredient romaine_lettuce = new VegetableIngredient("romaine lettuce", "green crunchy lettuce", LocalDate.now(), locations.get(1), "g", "Veggie", 100);
//        Ingredient carrot = new VegetableIngredient("carrot", "orange crunchy carrot", LocalDate.now(), locations.get(1), "singles", "Veggie", 3);
//        Ingredient yellow_potato = new VegetableIngredient("yellow potato", "yellow yukon gold potato", LocalDate.now(), locations.get(1), "g", "Veggie", 300);
//
//        // Diary
//        Ingredient milk = new DairyIngredient ("milk", "2% homogenized milk", LocalDate.now(), locations.get(1), "ml", "Dairy", 2000);
//        Ingredient parmesan = new DairyIngredient ("parmesan", "block of cheese, can be shredded", LocalDate.now(), locations.get(1), "g", "Dairy", 450);
//        Ingredient heavy_cream = new DairyIngredient ("heavy_cream", "heavy whipped fattening cream", LocalDate.now(), locations.get(1), "ml", "Dairy", 350);
//        Ingredient yogurt = new DairyIngredient ("yogurt", "plain yogurt", LocalDate.now(), locations.get(1), "ml", "Dairy", 350);
//        Ingredient sour_cream = new DairyIngredient("sour cream", "sour and creamy", LocalDate.now(), locations.get(1), "ml", "Dairy", 250);
//
//        // Protein
//        Ingredient minced_beef  = new ProteinIngredient("minced beef", "extra lean ground beef", LocalDate.now(), locations.get(1), "g", "Protein", 1000 );
//        Ingredient minced_pork  = new ProteinIngredient("minced pork", "lean ground pork", LocalDate.now(), locations.get(1), "g", "Protein", 1000 );
//        Ingredient egg  = new ProteinIngredient("egg", "white egg", LocalDate.now(), locations.get(1), "Singles", "Protein", 12 );
//        Ingredient chicken_drumstick  = new ProteinIngredient("chicken drumstick", "not the icecream", LocalDate.now(), locations.get(1), "Singles", "Protein", 6 );
//        Ingredient tuna  = new ProteinIngredient("tuna", "sushi grade wild tuna", LocalDate.now(), locations.get(1), "oz", "Protein", 20 );
//        Ingredient bacon  = new ProteinIngredient("bacon", "fat and greasy", LocalDate.now(), locations.get(1), "Singles", "Protein", 20 );
//
//        // Grain
//        Ingredient flour  = new GrainIngredient("flour", "all purpose flour", LocalDate.now(), locations.get(0), "oz", "Grain",  4 );
//        Ingredient rice  = new GrainIngredient("rice", "white rice", LocalDate.now(), locations.get(0), "g", "Grain",  400 );
//
//        // Lipids
//        Ingredient olive_oil  = new LipidIngredient("olive oil", "extra virgin", LocalDate.now(), locations.get(0), "ml", "Lipid",  500 );
//        Ingredient butter  = new LipidIngredient("butter", "softened yellow butter", LocalDate.now(), locations.get(1), "g", "Lipid",  300 );
//        Ingredient sesame_oil  = new LipidIngredient("sesame oil", "sesame fragrance", LocalDate.now(), locations.get(0), "ml", "Lipid",  300 );
//        Ingredient vegetable_oil  = new LipidIngredient("vegetable oil", "yellow oil", LocalDate.now(), locations.get(0), "ml", "Lipid",  500 );
//
//        // Spices
//        Ingredient sugar  = new SpiceIngredient("sugar", "real cane sugar", LocalDate.now(), locations.get(0), "g", "Spice", 200 );
//        Ingredient salt  = new SpiceIngredient("salt", "sea salt", LocalDate.now(), locations.get(0), "g", "Spice", 200 );
//        Ingredient garlic  = new SpiceIngredient("garlic", "aromatic", LocalDate.now(), locations.get(0), "Singles", "Spice", 6 );
//        Ingredient peppercorn  = new SpiceIngredient("peppercorn", "black peppercorn", LocalDate.now(), locations.get(0), "Singles", "Spice", 30 );
//        Ingredient black_pepper  = new SpiceIngredient("black pepper", "ground black pepper", LocalDate.now(), locations.get(0), "g", "Spice", 200 );
//        Ingredient ginger  = new SpiceIngredient("ginger", "grated ginger", LocalDate.now(), locations.get(0), "g", "Spice", 50 );
//
//        // Misc
//        Ingredient cooking_wine  = new MiscIngredient("cooking wine", "red cooking wine", LocalDate.now(), locations.get(0), "ml", "Misc", 400 );
//        Ingredient oyster_sauce  = new MiscIngredient("oyster sauce", "doesn't taste like oysters", LocalDate.now(), locations.get(0), "ml", "Misc", 400 );
//        Ingredient soy_sauce  = new MiscIngredient("soy sauce", "umami", LocalDate.now(), locations.get(0), "ml", "Misc", 40000 );
//        Ingredient honey  = new MiscIngredient("honey", "Golden sticky honey", LocalDate.now(), locations.get(0), "ml", "Misc", 200 );
//        Ingredient chicken_stock  = new MiscIngredient("chicken_stock", "salty soup base", LocalDate.now(), locations.get(0), "ml", "Misc", 600 );
//        Ingredient vanilla_extract  = new MiscIngredient("vanilla_extract", "sweet smelling vanilla", LocalDate.now(), locations.get(0), "ml", "Misc", 50 );
//

        testIngredients.add(lime);
        testIngredients.add(yellow_onion);
//        testIngredients.add(banana);
//        testIngredients.add(frozen_strawberries);
//
//        // veggies
//        testIngredients.add(yellow_onion);
//        testIngredients.add(yellow_potato);
//        testIngredients.add(tomato_paste);
//        testIngredients.add(tomato);
//        testIngredients.add(carrot);
//        testIngredients.add(romaine_lettuce);
//
//        // diary
//        testIngredients.add(milk);
//        testIngredients.add(sour_cream);
//        testIngredients.add(parmesan);
//        testIngredients.add(heavy_cream);
//        testIngredients.add(parmesan);
//
//        // protein
//        testIngredients.add(egg);
//        testIngredients.add(tuna);
//        testIngredients.add(minced_beef);
//        testIngredients.add(minced_pork);
//        testIngredients.add(chicken_drumstick);
//        testIngredients.add(bacon);
//
//        // grain
//        testIngredients.add(rice);
//        testIngredients.add(flour);
//
//        // lipid
//        testIngredients.add(butter);
//        testIngredients.add(olive_oil);
//        testIngredients.add(sesame_oil);
//        testIngredients.add(vegetable_oil);
//
//        // spices
//        testIngredients.add(peppercorn);
//        testIngredients.add(black_pepper);
//        testIngredients.add(salt);
//        testIngredients.add(garlic);
//        testIngredients.add(ginger);
//        testIngredients.add(sugar);
//
//        // misc
//        testIngredients.add(honey);
//        testIngredients.add(chicken_stock);
//        testIngredients.add(cooking_wine);
//        testIngredients.add(oyster_sauce);
//        testIngredients.add(soy_sauce);
//        testIngredients.add(vanilla_extract);

        return testIngredients;
    }

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
        String bb4month = Integer.toString(this.bestBefore.getMonthValue());;
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
