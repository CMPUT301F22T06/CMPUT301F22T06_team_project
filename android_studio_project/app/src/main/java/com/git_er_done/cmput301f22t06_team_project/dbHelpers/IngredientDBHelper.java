package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.adapters.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.fragments.IngredientsFragment;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
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
import java.util.Objects;

/**
 * DBHelper singleton class that provided methods to interact with the associated firestore db ingredient collection
 * Also provideds snapshot listeners that update the apps local storage (arraylist of ingredient items) and associated recyclerview adapters.
 */
public class IngredientDBHelper {

    private static FirebaseFirestore db;
    private static CollectionReference ingredientsDB;
    private static int selectedIngPos;
    private static IngredientDBHelper singleInstance = null;

    /**
     * Private constructor can only be called when an instance of this singleton is created
     */
    private IngredientDBHelper() {
        db = FirebaseFirestore.getInstance();
        ingredientsDB = db.collection("Ingredients");
        setupSnapshotListenerForLocalIngredientStorage();
    }

    /**
     * Static method to create instance of Singleton class
     * @return a single instance of a new ingredientDBHelper
     */
    public static IngredientDBHelper getInstance()
    {
        if (singleInstance == null)
            singleInstance = new IngredientDBHelper();
        return singleInstance;
    }

    private static ArrayList<Ingredient> ingredientsInStorage = new ArrayList<>();

    //NO SETTER  - only the snapshot listener callback will update local storage accordinly.
    //  Ingredients add/edit/ deleted will rely on the static DB helper methods which will
    //  result in the snapshot listeners updating the local storage
    public static ArrayList<Ingredient> getIngredientsFromStorage() {
        return ingredientsInStorage;
    }

    //TODO - Put newly added ingredients ontop of recyclerview top show user

    /**
     * This method adds an ingredient to our database in the incredient collection
     *
     * @param ingredient of type {@link Ingredient}
     * @returns void
     * @see MealDBHelper
     * @see RecipeDBHelper
     */
    public static void addIngredientToDB(Ingredient ingredient) {
        String name = ingredient.getName().toLowerCase();
        String desc = ingredient.getDesc().toLowerCase();
        String bestBefore = ingredient.getBestBefore().toString();
        String location = ingredient.getLocation().toLowerCase();
        String units = ingredient.getUnit();
        String category = ingredient.getCategory();
        Integer amount = ingredient.getAmount();
        String amountString = amount.toString();

        HashMap<String, Object> ingredientAttributes = new HashMap<>();

        ingredientAttributes.put("name", name);
        ingredientAttributes.put("description", desc);
        ingredientAttributes.put("best before", bestBefore);
        ingredientAttributes.put("location", location);
        ingredientAttributes.put("unit", units);
        ingredientAttributes.put("category", category);
        ingredientAttributes.put("amount", amountString);

        ingredientsDB
                .document(name)
                .set(ingredientAttributes)
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

    /**
     * Take a string and searches the ingredients database for it and deletes the document
     * with that name if it's found
     *
     * @param ingredient of type {@link String}
     * @returns void
     * @see MealDBHelper
     * @see RecipeDBHelper
     */
    public static void deleteIngredientFromDB(Ingredient ingredient, int position) {

        String nameOfIngredient = ingredient.getName();
        selectedIngPos = position;
        ingredientsDB
                .document(nameOfIngredient)
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
                        Log.d(TAG, "Data could not be deleted!" + e.toString());
                    }
                });
    }

    /**
     * Modifies the data of an ingredient that already exists in the database
     * @param newIngredient of type Ingredient
     * @param oldIngredient of type Ingredient
     * @param pos of type int
     */
    public static void modifyIngredientInDB(Ingredient newIngredient, Ingredient oldIngredient, int pos){
        String nameOfIngredient = oldIngredient.getName();
        selectedIngPos = pos;

        DocumentReference dr = ingredientsDB.document(nameOfIngredient);

        if (!Objects.equals(newIngredient.getDesc(), oldIngredient.getDesc())) {
            dr.update("description", newIngredient.getDesc());
        }

        if (!Objects.equals(newIngredient.getBestBefore().toString(), oldIngredient.getBestBefore().toString())) {
            dr.update("best before", newIngredient.getBestBefore().toString());
        }

        if (!Objects.equals(newIngredient.getLocation(), oldIngredient.getLocation())) {
            dr.update("location", newIngredient.getLocation());
        }

        if (!Objects.equals(newIngredient.getCategory(), oldIngredient.getCategory())) {
            dr.update("category", newIngredient.getCategory());
        }

        if (newIngredient.getBestBefore().compareTo(LocalDate.now()) < 0) {
            dr.update("amount", "0");
        }

        if (!Objects.equals(newIngredient.getAmount(), oldIngredient.getAmount())) {
            dr.update("amount", newIngredient.getAmount().toString());
        }

        if (!Objects.equals(newIngredient.getUnit(), oldIngredient.getUnit())) {
            dr.update("unit", newIngredient.getUnit());
        }
    }

    /**
     * This method take a document from firestore and takes the data then converts it into an Ingredient object
     * to return
     *
     * @param doc of type document snapshot
     * @return ingredient of type {@link Ingredient}
     * @see MealDBHelper
     * @see RecipeDBHelper
     */
    private static Ingredient createIngredient(DocumentSnapshot doc) {
        String name = doc.getId();
        String desc = (String) doc.getData().get("description");
        LocalDate bestBefore = LocalDate.parse((String) doc.getData().get("best before"));
        String location = (String) doc.getData().get("location");
        String unit = (String) doc.getData().get("unit");
        String category = (String) doc.getData().get("category");
        Integer amount = Integer.parseInt((String) doc.getData().get("amount"));

        Ingredient newIngredient = new Ingredient(name, desc, bestBefore, location, unit, category, amount);
        return newIngredient;
    }

    /**
     * Called when the DBHelper singleton is instantiated - this happens in main activity onCreate().
     * This ensures the private arraylist of ingredients stored in this class is always up to date with
     * the firestore DB.
     * This has nothing to do with the ingredientFragment recyclerview and does not rely on an adapter instance.
     */
    public void setupSnapshotListenerForLocalIngredientStorage() {
        db.collection("Ingredients")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("DB ERROR", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            Ingredient ingredient = createIngredient(dc.getDocument());
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                ingredientsInStorage.add(ingredient);
                            }

                            if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                ingredientsInStorage.set(selectedIngPos, ingredient);
                            }

                            if (dc.getType() == DocumentChange.Type.REMOVED) {
                                int position = ingredientsInStorage.indexOf(ingredient);
                                //If the rvAdapter returns a valid position
                                if (position != -1) {
                                    ingredientsInStorage.remove(position);
                                } else {
                                    Log.e("DB ERROR", "ERROR REMOVING INGREDIENT FROM STORAGE");
                                }
                            }
                        }
                    }
                });
    }

    /**
     * Sets up a snapshot listener to update the ingredient recyclerview adapter accordingly. Because
     * it relies on an adapter instance this method is called in the IngredientFragments onCreateView method after the
     * associated RecyclerView adapter is instantiated and attached to the ingredientsRecyclerView;
     *
     * @param adapter Instance of the IngredientRecyclerViewAdapter that is to be updated via firebase snapshot listener api callbacks
     */
    public static void setupSnapshotListenerForIngredientRVAdapter(IngredientsRecyclerViewAdapter adapter) {
        db.collection("Ingredients")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("DB ERROR", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            Ingredient ingredient = createIngredient(dc.getDocument());
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                adapter.addItem(ingredient);
                            }

                            if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                adapter.modifyIngredient(ingredient, selectedIngPos);
                            }

                            if (dc.getType() == DocumentChange.Type.REMOVED) {
                                int position = adapter.getIngredientsList().indexOf(ingredient);
                                //If the rvAdapter returns a valid position
                                if (position != -1) {
                                    adapter.deleteItem(position);
                                } else {
                                    Log.e("DB ERROR", "ERROR REMOVING INGREDIENT FROM RECYCLERVIEW ADAPTER");
                                }
                            }
                        }
                        IngredientsFragment.stopIngredientsFragmentProgressBar();
                    }
                });
    }

    /**
     * Checks and sets the amount of an ingredient to zero in the database if the ingredient is past its expiry data
     * It will then create a new ingredient and reupdate everything along with the amount to be 0
     */
    public static void setExpiredIngredientsAmountToZero(){
        LocalDate today = LocalDate.now();
        for (int i = 0; i < IngredientDBHelper.getIngredientsFromStorage().size(); i++) {
            Ingredient anIngredient = IngredientDBHelper.getIngredientsFromStorage().get(i);
            if (anIngredient.getBestBefore().compareTo(today) < 0) {
                Ingredient newIngredient = new Ingredient(anIngredient.getName(),
                        anIngredient.getDesc(),
                        anIngredient.getBestBefore(),
                        anIngredient.getLocation(),
                        anIngredient.getUnit(),
                        anIngredient.getCategory(),
                        0);
                IngredientDBHelper.modifyIngredientInDB(newIngredient, anIngredient, i);
            }
        }
    }
    /**
     * This function takes in a name of a ingredient and it will index through the ingredients in storage and return the found ingredients or -1 if it wasn't found.
     * @param ingredientName of type string
     * @return the location of the ingredient in the storage or -1 if it wasn't found
     */
    public static int getIndexOfIngredientFromName(String ingredientName) {
        for (Ingredient ingredient : ingredientsInStorage) {
            if (ingredient.getName().equals(ingredientName))
                return ingredientsInStorage.indexOf(ingredient);
        }
        return -1;
    }
}
