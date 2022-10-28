package com.git_er_done.cmput301f22t06_team_project.models;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.DairyIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.FruitIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.GrainIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.LipidIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.MiscIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.ProteinIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.SpiceIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.VegetableIngredient;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Ingredient {
    private String name;
    private String desc;
    private LocalDate best_before;
    private String location;
    private String units;
    private String category;
    private Integer amount;
    private boolean isVegetarian;
    private boolean isVegan;

    // No empty constructor since it should never be called anyway

    /**
     * Creates a new Ingredient object
     * @param name The name of the ingredient as a {@link String}.
     * @param desc A description of the ingredient as a {@link String}.
     * @param best_before The best before date of the ingredient as a {@link LocalDate}.
     * @param location The storage location of the ingredient as a {@link String}.
     * @param units The unit of measure (UOM) of the ingredient as a {@link String}.
     * @param category The category of the ingredient as a {@link String}.
     * @param amount The stored amount of the ingredient in its UOM as a {@link Integer}.
     */
    public Ingredient(String name, String desc, LocalDate best_before, String location, String units,
                      String category, Integer amount) {
        this.name = name;
        this.desc = desc;
        this.best_before = best_before;
        this.location = location;
        this.units = units;
        this.category = category;
        this.amount = amount;
    }

    /**
     * Quick and dirty generator of arraylist of ingredients for UI testing
     * @return ArrayList of {@link Ingredient} instances
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public final static ArrayList<Ingredient> createIngredientList(){
        ArrayList<Ingredient> testIngredients = new ArrayList<>();

        // Fruits
        Ingredient lime = new  FruitIngredient ("lime", "small green lime", LocalDate.now(), "Pantry", "Singles", "Fruit", 4);
        Ingredient apple = new  FruitIngredient ("apple", "red apple small", LocalDate.now(), "Pantry", "Singles", "Fruit", 4);
        Ingredient banana = new  FruitIngredient ("banana", "curved yellow banana", LocalDate.now(), "Pantry", "Singles", "Fruit", 4);
        Ingredient frozen_strawberries = new  FruitIngredient ("frozen strawberries", "small red seeded frozen fruit", LocalDate.now(), "Pantry", "g", "Fruit", 1000);

        // Veggies
        Ingredient yellow_onion = new VegetableIngredient("yellow_onion", "yellow skinned onion", LocalDate.now(), "Pantry", "Singles", "Veggie", 4);
        Ingredient tomato_paste = new VegetableIngredient("tomato paste", "Thick and creamy tomato paste", LocalDate.now(), "Pantry", "ml", "Veggie", 250);
        Ingredient tomato = new VegetableIngredient("tomato", "Bright red tomato", LocalDate.now(), "Fridge", "g", "Veggie", 500);
        Ingredient romaine_lettuce = new VegetableIngredient("romaine lettuce", "green crunchy lettuce", LocalDate.now(), "Fridge", "g", "Veggie", 100);
        Ingredient carrot = new VegetableIngredient("carrot", "orange crunchy carrot", LocalDate.now(), "Fridge", "g", "Veggie", 300);
        Ingredient yellow_potato = new VegetableIngredient("yellow potato", "yellow yukon gold potato", LocalDate.now(), "Fridge", "g", "Veggie", 300);

        // Diary
        Ingredient milk = new DairyIngredient ("milk", "2% homogenized milk", LocalDate.now(), "Fridge", "ml", "Dairy", 2000);
        Ingredient parmesan = new DairyIngredient ("parmesan", "block of cheese, can be shredded", LocalDate.now(), "Fridge", "g", "Dairy", 450);
        Ingredient heavy_cream = new DairyIngredient ("heavy_cream", "heavy whipped fattening cream", LocalDate.now(), "Fridge", "ml", "Dairy", 350);
        Ingredient yogurt = new DairyIngredient ("yogurt", "plain yogurt", LocalDate.now(), "Fridge", "ml", "Dairy", 350);
        Ingredient sour_cream = new DairyIngredient("sour cream", "sour and creamy", LocalDate.now(), "Fridge", "ml", "Dairy", 250);

        // Protein
        Ingredient minced_beef  = new ProteinIngredient("minced beef", "extra lean ground beef", LocalDate.now(), "Fridge", "g", "Protein", 1000 );
        Ingredient minced_pork  = new ProteinIngredient("minced pork", "lean ground pork", LocalDate.now(), "Fridge", "g", "Protein", 1000 );
        Ingredient egg  = new ProteinIngredient("egg", "white egg", LocalDate.now(), "Fridge", "Singles", "Protein", 12 );
        Ingredient chicken_drumstick  = new ProteinIngredient("chicken drumstick", "not the icecream", LocalDate.now(), "Fridge", "Singles", "Protein", 6 );
        Ingredient tuna  = new ProteinIngredient("tuna", "sushi grade wild tuna", LocalDate.now(), "Fridge", "oz", "Protein", 20 );
        Ingredient bacon  = new ProteinIngredient("bacon", "fat and greasy", LocalDate.now(), "Fridge", "Singles", "Protein", 20 );

        // Grain
        Ingredient flour  = new GrainIngredient("flour", "all purpose flour", LocalDate.now(), "Pantry", "oz", "Grain",  4 );
        Ingredient rice  = new GrainIngredient("rice", "white rice", LocalDate.now(), "Pantry", "g", "Grain",  400 );

        // Lipids
        Ingredient olive_oil  = new LipidIngredient("olive oil", "extra virgin", LocalDate.now(), "Pantry", "ml", "Lipid",  500 );
        Ingredient butter  = new LipidIngredient("butter", "softened yellow butter", LocalDate.now(), "Fridge", "g", "Lipid",  300 );
        Ingredient sesame_oil  = new LipidIngredient("sesame oil", "sesame fragrance", LocalDate.now(), "Pantry", "ml", "Lipid",  300 );
        Ingredient vegetable_oil  = new LipidIngredient("vegetable oil", "yellow oil", LocalDate.now(), "Pantry", "ml", "Lipid",  500 );

        // Spices
        Ingredient sugar  = new SpiceIngredient("sugar", "real cane sugar", LocalDate.now(), "Pantry", "g", "Spice", 200 );
        Ingredient salt  = new SpiceIngredient("salt", "sea salt", LocalDate.now(), "Pantry", "g", "Spice", 200 );
        Ingredient garlic  = new SpiceIngredient("garlic", "aromatic", LocalDate.now(), "Pantry", "Singles", "Spice", 6 );
        Ingredient peppercorn  = new SpiceIngredient("peppercorn", "black peppercorn", LocalDate.now(), "Pantry", "Singles", "Spice", 30 );
        Ingredient black_pepper  = new SpiceIngredient("black pepper", "ground black pepper", LocalDate.now(), "Pantry", "g", "Spice", 200 );
        Ingredient ginger  = new SpiceIngredient("ginger", "grated ginger", LocalDate.now(), "Pantry", "g", "Spice", 50 );

        // Misc
        Ingredient cooking_wine  = new MiscIngredient("cooking wine", "red cooking wine", LocalDate.now(), "Pantry", "ml", "Misc", 400 );
        Ingredient oyster_sauce  = new MiscIngredient("oyster sauce", "doesn't taste like oysters", LocalDate.now(), "Pantry", "ml", "Misc", 400 );
        Ingredient soy_sauce  = new MiscIngredient("soy sauce", "umami", LocalDate.now(), "Pantry", "ml", "Misc", 40000 );
        Ingredient honey  = new MiscIngredient("honey", "Golden sticky honey", LocalDate.now(), "Pantry", "ml", "Misc", 200 );
        Ingredient chicken_stock  = new MiscIngredient("chicken_stock", "salty soup base", LocalDate.now(), "Pantry", "ml", "Misc", 600 );
        Ingredient vanilla_extract  = new MiscIngredient("vanilla_extract", "sweet smelling vanilla", LocalDate.now(), "Pantry", "ml", "Misc", 50 );


        // Adding all the ingredients
        //fruits
        testIngredients.add(apple);
        testIngredients.add(lime);
        testIngredients.add(banana);
        testIngredients.add(frozen_strawberries);

        // veggies
        testIngredients.add(yellow_onion);
        testIngredients.add(yellow_potato);
        testIngredients.add(tomato_paste);
        testIngredients.add(tomato);
        testIngredients.add(carrot);
        testIngredients.add(romaine_lettuce);

        // diary
        testIngredients.add(milk);
        testIngredients.add(sour_cream);
        testIngredients.add(parmesan);
        testIngredients.add(heavy_cream);
        testIngredients.add(parmesan);

        // protein
        testIngredients.add(egg);
        testIngredients.add(tuna);
        testIngredients.add(minced_beef);
        testIngredients.add(minced_pork);
        testIngredients.add(chicken_drumstick);
        testIngredients.add(bacon);

        // grain
        testIngredients.add(rice);
        testIngredients.add(flour);

        // lipid
        testIngredients.add(butter);
        testIngredients.add(olive_oil);
        testIngredients.add(sesame_oil);
        testIngredients.add(vegetable_oil);

        // spices
        testIngredients.add(peppercorn);
        testIngredients.add(black_pepper);
        testIngredients.add(salt);
        testIngredients.add(garlic);
        testIngredients.add(ginger);
        testIngredients.add(sugar);

        // misc
        testIngredients.add(honey);
        testIngredients.add(chicken_stock);
        testIngredients.add(cooking_wine);
        testIngredients.add(oyster_sauce);
        testIngredients.add(soy_sauce);
        testIngredients.add(vanilla_extract);

        return testIngredients;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getBestBefore() {
        return best_before;
    }

    public void setBestBefore(LocalDate best_before) {
        this.best_before = best_before;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {

        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }
}
