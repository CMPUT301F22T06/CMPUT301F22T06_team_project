package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Meal keys are their associated UUID
 */
public class MealDBHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference mealsDB;

    private static MealDBHelper singleInstance = null;

    /**
     * Private constructor can only be called when an instance of this singleton is created
     */
    private MealDBHelper(){
        db = FirebaseFirestore.getInstance();
        mealsDB = db.collection("Meals");
//        setupSnapshotListenerForLocalMealStorage();
    }

    public static MealDBHelper getInstance(){
        if(singleInstance == null){
            singleInstance = new MealDBHelper();
        }
        return singleInstance;
    }

    //method to get all meals of an associated day

    //Hashmap storing all meals, where key = date, and the value is an arraylist of meals


    //method to convert a meal object to a corresponding POJO (Plain Old Java Object ) hashmap
    public static HashMap<String, Object> createPOJOHashMapFromMealObject(Meal meal){
        HashMap<String, Object> mealAttributes = new HashMap<>();

        String UUID = meal.getId().toString();
        String recipesAndServingSizes = createDelimitedStringFromMealRecipesArrayList(meal.getRecipesFromMeal());
        String ingredientsAndAmountAndUnits = createDelimitedStringFromMealIngredientsArrayList(meal.getOnlyIngredientsFromMeal());

        mealAttributes.put("UUID", UUID);
        mealAttributes.put("Recipes", recipesAndServingSizes);
        mealAttributes.put("Ingredients", ingredientsAndAmountAndUnits);

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
//    public Meal createMeal(DocumentSnapshot doc){
//
//    }

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

    public void deleteMealFromDB(String mealUUIDString){
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
}
