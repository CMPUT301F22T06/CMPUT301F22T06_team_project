package com.git_er_done.cmput301f22t06_team_project.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import java.util.ArrayList;

public class MealIngredientListViewAdapter extends ArrayAdapter<Ingredient> {
    public MealIngredientListViewAdapter(@NonNull Context context, ArrayList<Ingredient> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if(currentItemView == null){
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.meal_ingredient_list_item, parent, false);
        }
        Ingredient currentItemIngredient = getItem(position);

        TextView ingredientName = currentItemView.findViewById(R.id.tv_meal_ingredient_list_item_name);
        TextView ingredientAmount = currentItemView.findViewById(R.id.tv_meal_ingredient_list_item_amount);
        TextView ingredientUnit= currentItemView.findViewById(R.id.tv_meal_ingredient_list_item_unit);

        ingredientName.setText(currentItemIngredient.getName());
        ingredientAmount.setText(currentItemIngredient.getAmount().toString());
        ingredientUnit.setText(currentItemIngredient.getUnit());


        return currentItemView;
    }
}
