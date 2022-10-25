package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.VeganIngredient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class IngredientDBHelper {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final CollectionReference ingredientsDB = db.collection("Ingredients");

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

    public ArrayList<Ingredient> getData(){
        ArrayList<Ingredient> retrieved = new ArrayList<Ingredient>();
        ingredientsDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: value){
                    VeganIngredient ingredient = null;
                    String name = doc.getId();
                    String desc = (String) doc.getData().get("description");
                    LocalDate best_before = LocalDate.parse((String) doc.getData().get("best before"));
                    String location = (String) doc.getData().get("location");
                    String unit = (String) doc.getData().get("unit");
                    String category = (String) doc.getData().get("category");
                    Integer amount = Integer.parseInt((String) doc.getData().get("amount"));
                    if (category == "Vegan"){
                        ingredient = new VeganIngredient(name,desc,best_before,location,unit,category,amount);
                    }
                    retrieved.add(ingredient);
                }
            }
        });
        return retrieved;
    }

}
