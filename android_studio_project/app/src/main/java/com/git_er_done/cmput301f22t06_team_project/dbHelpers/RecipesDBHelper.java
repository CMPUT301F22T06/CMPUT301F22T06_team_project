package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.testIngredients;
import static com.git_er_done.cmput301f22t06_team_project.models.Recipe.testRecipes;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.controllers.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Saheel Sarker
 * @see IngredientDBHelper
 * @see MealPlannerDBHelper
 * @version 1 Since this is the first time I'm commenting
 */
public class RecipesDBHelper {

    private static RecipesRecyclerViewAdapter rvAdapter;
    public static int selectedRecipePos;
    public RecipesDBHelper(RecipesRecyclerViewAdapter adapter){
        rvAdapter = adapter;
        eventChangeListener(); //Initialize eventListener for RecyclerView
    }
    /**
     * This method add a recipe to our recipe data base
     * @param recipe of type {@link Recipe}
     * returns void
     * @see IngredientDBHelper
     * @see MealPlannerDBHelper
     */

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static final CollectionReference recipesDB = db.collection("Recipes");
    public static void addRecipe(Recipe recipe){
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
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
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
     * This delete a recipe from the Recipe data base by
     * taking a string argument to look for the document with that na,e
     * @param recipe of type {@link String}
     * returns void
     * @see IngredientDBHelper
     * @see MealPlannerDBHelper
     */
    public static void deleteRecipe(Recipe recipe, int pos){
        String nameofRecipe = recipe.getTitle();
        recipesDB
                .document(nameofRecipe)
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

    public static void modifyRecipeInDB(Recipe newRecipe, Recipe oldRecipe, int pos){
        // Really scuffed way of doing this, but I couldn't think of a better way.
        String nameOfRecipe = oldRecipe.getTitle();
        selectedRecipePos = pos;
        ArrayList update = new ArrayList<>();
        DocumentReference dr = recipesDB.document(nameOfRecipe);
        if(!Objects.equals(newRecipe.getTitle(), oldRecipe.getTitle())){
            // This one is special since the title doesn't exist in the details and i cant directly change the id so i have to remove and re-add.
            RecipesDBHelper.deleteRecipe(oldRecipe, selectedRecipePos);
            RecipesDBHelper.addRecipe(newRecipe);
        }

        if(!Objects.equals(newRecipe.getComments(), oldRecipe.getComments())){
            update.add(newRecipe.getComments());
            //dr.update("comment", newRecipe.getComments());
        }
        else{
            update.add(oldRecipe.getComments());
        }

        if(!Objects.equals(newRecipe.getCategory(), oldRecipe.getCategory())){
            update.add(newRecipe.getCategory());
        }
        else{
            update.add(oldRecipe.getCategory());
        }

        if(!Objects.equals(newRecipe.getPrep_time(), oldRecipe.getPrep_time())){
            update.add(String.valueOf(newRecipe.getPrep_time()));
           // dr.update("prep time", String.valueOf(newRecipe.getPrep_time()));
        }
        else{
            update.add(oldRecipe.getPrep_time());
        }

        if(!Objects.equals(newRecipe.getServings(), oldRecipe.getServings())){
            update.add(String.valueOf(newRecipe.getServings()));
           // dr.update("servings", String.valueOf(newRecipe.getServings()));
        }
        else{
            update.add(oldRecipe.getServings());
        }

        String result = TextUtils.join("|", update);
        dr.update("details", result);

    }

    /**
     * This method sets the adapter for the list of recipes but getting the data from our recipe Database
     * returns void
     * @see IngredientDBHelper
     * @see MealPlannerDBHelper
     */
    public void setRecipesAdapter(RecipesRecyclerViewAdapter recipesRecyclerViewAdapter, ArrayList<Recipe> retrieved){
        recipesDB.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot docs = task.getResult();
                for(QueryDocumentSnapshot doc: docs) {
                    Recipe recipe = createRecipe(doc);
                    retrieved.add(recipe);
                }
                recipesRecyclerViewAdapter.notifyDataSetChanged();
                // The adapter will be here
            }
        });
    }

    /**
     * This method is just a random method I made just in case we need to be able to look
     * for a specific recipe in the recipe Database but I haven't tested it nor is it being
     * used any at the moment
     * @param recipe of type {@link String}
     * returns void
     * @see IngredientDBHelper
     * @see MealPlannerDBHelper
     */
    public static Recipe searchForRecipe(String recipe) {
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

    /**
     * This method takes data from a document in the recipe Database
     * and turns it to a recipe object to return
     * @param doc of type {@link DocumentSnapshot}
     * returns void
     * @see IngredientDBHelper
     * @see MealPlannerDBHelper
     */
    private static Recipe createRecipe(DocumentSnapshot doc) {
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
        Integer prepTime = Integer.parseInt(recipeDetails[2]);
        Integer servings = Integer.parseInt(recipeDetails[3]);
        recipe = new Recipe(title, comments,category,prepTime,servings);

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

    public void eventChangeListener(){
        db.collection("Recipes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("DB ERROR", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                Recipe recipe = createRecipe(dc.getDocument());
                                testRecipes.add(recipe);
                                rvAdapter.notifyDataSetChanged();
                            }

                            if(dc.getType() == DocumentChange.Type.REMOVED){
                                Recipe recipe = createRecipe(dc.getDocument());
                                int position = testRecipes.indexOf(recipe);
                                testRecipes.remove(recipe);
                                rvAdapter.notifyItemRemoved(position);
                            }
                        }
                    }
                });
    }
}

