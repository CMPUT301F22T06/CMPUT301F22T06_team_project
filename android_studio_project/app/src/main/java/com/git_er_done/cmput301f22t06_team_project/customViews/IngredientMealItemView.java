package com.git_er_done.cmput301f22t06_team_project.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
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

    private TextView tvIngredientName;
    private TextView tvAmount;
    private TextView tvUnit;
    public Button buttonAddMealIngredientAmount;
    public Button buttonMinusMealIngredientAmount;

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
        tvIngredientName = findViewById(R.id.tv_meal_ingredient_list_item_name);
        tvAmount = findViewById(R.id.tv_meal_ingredient_list_item_amount);
        tvUnit = findViewById(R.id.tv_meal_ingredient_list_item_unit);
        buttonAddMealIngredientAmount = findViewById(R.id.btn_meal_ingredient_add_amount);
        buttonMinusMealIngredientAmount = findViewById(R.id.btn_meal_ingredient_minus_amount);
        // On click listeners for buttons
        buttonAddMealIngredientAmount.setOnClickListener(v -> {
            amount += 1;
            tvAmount.setText(Integer.toString(amount));
        });

        buttonMinusMealIngredientAmount.setOnClickListener(v -> {
            if (amount >= 1) {
                amount -= 1;
                tvAmount.setText(Integer.toString(amount));
            } else {
                tvAmount.setText("0");
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
     * Returns the currently displayed amount of ingredients
     *
     * @return The current number of ingredients as an {@link Integer} wrapper
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the value to be displayed
     *
     * @param amount The number to be displayed as an {@link Integer} wrapper
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
        tvAmount.setText(Integer.toString(amount));
    }

    /**
     * Sets the units to be displayed
     *
     * @param unit The unit of measure of the ingredient as a {@link String}
     */
    public void setUnit(String unit) {
        this.unit = unit;
        tvUnit.setText(unit);
    }

    /**
     * Returns the currently displayed unit of measure
     *
     * @return The displayed Unit of Measure as a {@link String}
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the name to be displayed beside the counter
     *
     * @param name The name of the Ingredient being displayed as a {@link String}
     */
    public void setName(String name) {
        this.name = name;
        tvIngredientName.setText(name);
    }

    /**
     * Returns the currently displayed Ingredient name
     *
     * @return the name of the displayed ingredient as a {@link String}
     */
    public String getName() {
        return name;
    }
}
