package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * @author Saheel Sarker
 * @see IngredientDBHelper
 * @see RecipesDBHelper
 * @version 1 because this is the first time I'm commenting
 */
public class MealPlannerDBHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final CollectionReference mealPlansDB = db.collection("mealPlans");

    public void addMealPlan(String mealPlan, HashMap<String,String> data){
        mealPlansDB
                .document(mealPlan)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
// These are a method which gets executed when the task is succeeded
                        Log.d(TAG, "Data has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
// These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Data could not be added!" + e.toString());
                    }
                });
    }

    /**
     * Take a string argument and searches the database with a document with the same name as the argument and deletes it
     * @param mealPlan of type String
     * returns void
     * @see IngredientDBHelper
     * @see RecipesDBHelper
     */
    public void deleteMealPlan(String mealPlan){
        mealPlansDB
                .document(mealPlan)
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
// These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Data could not be deleted!" + e.toString());
                    }
                });
    }
}
