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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.adapters.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.IngredientCategory;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.IngredientLocation;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.IngredientUnit;

import java.time.LocalDate;
import java.util.ArrayList;

//https://guides.codepath.com/android/using-dialogfragment  helpful resource

/**
 * Dialog fragment for adding and editting an ingredient item. Accessed from the Ingredient Fragment.
 * Extends DiaglogFragment - uses a fragment lifecycle instead of seperate dialog
 */
public class IngredientAddEditDialogFragment extends DialogFragment {

    static Ingredient si = null;
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

    EditText addUnitText;
    Button addUnitButton;
    Button deleteUnitButton;

    EditText addCategoryText;
    Button addCategoryButton;
    Button deleteCategoryButton;

    String name;
    String description;
    int amount;
    String location;
    String category;
    ArrayList<String> bestBeforeStringArray = new ArrayList<>();
    String unit;

    private static boolean isAddingNewIngredient = false;
    private static boolean isEdittingExistingIngredient = false;

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

    /**
     * Returns an inflated instance of this dialog fragments XML layout
     * @param inflater
     * @param container
     * @param savedInstanceState
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
     * @param view
     * @param savedInstanceState
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

        //Saheel's code

        addUserDefinedStuff(spLocation, addLocationButton, addLocationText, deleteLocationButton, "Add New Location", "Add Location", "location");
        deleteUserDefinedStuff(spLocation, deleteLocationButton, "location");
        addUserDefinedStuff(spUnit, addUnitButton, addUnitText, deleteUnitButton, "Add New Unit", "Add Unit", "unit");
        addUserDefinedStuff(spCategory,addCategoryButton, addCategoryText, deleteCategoryButton, "Add New Category", "Add Category", "category");
        deleteUserDefinedStuff(spCategory, deleteCategoryButton, "category");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEdittingExistingIngredient = false;
                isAddingNewIngredient = false;
                dismiss();
            }
        });

        //TODO - Save the added/editted ingredient attributes to the selected instance
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO - Check that all the current entries are valid
                //TODO - Add prompt asking user if they're sure they want to save the new/eddited ingredient

                assignIngredientAttributesFromViews();

                if(isEdittingExistingIngredient) {
                    int selectedIngredientIndex = rvAdapter.getIngredientsList().indexOf(si);
                    Ingredient newIngredient = rvAdapter.getIngredientsList().get(selectedIngredientIndex);
                    Ingredient oldIngredient = new Ingredient(
                            newIngredient.getName(),
                            newIngredient.getDesc(),
                            newIngredient.getBestBefore(),
                            newIngredient.getLocation(),
                            newIngredient.getUnit(),
                            newIngredient.getCategory(),
                            newIngredient.getAmount()
                    );
                    modifyIngredient(newIngredient);
                    IngredientDBHelper.modifyIngredientInDB(newIngredient,oldIngredient,selectedIngredientIndex);
                    //TODO - this adds duplicate items to ingredient list . Redo this to edit the existing recipes NOT add a new recipe.
//                    checkAndEditRecipesUnits();
                    isEdittingExistingIngredient = false;
                if (isEmpty(etName.getText())){
                    Toast.makeText(getActivity(), "Name can't be empty.", Toast.LENGTH_LONG).show();
                }
                else {
                    if (isEdittingExistingIngredient) {
                        int selectedIngredientIndex = rvAdapter.getIngredientsList().indexOf(si);
                        Ingredient newIngredient = rvAdapter.getIngredientsList().get(selectedIngredientIndex);
                        Ingredient oldIngredient = new Ingredient(
                                newIngredient.getName(),
                                newIngredient.getDesc(),
                                newIngredient.getBestBefore(),
                                newIngredient.getLocation(),
                                newIngredient.getUnit(),
                                newIngredient.getCategory(),
                                newIngredient.getAmount()
                        );
                        modifyIngredient(newIngredient);
                        IngredientDBHelper.modifyIngredientInDB(newIngredient, oldIngredient, selectedIngredientIndex);
                        checkAndEditRecipesUnits();
                        isEdittingExistingIngredient = false;
                    }

                    if (isAddingNewIngredient) {
                        Ingredient newIngredient = new Ingredient(name, description, LocalDate.now(), location, unit, category, amount);
                        IngredientDBHelper.addIngredientToDB(newIngredient);
                        isAddingNewIngredient = false;
                    }

                    rvAdapter.notifyDataSetChanged();

                    dismiss();
                }
            }
        });
    }

    void checkAndEditRecipesUnits(){
        String unit = spUnit.getSelectedItem().toString();
        String name = etName.getText().toString();

        RecipeDBHelper.updateRecipe(unit, name);
    }

    void deleteUserDefinedStuff(Spinner sp, Button deleteButton, String type){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String toDelete = (String) adapterView.getItemAtPosition(i);
                        if (type == "location"){
                            IngredientLocation.getInstance().deleteLocation(toDelete);
                        }
                        if (type == "unit"){
                            IngredientUnit.getInstance().deleteUnit(toDelete);
                        }
                        if (type == "category"){
                            IngredientCategory.getInstance().deleteCategory(toDelete);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }

    void addUserDefinedStuff(Spinner sp, Button addButton, EditText addText, Button deleteButton, String message, String notEqual, String type){
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i) == message){
                    addButton.setVisibility(View.VISIBLE);
                    addText.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = String.valueOf(addText.getText());
                            if (text != notEqual) {
                                if (type == "location") {
                                    IngredientLocation.getInstance().addLocation(text);
                                }
                                if (type == "unit") {
                                    IngredientUnit.getInstance().addUnit(text);
                                }
                                if (type == "category") {
                                    IngredientCategory.getInstance().addIngredientCategory(text);
                                }
                            }
                            addButton.setVisibility(View.INVISIBLE);
                            addText.setVisibility(View.INVISIBLE);
                            deleteButton.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



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

        addUnitText = view.findViewById(R.id.addUnit);
        addUnitButton = view.findViewById(R.id.addUnitButton);
        deleteUnitButton = view.findViewById(R.id.deleteUnitButton);

        addCategoryText = view.findViewById(R.id.addCategory);
        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        deleteCategoryButton = view.findViewById(R.id.deleteCategoryButton);
    }


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
    }


    public LocalDate getLocalDateFromStringArray(String year, String month, String date){
        LocalDate localDate = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(date));
        return localDate;
    }
}