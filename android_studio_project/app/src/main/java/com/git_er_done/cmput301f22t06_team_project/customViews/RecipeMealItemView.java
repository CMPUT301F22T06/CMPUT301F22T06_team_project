package com.git_er_done.cmput301f22t06_team_project.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.git_er_done.cmput301f22t06_team_project.R;

// Followed https://blog.netcetera.com/creating-your-first-android-custom-view-f4666925956

@SuppressLint("SetTextI18n")
public class RecipeMealItemView extends LinearLayout {

    private TextView tv_recipeName;
    private TextView tv_amount;
    private Button add;
    private Button minus;

    private String name;
    private Integer amount;

    public RecipeMealItemView(Context context) {
        super(context);
        init();

    }

    public RecipeMealItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        obtainStyledAttributes(context, attrs, 0);
    }

    public RecipeMealItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        obtainStyledAttributes(context, attrs, defStyleAttr);
    }

    private void init() {
        inflate(getContext(), R.layout.meal_recipe_list_item, this);
        tv_recipeName = findViewById(R.id.tv_meal_recipe_list_item_name);
        tv_amount = findViewById(R.id.tv_meal_recipe_list_item_servings);
        add = findViewById(R.id.btn_meal_recipe_add_serving);
        minus = findViewById(R.id.btn_meal_recipe_minus_serving);
        // On click listeners for buttons
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += 1;
                tv_amount.setText(Integer.toString(amount));
            }
        });

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount >= 1) {
                    amount -= 1;
                    tv_amount.setText(Integer.toString(amount));
                } else {
                    tv_amount.setText("0");
                }
            }
        });
    }

    private void obtainStyledAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IngredientMealItemView, defStyleAttr, 0);

            name = typedArray.getString(R.styleable.IngredientMealItemView_name);
            amount = typedArray.getInteger(R.styleable.IngredientMealItemView_amount, 0);
            return;
        }
        name = "N/A";
        amount = 0;
    }
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
        tv_amount.setText(Integer.toString(amount));
    }

    public void setName(String name) {
        this.name = name;
        tv_recipeName.setText(name);
    }

    public String getName() {
        return name;
    }
}
