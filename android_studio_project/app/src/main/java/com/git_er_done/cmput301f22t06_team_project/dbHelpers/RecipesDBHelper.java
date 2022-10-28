package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.icu.text.CaseMap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.DesertRecipe;
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
        //ArrayList<Ingredient> ingredients = recipe.getIngredients();
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
            ingredientData.put("amount",String.valueOf(ing.getAmount()));
            ingredientData.put("units",ing.getUnits());
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

    public void getAllRecipes(){
        ArrayList<Recipe> retrieved = new ArrayList<>();
        IngredientDBHelper ingredientDBHelper = new IngredientDBHelper();
        recipesDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: value){
                    String title = doc.getId();
                    String comments = (String) doc.getData().get("comments");
                    String category = (String) doc.getData().get("category");
                    Integer prepTime = Integer.parseInt((String) doc.getData().get("prep time"));
                    Integer servings = Integer.parseInt((String) doc.getData().get("servings"));
                    // Figure out way to retrieve ingredient data in subcollection
                    CollectionReference ingredientCollection = recipesDB.document(title).collection("ingredients");
                    ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
                    ingredientCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for (QueryDocumentSnapshot doc: value){
                                String ingredientName = doc.getId();
                                String units = (String) doc.getData().get("units");
                                Integer amount = Integer.parseInt((String) doc.getData().get("amount"));
                                Ingredient ingredient = ingredientDBHelper.searchForIngredient(ingredientName);
                                RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient,units,amount);
                                recipeIngredients.add(recipeIngredient);
                            }
                        }
                    });
                    if (category == "dinner"){

                    } else if (category == "breakfast"){

                    } else if (category == "lunch"){

                    } else if (category == "desert"){
                        DesertRecipe desertRecipe = new DesertRecipe(title,comments,category,prepTime,servings);

                    }
                }
            }
        });


    }
}
