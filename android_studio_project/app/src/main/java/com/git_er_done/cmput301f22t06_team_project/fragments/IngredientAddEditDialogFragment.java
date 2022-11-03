package com.git_er_done.cmput301f22t06_team_project.fragments;

import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.ingredientCategories;
import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.locations;
import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.testIngredients;

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
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

import java.util.ArrayList;

//https://guides.codepath.com/android/using-dialogfragment  helpful resource

/**
 * Dialog fragment for adding and editting an ingredient item. Accessed from the Ingredient Fragment.
 * Extends DiaglogFragment - uses a fragment lifecycle instead of seperate dialog
 */
public class IngredientAddEditDialogFragment extends DialogFragment {

    static Ingredient si = null;

    private EditText etName;
    private EditText etDescription;
    private DatePicker dpBestBeforeDate;
    private Spinner spLocation;
    private EditText etAmount;
    private Spinner spUnit;
    private Spinner spCategory;

    private Button btnCancel;
    private Button btnSave;

    /**
     * Empty constructor required
     */
    public IngredientAddEditDialogFragment(){}

    /**
     * Static constructor for a new instance of Ingredient add/edit dialog
     * Used in place of a traditional constructor
     * @param title - Title of the dialog fragment
     * @param selectedIngredient - Instance of the selected ingredient item retreived from the Recycler View  adapter
     * @return static instance of this dialog
     */
    public static IngredientAddEditDialogFragment newInstance(String title, Ingredient selectedIngredient){
        si = selectedIngredient;
        IngredientAddEditDialogFragment frag = new IngredientAddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("name",  selectedIngredient.getName());
        args.putString("description", selectedIngredient.getDesc());
        args.putStringArrayList("bestBeforeDate", selectedIngredient.getBestBeforeStringArrayList());
        args.putString("location", selectedIngredient.getLocation());
        args.putString("category", selectedIngredient.getCategory());
        args.putString("amount", selectedIngredient.getAmount().toString());
        //get unit

        frag.setArguments(args);
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

        String dialogTitle = getArguments().getString("title", "Default title ");

        //Get associated fied from view items
        etName = (EditText) view.findViewById(R.id.et_ingredient_add_edit_name);
        etDescription = (EditText) view.findViewById(R.id.et_ingredient_add_edit_description);
        dpBestBeforeDate = view.findViewById(R.id.dp_ingredient_add_edit_best_before_date);
        spLocation = view.findViewById(R.id.sp_ingredient_add_edit_location);
        etAmount = view.findViewById(R.id.et_ingredient_add_edit_amount);
        spUnit = view.findViewById(R.id.sp_ingredient_add_edit_unit);
        spCategory = view.findViewById(R.id.sp_ingredient_add_edit_category);
        btnCancel = view.findViewById(R.id.btn_ingredient_add_edit_cancel);
        btnSave = view.findViewById(R.id.btn_ingredient_add_edit_save);

        //IF WE ARE EDITTING AN EXISTING INGREDIENT - DISPLAY ITS CURRENT ATTRIBUTES

        //Set associate view items to attributes of selected ingredient
        String name = getArguments().getString("name", "---");
        String description = getArguments().getString("description", "---");
        String amount = getArguments().getString("amount", "---");
        String location = getArguments().getString("location", "---");
        String category = getArguments().getString("category", "---");
        ArrayList<String> bestBeforeStringArray = getArguments().getStringArrayList("bestBeforeDate");


        ArrayAdapter<String> locationSpinnerAdapter =
                new ArrayAdapter<>(getContext(),  android.R.layout.simple_spinner_dropdown_item, locations);
        locationSpinnerAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(locationSpinnerAdapter);

        ArrayAdapter<String> categorySpinnerAdapter =
                new ArrayAdapter<>(getContext(),  android.R.layout.simple_spinner_dropdown_item, ingredientCategories);
        categorySpinnerAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categorySpinnerAdapter);

        etName.setText(name);
        etDescription.setText(description);
        dpBestBeforeDate.init(Integer.parseInt(bestBeforeStringArray.get(0)),
                Integer.parseInt(bestBeforeStringArray.get(1))-1,  //NOTE: month is '0' indexed by date picker
                Integer.parseInt(bestBeforeStringArray.get(2)),
                null);
        spLocation.setSelection(location.indexOf(location));
        spCategory.setSelection(ingredientCategories.indexOf(category));
        etAmount.setText(amount);
        //set unit

        //IF WE ARE ADDING A NEW INGREDIENT - LEAVE INPUT FIELDS EMPTY TO SHOW HINTS

        /**
         * Handles 'cancel' button user interaction.
         * During ingredient add, nothing is saved and no new ingredient is added.\
         * During an edit, no edited parameters are saved. Original parameters are left
         */
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //TODO - Save the added/editted ingredient attributes to the selected instance
        /**
         * Handled 'save' button user interaction.
         * During ingredient add - a new ingredient is created with the provided paramters assuming they
         * are all a valid value. User is prompted and dialog stays if any values are invalid
         *
         */
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO - Check that all the current entries are valid
                Integer i = testIngredients.indexOf(si);
                si.setLocation(spLocation.getSelectedItem().toString());

                dismiss();
            }
        });

    }
}
