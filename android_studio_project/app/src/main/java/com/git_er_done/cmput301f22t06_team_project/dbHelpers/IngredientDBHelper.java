package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.MiscIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.ProteinIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.DairyIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.FruitIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.GrainIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.LipidIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.SpiceIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.VegetableIngredient;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class IngredientDBHelper {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference ingredientsDB = db.collection("Ingredients");

    public void addIngredient(Ingredient ingredient){
        String name = ingredient.getName();
        String desc = ingredient.getDesc();
        String best_before = ingredient.getBestBefore().toString();
        String location = ingredient.getLocation();
        String units = ingredient.getUnits();
        String category = ingredient.getCategory();
        Integer amount = ingredient.getAmount();
        String amount_string = amount.toString();
        HashMap<String, String> data = new HashMap<>();
        data.put("description",desc);
        data.put("best before", best_before);
        data.put("location",location);
        data.put("unit", units);
        data.put("category",category);
        data.put("amount",amount_string);

        ingredientsDB
                .document(name)
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

    public void deleteIngredient(String ingredient){
        ingredientsDB
                .document(ingredient)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Deleted has been deleted successfully!");
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

    /**
     * So this function is just a way so we don't need to pass the adapter in
     * the ingredientsDBhelper but instead return the ingredients and set the adapter
     * in the controller or something
     * @param firebaseCallback
     */
    public void getAllIngredients(FirebaseCallback firebaseCallback) {
        ArrayList<Ingredient> retrieved = new ArrayList<>();
        ingredientsDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot docs, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: docs){
                    Ingredient ingredient =  createIngredient(doc);
                    retrieved.add(ingredient);
                }
                firebaseCallback.onCallback(retrieved);
            }
        });
    }

    /**
     * Here we just pass the adapter and set it here. It's less work but it also has more coupling and we
     * might possibly lose out on the ability to have to alter the ingredients in the controller for some
     * reason.
     * @param adapter
     * @param ingredients
     */
    public void fillAdapter(IngredientsRecyclerViewAdapter adapter, ArrayList<Ingredient> ingredients){
        ingredients.clear();
        ingredientsDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot docs, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: docs){
                    Ingredient ingredient =  createIngredient(doc);
                    ingredients.add(ingredient);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void searchForIngredient(String ingredient, IngredientsFirebaseCallBack ingredientsFirebaseCallBack) {
        ArrayList<Ingredient> retrieved = new ArrayList<Ingredient>();
        Log.d(TAG, "The name I'm looking for is " + ingredient);
        ingredientsDB.document(ingredient).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    Log.d(TAG, "The document name is " + doc.getId());
                    Ingredient ingredient = createIngredient(doc);
                    ingredientsFirebaseCallBack.onCallback(ingredient);
                }
            }
        });
    }

    private Ingredient createIngredient(DocumentSnapshot doc) {
        Ingredient ingredient = null;
        String name = doc.getId();
        String desc = (String) doc.getData().get("description");
        LocalDate best_before = LocalDate.parse((String) doc.getData().get("best before"));
        String location = (String) doc.getData().get("location");
        String unit = (String) doc.getData().get("unit");
        String category = (String) doc.getData().get("category");
        Integer amount = Integer.parseInt((String) doc.getData().get("amount"));
        if (category.equals("dairy")) {
            ingredient = new DairyIngredient(name,desc,best_before,location,unit,category,amount);
        }else if (category.equals("fruit")) {
            ingredient = new FruitIngredient(name,desc,best_before,location,unit,category,amount);
        }else if (category.equals("grain")) {
            ingredient = new GrainIngredient(name,desc,best_before,location,unit,category,amount);
        }else if (category.equals("lipid")) {
            ingredient = new LipidIngredient(name,desc,best_before,location,unit,category,amount);
        }else if (category.equals("protein")) {
            ingredient = new ProteinIngredient(name,desc,best_before,location,unit,category,amount);
        }else if (category.equals("spice")) {
            ingredient = new SpiceIngredient(name,desc,best_before,location,unit,category,amount);
        }else if (category.equals("vegetable")) {
            ingredient = new VegetableIngredient(name, desc, best_before, location, unit, category, amount);
        }else if (category.equals("misc")) {
            ingredient = new MiscIngredient(name, desc, best_before, location, unit, category, amount);
        }
        return ingredient;
    }

}

