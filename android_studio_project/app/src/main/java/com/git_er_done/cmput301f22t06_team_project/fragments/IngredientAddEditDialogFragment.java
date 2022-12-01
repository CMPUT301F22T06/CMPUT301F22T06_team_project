package com.git_er_done.cmput301f22t06_team_project.fragments;

import static android.text.TextUtils.isEmpty;
import static com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient.ingredientCategories;
import static com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient.ingredientLocations;
import static com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient.ingredientUnits;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.adapters.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.UserDefinedDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import java.time.LocalDate;
import java.util.ArrayList;

//https://guides.codepath.com/android/using-dialogfragment  helpful resource

/**
 * Dialog fragment for adding and editting an ingredient item. Accessed from the Ingredient Fragment.
 * Extends DiaglogFragment - uses a fragment lifecycle instead of seperate dialog
 */
public class IngredientAddEditDialogFragment extends DialogFragment {

    static Ingredient si = null;
    static Ingredient shopi = null;
    static IngredientsRecyclerViewAdapter rvAdapter = null;

    private EditText etName;
    private EditText etDescription;
    private DatePicker dpBestBeforeDate;
    private Spinner spLocation;
    private EditText etAmount;
    private Spinner spUnit;
    private Spinner spCategory;
    private Button btnCancel;
    private Button btnSave;

    EditText addLocationText;
    Button addLocationButton;
    Button deleteLocationButton;
    Button editLocationButton;
    TextView displayLocationText;

    EditText addUnitText;
    Button addUnitButton;
    Button deleteUnitButton;
    Button editUnitButton;

    EditText addCategoryText;
    Button addCategoryButton;
    Button deleteCategoryButton;
    Button editCategoryButton;

    String name;
    String description;
    int amount;
    String location;
    String category;
    ArrayList<String> bestBeforeStringArray = new ArrayList<>();
    String unit;

    private static boolean isAddingNewIngredient = false;
    private static boolean isEdittingExistingIngredient = false;
    private static boolean isAddingFromShoppingList = false;

    /**
     * Empty constructor required
     */
    public IngredientAddEditDialogFragment(){}

    /**
     * Static constructor for a new instance of Ingredient add/edit dialog
     * Used in place of a traditional constructor
     * @param selectedIngredient - Instance of the selected ingredient item retreived from the Recycler View  adapter
     * @return static instance of this dialog
     */
    public static IngredientAddEditDialogFragment newInstance(Ingredient selectedIngredient, IngredientsRecyclerViewAdapter adapter){
        //Assign local references to arguments passed to this fragment
        si = selectedIngredient;
        rvAdapter = adapter;

        IngredientAddEditDialogFragment frag = new IngredientAddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("name",  selectedIngredient.getName());
        args.putString("description", selectedIngredient.getDesc());
        args.putStringArrayList("bestBeforeDate", selectedIngredient.getBestBeforeStringArrayList());
        args.putString("location", selectedIngredient.getLocation());
        args.putString("category", selectedIngredient.getCategory());
        args.putInt("amount", selectedIngredient.getAmount());
        args.putString("unit", selectedIngredient.getUnit());
        frag.setArguments(args);

        isEdittingExistingIngredient = true;
        return frag;
    }

    public static IngredientAddEditDialogFragment newInstance(IngredientsRecyclerViewAdapter adapter){
        rvAdapter = adapter;
        IngredientAddEditDialogFragment frag = new IngredientAddEditDialogFragment();
        isAddingNewIngredient = true;
        return frag;
    }


    public static IngredientAddEditDialogFragment newInstance(Ingredient selectedIngredient, Ingredient ingredientFromShoppingList, IngredientsRecyclerViewAdapter adapter){
        //Assign local references to arguments passed to this fragment
        shopi = ingredientFromShoppingList;
        si = selectedIngredient;
        rvAdapter = adapter;

        IngredientAddEditDialogFragment frag = new IngredientAddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("name",  selectedIngredient.getName());
        args.putString("description", selectedIngredient.getDesc());
        args.putStringArrayList("bestBeforeDate", selectedIngredient.getBestBeforeStringArrayList());
        args.putString("location", selectedIngredient.getLocation());
        args.putString("category", selectedIngredient.getCategory());
        args.putInt("amount", 0);
        args.putString("unit", selectedIngredient.getUnit());
        frag.setArguments(args);

        isAddingFromShoppingList = true;
        return frag;
    }


    /**
     * Returns an inflated instance of this dialog fragments XML layout
     * @param inflater of type layoutinflator
     * @param container of type viewgroup
     * @param savedInstanceState of type bundle
     * @return View - Inflated dialog fragment layout
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_ingredient_add_edit_dialog, container);
    }

    //TODO - Modify dialog title to show if user is editting or adding new ingredient
    /**
     * Called upon creation of the dialogFragment view.
     * View item variables are assigned to their associated xml view items.
     * During an add, each field is left empty.
     * During an edit, each field is filled in with the associated parameters of the selected ingredient item
     * @param view of type view
     * @param savedInstanceState of type bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachLayoutViewsToLocalInstances(view);
        setupAdapters();

        if(isEdittingExistingIngredient) {
            assignSelectedIngredientAttributesFromFragmentArgs();
            fillViewsWithSelectedIngredientAttributes();
            etName.setEnabled(false);
        }

        if(isAddingFromShoppingList) {
            assignSelectedIngredientAttributesFromFragmentArgs();
            fillViewsWithSelectedIngredientAttributes();
            etName.setEnabled(false);
        }

        // Button checking
        addUserDefinedStuff(addLocationButton, addLocationText, "location");
        addUserDefinedStuff(addUnitButton, addUnitText, "unit");
        addUserDefinedStuff(addCategoryButton, addCategoryText, "category");

        deleteUserDefinedStuff(spLocation, deleteLocationButton, "location");
        deleteUserDefinedStuff(spCategory, deleteCategoryButton, "category");
        deleteUserDefinedStuff(spUnit, deleteUnitButton, "unit");

        editUserDefinedStuff(addLocationButton, deleteLocationButton, editLocationButton, addLocationText);
        editUserDefinedStuff(addCategoryButton, deleteCategoryButton, editCategoryButton, addCategoryText);
        editUserDefinedStuff(addUnitButton, deleteUnitButton, editUnitButton, addUnitText);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEdittingExistingIngredient = false;
                isAddingNewIngredient = false;
                dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO - Add prompt asking user if they're sure they want to save the new/eddited ingredient
                int day = dpBestBeforeDate.getDayOfMonth();
                int year = dpBestBeforeDate.getYear();
                int month = dpBestBeforeDate.getMonth();
                assignIngredientAttributesFromViews();
                if (!checkExceptions()){
                    if (isEdittingExistingIngredient) {
                        ArrayList<Ingredient> ingredientsFromStorage= IngredientDBHelper.getIngredientsFromStorage();
                        Ingredient newIngredient = modifiedIngredient();
                        int selectedIngredientIndex = ingredientsFromStorage.indexOf(newIngredient);
                        Ingredient oldIngredient = ingredientsFromStorage.get(selectedIngredientIndex);
                        IngredientDBHelper.modifyIngredientInDB(newIngredient, oldIngredient, selectedIngredientIndex);
                        RecipeDBHelper.updateRecipe(newIngredient.getUnit(), newIngredient.getLocation(), newIngredient.getCategory(), newIngredient.getName());
                        //TODO - this adds duplicate items to ingredient list . Redo this to edit the existing recipes NOT add a new recipe.
                        isEdittingExistingIngredient = false;
                        dismiss();
                    }

                    if (isAddingNewIngredient) {
                        if (!checkDuplicateInRecyclerView()){
                            Ingredient newIngredient = new Ingredient(name, description, LocalDate.of(year, month + 1, day), location, unit, category, amount);
                            IngredientDBHelper.addIngredientToDB(newIngredient);
                            isAddingNewIngredient = false;
                            dismiss();
                        }
                    }

                    if (isAddingFromShoppingList){
                        ArrayList<Ingredient> ingredientsFromStorage= IngredientDBHelper.getIngredientsFromStorage();
                        Ingredient newIngredient = modifiedIngredient();
                        int selectedIngredientIndex = ingredientsFromStorage.indexOf(newIngredient);
                        Ingredient oldIngredient = ingredientsFromStorage.get(selectedIngredientIndex);
                        Ingredient ingredientToAddto = oldIngredient.clone();
                        ingredientToAddto.setBestBefore(modifiedIngredient().getBestBefore());
                        ingredientToAddto.setLocation(modifiedIngredient().getLocation());
                        ingredientToAddto.setCategory(modifiedIngredient().getCategory());
                        ingredientToAddto.setColor(modifiedIngredient().getColor());
                        ingredientToAddto.setUnit(modifiedIngredient().getUnit());
                        ingredientToAddto.setDesc(modifiedIngredient().getDesc());
                        ingredientToAddto.setAmount(ingredientToAddto.getAmount() + newIngredient.getAmount());
                        IngredientDBHelper.modifyIngredientInDB(ingredientToAddto, oldIngredient, selectedIngredientIndex);
                        isAddingFromShoppingList = false;

                        dismiss();
                    }
                }
            }
        });
    }

    public Ingredient modifiedIngredient () {
        Ingredient modifiedIngredient = si.clone();

        modifiedIngredient.setName(etName.getText().toString());
        modifiedIngredient.setDesc(etDescription.getText().toString());
        modifiedIngredient.setBestBefore(getLocalDateFromStringArray(
                String.valueOf(dpBestBeforeDate.getYear()),
                String.valueOf(dpBestBeforeDate.getMonth() + 1), //NOTE: Date picker month is 0 indexed, localDate is 1 indexed
                String.valueOf(dpBestBeforeDate.getDayOfMonth())
        ));
        modifiedIngredient.setAmount(Integer.parseInt(String.valueOf(etAmount.getText())));
        modifiedIngredient.setUnit(spUnit.getSelectedItem().toString());
        modifiedIngredient.setCategory(spCategory.getSelectedItem().toString());
        modifiedIngredient.setLocation(spLocation.getSelectedItem().toString());
        return  modifiedIngredient;
    }

    /**
     * This function checks to see if the name of the ingredient inputted already exists in the recycler view. otherwise show
     * toast and make it so that the user can't save without having a name.
     * @return True if there already exists a name in the database that exists already. False if it doesn't exist.
     */
    boolean checkDuplicateInRecyclerView(){
        for (Ingredient i : rvAdapter.getIngredientsList()){ // Checks to see if there exists an ingredient of the same name already
            if (i.getName().equals(etName.getText().toString())) {
                Toast.makeText(getActivity(), "An ingredient of the same name exists already.", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    /**
     * This function checks to see if a value added into any of the userdefined dropdowns (units, location, category) already exist.
     * If the value exists, return true, if not, return false.
     * @param userDefinedText - this is the text that wants to be added into the dropdown
     * @param type - this tells the program which 'add' button the user clicked.
     * @return - true if the value wanting to add to the dropdown exists, false otherwise
     */
    boolean checkDuplicateInUserDefined(String userDefinedText, String type){
        if (type.equals("location")) {
            for (String i : UserDefinedDBHelper.getIngredientLocations()){
                if (userDefinedText.equalsIgnoreCase(i)){
                    Toast.makeText(getActivity(), "Something with this name exists already.", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
        if (type.equals("unit")) {
            for (String i : UserDefinedDBHelper.getIngredientUnits()){
                if (userDefinedText.equalsIgnoreCase(i)){
                    Toast.makeText(getActivity(), "Something with this name exists already.", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
        if (type.equals("category")) {
            for (String i : UserDefinedDBHelper.getIngredientCategories()){
                if (userDefinedText.equalsIgnoreCase(i)) {
                    Toast.makeText(getActivity(), "Something with this name exists already.", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This function checks for if the name is empty, if the amount is 0 and if the date inputted is in the past. A toast will be shown with a description
     * @return True if name is empty, if amount is 0, if date is in the past, false otherwise
     */
    boolean checkExceptions(){
        int day = dpBestBeforeDate.getDayOfMonth();
        int year = dpBestBeforeDate.getYear();
        int month = dpBestBeforeDate.getMonth();
        if (isEmpty(etName.getText())) {
            Toast.makeText(getActivity(), "Name can't be empty.", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (etAmount.getText().toString().equals(Character.toString('0'))) {
            Toast.makeText(getActivity(), "Amount has to be greater than 0.", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (LocalDate.of(year, month + 1, day).isBefore(LocalDate.now())){
            Toast.makeText(getActivity(), "Best before date can't be in the past", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *  This function is called when the 'delete' button is pressed and will delete the corresponding selected item in the spinner
     *  The function will also bar the user from deleting all items and will force the user to always keep one.
     *  When deleting an item in the spinner, the spinner will autoselect a different value.
     * @param sp - the spinner that the value will be deleted from
     * @param deleteButton - which delete button that was selected (there are 3. unit, category, location)
     * @param type - to check which spinner and delete button to use.
     */
    void deleteUserDefinedStuff(Spinner sp, Button deleteButton, String type){
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sp.getAdapter().getCount() > 1) {
                            String toDelete = (String) adapterView.getItemAtPosition(i);
                            if (type.equals("location")) {
                                UserDefinedDBHelper.deleteUserDefined(toDelete, "ingredientLocations", 0);
                            }
                            if (type.equals("unit")) {
                                UserDefinedDBHelper.deleteUserDefined(toDelete, "ingredientUnits", 0);
                            }
                            if (type.equals("category")) {
                                UserDefinedDBHelper.deleteUserDefined(toDelete, "ingredientCategory", 0);
                            }
                            //This changes the dropdown value to something that isn't currently selected.
                            if (i == 0) {
                                sp.setSelection(sp.getAdapter().getCount() - 1); // Last value in the list
                            } else {
                                sp.setSelection(0);
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "There must be atleast one item left in the dropdown.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /**
     * This function checks if the edit button is pressed and will make the corresponding add,delete and text box visible.
     * Clicking the edit button will make those things disappear again.
     * @param addButton - the add button to appear and disappear on edit button click
     * @param deleteButton - the delete button to appear and disappear on edit button click
     * @param editButton - the edit button to appear and disappear on edit button click
     * @param addText - the text box to appear and disappear on edit button click
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
     * This function adds the values in the edittext into the spinner. It will check for things like empty values and if the value exists already.
     * @param addButton - to show which button was pressed (unit, location, category)
     * @param addText - the value of what was written in the edit text.
     * @param type -  to find out which addtext and what addbutton to use.
     */
    void addUserDefinedStuff(Button addButton, EditText addText, String type){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = String.valueOf(addText.getText());
                if (isEmpty(text)) {
                    Toast.makeText(getActivity(), "This value can't be empty", Toast.LENGTH_LONG).show();
                } else if (!checkDuplicateInUserDefined(text, type)) {
                    if (type.equals("location")) {
                        UserDefinedDBHelper.addUserDefined(text, "ingredientLocations");
                    }
                    if (type.equals("unit")) {
                        UserDefinedDBHelper.addUserDefined(text, "ingredientUnits");
                    }
                    if (type.equals("category")) {
                        UserDefinedDBHelper.addUserDefined(text, "ingredientCategory");
                    }
                    addText.setText("");
                }
            }
        });
    }

    /**
     * This function creates a new ingredient and passes in all the values that were inputted into the fragment
     * The function new ingredient is then saved to be added into the database later.
     * @param ingredient - the ingredient that is being edited.
     */
    void modifyIngredient(Ingredient ingredient){
        Ingredient modifiedIngredient = ingredient;

        modifiedIngredient.setName(etName.getText().toString());
        modifiedIngredient.setDesc(etDescription.getText().toString());
        modifiedIngredient.setBestBefore(getLocalDateFromStringArray(
                String.valueOf(dpBestBeforeDate.getYear()),
                String.valueOf(dpBestBeforeDate.getMonth() + 1), //NOTE: Date picker month is 0 indexed, localDate is 1 indexed
                String.valueOf(dpBestBeforeDate.getDayOfMonth())
        ));
        modifiedIngredient.setAmount(Integer.parseInt(String.valueOf(etAmount.getText())));
        modifiedIngredient.setUnit(spUnit.getSelectedItem().toString());
        modifiedIngredient.setCategory(spCategory.getSelectedItem().toString());
        modifiedIngredient.setLocation(spLocation.getSelectedItem().toString());
    }

    /**
     * This function assigns all the attributes from the views into variables.
     */
    void assignIngredientAttributesFromViews(){
        name = String.valueOf(etName.getText());
        description = String.valueOf(etDescription.getText());
        bestBeforeStringArray.clear();
        bestBeforeStringArray.add(String.valueOf(dpBestBeforeDate.getYear()));
        bestBeforeStringArray.add(String.valueOf(dpBestBeforeDate.getMonth() + 1)); //NOTE: Date picker month is 0 indexed, localDate is 1 indexed
        bestBeforeStringArray.add(String.valueOf(dpBestBeforeDate.getDayOfMonth()));
        amount = Integer.parseInt(String.valueOf(etAmount.getText()));
        unit = spUnit.getSelectedItem().toString();
        category = spCategory.getSelectedItem().toString();
        location = spLocation.getSelectedItem().toString();
    }

    /**
     * This function gets all the fragment arguments and puts them into corresponding variables.
     */
    void assignSelectedIngredientAttributesFromFragmentArgs(){
        //Set associated view items to attributes of selected ingredient from argument bundle passed to this fragment on creation
        name = getArguments().getString("name", "---");
        description = getArguments().getString("description", "---");
        amount = getArguments().getInt("amount", 1);
        location = getArguments().getString("location", "---");
        category = getArguments().getString("category", "---");
        bestBeforeStringArray = getArguments().getStringArrayList("bestBeforeDate");
        unit = getArguments().getString("unit", "---");
    }

    /**
     * This function will fill all the views with the selected ingredient attributes
     */
    void fillViewsWithSelectedIngredientAttributes(){
        //Update editable attribute views with values of selected ingredient instances
        etName.setText(name);
        etDescription.setText(description);
        dpBestBeforeDate.init(Integer.parseInt(bestBeforeStringArray.get(0)),
                Integer.parseInt(bestBeforeStringArray.get(1)) - 1,  //NOTE: month is '0' indexed by date picker
                Integer.parseInt(bestBeforeStringArray.get(2)),
                null);
        spLocation.setSelection(ingredientLocations.indexOf(location));
        spCategory.setSelection(ingredientCategories.indexOf(category));
        etAmount.setText(String.valueOf(amount));
        spUnit.setSelection(ingredientUnits.indexOf(unit));

    }

    /**
     * This function will attach all the layout views to local instances
     * @param view - this is the fragment to get all the layout views to attach to local instances
     */
    void attachLayoutViewsToLocalInstances(View view){
        etName = (EditText) view.findViewById(R.id.et_ingredient_add_edit_name);
        etDescription = (EditText) view.findViewById(R.id.et_ingredient_add_edit_description);
        dpBestBeforeDate = view.findViewById(R.id.dp_ingredient_add_edit_best_before_date);
        spLocation = view.findViewById(R.id.sp_ingredient_add_edit_location);
        etAmount = view.findViewById(R.id.et_ingredient_add_edit_amount);
        spUnit = view.findViewById(R.id.sp_ingredient_add_edit_unit);
        spCategory = view.findViewById(R.id.sp_ingredient_add_edit_category);
        btnCancel = view.findViewById(R.id.btn_ingredient_add_edit_cancel);
        btnSave = view.findViewById(R.id.btn_ingredient_add_edit_save);

        addLocationText = view.findViewById(R.id.addLocation);
        addLocationButton = view.findViewById(R.id.addLocationButton);
        deleteLocationButton = view.findViewById(R.id.deleteLocationButton);
        editLocationButton = view.findViewById(R.id.editLocationButton);

        addUnitText = view.findViewById(R.id.addUnit);
        addUnitButton = view.findViewById(R.id.addUnitButton);
        deleteUnitButton = view.findViewById(R.id.deleteUnitButton);
        editUnitButton = view.findViewById(R.id.editUnitButton);

        addCategoryText = view.findViewById(R.id.addCategory);
        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        deleteCategoryButton = view.findViewById(R.id.deleteCategoryButton);
        editCategoryButton = view.findViewById(R.id.editCategoryButton);
    }

    /**
     * This function sets up all the adapters by getting them first from the layout views and then assigns them to different things.
     */
    void setupAdapters(){
        //Declare and instantiate adapters for spinners
        ArrayAdapter<String> locationSpinnerAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ingredientLocations);
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> categorySpinnerAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ingredientCategories);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> unitSpinnerAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ingredientUnits);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set adapters to corresponding spinners
        spLocation.setAdapter(locationSpinnerAdapter);
        spCategory.setAdapter(categorySpinnerAdapter);
        spUnit.setAdapter(unitSpinnerAdapter);

        UserDefinedDBHelper.setupSnapshotListenerForIngredientUserDefinedAdapter(
                unitSpinnerAdapter,
                locationSpinnerAdapter,
                categorySpinnerAdapter);

        UserDefinedDBHelper.addUserDefined("to delete", "ingredientCategory");
        UserDefinedDBHelper.deleteUserDefined("to delete", "ingredientCategory", 0);
        UserDefinedDBHelper.addUserDefined("to delete", "ingredientUnits");
        UserDefinedDBHelper.deleteUserDefined("to delete", "ingredientUnits", 0);
        UserDefinedDBHelper.addUserDefined("to delete", "ingredientLocations");
        UserDefinedDBHelper.deleteUserDefined("to delete", "ingredientLocations", 0);
    }

    /**
     * This function takes in a year, month and date and turns them into integers.
     * @param year of type string
     * @param month of type string
     * @param date of type string
     * @return - returns the dates that were converted to a string and put together in a tpe localdate.
     */
    public LocalDate getLocalDateFromStringArray(String year, String month, String date){
        LocalDate localDate = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(date));
        return localDate;
    }
}