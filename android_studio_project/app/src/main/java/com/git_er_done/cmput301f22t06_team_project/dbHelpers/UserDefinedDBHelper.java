package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.adapters.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.fragments.RecipesFragment;
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

import java.lang.reflect.Array;
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

    public static void addUserDefined(String userDefined, String type){
        DocumentReference dr = userDefinedDB.document(type);
        dr.update(userDefined,userDefined);
    }

    public static void deleteUserDefined(String userDefined, String document, int position){
        selectedUserDefinedPos = position;
        DocumentReference dr = userDefinedDB.document(document);
        dr.update(userDefined, FieldValue.delete());
    }

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
                                if (documentSnapshot.getId() == "ingredientCategory"){
                                    ingredientCategories = newArray;
                                }
                                if (documentSnapshot.getId() == "ingredientUnits"){
                                    ingredientUnits = newArray;
                                }
                                if (documentSnapshot.getId() == "ingredientLocations"){
                                    ingredientLocations = newArray;
                                }
                                if (documentSnapshot.getId().equals("recipeCategory")){
                                    recipeCategories = newArray;
                                }
                            }
                        }
                    }
                });
    }


    public static void setupSnapshotListenerForRecipeCategoryAdapter(
//            ArrayAdapter<String> ingredientUnitAdapter,
//            ArrayAdapter<String> ingredientLocationAdapter,
//            ArrayAdapter<String> ingredientCategoryAdapter,
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
//                                if (documentSnapshot.getId() == "ingredientCategories"){
//                                    ingredientCategoryAdapter.clear();
//                                    for (String i: newArray){
//                                        ingredientCategoryAdapter.add(i);
//                                    };
//                                }
//                                if (documentSnapshot.getId() == "ingredientUnits"){
//                                    ingredientUnitAdapter.clear();
//                                    for (String i: newArray){
//                                        ingredientUnitAdapter.add(i);
//                                    };
//                                }
//                                if (documentSnapshot.getId() == "ingredientLocations"){
//                                    ingredientUnitAdapter.clear();
//                                    for (String i: newArray){
//                                        ingredientUnitAdapter.add(i);
//                                    };
//                                }
                                if (documentSnapshot.getId() == "recipesCategories"){
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


}
