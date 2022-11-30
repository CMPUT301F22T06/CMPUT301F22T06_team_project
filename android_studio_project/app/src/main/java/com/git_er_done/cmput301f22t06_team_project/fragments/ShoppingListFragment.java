package com.git_er_done.cmput301f22t06_team_project.fragments;

//import static com.git_er_done.cmput301f22t06_team_project.models.shoppingList.ShoppingListIngredient.testShoppingList;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.MealDBHelper;
import com.git_er_done.cmput301f22t06_team_project.interfaces.ShoppingListRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.adapters.ShoppingListRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.git_er_done.cmput301f22t06_team_project.models.shoppingList.ShoppingListIngredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShoppingListFragment extends Fragment implements ShoppingListRecyclerViewInterface {

    private RecyclerView rvShoppingListItems;
    private ShoppingListRecyclerViewAdapter rvAdapter;

    ArrayList<Ingredient> testShoppingList;

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
        testShoppingList = compareBetweenIDBandMDB();
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
                        Collections.sort(testShoppingList, new Comparator<Ingredient>() {
                                    @Override
                                    public int compare(Ingredient lhs, Ingredient rhs) {
                                        return lhs.getName().compareTo(rhs.getName());
                                    }
                                });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.shopping_list_action_sort_by_description:
                        Collections.sort(testShoppingList, new Comparator<Ingredient>() {
                            @Override
                            public int compare(Ingredient lhs, Ingredient rhs) {
                                return lhs.getDesc().compareTo(rhs.getDesc());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.shopping_list_action_sort_by_category:
                        Collections.sort(testShoppingList, new Comparator<Ingredient>() {
                            @Override
                            public int compare(Ingredient lhs, Ingredient rhs) {
                                return lhs.getCategory().compareTo(rhs.getCategory());
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
        rvAdapter = new ShoppingListRecyclerViewAdapter(testShoppingList, this, requireActivity());
        rvShoppingListItems.setAdapter(rvAdapter);
        rvShoppingListItems.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(rvAdapter));
//        itemTouchHelper.attachToRecyclerView(rvShoppingListItems);
    }

    /**
     * Finds the difference between what's in the storage and what we need for the current meal plan
     * and create a shopping list based on what's not in the storage
     * @return
     */
    public static ArrayList<Ingredient> compareBetweenIDBandMDB () {
        Ingredient toDeleteIngredient = new Ingredient("to delete", " ", LocalDate.now(), " ", " ", " ", 0, 0);
        IngredientDBHelper.addIngredientToDB(toDeleteIngredient);
        IngredientDBHelper.deleteIngredientFromDB(toDeleteIngredient, 0);
        ArrayList<Meal> mealPlansFromMDB = MealDBHelper.getMealsFromStorage();
        ArrayList<Meal> mealPlansCopy = new ArrayList<>();
        for (Meal i: mealPlansFromMDB){
            if (i.getDate().compareTo(LocalDate.now()) >= 0) {
                mealPlansCopy.add(i.clone());
            }
        }

        ArrayList<Ingredient> totalIngredientsforMealPlan = new ArrayList<>();
        for (Meal meal: mealPlansCopy){ // Get all the ingredients and total them from the meal plans'
             ArrayList<Ingredient> ingredientsFromMeal = meal.getAllIngredientsFromMeal();
             for (Ingredient i: ingredientsFromMeal){
                 if(totalIngredientsforMealPlan.contains(i)){
                     int index = totalIngredientsforMealPlan.indexOf(i);
                     Ingredient ingredientToAdd = totalIngredientsforMealPlan.get(index);
                     ingredientToAdd.setAmount(ingredientToAdd.getAmount()+i.getAmount());
                 }
                 else {
                     totalIngredientsforMealPlan.add(i);
                 }
             }
        }

        ArrayList<Ingredient> ingredientsFromIDB = IngredientDBHelper.getIngredientsFromStorage();

        ArrayList<Ingredient> ingredientsCopy = new ArrayList<>();
        for (Ingredient i: ingredientsFromIDB){
            ingredientsCopy.add(i.clone());
        }
        for (Ingredient i: totalIngredientsforMealPlan){ // This'll take the difference between what's in the IngredientDB and the totalIngredients for the meal plan
            if (ingredientsCopy.contains(i)){
                int index = ingredientsCopy.indexOf(i);
                Ingredient ingredientToSub = ingredientsCopy.get(index);
                ingredientToSub.setAmount(ingredientToSub.getAmount() - i.getAmount());
            }
            else {
                Ingredient iClone = i.clone();
                iClone.setAmount(-i.getAmount());
                ingredientsCopy.add(iClone);
            }
        }

        ArrayList<Ingredient> shoppingList = new ArrayList<>();
        for (Ingredient i: ingredientsCopy){ //Any ingredient in the ingredientFromDB list that's zero gets added to the shopping List
            if (i.getAmount() < 0){
                Ingredient iClone = i.clone();
                iClone.setAmount(-i.getAmount()); //reverse the negative sign
                shoppingList.add(iClone);
            }
        }
        return shoppingList;
    }

    @Override
    public void onItemLongClick(int position) {

    }


}