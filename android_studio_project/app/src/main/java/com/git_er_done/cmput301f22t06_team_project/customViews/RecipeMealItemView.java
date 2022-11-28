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
 * A custom view for displaying Recipes in the meal planner calendar
 *
 * @author Kirk Rieberger
 */
@SuppressLint("SetTextI18n")
public class RecipeMealItemView extends LinearLayout {

    private TextView tvRecipeTitle;
    private TextView tvServings;
    public Button buttonAddMealRecipeServing;
    public Button buttonMinusMealRecipeServing;

    private String title;
    private Integer servings;

    /**
     * Constructor to be used by the compiler
     *
     * @param context The application environment
     */
    public RecipeMealItemView(Context context) {
        super(context);
        init();

    }

    /**
     * Constructor to be used by the compiler
     *
     * @param context The application environment
     * @param attrs   A collection of attributes
     */
    public RecipeMealItemView(Context context, AttributeSet attrs) {
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
    public RecipeMealItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        obtainStyledAttributes(context, attrs, defStyleAttr);
    }

    /**
     * Initialization function to set up the contained views and onClick listeners
     */
    private void init() {
        inflate(getContext(), R.layout.meal_recipe_list_item, this);
        tvRecipeTitle = findViewById(R.id.tv_meal_recipe_list_item_name);
        tvServings = findViewById(R.id.tv_meal_recipe_list_item_servings);
        buttonAddMealRecipeServing = findViewById(R.id.btn_meal_recipe_add_serving);
        buttonMinusMealRecipeServing = findViewById(R.id.btn_meal_recipe_minus_serving);
        // On click listeners for buttons
        buttonAddMealRecipeServing.setOnClickListener(v -> {
            servings += 1;
            tvServings.setText(Integer.toString(servings));
        });

        buttonMinusMealRecipeServing.setOnClickListener(v -> {
            if (servings >= 1) {
                servings -= 1;
                tvServings.setText(Integer.toString(servings));
            } else {
                tvServings.setText("0");
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

            title = typedArray.getString(R.styleable.RecipeMealItemView_rec_name);
            servings = typedArray.getInteger(R.styleable.RecipeMealItemView_rec_amount, 0);
            return;
        }
        title = "N/A";
        servings = 0;
    }

    /**
     * Returns the currently displayed number of servings
     *
     * @return The current number of recipe servings as an {@link Integer} wrapper
     */
    public Integer getServings() {
        return servings;
    }

    /**
     * Sets the number of servings to be displayed
     *
     * @param servings The number of servings to be displayed as an {@link Integer} wrapper
     */
    public void setServings(Integer servings) {
        this.servings = servings;
        tvServings.setText(Integer.toString(servings));
    }

    /**
     * Sets the name to be displayed beside the counter
     *
     * @param title The name of the Ingredient being displayed as a {@link String}
     */
    public void setTitle(String title) {
        this.title = title;
        tvRecipeTitle.setText(title);
    }

    /**
     * Returns the currently displayed Ingredient name
     *
     * @return the name of the displayed ingredient as a {@link String}
     */
    public String getTitle() {
        return title;
    }
}
