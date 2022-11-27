package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Meal keys are their associated UUID
 */
public class MealDBHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference mealsDB;
    private int selectedMealPos = -1;
    private static MealDBHelper singleInstance = null;

    /**
     * Private constructor can only be called when an instance of this singleton is created
     */
    private MealDBHelper(){
        db = FirebaseFirestore.getInstance();
        mealsDB = db.collection("Meals");
        setupSnapshotListenerForLocalMealStorage();
    }

    public static MealDBHelper getInstance(){
        if(singleInstance == null){
            singleInstance = new MealDBHelper();
        }
        return singleInstance;
    }

    private static ArrayList<Meal> mealsInStorage = new ArrayList<>();

    public static ArrayList<Meal> getMealsFromStorage(){
        return mealsInStorage;
    }

    public static ArrayList<Meal> getMealsFromStorageAtDate(LocalDate providedDate){
        ArrayList<Meal> mealsForThisDate = new ArrayList<>();

        //loop through all meals in storage, add any that share the provided date to return list
        for(int i = 0; i < mealsInStorage.size(); i++){
            if(mealsInStorage.get(i).getDate().equals(providedDate)){
                mealsForThisDate.add(mealsInStorage.get(i));
            }
        }

        return mealsForThisDate;
    }

    //method to get all meals of an associated day

    //Hashmap storing all meals, where key = date, and the value is an arraylist of meals


    //method to convert a meal object to a corresponding POJO (Plain Old Java Object ) hashmap
    public static HashMap<String, Object> createPOJOHashMapFromMealObject(Meal meal){
        HashMap<String, Object> mealAttributes = new HashMap<>();

        String UUID = meal.getId().toString();
        String recipesAndServingSizes = createDelimitedStringFromMealRecipesArrayList(meal.getRecipesFromMeal());
        String ingredientsAndAmountAndUnits = createDelimitedStringFromMealIngredientsArrayList(meal.getOnlyIngredientsFromMeal());
        String date = meal.getDate().toString();

        mealAttributes.put("uuid", UUID);
        mealAttributes.put("recipes", recipesAndServingSizes);
        mealAttributes.put("ingredients", ingredientsAndAmountAndUnits);
        mealAttributes.put("date", date);

        return mealAttributes;
    }

    public static String createDelimitedStringFromMealRecipesArrayList(ArrayList<Recipe> recipes){
        String res = new String();
        //loop through recipes and append each recipe title and associated serving size to string
        for(int i = 0; i < recipes.size(); i++){
            String delimPacket = "|";
            delimPacket += recipes.get(i).getTitle();
            delimPacket += ",";
            delimPacket += recipes.get(i).getServings().toString();
            res += delimPacket;
        }
        return res;
    }

    public static ArrayList<Recipe> createRecipeArrayListFromDelimitedString(String delimitedRecipeString){
        ArrayList<Recipe> recipesList = new ArrayList<>();

//        delimitedRecipeString = delimitedRecipeString.replaceFirst("^|", ""); //Remove the first delimiter from the recipe string packet
        //Split string into individual recies based on | delimiter
        String[] recipesStringArray = delimitedRecipeString.replaceFirst("^\\|", "").split("\\|");

        //loop through each recipe string packet and split name and servings
        for(int i = 0; i < recipesStringArray.length; i++){
            String[] recipeNameAndServingString = recipesStringArray[i].split("\\,");
            String title = recipeNameAndServingString[0];
            Integer servings = Integer.parseInt(recipeNameAndServingString[1]);

            //Create a recipe instance referencing the recipes in local storage (which should match the db thanks to the snapshot listener)
            int recipeIndex = RecipeDBHelper.getIndexOfRecipeFromTitle(title);
            //Find the recipe with the same name
            Recipe referenceRecipeFromStorage = RecipeDBHelper.getRecipesFromStorage().get(recipeIndex);
            Recipe thisMealsRecipeInstance = new Recipe(
                    title,
                    referenceRecipeFromStorage.getComments(),
                    referenceRecipeFromStorage.getCategory(),
                    referenceRecipeFromStorage.getPrep_time(),
                    servings
            );
            recipesList.add(thisMealsRecipeInstance);
        }


        return recipesList;
    }

    public static String createDelimitedStringFromMealIngredientsArrayList(ArrayList<Ingredient> ingredients){
        String res = new String();
        //loop through ingredients and append each ingredient name, amount, and unit to string
        for(int i = 0; i < ingredients.size(); i++){
            String delimPacket = "|";
            delimPacket += ingredients.get(i).getName();
            delimPacket += ",";
            delimPacket += ingredients.get(i).getAmount().toString();
            delimPacket += ",";
            delimPacket += ingredients.get(i).getUnit();
            res += delimPacket;
        }
        return res;
    }


    //method to convert a retrieved meal POJO HashMap back to its corresponding object instance
    //This is reffered to as 'createIngredient' in the ingredientDBHelper
    public Meal createMeal(DocumentSnapshot doc){
        UUID id = UUID.fromString(doc.getId());

        Map<String, Object> fromDB = doc.getData();
        HashMap<String,String> fromDBbutString = new HashMap<>();
        for (String key: fromDB.keySet()) {
            fromDBbutString.put(key,(String) fromDB.get(key));
        }

        ArrayList<Recipe> recipes = createRecipeArrayListFromDelimitedString(fromDBbutString.get("recipes"));

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        LocalDate date = LocalDate.parse((String) doc.getData().get("date"));

        Meal createdMeal = new Meal(id, recipes, ingredients, date);
        return createdMeal;
    }

    public static void addMealToDB(Meal meal){
        String mealUUIDString = meal.getId().toString();
        HashMap<String, Object> mealObjectPOJOHashMap = createPOJOHashMapFromMealObject(meal);
        mealsDB
                .document(mealUUIDString)
                .set(mealObjectPOJOHashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Data has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Data could not be added!" + e.toString());
                    }
                });
    }

    public static void deleteMealFromDB(String mealUUIDString){
        mealsDB
                .document(mealUUIDString)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Deleted has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Data could not be deleted!" + e.toString());
                    }
                });
    }

    public void modifyMealInDB(){

    }

    public void setupSnapshotListenerForLocalMealStorage(){
        db.collection("Meals")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("DB ERROR", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            Meal meal = createMeal(dc.getDocument());

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                mealsInStorage.add(meal);
                            }

                            if(dc.getType() == DocumentChange.Type.MODIFIED){
                                mealsInStorage.set(selectedMealPos, meal);
                            }

                            if(dc.getType() == DocumentChange.Type.REMOVED){
                                int position = mealsInStorage.indexOf(meal);
                                //If the rvAdapter returns a valid position
                                if(position != -1){
                                    mealsInStorage.remove(position);
                                }
                                else{
                                    Log.e("DB ERROR", "ERROR REMOVING INGREDIENT FROM STORAGE");
                                }
                            }
                        }
                    }
                });
    }
}
