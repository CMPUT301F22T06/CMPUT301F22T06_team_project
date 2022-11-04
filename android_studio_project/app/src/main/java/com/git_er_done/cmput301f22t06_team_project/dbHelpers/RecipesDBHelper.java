package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipesDBHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final CollectionReference recipesDB = db.collection("Recipes");

    public void addRecipe(Recipe recipe){
        HashMap<String, String> sendToDb = new HashMap<>();

        String title = recipe.getTitle();

        String comments = recipe.getComments();
        String category = recipe.getCategory();
        String prepTime = String.valueOf(recipe.getPrep_time());
        String servings = String.valueOf(recipe.getServings());
        String firstField = comments + "|" + category+ "|" + prepTime + "|" + servings;
        sendToDb.put("details", firstField);

        ArrayList<RecipeIngredient> recipeIngredients = recipe.getIngredients();
        String ingredientFields;

        for (RecipeIngredient i: recipeIngredients) {
            String name = i.getName();
            String units = i.getUnits();
            String amount = String.valueOf(i.getAmount());
            String comment = i.getComment();
            ingredientFields = units + "|" + String.valueOf(amount) + "|" + comment;
            sendToDb.put(name, ingredientFields);
        }

        recipesDB
                .document(title)
                .set(sendToDb)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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

    public void setRecipesAdapter(){
        ArrayList<Recipe> retrieved = new ArrayList<>(); // This will be passed instead when adapter is done
        recipesDB.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot docs = task.getResult();
                for(QueryDocumentSnapshot doc: docs) {
                    Recipe recipe = createRecipe(doc);
                    retrieved.add(recipe);
                }
                // The adapter will be here
            }
        });
    }

    public Recipe searchForRecipe(String recipe) {
        ArrayList<Recipe> retrieved = new ArrayList<>();
//        IngredientDBHelper ingredientDBHelper = new IngredientDBHelper();
        recipesDB.document(recipe).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException error) {
                Recipe recipe = createRecipe(doc);
                retrieved.add(recipe);
            }
        });
        return retrieved.get(0);
    }

    private Recipe createRecipe(DocumentSnapshot doc) {
        Recipe recipe = null;
        String title = doc.getId();
        Map<String, Object> fromDB = doc.getData();
        HashMap<String,String> fromDBbutString = new HashMap<>();
        for (String key: fromDB.keySet()) {
            fromDBbutString.put(key,(String) fromDB.get(key));
        }
        
        String[] recipeDetails = (fromDBbutString.remove("details")).split("\\|");
        String comments = String.valueOf(recipeDetails[0]);
        String category = recipeDetails[1];
        Integer prepTime = Integer.parseInt(recipeDetails[2].toString());
        Integer servings = Integer.parseInt(recipeDetails[3].toString());
        recipe = new Recipe(title,comments,category,prepTime,servings);

        for (String key: fromDBbutString.keySet()) {
            String[] ingredientDetails = (fromDBbutString.get(key)).split("\\|");
            String name = key;
            String units = ingredientDetails[0];
            Integer amount = Integer.parseInt(ingredientDetails[1]);
            String comment = ingredientDetails[2];
            RecipeIngredient recipeIngredient = new RecipeIngredient(name,units,amount,comment);
            recipe.addIngredient(recipeIngredient);
        }

        return recipe;
    }
}

