package com.git_er_done.cmput301f22t06_team_project.fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe.recipeCategories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.git_er_done.cmput301f22t06_team_project.adapters.RecipeIngredientsViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.adapters.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipesDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeCategory;

import java.io.ByteArrayOutputStream;
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
    private Button btnCamera;
    private Button btnCancel;
    private Button btnSave;

    RecipeIngredientsViewAdapter recipeIngredientsViewAdapter;

    private EditText addCategoryText;
    private Button addCategoryButton;
    private TextView addCategoryTitle;

    String title;
    String prep_time;
    String servings;
    String comments;
    String ingredients;
    String category;
    String imageURI;

    private static boolean isAddingNewRecipe = false;
    private static boolean isEdittingExistingRecipe = false;
    ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
    ArrayList<String> ingredientNames = new ArrayList<>(); // For ingredients that are in the recipe

    int SELECT_PICTURE = 200;
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_IMAGE_CAPTURE = 111;

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
        args.putString("image", selectedRecipe.getImage());

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
        Log.d(TAG, "ADADADAD" + imageURI);

        attachLayoutViewsToLocalInstances(view);
        setupAdapters();

        ArrayList<String> ingredientStorage = new ArrayList<>(); // Ingredients that arent in the recipe (in the storage)
        ArrayAdapter<String> recipeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ingredientStorage);

        spIngredients_dropdown.setAdapter(recipeAdapter);
        IngredientDBHelper.setSpIngredientsDropDownAdapter(recipeAdapter,ingredientStorage); // Saheel did this

        recipeIngredientsViewAdapter = new RecipeIngredientsViewAdapter(recipeIngredients,getContext()); // Saheel did this
        lvIngredients_view.setAdapter(recipeIngredientsViewAdapter);
        Log.d(TAG, "ADADADAD" + imageURI);
        if(isEdittingExistingRecipe) {
            assignSelectedRecipeAttributesFromFragmentArgs();
            fillViewsWithSelectedRecipeAttributes();
            RecipesDBHelper.setRecipeIngredientAdapter(title, recipeIngredientsViewAdapter, recipeIngredients);
            etTitle.setEnabled(false);
        }
        else{
            spCategory.setSelection(1);
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
        lvIngredients_view.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //Buttons to cancel, save recipe, upload image, add ingredient
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = spIngredients_dropdown.getSelectedItem().toString();
                String[] nameUnit = selected.split(", ");
                RecipeIngredient newIngredient = new RecipeIngredient(nameUnit[0],nameUnit[1], 0, "comment");
                recipeIngredients.add(newIngredient);
                recipeIngredientsViewAdapter.notifyDataSetChanged();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera();
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
                    int selectedRecipeIndex = rvAdapter.getRecipesList().indexOf(si);
                    Recipe oldRecipe = rvAdapter.getRecipesList().get(selectedRecipeIndex);
                    //RecipesDBHelper.deleteRecipe(oldRecipe);
                    Recipe newRecipe = modifiedRecipe();
                    RecipesDBHelper.addRecipe(newRecipe);
                    isEdittingExistingRecipe = false;
                }

                if(isAddingNewRecipe){
                    Recipe newRecipe = new Recipe(title, comments, category, Integer.parseInt(prep_time), Integer.parseInt(servings));
                    // Still need to add recipeIngredients here somehow
                    RecipesDBHelper.addRecipe(newRecipe);
                    isAddingNewRecipe = false;
                }

                rvAdapter.notifyDataSetChanged();
                dismiss();
            } // broke here
        });
    }

    /**
     * This function is triggered when the select image button is clicked.
     * It then opens up the gallery and prompts the user to choose an image.
     */
    void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    /**
     * This function is triggered when the camera button is clicked.
     * It then opens up the camera and prompts the user to take a picture.
     */
    void camera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, SELECT_PICTURE);
    }

    /**
     * This method is triggered when the user selects an image from the image chooser
     * It then sets the image view image.
     * @param requestCode of type int
     * @param resultCode of type int
     * @param data of type intent
     */
    // TODO: Remove deprecated code for something more up to date
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            view_recipe_image.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

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

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        int selectedRecipeIndex = rvAdapter.getRecipesList().indexOf(si);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        imageURI = imageEncoded;
        RecipesDBHelper.addImageToDB(imageEncoded, rvAdapter.getRecipesList().get(selectedRecipeIndex));
    }

    Recipe modifiedRecipe(){
        Recipe modifiedRecipe = new Recipe(
                etTitle.getText().toString(),
                (etComments.getText().toString()),
                spCategory.getSelectedItem().toString(),
                Integer.parseInt(String.valueOf(etPrep_time.getText())),
                Integer.parseInt(String.valueOf(etServings.getText())));

        ArrayList<RecipeIngredient> modifiedRecipeIngredients = new ArrayList<>();
        for (int i = 0; i < lvIngredients_view.getChildCount(); i++) {
            View child = lvIngredients_view.getChildAt(i);
            TextView name = child.findViewById(R.id.name_of_ingredient);
            String nameString = name.getText().toString();

            EditText comment = child.findViewById(R.id.comment_of_ingredient);
            String commentString = comment.getText().toString();

            TextView amount = child.findViewById(R.id.amount_of_ingredient);
            String amountString = amount.getText().toString();
            Integer amountInt = Integer.parseInt(amountString);

            EditText unit = child.findViewById(R.id.unit_of_ingredient);
            String unitString = unit.getText().toString();

            RecipeIngredient modifiedRecipeIngredient = new RecipeIngredient(nameString, unitString, amountInt, commentString);
            modifiedRecipeIngredients.add(modifiedRecipeIngredient);
        }
        modifiedRecipe.setImage(imageURI);
        modifiedRecipe.setIngredientsList(modifiedRecipeIngredients); // Saheel
        return modifiedRecipe;
    }

    void assignRecipeAttributesFromViews(){
        title = String.valueOf(etTitle.getText());
        comments = String.valueOf(etComments.getText());
        prep_time = String.valueOf(etPrep_time.getText());
        servings = String.valueOf(etServings.getText());
        category = spCategory.getSelectedItem().toString();
      }

    void assignSelectedRecipeAttributesFromFragmentArgs(){
        //Set associate view items to attributes of selected recipe from view bundle
        title = getArguments().getString("title", "");
        prep_time = getArguments().getString("prep_time", "1");
        servings = getArguments().getString("servings", "1");
        comments = getArguments().getString("comments", "aaaaa");
        ingredients = getArguments().getString("ingredients", "");
        category = getArguments().getString("category", "");
        imageURI = getArguments().getString("image", "");
    }

    void fillViewsWithSelectedRecipeAttributes(){
        //Update editable attribute views with values of selected recipe instances
        etTitle.setText(title);
        etServings.setText(String.valueOf(servings));
        etPrep_time.setText(String.valueOf(prep_time));
        etComments.setText(comments);
        spCategory.setSelection(recipeCategories.indexOf(category));
        byte [] encodeByte = Base64.decode(imageURI,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        view_recipe_image.setImageBitmap(bitmap);
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
        btnCamera = view.findViewById(R.id.btn_recipe_add_edit_camera);
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

        ArrayList<String> ingredientStorage = new ArrayList<>(); // Ingredients that arent in the recipe (in the storage)
        ArrayAdapter<String> recipeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ingredientStorage);
        spIngredients_dropdown.setAdapter(recipeAdapter);
        IngredientDBHelper.setSpIngredientsDropDownAdapter(recipeAdapter,ingredientStorage); // Saheel did this

    }

}

