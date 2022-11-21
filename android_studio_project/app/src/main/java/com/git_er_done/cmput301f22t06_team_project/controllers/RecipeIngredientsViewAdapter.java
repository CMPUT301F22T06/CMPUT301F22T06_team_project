package com.git_er_done.cmput301f22t06_team_project.controllers;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientsViewAdapter extends ArrayAdapter<RecipeIngredient> {
    private ArrayList<RecipeIngredient> recipeIngredients;
    private Context context;
    private TextView name;
    private EditText comment;
    private Button minus_button;
    //private TextView amount;
    private Button plus_button;
    private EditText unit;
    private Button delete_button;

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

        minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer int_amount = Integer.parseInt((String) amount.getText());
                int_amount = int_amount - 1;
                amount.setText(String.valueOf(int_amount));
            }
        });

        amount.setText(String.valueOf(recipeIngredient.getAmount()));

        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer int_amount = Integer.parseInt((String) amount.getText());
                int_amount = int_amount + 1;
                amount.setText(String.valueOf(int_amount));
            }
        });

        unit.setText(recipeIngredient.getUnits());

        delete_button.setOnClickListener(new View.OnClickListener() {
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
        minus_button = (Button) listItem.findViewById(R.id.minus_button);
        //amount = (TextView) listItem.findViewById(R.id.amount_of_ingredient);
        plus_button = (Button) listItem.findViewById(R.id.plus_button);
        unit = (EditText) listItem.findViewById(R.id.unit_of_ingredient);
        delete_button = (Button) listItem.findViewById(R.id.delete_button);
    }

}
