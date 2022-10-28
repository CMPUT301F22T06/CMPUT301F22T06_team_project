package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {

    ArrayList<Ingredient> testIngredients;
    RecyclerView rvIngredients;


    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ingredient, container, false);
        rvIngredients = (RecyclerView) root.findViewById(R.id.rv_ingredients_list);
        rvIngredients.setHasFixedSize(true);

        rvIngredients.setLayoutManager(new LinearLayoutManager(this.getContext()));

        testIngredients = Ingredient.createIngredientList();
        IngredientDBHelper dbHelper = new IngredientDBHelper();
        for (Ingredient ingredient: testIngredients) {
            dbHelper.addIngredient(ingredient);
        }
        dbHelper.deleteIngredient("apple");
        retrievedIngredients = dbHelper.getData();
        IngredientsRecyclerViewAdapter adapter = new IngredientsRecyclerViewAdapter(testIngredients);
        rvIngredients.setAdapter(adapter);

        // Inflate the layout for this fragment
        return root;
    }

    private void showEditDialog(Ingredient selectedIngredient) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        IngredientAddEditDialogFragment editNameDialogFragment =
                IngredientAddEditDialogFragment.newInstance(
                        "Edit Ingredient Dialog",
                        selectedIngredient);
        editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");

    }

    @Override
    public void onItemLongClick(int position) {
        Toast toast = Toast.makeText(getActivity(),
                "Item at pos: "  + Integer.toString(position) + " selected",
                Toast.LENGTH_SHORT);
        toast.show();

        Ingredient selectedIngredient = rvAdapter.getItem(position);

        //Create a dialog displaying all of the selected ingredients attributes
        showEditDialog(selectedIngredient);

    }
}