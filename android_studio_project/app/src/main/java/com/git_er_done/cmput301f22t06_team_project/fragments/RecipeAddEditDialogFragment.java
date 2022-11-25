package com.git_er_done.cmput301f22t06_team_project.fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.text.TextUtils.isEmpty;
import static com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe.recipeCategories;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.adapters.RecipeIngredientsViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.adapters.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeCategory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    Context context;
    Bitmap imageBitmap = null;

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

        attachLayoutViewsToLocalInstances(view);
        setupAdapters();
        context = this.getContext();

        ArrayList<String> ingredientStorage = new ArrayList<>(); // Ingredients that arent in the recipe (in the storage)
        ArrayAdapter<String> recipeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ingredientStorage);

        spIngredients_dropdown.setAdapter(recipeAdapter);
        IngredientDBHelper.setSpIngredientsDropDownAdapter(recipeAdapter,ingredientStorage); // Saheel did this

        recipeIngredientsViewAdapter = new RecipeIngredientsViewAdapter(recipeIngredients,getContext()); // Saheel did this
        lvIngredients_view.setAdapter(recipeIngredientsViewAdapter);
        if(isEdittingExistingRecipe) {
            assignSelectedRecipeAttributesFromFragmentArgs();
            fillViewsWithSelectedRecipeAttributes();
            for (RecipeIngredient i: si.getIngredients()){
                recipeIngredients.add(i);
            }
            recipeIngredientsViewAdapter.notifyDataSetChanged();
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
                RecipeIngredient newIngredient = new RecipeIngredient(nameUnit[0],nameUnit[1], 0, " ");
                recipeIngredients.add(newIngredient);
                recipeIngredientsViewAdapter.notifyDataSetChanged();
            }
        });

//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageChooser();
//            }
//        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
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
                boolean duplicate = false;
                int selectedRecipeIndex = rvAdapter.getRecipesList().indexOf(si);
                if (isEmpty(etTitle.getText())){
                    Toast.makeText(getActivity(), "Title can't be empty.", Toast.LENGTH_LONG).show();
                }
                else if (etPrep_time.getText().toString().equals(Character.toString('0'))){
                    Toast.makeText(getActivity(), "Preptime has to be greater than 0.", Toast.LENGTH_LONG).show();
                }
                else if (etServings.getText().toString().equals(Character.toString('0'))){
                    Toast.makeText(getActivity(), "Servings has to be greater than 0.", Toast.LENGTH_LONG).show();
                }
                else{
                    if(isEdittingExistingRecipe) {
                        Recipe oldRecipe = rvAdapter.getRecipesList().get(selectedRecipeIndex);
                        Recipe newRecipe = modifiedRecipe();
                        RecipeDBHelper.modifyRecipeInDB(newRecipe,oldRecipe, selectedRecipeIndex);
                        isEdittingExistingRecipe = false;

                        if (imageBitmap == null){
                            RecipeDBHelper.addImageToDB("", rvAdapter.getRecipesList().get(selectedRecipeIndex));
                        }
                        else{
                            encodeBitmapAndSaveToFirebase(imageBitmap);
                        }
                        dismiss();
                    }

                    if(isAddingNewRecipe){
                        for (Recipe i : rvAdapter.getRecipesList()){
                            if (i.getTitle().equals(etTitle.getText().toString())) {
                                duplicate = true;
                            }
                        }

                        if (duplicate){
                            Toast.makeText(getActivity(), "A recipe of the same name exists already.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Recipe newRecipe = new Recipe(title, comments, category, Integer.parseInt(prep_time), Integer.parseInt(servings));
                            // Still need to add recipeIngredients here somehow
                            if (imageBitmap == null) {
                                newRecipe.setImage("");
                            } else {
                                encodeBitmapAndSaveToFirebase(imageBitmap);
                            }
                            RecipeDBHelper.addRecipe(newRecipe);
                            isAddingNewRecipe = false;
                            dismiss();
                        }
                    }
                    rvAdapter.notifyDataSetChanged();
                    dismiss();
                }




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
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA_REQUEST) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                view_recipe_image.setImageBitmap(imageBitmap);
                Log.d(TAG, "TOOK A PICTURE" + imageBitmap);

            }

            if (requestCode == SELECT_PICTURE) {
                // Get the uri of the image from data
                Uri selectedImageUri = data.getData();
                // update the preview image in the layout
                view_recipe_image.setImageURI(selectedImageUri);
                imageBitmap = decodeUriAsBitmap(context, selectedImageUri);

                Log.d(TAG, "SELECTED A PICTURE" + imageBitmap);

            }

        }
    }

    /**
     * This function takes in the uri from the selected image from the gallery and coneverts it to a bitmap
     * @param context
     * @param uri this is the uri of the image.
     * @return Returns the converted bitmap so it can be used later on.
     */
    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * This function takes in a bitmap and then adds it to the database by converting it back to a uri
     * @param bitmap
     */
    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        int selectedRecipeIndex = rvAdapter.getRecipesList().indexOf(si);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        imageURI = imageEncoded;
        RecipeDBHelper.addImageToDB(imageEncoded, rvAdapter.getRecipesList().get(selectedRecipeIndex));
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(requireActivity(), new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(getActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
            camera();
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(getActivity(), "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }

    Recipe modifiedRecipe(){
        Recipe modifiedRecipe = new Recipe(
                etTitle.getText().toString(),
                (etComments.getText().toString()),
                spCategory.getSelectedItem().toString(),
                Integer.parseInt(String.valueOf(etPrep_time.getText())),
                Integer.parseInt(String.valueOf(etServings.getText())));

        ArrayList<RecipeIngredient> modifiedRecipeIngredients = recipeIngredientsViewAdapter.getRecipeIngredients();
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
        btnAddIngredient = view.findViewById(R.id.btn_recipe_add_edit_add_ingredient);
        // stuff for photos
//        btnUpload = view.findViewById(R.id.btn_recipe_add_edit_upload);
        view_recipe_image = view.findViewById(R.id.view_recipe_image);
        btnCamera = view.findViewById(R.id.btn_recipe_add_edit_camera);

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

