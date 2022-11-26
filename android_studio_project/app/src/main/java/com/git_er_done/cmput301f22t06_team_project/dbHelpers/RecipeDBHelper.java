package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.adapters.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.adapters.RecipeIngredientsViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.adapters.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeIngredient;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.git_er_done.cmput301f22t06_team_project.fragments.RecipesFragment;

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
public class RecipeDBHelper {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static final CollectionReference recipesDB = db.collection("Recipes");
    private static int selectedRecipePos;
    private static ArrayList<Recipe> recipesInStorage= new ArrayList<>();
    private static RecipeDBHelper singleInstance = null;

    private RecipeDBHelper() {
        setupSnapshotListenerForLocalRecipeStorage();
    }

    // Static method to create instance of Singleton class
    public static RecipeDBHelper getInstance()
    {
        if (singleInstance == null)
            singleInstance = new RecipeDBHelper();
        return singleInstance;
    }

    public static ArrayList<Recipe> getRecipesFromStorage(){
        return recipesInStorage;
    }

    /**
     * This method add a recipe to our recipe data base
     * @param recipe of type {@link Recipe}
     * returns void
     * @see IngredientDBHelper
     * @see MealPlannerDBHelper
     */
    public static void addRecipe(Recipe recipe){
        HashMap<String, String> sendToDb = new HashMap<>();

        String title = recipe.getTitle();
        String comments = recipe.getComments();
        String category = recipe.getCategory();
        String prepTime = String.valueOf(recipe.getPrep_time());
        String servings = String.valueOf(recipe.getServings());
        String image = recipe.getImage();
        String firstField = comments + "|" + category+ "|" + prepTime + "|" + servings;

        sendToDb.put("details", firstField);

        sendToDb.put("image", recipe.getImage());

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
                        Log.d(TAG, "Data could not be added!" + e.toString());
                    }
                });
    }

    public static void addImageToDB(String imageURI, Recipe recipe){
        HashMap<String, String> sendToDb = new HashMap<>();
        sendToDb.put("image", imageURI);
        DocumentReference documentReference = recipesDB.document(recipe.getTitle());
        documentReference.update("image",imageURI);
    }

    /**
     * This delete a recipe from the Recipe data base by
     * taking a string argument to look for the document with that name
     * @param recipe of type {@link String}
     * returns void
     * @param position
     * @see IngredientDBHelper
     * @see MealPlannerDBHelper
     */
    public static void deleteRecipe(Recipe recipe, int position){
        String nameofRecipe = recipe.getTitle();
        selectedRecipePos = position;
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
                        Log.d(TAG, "Data could not be deleted!" + e.toString());
                    }
                });
    }

    public static void modifyRecipeInDB(Recipe newRecipe, Recipe oldRecipe, int pos){
        // Really scuffed way of doing this, but I couldn't think of a better way.
        selectedRecipePos = pos;
        String nameOfRecipe = oldRecipe.getTitle();
        DocumentReference dr = recipesDB.document(nameOfRecipe);
        String comments = newRecipe.getComments();
        String category = newRecipe.getCategory();
        String prepTime = String.valueOf(newRecipe.getPrep_time());
        String servings = String.valueOf(newRecipe.getServings());
        String image = newRecipe.getImage();
        String firstField = comments + "|" + category+ "|" + prepTime + "|" + servings;
        dr.update("details", firstField);
        dr.update("image", image);

        for (RecipeIngredient i: oldRecipe.getIngredients()){
            dr.update(i.getName(), FieldValue.delete());
        }

        String ingredientFields;
        for (RecipeIngredient i: newRecipe.getIngredients()) {
            String name = i.getName();
            String units = i.getUnits();
            String amount = String.valueOf(i.getAmount());
            String comment = i.getComment();
            ingredientFields = units + "|" + String.valueOf(amount) + "|" + comment;
            dr.update(name, ingredientFields);
        }

    }

    /**
     * This method is just a random method I made just in case we need to be able to look
     * for a specific recipe in the recipe Database but I haven't tested it nor is it being
     * used any at the moment
     * returns void
     * @see IngredientDBHelper
     * @see MealPlannerDBHelper
     */
    public static void setRecipeIngredientAdapter(String title, RecipeIngredientsViewAdapter adapter, ArrayList<RecipeIngredient> ingredientList) {
        recipesDB.document(title).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException error) {
                Recipe recipe = createRecipe(doc);
                ArrayList<RecipeIngredient> recipeIngredients = recipe.getIngredients();
                for (RecipeIngredient i: recipeIngredients){
                    ingredientList.add(i);
                }
                adapter.notifyDataSetChanged();

            }
        });
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
        Recipe recipe = new Recipe(title, comments,category,prepTime,servings);

        String image = fromDBbutString.remove("image");
        recipe.setImage(image);

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

    /**
     *  Called when the DBHelper singleton is instantiated - this happens in main activity onCreate().
     *  This ensures the private arraylist of recipes stored in this class is always up to date with
     *      the firestore DB.
     *  This has nothing to do with the recipeFragment recyclerview and does not rely on an adapter instance.
     */
    public void setupSnapshotListenerForLocalRecipeStorage(){
        db.collection("Recipes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("DB ERROR", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            Recipe recipe = createRecipe(dc.getDocument());
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                recipesInStorage.add(recipe);
                            }

                            if(dc.getType() == DocumentChange.Type.MODIFIED){
                                recipesInStorage.set(selectedRecipePos, recipe);
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

    /**
     * Sets up a snapshot listener to update the recipe recyclerview adapter accordingly. Because
     * it relies on an adapter instance this method is called in the RecipeFragment onCreateView method after the
     * associated RecyclerView adapter is instantiated and attached to the RecipesRecyclerView;
     * @param adapter Instance of the RecipesRecyclerViewAdapter that is to be updated via firebase snapshot listener api callbacks
     */
    public static void setupSnapshotListenerForRecipeRVAdapter(RecipesRecyclerViewAdapter adapter){
        db.collection("Recipes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("DB ERROR", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            Recipe recipe = createRecipe(dc.getDocument());
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                adapter.addRecipe(recipe);
                            }

                            if(dc.getType() == DocumentChange.Type.MODIFIED){
                                adapter.modifyRecipe(recipe, selectedRecipePos);
                            }

                            if(dc.getType() == DocumentChange.Type.REMOVED){
                                int position = adapter.getRecipesList().indexOf(recipe);
                                //If the rvAdapter returns a valid position
                                if(position != -1){
                                    adapter.deleteRecipe(position);
                                }
                                else{
                                    Log.e("DB ERROR", "ERROR REMOVING RECIPE FROM RECYCLERVIEW ADAPTER");
                                }
                            }
                        }

                        if (adapter.getRecipesList().size() == recipesInStorage.size() ){
                            RecipesFragment.stopRecipesFragmentProgressBar();
                        }
                    }
                });
    }

}

