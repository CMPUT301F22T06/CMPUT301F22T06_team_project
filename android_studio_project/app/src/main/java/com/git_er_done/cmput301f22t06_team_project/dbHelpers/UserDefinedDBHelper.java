package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.IngredientCategory;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.IngredientLocation;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.IngredientUnit;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDefinedDBHelper {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static final CollectionReference userDefinedDB = db.collection("UserDefined");
    private static int selectedUserDefinedPos;

    private static ArrayList<String> ingredientCategories = new ArrayList<>();
    private static ArrayList<String> ingredientUnits = new ArrayList<>();
    private static ArrayList<String> ingredientLocations = new ArrayList<>();
    private static ArrayList<String> recipeCategories = new ArrayList<>();
    private static UserDefinedDBHelper singleInstance = null;

    private UserDefinedDBHelper() {
        setupSnapshotListenerForUserDefinedStuff();
    }

    public static UserDefinedDBHelper getInstance()
    {
        if (singleInstance == null)
            singleInstance = new UserDefinedDBHelper();
        return singleInstance;
    }

    public static ArrayList<String> getIngredientCategories() {
        return ingredientCategories;
    }

    public static ArrayList<String> getIngredientUnits() {
        return ingredientUnits;
    }

    public static ArrayList<String> getIngredientLocations() {
        return ingredientLocations;
    }

    public static ArrayList<String> getRecipeCategories() {
        return recipeCategories;
    }

    public void addIUserDefined(String userDefined, String type){
        HashMap<String, String> sendToDb = new HashMap<>();
        sendToDb.put(userDefined, userDefined);
        userDefinedDB
                .document(type)
                .set(sendToDb)
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

    public static void deleteUserDefined(String userDefined, String document, int position){
        selectedUserDefinedPos = position;
        DocumentReference dr = userDefinedDB.document(document);
        dr.update(userDefined, FieldValue.delete());
    }


    private void setupSnapshotListenerForUserDefinedStuff() {
        db.collection("UserDefined")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("DB ERROR", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            DocumentSnapshot documentSnapshot = dc.getDocument();
                            Map<String, Object> map = documentSnapshot.getData();
                            ArrayList<String> newArray = new ArrayList<>();
                            for (String key: map.keySet()){
                                newArray.add(key);
                            }
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                recipesInStorage.add(recipe);
                            }

                            if(dc.getType() == DocumentChange.Type.REMOVED){
                                int position = recipesInStorage.indexOf(recipe);
                                //If the rvAdapter returns a valid position
                                if(position != -1){
                                    recipesInStorage.remove(position);
                                }
                                else{
                                    Log.e("DB ERROR", "ERROR REMOVING RECIPE FROM STORAGE");
                                }
                            }
                        }
                    }
                });
    }
}
