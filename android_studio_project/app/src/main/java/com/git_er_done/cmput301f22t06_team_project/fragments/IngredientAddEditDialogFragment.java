package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

//https://guides.codepath.com/android/using-dialogfragment  helpful resource

/**
 * ALL fragment dialog items MUST be from the androidx.fragment.app namespace !
 */
public class IngredientAddEditDialogFragment extends DialogFragment {
    private EditText etName;
    private EditText etDescription;
    private DatePicker dpBestBeforeDate;
    private Spinner spLocation;
    private EditText etAmount;
    private Spinner spUnit;
    private Spinner spCategory;

    public IngredientAddEditDialogFragment(){
        //Empty constructor required. New instance static constructor below is called upon instantiation
    }

    //TODO - get bb4 date, month, year and store as seperate string
    public static IngredientAddEditDialogFragment newInstance(String title, Ingredient selectedIngredient){
        IngredientAddEditDialogFragment frag = new IngredientAddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("name",  selectedIngredient.getName());
        args.putString("description", selectedIngredient.getDesc());
        //Get best before date
        //Get location
        args.putString("amount", selectedIngredient.getAmount().toString());
        //get unit
        //get category

        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_ingredient_add_edit_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get associated fied from view items
        etName = (EditText) view.findViewById(R.id.et_ingredient_add_edit_name);
        etDescription = (EditText) view.findViewById(R.id.et_ingredient_add_edit_description);
        dpBestBeforeDate = view.findViewById(R.id.dp_ingredient_add_edit_best_before_date);
        spLocation = view.findViewById(R.id.sp_ingredient_add_edit_location);
        etAmount = view.findViewById(R.id.et_ingredient_add_edit_amount);
        spUnit = view.findViewById(R.id.sp_ingredient_add_edit_unit);
        spCategory = view.findViewById(R.id.sp_ingredient_add_edit_category);


        String dialogTitle = getArguments().getString("title", "Default title ");

        //IF WE ARE EDITTING AN EXISTING INGREDIENT - DISPLAY ITS CURRENT ATTRIBUTES

        //Set associate view items to attributes of selected ingredient
        String name = getArguments().getString("name", "def - no name found");
        String description = getArguments().getString("description", "def - desc");
        String amount = getArguments().getString("amount", "1");

        etName.setText(name);
        etDescription.setText(description);
        //set bb4 date
        //set location
        etAmount.setText(amount);
        //set unit
        //set category


        //IF WE ARE ADDING A NEW INGREDIENT - LEAVE INPUT FIELDS EMPTY TO SHOW HINTS


    }
}
