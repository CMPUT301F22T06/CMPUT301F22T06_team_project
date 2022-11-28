package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Saheel Sarker
 * @ingredientsAddEditFragment and @recipesAddEditFragment (for now)
 * @Version 1 (Because I didn't write the version before writing this)
 * @see MealDBHelper
 * @see RecipeDBHelper
 * @see IngredientDBHelper
 */

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

    /**
     * This method creates a instance of UserDefinedDBHelper to use anywhere in the code
     */
    public static UserDefinedDBHelper getInstance()
    {
        if (singleInstance == null)
            singleInstance = new UserDefinedDBHelper();
        return singleInstance;
    }

    /**
     * @return ingredientCategories which is an Arraylist<String>
     */

    public static ArrayList<String> getIngredientCategories() {
        return ingredientCategories;
    }

    /**
     * @return ingredientUnits which is an Arraylist<String>
     */

    public static ArrayList<String> getIngredientUnits() {
        return ingredientUnits;
    }

    /**
     * @return ingredientLocations which is an Arraylist<String>
     */

    public static ArrayList<String> getIngredientLocations() {
        return ingredientLocations;
    }

    /**
     * @return recipeCategories which is an Arraylist<String>
     */

    public static ArrayList<String> getRecipeCategories() {
        return recipeCategories;
    }

    /**
     * @param {userDefined which is String} {type which is a String}
     * Adds a userDefined "something" to the database
     */

    public static void addUserDefined(String userDefined, String type){
        DocumentReference dr = userDefinedDB.document(type);
        dr.update(userDefined,userDefined);
    }

    /**
     * @param {userDefined which is String} {type which is a String} {position which is an int}
     * Deletes a userDefined "something" in the database
     */

    public static void deleteUserDefined(String userDefined, String document, int position){
        selectedUserDefinedPos = position;
        DocumentReference dr = userDefinedDB.document(document);
        dr.update(userDefined, FieldValue.delete());
    }

    /**
     * starts a listener in the database whenever data has changed and changes every array accordingly
     */

    private static void setupSnapshotListenerForUserDefinedStuff() {
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
                            if(dc.getType() == DocumentChange.Type.MODIFIED) {
                                if (documentSnapshot.getId().equals("ingredientCategory")) {
                                    ingredientCategories.clear();
                                    for (String i : newArray) {
                                        ingredientCategories.add(i);
                                    }
                                }
                                if (documentSnapshot.getId().equals("ingredientUnits")){
                                    ingredientUnits.clear();
                                    for (String i : newArray) {
                                        ingredientUnits.add(i);
                                    }
                                }
                                if (documentSnapshot.getId().equals("ingredientLocations")){
                                    ingredientLocations.clear();
                                    for (String i : newArray) {
                                        ingredientLocations.add(i);
                                    }
                                }
                                if (documentSnapshot.getId().equals("recipeCategory")){
                                    recipeCategories.clear();
                                    for (String i : newArray) {
                                        recipeCategories.add(i);
                                    }
                                }
                            }
                        }
                    }
                });
    }

    /**
     * starts a listener in the database whenever data has changed
     * and changes the recipes category spinner adapter accordingly
     */

    public static void setupSnapshotListenerForRecipeCategoryAdapter(
            ArrayAdapter<String> recipeCategoryAdapter){
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
                            if(dc.getType() == DocumentChange.Type.MODIFIED) {
                                if (documentSnapshot.getId().equals("recipeCategory")){
                                    recipeCategoryAdapter.clear();
                                    for (String i: newArray){
                                        recipeCategoryAdapter.add(i);
                                    };
                                    recipeCategoryAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
    }

    /**
     * starts a listener in the database whenever data has changed
     * and changes every spinner adapter in the ingredients add/edit fragment accordingly
     */

    public static void setupSnapshotListenerForIngredientUserDefinedAdapter(
            ArrayAdapter<String> ingredientUnitAdapter,
            ArrayAdapter<String> ingredientLocationAdapter,
            ArrayAdapter<String> ingredientCategoryAdapter){
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
                            if(dc.getType() == DocumentChange.Type.MODIFIED) {
                                if (documentSnapshot.getId().equals("ingredientCategory")){
                                    ingredientCategoryAdapter.clear();
                                    for (String i: newArray){
                                        ingredientCategoryAdapter.add(i);
                                    };
                                    ingredientCategoryAdapter.notifyDataSetChanged();
                                }
                                if (documentSnapshot.getId().equals("ingredientUnits")){
                                    ingredientUnitAdapter.clear();
                                    for (String i: newArray){
                                        ingredientUnitAdapter.add(i);
                                    };
                                    ingredientUnitAdapter.notifyDataSetChanged();
                                }
                                if (documentSnapshot.getId().equals("ingredientLocations")){
                                    ingredientLocationAdapter.clear();
                                    for (String i: newArray){
                                        ingredientLocationAdapter.add(i);
                                    }
                                    ingredientLocationAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
    }
}
