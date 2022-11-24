package com.git_er_done.cmput301f22t06_team_project.fragments;

import static com.git_er_done.cmput301f22t06_team_project.models.shoppingList.ShoppingListIngredient.testShoppingList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.interfaces.ShoppingListRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.adapters.ShoppingListRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.git_er_done.cmput301f22t06_team_project.models.shoppingList.ShoppingListIngredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.shopping_list_ingredient_sort_menu, menu);

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.shopping_list_action_sort_by_name:
                        Collections.sort(testShoppingList, new Comparator<ShoppingListIngredient>() {
                                    @Override
                                    public int compare(ShoppingListIngredient lhs, ShoppingListIngredient rhs) {
                                        return lhs.getIngredient().getName().compareTo(rhs.getIngredient().getName());
                                    }
                                });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.shopping_list_action_sort_by_description:
                        Collections.sort(testShoppingList, new Comparator<ShoppingListIngredient>() {
                            @Override
                            public int compare(ShoppingListIngredient lhs, ShoppingListIngredient rhs) {
                                return lhs.getIngredient().getDesc().compareTo(rhs.getIngredient().getDesc());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.shopping_list_action_sort_by_category:
                        Collections.sort(testShoppingList, new Comparator<ShoppingListIngredient>() {
                            @Override
                            public int compare(ShoppingListIngredient lhs, ShoppingListIngredient rhs) {
                                return lhs.getIngredient().getCategory().compareTo(rhs.getIngredient().getCategory());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);


        return root;
    }

    //TODO - On start check if there is a larger amount of an ingredient needed by a recipe in meal planner
    // than is available in the ingredient storage
    //  - if so, add the required extra ingredients to the shopping list ( check if already in shopping list,
    //  - if not add, if yes then increase requiredAmount
    @Override
    public void onStart() {
        super.onStart();
    }

    private void setupRecyclerView(){
        rvAdapter = new ShoppingListRecyclerViewAdapter(testShoppingList, this);
        rvShoppingListItems.setAdapter(rvAdapter);
        rvShoppingListItems.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(rvAdapter));
//        itemTouchHelper.attachToRecyclerView(rvShoppingListItems);
    }

    private void compareBetweenIDBandMDB () {
        // We need some way to get these arrayLists
        ArrayList<Meal> mealPlansFromMDB = null; //MealPleanDB
        ArrayList<Ingredient> totalIngredientsforMealPlan = new ArrayList<>();
        for (Meal meal: mealPlansFromMDB){ // Get all the ingredients and total them from the meal plans
             ArrayList<Ingredient> ingredientsFromMeal = meal.getIngredientsFromMeal();
             for (Ingredient i: ingredientsFromMeal){
                 Boolean alreadyInTotalIngredients = false;
                 for (Ingredient j: totalIngredientsforMealPlan){
                     if (i.getName() == j.getName()){
                         j.setAmount(j.getAmount() + i.getAmount());
                         alreadyInTotalIngredients = true;
                     }
                 }
                 if (!alreadyInTotalIngredients){
                     totalIngredientsforMealPlan.add(i);
                 }
             }
        }

        ArrayList<Ingredient> ingredientsFromIDB = null; //IngredientDB
        for (Ingredient i: totalIngredientsforMealPlan){ // This'll take the difference between what's in the IngredientDB and the totalIngredients for the meal plan
            Boolean alreadyInIDB = false;
            for (Ingredient j: ingredientsFromIDB){
                if (i.getName() == j.getName()){
                    j.setAmount(j.getAmount() - i.getAmount());
                    alreadyInIDB = true;
                }
            }
            if (!alreadyInIDB){
                Ingredient iClone = i.clone();
                iClone.setAmount(-i.getAmount());
                ingredientsFromIDB.add(iClone);
            }
        }

        ArrayList<Ingredient> shoppingList = new ArrayList<>();
        for (Ingredient i: ingredientsFromIDB){ //Any ingredient in the ingredientFromDB list that's zero gets added to the shopping List
            if (i.getAmount() < 0){
                Ingredient iClone = i.clone();
                iClone.setAmount(-i.getAmount()); //reverse the negative sign
                shoppingList.add(iClone);
            }
        }
    }

    @Override
    public void onItemLongClick(int position) {

    }


}