package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.icu.text.CaseMap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class RecipesDBHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final CollectionReference recipesDB = db.collection("recipes");

    public void addRecipe(Recipe recipe){
        String title = recipe.getTitle();
        String comments = recipe.getComments();
        String category = recipe.getCategory();
        String prepTime = String.valueOf(recipe.getPrep_time());
        String servings = String.valueOf(recipe.getServings());
        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        HashMap<String,String> data = new HashMap<>();
        data.put("comments",comments);
        data.put("category", category);
        data.put("prep time",prepTime);
        data.put("servings", servings);

        recipesDB
                .document(title)
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
        CollectionReference ingredientsCollection = recipesDB.document(title).collection("ingredients");
        ArrayList<RecipeIngredient> recipeIngredients = recipe.getIngredients();
        for (RecipeIngredient ing: recipeIngredients){
            HashMap<String,String> ingredientData = new HashMap<>();
            ingredientData.put(ing.getUnits(),String.valueOf(ing.getAmount()));
            ingredientsCollection.document(ing.getName()).set(ingredientData);
        }
    }

    public void deleteRecipe(String recipe){
        recipesDB
                .document(recipe)
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

    public void get(){
        ArrayList<Recipe> retrieved = new ArrayList<>();
        recipesDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: value){
//                    VeganIngredient ingredient = null;
                    String title = doc.getId();
                    String comments = (String) doc.getData().get("comments");
                    String category = (String) doc.getData().get("category");
                    String prepTime = (String) doc.getData().get("prep time");
                    String servings = (String) doc.getData().get("servings");
                    // Figure out way to retrieve ingredient data in subcollection
                }
            }
        });


    }
}
