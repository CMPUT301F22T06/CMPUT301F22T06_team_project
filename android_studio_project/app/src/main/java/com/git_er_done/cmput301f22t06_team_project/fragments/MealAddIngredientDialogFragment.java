package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.adapters.MealsAddIngredientListViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealAddIngredientDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealAddIngredientDialogFragment extends DialogFragment {

    MealsAddIngredientListViewAdapter mealsAddIngredientListViewAdapter;

    Button btnCancel;
    Button btnAddSelectedIngredientsToMeal;
    ListView listViewAddIngredientsToMeal;

    /**
     * Required empty constructor
     */
    public MealAddIngredientDialogFragment() {}

    public static MealAddIngredientDialogFragment newInstance() {
        MealAddIngredientDialogFragment fragment = new MealAddIngredientDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_add_ingredient_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCancel = view.findViewById(R.id.btn_meal_add_ingredient_cancel);
        btnAddSelectedIngredientsToMeal = view.findViewById(R.id.btn_meal_add_ingredient_add_selected_items);
        listViewAddIngredientsToMeal = view.findViewById(R.id.lv_ingredients_available_to_add_to_meal);

        mealsAddIngredientListViewAdapter = new MealsAddIngredientListViewAdapter(getContext(), IngredientDBHelper.getIngredientsFromStorage());
        listViewAddIngredientsToMeal.setAdapter(mealsAddIngredientListViewAdapter);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dismiss();
            }
        });

        btnAddSelectedIngredientsToMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}