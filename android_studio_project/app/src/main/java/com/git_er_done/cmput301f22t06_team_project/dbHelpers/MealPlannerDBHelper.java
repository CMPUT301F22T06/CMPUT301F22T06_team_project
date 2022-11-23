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
    final CollectionReference mealPlansDB = db.collection("meals");

}
