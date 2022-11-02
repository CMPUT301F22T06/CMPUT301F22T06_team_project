package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;

//https://guides.codepath.com/android/using-dialogfragment  helpful resource

/**
 * ALL fragment dialog items MUST be from the androidx.fragment.app namespace !
 */
public class RecipeAddEditDialogFragment extends DialogFragment {
    private EditText etName;
    private EditText etDescription;
    private EditText etComments;
    private EditText etServings;
    private EditText etPrep_time;
    private Spinner spCategory;
    // Add an ingredient add/edit dialog fragment

    private Button btnCancel;
    private Button btnDelete;
    private Button btnSave;


    public RecipeAddEditDialogFragment(){
        //Empty constructor required. New instance static constructor below is called upon instantiation
    }

    public static RecipeAddEditDialogFragment newInstance(String title, Recipe selectedRecipe){
        RecipeAddEditDialogFragment frag = new RecipeAddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("name",  selectedRecipe.getTitle());
        // get image
        //args.putString("description", selectedRecipe.getDesc());
        args.putString("Serves", selectedRecipe.getServings().toString());
        args.putString("Prep Time", selectedRecipe.getPrep_time().toString());
        args.putString("Category", selectedRecipe.getCategory());

        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_recipe_add_edit_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get associated field from view items
        etName = (EditText) view.findViewById(R.id.et_recipe_add_edit_name);
        etDescription = (EditText) view.findViewById(R.id.et_recipe_add_edit_description);
        etServings = (EditText) view.findViewById(R.id.et_recipe_add_edit_servings);
        etPrep_time = (EditText) view.findViewById(R.id.et_recipe_add_edit_preptime);
        etComments = (EditText) view.findViewById(R.id.et_recipe_add_edit_comments);
        spCategory = view.findViewById(R.id.sp_recipe_add_edit_category);
        btnCancel = view.findViewById(R.id.btn_recipe_add_edit_cancel);
        btnDelete = view.findViewById(R.id.btn_recipe_add_edit_delete);
        btnSave = view.findViewById(R.id.btn_recipe_add_edit_save);


        String dialogTitle = getArguments().getString("title", "Default title ");

        //IF WE ARE EDITTING AN EXISTING INGREDIENT - DISPLAY ITS CURRENT ATTRIBUTES

        //Set associate view items to attributes of selected ingredient
        String title = getArguments().getString("title", "def - no title found");
        String description = getArguments().getString("description", "def - desc");
        String prep_time = getArguments().getString("prep_time", "def - preptime");
        String servings = getArguments().getString("servings", "def - servings");
        String comments = getArguments().getString("comments", "def - comments");
        // get ingredients
        // get picture

        etName.setText(title);
        etDescription.setText(description);
        etServings.setText(servings);
        etPrep_time.setText(prep_time);
        etComments.setText(comments);
        //set category

        //IF WE ARE ADDING A NEW RECIPE - LEAVE INPUT FIELDS EMPTY TO SHOW HINTS


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

        //TODO - Save the added/editted ingredient attributes to the selected instance
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                dismiss();
            }
        });

    }
}
