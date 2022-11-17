package com.git_er_done.cmput301f22t06_team_project.fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.ingredientCategories;
import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.testIngredients;
import static com.git_er_done.cmput301f22t06_team_project.models.Recipe.recipeCategories;
import static com.git_er_done.cmput301f22t06_team_project.models.Recipe.testRecipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.controllers.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipesDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Location;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.RecipeCategory;

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
    static Recipe si = null;
    static RecipesRecyclerViewAdapter rvAdapter = null;

    private EditText etTitle;
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
    private Button btnSave;

    private EditText addCategoryText;
    private Button addCategoryButton;
    private TextView addCategoryTitle;

    String title;
    String prep_time;
    String servings;
    String comments;
    String ingredients;
    String category;

    private static boolean isAddingNewRecipe = false;
    private static boolean isEdittingExistingRecipe = false;
    ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
    ArrayList<String> ingredientNames = new ArrayList<>(); // For ingredients that are in the recipe

    int SELECT_PICTURE = 200;

    /**
     * Empty constructor required
     */
    public RecipeAddEditDialogFragment(){
        //New instance static constructor below is called upon instantiation
    }

    /**
     * This method takes in a selected recipe and outputs a fragment with all the necessary information from the input
     * @param selectedRecipe of type recipe class
     * @return static instance of this dialog
     */
    public static RecipeAddEditDialogFragment newInstance(Recipe selectedRecipe, RecipesRecyclerViewAdapter adapter){
        //Assign local references to arguments passed to this fragment
        si = selectedRecipe;
        rvAdapter = adapter;

        RecipeAddEditDialogFragment frag = new RecipeAddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", selectedRecipe.getTitle());
        args.putString("comments", selectedRecipe.getComments());
        args.putString("servings", String.valueOf(selectedRecipe.getServings()));
        args.putString("prep_time", String.valueOf(selectedRecipe.getPrep_time()));
        args.putString("category", selectedRecipe.getCategory());

        for (RecipeIngredient i : selectedRecipe.getIngredients()){
            args.putString("ingredients", i.toString());
        }
        frag.setArguments(args);

        isEdittingExistingRecipe = true;
        return frag;
    }

    public static RecipeAddEditDialogFragment newInstance(RecipesRecyclerViewAdapter adapter){
        rvAdapter = adapter;
        RecipeAddEditDialogFragment frag = new RecipeAddEditDialogFragment();
        isAddingNewRecipe = true;
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

        attachLayoutViewsToLocalInstances(view);
        setupAdapters();

        ArrayAdapter<String> ingredientView = new ArrayAdapter<String>(getActivity(), R.layout.ingredient_listview, ingredientNames);
        // Test data
        ArrayList<String> ingredientStorage = new ArrayList<>(); // Ingredients that arent in the recipe (in the storage)
        ArrayList<String> ingredientUnit = new ArrayList<>(); // Ingredients that arent in the recipe (in the storage)
        RecipeIngredient appleRI = new RecipeIngredient("apple", "g", 2, "slice into eighths");
        RecipeIngredient orangeRI = new RecipeIngredient("orange", "g", 2, "take apart at its seams");
        RecipeIngredient grapeRI = new RecipeIngredient("grape", "g", 2, "remove tips");
        RecipeIngredient watermelonRI = new RecipeIngredient("watermelon", "g", 2, "slice into cubes");
        RecipeIngredient honeydewRI = new RecipeIngredient("honeydew", "g", 2, "slice into cubes");
        RecipeIngredient mangoRI = new RecipeIngredient("mango", "g", 2, "slice into cubes");

        recipeIngredients.add(appleRI);
        recipeIngredients.add(orangeRI);
        recipeIngredients.add(grapeRI);
        recipeIngredients.add(watermelonRI);
        recipeIngredients.add(honeydewRI);
        // Take in all the recipe ingredients and put them into a more readable format. probably a better way to do this.
        // TODO: get name of all ingredients from ingredient storage and put into "ingredientstorage"
        for (RecipeIngredient i : recipeIngredients) {
            ingredientNames.add(i.getName());

            ingredientUnit.add(i.getUnits());
        }

        ArrayAdapter<String> recipeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ingredientStorage);
        spIngredients_dropdown.setAdapter(recipeAdapter);
        IngredientDBHelper.setSpIngredientsDropDownAdapter(recipeAdapter,ingredientStorage); // Saheel did this


        if(isEdittingExistingRecipe) {
            Log.d(TAG, "AAAAA" + testIngredients);
            assignSelectedRecipeAttributesFromFragmentArgs();
            fillViewsWithSelectedRecipeAttributes();
            ArrayAdapter<String> recipeIngredientUnit = new ArrayAdapter<String>(getActivity(), R.layout.ingredient_listview, ingredientUnit);

            for (RecipeIngredient ingredient : recipeIngredients) {
                getArguments().getString(ingredient.getName(), "def - ingredients");
            }

            //TODO: make it that the dropdown shows all ingredients in the storage and have the list view show all current ingredients in the recipe


            lvIngredients_view.setAdapter(ingredientView);
            //lvIngredients_view.setAdapter(recipeIngredientUnit);
        }

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i) == "Add Category"){
                    addCategoryButton.setVisibility(View.VISIBLE);
                    addCategoryText.setVisibility(View.VISIBLE);
                    spCategory.setVisibility(View.INVISIBLE);

                    addCategoryButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String CategoryText = String.valueOf(addCategoryText.getText());
                            if (CategoryText != "Add Category") {
                                RecipeCategory.getInstance().addRecipeCategory(CategoryText);
                            }
                            addCategoryButton.setVisibility(View.INVISIBLE);
                            addCategoryText.setVisibility(View.INVISIBLE);
                            spCategory.setVisibility(View.VISIBLE);

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //IF WE ARE ADDING A NEW RECIPE - LEAVE INPUT FIELDS EMPTY TO SHOW HINTS

        //Buttons to cancel, save recipe, upload image, add ingredient
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
                isEdittingExistingRecipe = false;
                isAddingNewRecipe = false;
                dismiss();
            }
        });

        //TODO - Save the added/edited ingredient attributes to the selected instance
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO - Check that all the current entries are valid
                //TODO - Add prompt asking user if they're sure they want to save the new/eddited ingredient
                assignRecipeAttributesFromViews();

                if(isEdittingExistingRecipe) {
                    int selectedRecipeIndex = testRecipes.indexOf(si);
                    Recipe newRecipe = testRecipes.get(selectedRecipeIndex);
                    Recipe oldRecipe = new Recipe(
                            newRecipe.getTitle(),
                            newRecipe.getComments(),
                            newRecipe.getCategory(),
                            newRecipe.getPrep_time(),
                            newRecipe.getServings()

                            //newRecipe.getIngredients()
                    );
                    //RecipesDBHelper.searchForRecipe(oldRecipe.getTitle());
                    modifyRecipe(newRecipe);
                    RecipesDBHelper.modifyRecipeInDB(newRecipe,oldRecipe,selectedRecipeIndex);
                    //RecipesDBHelper.addRecipe(newRecipe);
                    isEdittingExistingRecipe = false;
                }

                if(isAddingNewRecipe){
                    addRecipe();
                    isAddingNewRecipe = false;
                }

                rvAdapter.notifyDataSetChanged();
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

    void addRecipe(){
        Recipe newRecipe = new Recipe(title, comments, category, Integer.parseInt(prep_time), Integer.parseInt(servings));
        RecipesDBHelper.addRecipe(newRecipe);
    }

    void modifyRecipe(Recipe recipe){
        Recipe modifiedRecipe = recipe;

        modifiedRecipe.setTitle(etTitle.getText().toString());
        modifiedRecipe.setComments(etComments.getText().toString());
        modifiedRecipe.setPrep_time(Integer.parseInt(String.valueOf(etPrep_time.getText())));
        modifiedRecipe.setServings(Integer.parseInt(String.valueOf(etServings.getText())));
        modifiedRecipe.setCategory(spCategory.getSelectedItem().toString());
        //modifiedRecipe.setRecipeIngredients(lvIngredients_view);
    }

    void assignRecipeAttributesFromViews(){
        title = String.valueOf(etTitle.getText());
        comments = String.valueOf(etComments.getText());
        prep_time = String.valueOf(etPrep_time.getText());
        servings = String.valueOf(etServings.getText());
        category = spCategory.getSelectedItem().toString();
        //ingredients = lvIngredients_view.getSelectedItem().toString();
    }

    void assignSelectedRecipeAttributesFromFragmentArgs(){
        //Set associate view items to attributes of selected recipe from view bundle
        title = getArguments().getString("title", "");
        prep_time = getArguments().getString("prep_time", "0");
        servings = getArguments().getString("servings", "0");
        comments = getArguments().getString("comments", "");
        ingredients = getArguments().getString("ingredients", "");
        category = getArguments().getString("category", "");

    }

    void fillViewsWithSelectedRecipeAttributes(){
        //Update editable attribute views with values of selected recipe instances
        etTitle.setText(title);
        etServings.setText(String.valueOf(servings));
        etPrep_time.setText(String.valueOf(prep_time));
        etComments.setText(comments);

        spCategory.setSelection(recipeCategories.indexOf(category));
    }

    void attachLayoutViewsToLocalInstances(View view){
        etTitle = (EditText) view.findViewById(R.id.et_recipe_add_edit_name);
        etServings = (EditText) view.findViewById(R.id.et_recipe_add_edit_servings);
        etPrep_time = (EditText) view.findViewById(R.id.et_recipe_add_edit_preptime);
        etComments = (EditText) view.findViewById(R.id.et_recipe_add_edit_comments);
        //spCategory = view.findViewById(R.id.et_recipe_add_edit_category);
        lvIngredients_view = view.findViewById(R.id.lv_recipe_add_edit_ingredients_view);
        spCategory = view.findViewById(R.id.sp_recipe_add_edit_category);
        spIngredients_dropdown = view.findViewById(R.id.sp_recipe_add_edit_ingredients_dropdown);
        btnCancel = view.findViewById(R.id.btn_recipe_add_edit_cancel);
        btnSave = view.findViewById(R.id.btn_recipe_add_edit_save);
        btnUpload = view.findViewById(R.id.btn_recipe_add_edit_upload);
        btnAddIngredient = view.findViewById(R.id.btn_recipe_add_edit_add_ingredient);
        // stuff for photos
        btnUpload = view.findViewById(R.id.btn_recipe_add_edit_upload);
        view_recipe_image = view.findViewById(R.id.view_recipe_image);

        addCategoryText = view.findViewById((R.id.addCategory));
        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryTitle = view.findViewById(R.id.categoryTitle);
    }

    void setupAdapters(){
        ArrayAdapter<String> categorySpinnerAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, recipeCategories);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCategory.setAdapter(categorySpinnerAdapter);
    }

}

