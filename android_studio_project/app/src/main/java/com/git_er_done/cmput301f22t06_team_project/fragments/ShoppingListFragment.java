package com.git_er_done.cmput301f22t06_team_project.fragments;

import static com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper.getIndexOfIngredientFromName;
import static com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper.modifyIngredientInDB;
import static androidx.fragment.app.FragmentManager.TAG;
import static com.git_er_done.cmput301f22t06_team_project.models.shoppingList.ShoppingListIngredient.testShoppingList;

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
import android.widget.Button;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.interfaces.ShoppingListRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.adapters.ShoppingListRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.shoppingList.ShoppingListIngredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShoppingListFragment extends Fragment implements ShoppingListRecyclerViewInterface {

    private RecyclerView rvShoppingListItems;
    private FloatingActionButton fabAddShoppingListItem;
    private ShoppingListRecyclerViewAdapter rvAdapter;
    private Button gotButton;

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
        gotButton = root.findViewById(R.id.got_shopping_list_item_button);

        setupRecyclerView();

//        gotButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // add testShoppingList.get(position) * QuantityNeeded to ingredients
//                // by Ingredient.setamount = amount + quantitybought
//                Ingredient selectedIngredient = testShoppingList.get(position).getIngredient();
//                int quantity = testShoppingList.get(position).getRequiredAmount();
//                testShoppingList.get(position).getIngredient().setAmount(selectedIngredient.getAmount() + quantity);
//                int pos = getIndexOfIngredientFromName(selectedIngredient.getName());
//                Ingredient newIngredient = new Ingredient(selectedIngredient.getName(), selectedIngredient.getDesc(), selectedIngredient.getBestBefore(),
//                        selectedIngredient.getLocation(), selectedIngredient.getUnit(), selectedIngredient.getCategory(),
//                        selectedIngredient.getAmount() + quantity);
//                modifyIngredientInDB(newIngredient, selectedIngredient, pos);
//            }
//        });


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

    /**
     * Finds the difference between what's in the storage and what we need for the current meal plan
     * and create a shopping list based on what's not in the storage
     */
    public static void compareBetweenIDBandMDB () {
        // We need some way to get these arrayLists
        ArrayList<Meal> mealPlansFromMDB = new ArrayList<>();

        //Meal 1
        ArrayList<Ingredient> ingredientsInMeal = new ArrayList<>();
        ArrayList<Recipe> recipesInMeal = new ArrayList<>();
        //Recipe 1
        Ingredient apple1 = new Ingredient("apple", " ", LocalDate.now(), " ", " ", " ", 3);
        Ingredient orange1 = new Ingredient("orange", " ", LocalDate.now(), " ", " ", " ", 4);
        Ingredient banana1 = new Ingredient("banana", " ", LocalDate.now(), " ", " ", " ", 1);
        Recipe recipe = new Recipe("fruit salad", " ", " ", 0, 2);
        recipe.addIngredient(apple1);
        recipe.addIngredient(orange1);
        recipe.addIngredient(banana1);
        recipesInMeal.add(recipe);

        //Ingredients
        Ingredient apple2 = new Ingredient("apple", " ", LocalDate.now(), " ", " ", " ", 10);
        Ingredient orange2 = new Ingredient("orange", " ", LocalDate.now(), " ", " ", " ", 4);
        ingredientsInMeal.add(apple2);
        ingredientsInMeal.add(orange2);

        Meal meel = new Meal(recipesInMeal, ingredientsInMeal, LocalDate.now());
        mealPlansFromMDB.add(meel);

        //Meal 2
        ArrayList<Ingredient> ingredientsInMeal2 = new ArrayList<>();
        ArrayList<Recipe> recipesInMeal2 = new ArrayList<>();
        //Recipe 2

        Ingredient cream = new Ingredient("cream", " ", LocalDate.now(), " ", " ", " ", 2);
        Ingredient sugar = new Ingredient("sugar", " ", LocalDate.now(), " ", " ", " ", 6);
        Ingredient eggs = new Ingredient("eggs", " ", LocalDate.now(), " ", " ", " ", 1);
        Ingredient ice = new Ingredient("ice", " ", LocalDate.now(), " ", " ", " ", 50);
        Recipe iceCream = new Recipe("Icecream", " ", " ", 0, 1);
        iceCream.addIngredient(cream);
        iceCream.addIngredient(sugar);
        iceCream.addIngredient(eggs);
        iceCream.addIngredient(ice);
        recipesInMeal2.add(iceCream);

        //Ingredients (None)

        Meal meel2 = new Meal(recipesInMeal2, ingredientsInMeal2, LocalDate.now());
        mealPlansFromMDB.add(meel2);
        ArrayList<Ingredient> totalIngredientsforMealPlan = new ArrayList<>();

        for (Meal meal: mealPlansFromMDB){ // Get all the ingredients and total them from the meal plans
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

        //Storage
        ArrayList<Ingredient> ingredientsFromIDB = new ArrayList<>();
        Ingredient apple3 = new Ingredient("apple", " ", LocalDate.now(), " ", " ", " ", 21);
        Ingredient orange3 = new Ingredient("orange", " ", LocalDate.now(), " ", " ", " ", 30);
        Ingredient ice2 = new Ingredient("ice", " ", LocalDate.now(), " ", " ", " ", 40);
        ingredientsFromIDB.add(apple3);
        ingredientsFromIDB.add(orange3);
        ingredientsFromIDB.add(ice2);

        for (Ingredient i: totalIngredientsforMealPlan){ // This'll take the difference between what's in the IngredientDB and the totalIngredients for the meal plan
            if (ingredientsFromIDB.contains(i)){
                int index = ingredientsFromIDB.indexOf(i);
                Ingredient ingredientToSub = ingredientsFromIDB.get(index);
                ingredientToSub.setAmount(ingredientToSub.getAmount() - i.getAmount());
            }
            else {
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