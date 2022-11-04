package com.git_er_done.cmput301f22t06_team_project.models;

import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.BreakFastRecipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.DessertRecipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.DinnerRecipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.LunchRecipe;

import java.util.ArrayList;


public class Recipe {
    private ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
    private String title;
    private String comments;
    private String category;
    private int prep_time;
    private int servings;

    // No empty constructor since it should never be called anyway

    /**
     * Creates a new Recipe object.
     * @param title The name of the Recipe
     * @param comments A brief description of the Recipe
     * @param category The user-defined category of the Recipe
     * @param prep_time The time it takes to prepare the Recipe
     * @param servings The number of people this Recipe serves
     */
    public Recipe(String title, String comments, String category, int prep_time, int servings) {
        this.title = title;
        this.comments = comments;
        this.category = category;
        this.prep_time = prep_time;
        this.servings = servings;
    }

    /**
     * Returns an {@link ArrayList} of {@link RecipeIngredient}s contained in the Recipe
     * @return The ingredients contained in the recipe
     */
    public ArrayList<RecipeIngredient> getIngredients() {
        return recipeIngredients;
    }

    /**
     * Sets a new list of Ingredients for the recipe, replacing the old list.
     * @param recipeIngredients An {@link ArrayList} of {@link RecipeIngredient}s to be set.
     */
    public void setIngredientsList(ArrayList<RecipeIngredient> recipeIngredients) { //Got rid of this
        this.recipeIngredients = recipeIngredients;
    }

    /**
     * Adds a new Ingredient to the recipe alongside the old ones
     * @param recipeIngredient The new {@link RecipeIngredient} to be added
     */
    public void addIngredient(RecipeIngredient recipeIngredient) { // I changed this
        recipeIngredients.add(recipeIngredient);
    }

    /**
     * Removes the specified {@link RecipeIngredient} if it exists in the Recipe
     * @param recipeIngredient The Ingredient to be removed.
     */
    public void removeIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredients.contains(recipeIngredient)) {
            recipeIngredients.remove(recipeIngredient);
        } else {
            throw new IllegalArgumentException("That ingredient does not exist in this recipe!");
        }
    }

    /**
     * Creates an {@link ArrayList} of recipes for UI testing
     * @return An {@link ArrayList} of recipes.
     */
    public final static ArrayList<Recipe> createRecipeList() {
        ArrayList<Recipe> testRecipes = new ArrayList<>();
        // Breakfast
        Recipe fruit_salad = new BreakFastRecipe("perfect summer fruit salad", "mybaa82\n" +
                "It was great. I may change it up next time but for now, perfect\n" +
                "\n" +
                "Barb Gregory\n" +
                "I did not make any changes. Made it exactly as the recipe called for. It was easy to make and everyone loved the taste. I will make it again\n" +
                "\n" +
                "Morgon Barg\n" +
                "I love this recipe! The sauce is amazing. I have been making it for the 4th of July and it has become a repeat request dish for me to bring! Thank you!!", "breakfast", 30, 10);

        Recipe spicy_tuna_poke = new BreakFastRecipe("spicy tuna poke bowl", "Cassy\n" +
                "I love all of these ideas !! However – as crazy as it may seem- I don’t like Avocado.  So many recipes call for it — and I would love some ideas for a substitute. Thank you for sharing all of these !!!\n" +
                "\n" +
                "Mario\n" +
                "This was a huge hit. Obviously the recipe can be used as a starting point for your own variations based on personal taste and what’s available in your refrigerator / garden at the time, but it’s just great as is.\n" +
                "\n" +
                "Nicole\n" +
                "So good!  Had to go to the Japanese grocery store for the tuna, but so worth it!", "breakfast", 15, 2);

        // Lunch
        Recipe fried_rice = new LunchRecipe("easy fried rice", "anniefitness\n" +
                "This pumpkin soup is healthy and so delicious! Perfect winter meal with warm rolls.\n" +
                "\n" +
                "Alisonjaym\n" +
                "Simple, easy to make with a smaller number of ingredients. Very tasty,\n" +
                "\n" +
                "Topysy1968\n" +
                "My absolute go to Pumpkin Soup Big Batches Made Evert Winter Delicious", "lunch", 40, 4);

        Recipe honey_soy_chicken = new LunchRecipe("honey soy chicken", "Pirihonga\n" +
                "I used Chicken breasts rather than drumsticks. Didn’t have any ginger unfortunately, but the recipes still tastes great and a lovely low carb recipe.\n" +
                "\n" +
                "lorellemac\n" +
                "Result wasnt really a knockout. Didnt find drumsticks sticky or sweet enough. No one raved.\n" +
                "\n" +
                "Fuschia\n" +
                "I use drumettes (half of chicken wing). This makes a great fingerfood. So easy to make and always popular.", "lunch", 165, 4);

        // Dinner
        Recipe pumpkin_soup = new DinnerRecipe("pumpkin soup", "crispy fox\n" +
                "The young fella and I cook this every week. We love it!\n" +
                "\n" +
                "Dsei\n" +
                "Added some thin sliced chicken breast as well. Great quick, easy meal. The measurements in the recipe easily made enough for 2 good sized bowls as a main. Save time and use a cup of microwave rice.\n" +
                "\n" +
                "EWiltshire\n" +
                "I was given so many bags of rice and couldnt figure out what to make with it! Just made this in batches and Boom! Even my 2 year old loves this!", "dinner", 50, 6);

        Recipe pad_thai = new DinnerRecipe("pad thai", "CamTer\n" +
                "Family enjoyed this one, its an easy recipe to tweak a little to meet your tastes.\n" +
                "\n" +
                "JodieTamblyn\n" +
                "Not the most authentic recipe but it worked for our family as a simple weeknight meal. Was a hit with my two fussy kids who wouldn’t normally eat pad Thai.\n" +
                "\n" +
                "Mrs Crispy\n" +
                "Quick and easy to prepare. I found it quite nice and definitely filling. Not a win for my kids, only 1 out of 3 actually ate it. It was a good experience to try but I probably won't make again.", "dinner", 40, 4);

        // Dessert
        Recipe vanilla_icecream = new DessertRecipe("vanilla icecream", "Lyn Wanday\n" +
                "I didnt make any changes the recipe was very nice and this i the only recipe that works for me\n" +
                "\n" +
                "Terry Brean\n" +
                "My husband like this vanilla because it made a hard ice cream so he can put hot fudge sauce on without melting so fast.\n" +
                "\n" +
                "Irene Teh\n" +
                "Easy and delicious but more of an icy texture than creamy. Made no alterations to the recipe.\n", "dessert", 155, 4);

        Recipe bloody_mary = new DessertRecipe("bloody mary", "judynobark\n" +
                "These were great. After sampling these, I'd made a pitcher, we doubled the vodka. I also served it with a slice of crisp bacon. I got rave reviews and they looked so great.\n" +
                "\n" +
                "Coleen McClish\n" +
                "I made the bloody mary mix but since I don't like vodka I used beer instead and made bloody bulls. They turned out excellent.\n" +
                "\n" +
                "Janet\n" +
                "Too salty", "dessert", 2, 1);


        // Initialize the recipes
        // breakfast
        testRecipes.add(fruit_salad);
        testRecipes.add(spicy_tuna_poke);

        // lunch
        testRecipes.add(fried_rice);
        testRecipes.add(honey_soy_chicken);

        // dinner
        testRecipes.add(pumpkin_soup);
        testRecipes.add(pad_thai);

        // dessert
        testRecipes.add(bloody_mary);
        testRecipes.add(vanilla_icecream);

        return testRecipes;
    }

    public ArrayList<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    /**
     * Returns the Name of the recipe as a {@link String}
     * @return The title of the Recipe
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new name for the recipe, overwriting the old one.
     * @param title The new name as a {@link String}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the recipe as a {@link String}
     * @return The description of the recipe.
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets a new description for the recipe, overwriting the old one.
     * @param comments The description to be set as a {@link String}
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Returns the category of the recipe as a {@link String}
     * @return The category of the recipe.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets a new category for the recipe, overwriting the old one.
     * @param category The new category of the Recipe as a {@link String}
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *  Returns the amount of time it takes to make the recipe as an int
     * @return The time the recipe takes to cook
     */
    public int getPrep_time() {
        return prep_time;
    }

    /**
     * Sets a new cooking time for the recipe, overwriting the old one.
     * @param prep_time The new cooking time as an int.
     */
    public void setPrep_time(int prep_time) {
        this.prep_time = prep_time;
    }

    /**
     * Returns the number of people the recipe can serve
     * @return The number of people that can be fed with the recipe
     */
    public int getServings() {
        return servings;
    }

    /**
     * Sets a new number of servings, overwriting the old one
     * @param servings The new number of servings
     */
    public void setServings(int servings) {
        this.servings = servings;
    }
}
