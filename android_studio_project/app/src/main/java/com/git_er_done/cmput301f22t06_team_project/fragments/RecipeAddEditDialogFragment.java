package com.git_er_done.cmput301f22t06_team_project.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.RecipesRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.controllers.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;

import java.util.ArrayList;

//https://guides.codepath.com/android/using-dialogfragment  helpful resource

/**
 * @Author Kevin Shao
 * @See IngredientAddEditDialogFragment for similar implementation
 * @Version 1.0
 * @Function This class, like the name of the class says, gives functionality to the user to add, edit and delete recipes.
 * ALL fragment dialog items MUST be from the androidx.fragment.app namespace !
 */
public class RecipeAddEditDialogFragment extends DialogFragment {
    private EditText etName;
    private EditText etComments;
    private EditText etServings;
    private EditText etPrep_time;
    private ListView lvIngredients_view;
    private Spinner spCategory;
    private Spinner spIngredients_dropdown;
    private ImageView view_recipe_image;
    private Button btnAddIngredient;
    private Button btnUpload;
    private Button btnCancel;
    private Button btnDelete;
    private Button btnSave;

    int SELECT_PICTURE = 200;
    public RecipeAddEditDialogFragment(){
        //Empty constructor required. New instance static constructor below is called upon instantiation
    }

    /**
     * This method takes in a selected recipe and outputs a fragment with all the necessary information from the input
     * @param title of type string
     * @param selectedRecipe of type recipe class
     * @return the fragment that has all the recipe information
     */
    public static RecipeAddEditDialogFragment newInstance(String title, Recipe selectedRecipe){
        RecipeAddEditDialogFragment frag = new RecipeAddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", selectedRecipe.getTitle());
        args.putString("servings", String.valueOf(selectedRecipe.getServings()));
        args.putString("prep_time", String.valueOf(selectedRecipe.getPrep_time()));
        args.putString("category", selectedRecipe.getCategory());

        for (RecipeIngredient i : selectedRecipe.getIngredients()){
            args.putString("ingredients", i.toString());
        }
        frag.setArguments(args);
        return frag;
    }

    /**
     * This method inflates the fragment xml for visual representation by reading the file and makes it visible
     * @param inflater of type layoutinflator
     * @param container of type viewgroup
     * @param savedInstanceState of type bundle
     * @return the visible object that is read from the xml file
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_recipe_add_edit_dialog, container);
    }

    /**
     *This method creates all the variables and puts each piece of information into its spot in the xml
     * @param view of type view
     * @param savedInstanceState of type bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Get associated field from view items
        etName = (EditText) view.findViewById(R.id.et_recipe_add_edit_name);
        etServings = (EditText) view.findViewById(R.id.et_recipe_add_edit_servings);
        etPrep_time = (EditText) view.findViewById(R.id.et_recipe_add_edit_preptime);
        etComments = (EditText) view.findViewById(R.id.et_recipe_add_edit_comments);
        lvIngredients_view = view.findViewById(R.id.lv_recipe_add_edit_ingredients_view);
        spCategory = view.findViewById(R.id.sp_recipe_add_edit_category);
        spIngredients_dropdown = view.findViewById(R.id.sp_recipe_add_edit_ingredients_dropdown);
        btnCancel = view.findViewById(R.id.btn_recipe_add_edit_cancel);
        btnDelete = view.findViewById(R.id.btn_recipe_add_edit_delete);
        btnSave = view.findViewById(R.id.btn_recipe_add_edit_save);
        btnUpload = view.findViewById(R.id.btn_recipe_add_edit_upload);
        btnAddIngredient = view.findViewById(R.id.btn_recipe_add_edit_add_ingredient);
        // stuff for photos
        btnUpload = view.findViewById(R.id.btn_recipe_add_edit_upload);
        view_recipe_image = view.findViewById(R.id.view_recipe_image);

        String dialogTitle = getArguments().getString("title", "Default title ");

        //Set associate view items to attributes of selected ingredient
        String title = getArguments().getString("title", "");
        String prep_time = getArguments().getString("prep_time", "0");
        String servings = getArguments().getString("servings", "0");
        String comments = getArguments().getString("comments", "");
        String ingredients = getArguments().getString("ingredients", "");

        // Test data
        ArrayList<Recipe> dummyArray = new ArrayList<>();

        RecipesRecyclerViewAdapter dummyAdapter = new RecipesRecyclerViewAdapter(dummyArray, (RecipesRecyclerViewInterface) this);
        ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
        ArrayList<String> ingredientNames = new ArrayList<>(); // For ingredients that are in the recipe
        IngredientDBHelper ingredientDBHelper = new IngredientDBHelper();

        //ingredientDBHelper.addIngredient(dummyAdapter, recipeIngredients);
        //ArrayList<String> ingredientStorage = dummyAdapter.mRecipes; // Ingredients that arent in the recipe (in the storage)
        ArrayList<String> ingredientStorage = new ArrayList<>(); // Ingredients that arent in the recipe (in the storage)
        ArrayList<String> ingredientUnit = new ArrayList<>(); // Ingredients that arent in the recipe (in the storage)
        RecipeIngredient appleRI = new RecipeIngredient("apple","g",2, "slice into eighths");
        RecipeIngredient orangeRI = new RecipeIngredient("orange","g", 2, "take apart at its seams");
        RecipeIngredient grapeRI = new RecipeIngredient("grape","g", 2, "remove tips");
        RecipeIngredient watermelonRI = new RecipeIngredient("watermelon","g", 2, "slice into cubes");
        RecipeIngredient honeydewRI = new RecipeIngredient("honeydew","g", 2, "slice into cubes");
        RecipeIngredient mangoRI = new RecipeIngredient("mango","g", 2, "slice into cubes");

        recipeIngredients.add(appleRI);
        recipeIngredients.add(orangeRI);
        recipeIngredients.add(grapeRI);
        recipeIngredients.add(watermelonRI);
        recipeIngredients.add(honeydewRI);

        // Take in all the recipe ingredients and put them into a more readable format. probably a better way to do this.
        // TODO: get name of all ingredients from ingredient storage and put into "ingredienstorage"
        for (RecipeIngredient i : recipeIngredients){
            ingredientNames.add(i.getName());
            ingredientStorage.add(i.getName());
            ingredientUnit.add(i.getUnits());
        }
        ingredientStorage.add(mangoRI.getName());

        ArrayAdapter<String> recipeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ingredientStorage);
        ArrayAdapter<String> ingredientView = new ArrayAdapter<String>(getActivity(), R.layout.ingredient_listview, ingredientNames);
        ArrayAdapter<String> recipeIngredientUnit = new ArrayAdapter<String>(getActivity(), R.layout.ingredient_listview, ingredientUnit);

        for (RecipeIngredient ingredient: recipeIngredients){
            getArguments().getString(ingredient.getName(), "def - ingredients");
        }

        //TODO: make it that the dropdown shows all ingredients in the storage and have the list view show all current ingredients in the recipe
        etName.setText(title);
        etServings.setText(servings);
        etPrep_time.setText(prep_time);
        etComments.setText(comments);
        spIngredients_dropdown.setAdapter(recipeAdapter);
        lvIngredients_view.setAdapter(ingredientView);
        //lvIngredients_view.setAdapter(recipeIngredientUnit);
        //set category

        //IF WE ARE ADDING A NEW RECIPE - LEAVE INPUT FIELDS EMPTY TO SHOW HINTS

        //Buttons to cancel, save recipe, upload image, add ingredient and delete recipe
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = spIngredients_dropdown.getSelectedItem().toString();
                ingredientNames.add(selected);
                ingredientView.notifyDataSetChanged();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //TODO - Implement the delete button to remove the recipe
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        //TODO - Save the added/edited ingredient attributes to the selected instance
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                dismiss();
            }
        });
    }

    /**
     * This function is triggered when the select image button is clicked.
     * It then opens up the gallery and prompts the user to choose an image.
     */
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    /**
     * This method is triggered when the user selects an image from the image chooser
     * It then sets the image view image.
     * @param requestCode of type int
     * @param resultCode of type int
     * @param data of type intent
     */
    // TODO: Remove deprecated code for something more up to date
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    view_recipe_image.setImageURI(selectedImageUri);
                }
            }
        }
    }
}
