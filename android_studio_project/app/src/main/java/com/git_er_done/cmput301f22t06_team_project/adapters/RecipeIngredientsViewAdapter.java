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
import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeIngredient;

import java.util.ArrayList;

public class RecipeIngredientsViewAdapter extends ArrayAdapter<RecipeIngredient> {
    private ArrayList<RecipeIngredient> recipeIngredients;
    private Context context;
    private TextView name;
    private EditText comment;
    private Button minusButton;
    private Button plusButton;
    private EditText unit;
    private Button deleteButton;

    public RecipeIngredientsViewAdapter(ArrayList<RecipeIngredient> recipeIngredients, Context context){
        super(context, R.layout.recipe_ingredient_item_layout, recipeIngredients);
        this.context = context;
        this.recipeIngredients = recipeIngredients;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: Cry");
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.recipe_ingredient_item_layout,parent,false);
        }

        RecipeIngredient recipeIngredient = recipeIngredients.get(position);

        setViews(listItem);
        final TextView amount = (TextView) listItem.findViewById(R.id.amount_of_ingredient);;

        name.setText(recipeIngredient.getName());
        comment.setText(recipeIngredient.getComment());
        amount.setText(String.valueOf(recipeIngredient.getAmount()));
        unit.setText(recipeIngredient.getUnits());

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer intAmount = Integer.parseInt((String) amount.getText());
                if(intAmount > 0){
                    intAmount = intAmount - 1;
                    amount.setText(String.valueOf(intAmount));
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

        notifyDataSetChanged();

        return listItem;
    }

    private void setViews(View listItem){
        name = (TextView) listItem.findViewById(R.id.name_of_ingredient);
        comment = (EditText) listItem.findViewById(R.id.comment_of_ingredient);
        minusButton = (Button) listItem.findViewById(R.id.minus_button);
        plusButton = (Button) listItem.findViewById(R.id.plus_button);
        unit = (EditText) listItem.findViewById(R.id.unit_of_ingredient);
        deleteButton = (Button) listItem.findViewById(R.id.delete_button);
    }

}
