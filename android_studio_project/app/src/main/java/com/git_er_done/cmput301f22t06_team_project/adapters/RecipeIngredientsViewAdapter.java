package com.git_er_done.cmput301f22t06_team_project.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import java.util.ArrayList;

/**
 * @author Saheel Sarker
 * @version 1.3
 * Makes the list of ingredients for each recipe including the buttons
 */

public class RecipeIngredientsViewAdapter extends ArrayAdapter<Ingredient> {

    private ArrayList<Ingredient> recipeIngredients;
    private Context context;
    private TextView name;
    private EditText comment;
    private Button minusButton;
    private Button plusButton;
    private TextView unit;
    private Button deleteButton;

    /**
     * This initializes the adapter
     * @param recipeIngredients which is a ArrayList<Ingredient>
     * @param context which is a Context
     */

    public RecipeIngredientsViewAdapter(ArrayList<Ingredient> recipeIngredients, Context context){
        super(context, R.layout.recipe_ingredient_item_layout, recipeIngredients);
        this.context = context;
        this.recipeIngredients = recipeIngredients;
    }

    /**
     * Creates each row in the list of ingredients for a recipe
     * @param position which is the position of a row in the list of type int
     * @param convertView which is the current row of type View
     * @param parent which is the listview that the row is in
     * @return listitem of type view
     */

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: Cry");
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.recipe_ingredient_item_layout,parent,false);
        }

        Ingredient recipeIngredient = recipeIngredients.get(position);

        setViews(listItem);
        final TextView amount = (TextView) listItem.findViewById(R.id.amount_of_ingredient);;

        name.setText(recipeIngredient.getName());
        comment.setText(recipeIngredient.getDesc());
        amount.setText(String.valueOf(recipeIngredient.getAmount()));
        unit.setText(recipeIngredient.getUnit());

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer intAmount = Integer.parseInt((String) amount.getText());
                if(intAmount > 0){
                    intAmount = intAmount - 1;
                    amount.setText(String.valueOf(intAmount));
                    recipeIngredient.setAmount(Integer.parseInt(amount.getText().toString()));

                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer intAmount = Integer.parseInt((String) amount.getText());
                if(intAmount < 998){
                    intAmount = intAmount + 1;
                    amount.setText(String.valueOf(intAmount));
                    recipeIngredient.setAmount(Integer.parseInt(amount.getText().toString()));
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(recipeIngredient);
                notifyDataSetChanged();
            }
        });

        recipeIngredient.setAmount(Integer.parseInt(amount.getText().toString()));

        comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    recipeIngredient.setDesc(comment.getText().toString());
                }
            }
        });

        notifyDataSetChanged();

        return listItem;
    }

    /**
     * Sets the views with the resources
     * @param listItem The view for each row
     */

    private void setViews(View listItem){
        name = (TextView) listItem.findViewById(R.id.name_of_ingredient);
        comment = (EditText) listItem.findViewById(R.id.comment_of_ingredient);
        minusButton = (Button) listItem.findViewById(R.id.minus_button);
        plusButton = (Button) listItem.findViewById(R.id.plus_button);
        unit = (TextView) listItem.findViewById(R.id.unit_of_ingredient);
        deleteButton = (Button) listItem.findViewById(R.id.delete_button);
    }

    /**
     * returns the list of ingredients for the recipe
     * @return recipeIngredients of type ArrayList<Ingredient>
     */
    public ArrayList<Ingredient> getRecipeIngredients() {
        return recipeIngredients;
    }

}
