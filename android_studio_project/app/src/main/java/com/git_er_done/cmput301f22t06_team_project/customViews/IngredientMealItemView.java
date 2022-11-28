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

/**
 * A custom view for displaying Ingredients in the meal planner calendar
 *
 * @author Kirk Rieberger
 */
@SuppressLint("SetTextI18n")
public class IngredientMealItemView extends LinearLayout {

    private TextView tv_ingredientName;
    private TextView tv_amount;
    private TextView tv_unit;
    private Button add;
    private Button minus;

    private String name;
    private Integer amount;
    private String unit;

    /**
     * Constructor to be used by the compiler
     *
     * @param context The application environment
     */
    public IngredientMealItemView(Context context) {
        super(context);
        init();

    }

    /**
     * Constructor to be used by the compiler
     *
     * @param context The application environment
     * @param attrs   A collection of attributes
     */
    public IngredientMealItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        obtainStyledAttributes(context, attrs, 0);
    }

    /**
     * Constructor to be used by the compiler
     *
     * @param context      The application environment
     * @param attrs        A collection of attributes
     * @param defStyleAttr The default style attribute value
     */
    public IngredientMealItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        obtainStyledAttributes(context, attrs, defStyleAttr);
    }

    /**
     * Initialization function to set up the contained views and onClick listeners
     */
    private void init() {
        inflate(getContext(), R.layout.meal_ingredient_list_item, this);
        tv_ingredientName = findViewById(R.id.tv_meal_ingredient_list_item_name);
        tv_amount = findViewById(R.id.tv_meal_ingredient_list_item_amount);
        tv_unit = findViewById(R.id.tv_meal_ingredient_list_item_unit);
        add = findViewById(R.id.btn_meal_ingredient_add_amount);
        minus = findViewById(R.id.btn_meal_ingredient_minus_amount);
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

    /**
     * An initialization function to get default style attributes from the associated .xml file
     *
     * @param context      The application environment
     * @param attrs        A collection of attributes
     * @param defStyleAttr The default style attribute value
     */
    private void obtainStyledAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IngredientMealItemView, defStyleAttr, 0);

            name = typedArray.getString(R.styleable.IngredientMealItemView_ing_name);
            amount = typedArray.getInteger(R.styleable.IngredientMealItemView_ing_amount, 0);
            unit = typedArray.getString(R.styleable.IngredientMealItemView_ing_unit);
            return;
        }
        name = "N/A";
        amount = 0;
        unit = "N/A";
    }

    /**
     * @return
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
        tv_amount.setText(Integer.toString(amount));
    }

    /**
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
        tv_unit.setText(unit);
    }

    /**
     * @return
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        tv_ingredientName.setText(name);
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }
}
