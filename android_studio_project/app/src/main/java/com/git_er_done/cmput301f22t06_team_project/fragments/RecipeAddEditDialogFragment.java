package com.git_er_done.cmput301f22t06_team_project.fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.text.TextUtils.isEmpty;
import static com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe.recipeCategories;

import android.Manifest;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.adapters.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.adapters.RecipeIngredientsViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.adapters.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.callbacks.SwipeToDeleteIngredientCallback;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.UserDefinedDBHelper;
import com.git_er_done.cmput301f22t06_team_project.interfaces.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
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
    private Button openIngredientButton;

    RecipeIngredientsViewAdapter recipeIngredientsViewAdapter;

    ArrayList<Ingredient> ingredientStorage;

    ArrayList<String> ingredientNames;

    private EditText addCategoryText;
    private Button addCategoryButton;
    private Button deleteCategoryButton;
    private TextView addCategoryTitle;
    private Button editCategoryButton;
    ArrayAdapter<String> recipeAdapter;

    String title;
    String prep_time;
    String servings;
    String comments;
    String ingredients;
    String category;
    String imageURI;
    String categoryText;

    private static boolean isAddingNewRecipe = false;
    private static boolean isEdittingExistingRecipe = false;
    ArrayList<Ingredient> recipeIngredients = new ArrayList<>();

    int SELECT_PICTURE = 200;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    Context context;
    Bitmap imageBitmap = null;

    RecyclerView rvIngredients;

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

        for (Ingredient i : selectedRecipe.getIngredients()){
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
        editUserDefinedStuff(addCategoryButton, deleteCategoryButton, editCategoryButton, addCategoryText);

        if(isEdittingExistingRecipe) {
            assignSelectedRecipeAttributesFromFragmentArgs();
            fillViewsWithSelectedRecipeAttributes();
            for (Ingredient i: si.getIngredients()){
                recipeIngredients.add(i);
            }
            recipeIngredientsViewAdapter.notifyDataSetChanged();
            etTitle.setEnabled(false);
        }
        else{
            spCategory.setSelection(1);
        }

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

        //Buttons to cancel, save recipe, upload image, add ingredient, add/delete recipe categories
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(spCategory.getAdapter().getCount() > 1){
                            String toDelete = (String) adapterView.getItemAtPosition(i);
                            UserDefinedDBHelper.deleteUserDefined(toDelete, "recipeCategory", 0);
                            //This changes the dropdown value to something that isn't currently selected.
                            if (i == 0) {
                                spCategory.setSelection(spCategory.getAdapter().getCount() - 1); // Last value in the list
                            }
                            else{
                                spCategory.setSelection(0);
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "There must be atleast one category left.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryText = String.valueOf(addCategoryText.getText());
                if (isEmpty(categoryText)){
                    Toast.makeText(getActivity(), "You can't add an empty category", Toast.LENGTH_LONG).show();
                }
                else if (!checkDuplicateInRecyclerView()) {
                    UserDefinedDBHelper.addUserDefined(categoryText, "recipeCategory");
                    addCategoryText.setText("");
                }
            }
        });

        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer index = spIngredients_dropdown.getSelectedItemPosition();
                Ingredient newIngredient = ingredientStorage.get(index).clone();
                newIngredient.setDesc(" ");
                newIngredient.setAmount(0);
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO - Add prompt asking user if they're sure they want to save the new/eddited ingredient
                assignRecipeAttributesFromViews();
                int selectedRecipeIndex = rvAdapter.getRecipesList().indexOf(si);
                if (!checkExceptions()){
                    if(isEdittingExistingRecipe) {
                        Recipe oldRecipe = rvAdapter.getRecipesList().get(selectedRecipeIndex);
                        Recipe newRecipe = modifiedRecipe();
                        RecipeDBHelper.modifyRecipeInDB(newRecipe,oldRecipe, selectedRecipeIndex);
                        isEdittingExistingRecipe = false;
                        dismiss();
                    }

                    if(isAddingNewRecipe){
                        if (!checkDuplicateInRecyclerView()){
                            Recipe newRecipe = modifiedRecipe();
                            // Still need to add recipeIngredients here somehow
                            RecipeDBHelper.addRecipe(newRecipe);
                            if (imageBitmap == null) {
                                newRecipe.setImage(" ");
                            } else {
                                encodeBitmapAndSaveToFirebase(imageBitmap);
                            }
                            isAddingNewRecipe = false;
                            dismiss();
                        }
                    }
                }
            }
        });

        openIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientsRecyclerViewAdapter ivAdapter = new IngredientsRecyclerViewAdapter(new IngredientsRecyclerViewInterface() {
                    @Override
                    public void onItemLongClick(int position) {

                    }

                    @Override
                    public void onItemDeleted(Ingredient ing, int position) {

                    }
                });
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                IngredientAddEditDialogFragment editNameDialogFragment =
                        IngredientAddEditDialogFragment.newInstance(
                                ivAdapter);
                editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");
                ingredientNames.clear();
                for(Ingredient i: ingredientStorage){
                    ingredientNames.add(i.getName());
                }
                recipeAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     *  This function checks to see if the inputted title exists already in the recyclerview and outputs a toast if there exists one already
     *  It will also check the inputted recipe category for existing values
     * @return True if the title inputted value exists in the database, false otherwise
     */
    boolean checkDuplicateInRecyclerView(){
        for (Recipe i : rvAdapter.getRecipesList()){ // Checks to see if there exists an ingredient of the same name already
            if (i.getTitle().equals(etTitle.getText().toString()) && !isEdittingExistingRecipe) {
                Toast.makeText(getActivity(), "A recipe of the same name exists already.", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        //Checks for duplicate recipe categories
        for (String i : recipeCategories){
            if (categoryText.equalsIgnoreCase(i)){
                Toast.makeText(getActivity(), "A recipe category of the same name exists already.", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the title is empty, if the preptime or servings is 0. If they are, output a toast/
     * @return - returns true if the title is empty or the preptime/servings is 0. False otherwise.
     */
    boolean checkExceptions(){
        if (isEmpty(etTitle.getText())){
            Toast.makeText(getActivity(), "The title can't be empty", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (etPrep_time.getText().toString().equals(Character.toString('0'))){
            Toast.makeText(getActivity(), "Preptime has to be greater than 0.", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (etServings.getText().toString().equals(Character.toString('0'))){
            Toast.makeText(getActivity(), "Servings has to be greater than 0.", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * set OnClickListeners for each of the 'edit' buttons. All of these buttons will make their respective
     * add and delete buttons, along with editText boxes appear.
     * @param addButton - the button to add a recipe category to the db
     * @param deleteButton - the button to delete a recipe category from the db
     * @param editButton - the edit button to show the add/delete/text
     * @param addText - the text to go into the db after clicking the add button
     */
    void editUserDefinedStuff(Button addButton, Button deleteButton, Button editButton, EditText addText){
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addButton.getVisibility() == View.VISIBLE){
                    addButton.setVisibility(View.GONE);
                    addText.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.GONE);
                }
                else{
                    addButton.setVisibility(View.VISIBLE);
                    addText.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                }
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
                encodeBitmapAndSaveToFirebase(imageBitmap);
            }

            if (requestCode == SELECT_PICTURE) {
                // Get the uri of the image from data
                Uri selectedImageUri = data.getData();
                // update the preview image in the layout
                view_recipe_image.setImageURI(selectedImageUri);
                imageBitmap = decodeUriAsBitmap(context, selectedImageUri);
                encodeBitmapAndSaveToFirebase(imageBitmap);
                Log.d(TAG, "SELECTED A PICTURE" + imageBitmap);

            }
        }
    }

    /**
     * This function takes in the uri from the selected image from the gallery and coneverts it to a bitmap
     * @param context - used to access the standard common resources needed to recode the uri
     * @param uri this is the uri of the image.
     * @return Returns the converted bitmap so it can be used later on.
     */
    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                    bitmap, 300, 300, false);
            bitmap = resizedBitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * This function takes in a bitmap and then adds it to the database by converting it back to a uri
     * @param bitmap - the image bitmap to use so the function can convert it to a URI to pass into the db
     */
    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        imageURI = imageEncoded;
    }

    /**
     * This function will check the camera permissions of the program. If the persmissions are accepted, it will open the camera
     * @param permission - used to check whether or not the permissions is accepted or not
     * @param requestCode - the camera request code
     */
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(requireActivity(), new String[] { permission }, requestCode);
        }
        else {
            camera();
        }
    }

    /**
     * This function is called when the user accepts or decline the permission.
     * Request Code is used to check which permission called this function.
     * This request code is provided when the user is prompt for permission.
     * @param requestCode - this code tells the function which permission to ask and check
     * @param permissions - A list of strings to hold all the permissions
     * @param grantResults - shows the results after the permissions are granted or not
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(getActivity(), "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }

    /**
     * Creates a new modified recipe and puts values inputted from the fragment into the recipe.
     * @return the new created recipe.
     */
    Recipe modifiedRecipe(){
        Recipe modifiedRecipe = new Recipe(
                etTitle.getText().toString(),
                (etComments.getText().toString()),
                spCategory.getSelectedItem().toString(),
                Integer.parseInt(String.valueOf(etPrep_time.getText())),
                Integer.parseInt(String.valueOf(etServings.getText())),
                imageURI);

        ArrayList<Ingredient> modifiedRecipeIngredients = recipeIngredientsViewAdapter.getRecipeIngredients();
        modifiedRecipe.setImage(imageURI);
        modifiedRecipe.setIngredientsList(modifiedRecipeIngredients);
        return modifiedRecipe;
    }

    /**
     * Assigns all the values from the fragment views into strings
     */
    void assignRecipeAttributesFromViews(){
        title = String.valueOf(etTitle.getText());
        comments = String.valueOf(etComments.getText());
        prep_time = String.valueOf(etPrep_time.getText());
        servings = String.valueOf(etServings.getText());
        category = spCategory.getSelectedItem().toString();
      }

    /**
     * Assigns all selected recipe attributes from the fragment args and puts them into variables
     */
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

    /**
     * This function fills the views with the selected reipe attributes.
     */
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

    /**
     *  This function will attach all the layout views to local instances
     * @param view - this is the fragment to get all the layout views to attach to local variables
     */
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
        btnUpload = view.findViewById(R.id.btn_recipe_add_edit_upload);
        view_recipe_image = view.findViewById(R.id.view_recipe_image);
        btnCamera = view.findViewById(R.id.btn_recipe_add_edit_camera);

        addCategoryText = view.findViewById((R.id.addCategory));
        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryTitle = view.findViewById(R.id.categoryTitle);
        deleteCategoryButton = view.findViewById(R.id.deleteCategoryButton);
        editCategoryButton = view.findViewById(R.id.editCategoryButton);
        openIngredientButton = view.findViewById(R.id.openIngredientAddPage);
    }

    /**
     * This function sets up all the adapters by getting them first from the layout view and then assigns them to different things.
     */
    void setupAdapters(){
        context = this.getContext();
        ingredientStorage = IngredientDBHelper.getIngredientsFromStorage();
        ArrayAdapter<String> categorySpinnerAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, recipeCategories);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categorySpinnerAdapter);
        UserDefinedDBHelper.setupSnapshotListenerForRecipeCategoryAdapter(categorySpinnerAdapter);
        UserDefinedDBHelper.addUserDefined("to delete", "recipeCategory");
        UserDefinedDBHelper.deleteUserDefined("to delete", "recipeCategory", 0);

        ingredientNames = new ArrayList<>();
        for (Ingredient i: ingredientStorage){
            ingredientNames.add(i.getName());
        }
        recipeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ingredientNames);
        spIngredients_dropdown.setAdapter(recipeAdapter);
        recipeIngredientsViewAdapter = new RecipeIngredientsViewAdapter(recipeIngredients,context);
        lvIngredients_view.setAdapter(recipeIngredientsViewAdapter);

    }

}

