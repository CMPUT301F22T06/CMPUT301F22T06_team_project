package com.git_er_done.cmput301f22t06_team_project.fragments;

import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.testIngredients;
import static com.git_er_done.cmput301f22t06_team_project.models.ShoppingListIngredient.testShoppingList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.ShoppingListRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.SwipeToDeleteCallback;
import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.controllers.ShoppingListRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShoppingListFragment extends Fragment implements ShoppingListRecyclerViewInterface {

    private RecyclerView rvShoppingListItems;
    private FloatingActionButton fabAddShoppingListItem;
    private ShoppingListRecyclerViewAdapter rvAdapter;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Shopping List");


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_shopping_list, container, false);

        rvShoppingListItems = (RecyclerView) root.findViewById(R.id.rv_shopping_list);
        fabAddShoppingListItem = root.findViewById(R.id.fab_shopping_list_ingredient_add);

        setupRecyclerView();

        return root;
    }


    private void setupRecyclerView(){
        rvAdapter = new ShoppingListRecyclerViewAdapter(testShoppingList, this);
        rvShoppingListItems.setAdapter(rvAdapter);
        rvShoppingListItems.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(rvAdapter));
//        itemTouchHelper.attachToRecyclerView(rvShoppingListItems);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}