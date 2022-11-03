package com.git_er_done.cmput301f22t06_team_project.fragments;

import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.ingredientCategories;
import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.locations;
import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.testIngredients;
import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.units;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;

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
        }

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
                    int selectedIngredientIndex = testIngredients.indexOf(si);
                    Ingredient ingredientToModify = testIngredients.get(selectedIngredientIndex);
                    modifyIngredient(ingredientToModify);
                    isEdittingExistingIngredient = false;
                }

                if(isAddingNewIngredient){
                    addIngredient();
                    isAddingNewIngredient = false;
                }

                rvAdapter.notifyDataSetChanged();

                dismiss();
            }
        });

    }


    void addIngredient(){
        Ingredient newIngredient = new Ingredient(name, description, LocalDate.now(), location, unit, category, amount);
        testIngredients.add(newIngredient);
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
        amount = getArguments().getInt("amount", -1);
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
        spLocation.setSelection(locations.indexOf(location));
        spCategory.setSelection(ingredientCategories.indexOf(category));
        etAmount.setText(String.valueOf(amount));
        spUnit.setSelection(units.indexOf(unit));
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
    }

    void setupAdapters(){
        //Declare and instantiate adapters for spinners
        ArrayAdapter<String> locationSpinnerAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, locations);
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> categorySpinnerAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ingredientCategories);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> unitSpinnerAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, units);
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
