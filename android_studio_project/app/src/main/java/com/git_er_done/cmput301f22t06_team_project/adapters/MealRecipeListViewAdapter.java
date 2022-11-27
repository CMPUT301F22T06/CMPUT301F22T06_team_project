package com.git_er_done.cmput301f22t06_team_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import java.util.ArrayList;

public class MealRecipeListViewAdapter extends ArrayAdapter<Recipe> {
    public MealRecipeListViewAdapter(@NonNull Context context, ArrayList<Recipe> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if(currentItemView == null){
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.meal_recipe_list_item, parent, false);
        }
        Recipe currentItemRecipe = getItem(position);

        TextView recipeName = currentItemView.findViewById(R.id.tv_meal_recipe_list_item_name);
        TextView recipeAmount = currentItemView.findViewById(R.id.tv_meal_recipe_list_item_servings);

        recipeName.setText(currentItemRecipe.getTitle());
        recipeAmount.setText(currentItemRecipe.getServings().toString());

        return currentItemView;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        MealRecipeListViewAdapter listAdapter = (MealRecipeListViewAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() + listItem.getMeasuredHeightAndState()/2;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
