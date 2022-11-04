package com.git_er_done.cmput301f22t06_team_project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


// TODO: Change checks once placeholder text is gone

@RunWith(AndroidJUnit4.class)
public class EspressoTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testIngredientRecyclerView() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_ingredients_menu_item));

        onView(withText("apple")).check(matches(isDisplayed()));
    }

    @Test
    public void testMealPlannerRecyclerView() {

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_meal_planner_menu_item));

        onView(withText("MEAL PLANNER FRAGMENT")).check(matches(isDisplayed()));
    }

    @Test
    public void testShoppingListRecyclerView() {

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_shopping_list_menu_item));

        onView(withText("SHOPPING LIST FRAGMENT")).check(matches(isDisplayed()));
    }

    @Test
    public void testRecipeRecyclerView() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_recipes_menu_item));

        onView(withText("RECIPES FRAGMENT")).check(matches(isDisplayed()));
    }

    @Test
    public void testNavDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        onView(withText("I like to eat food")).check(matches(isDisplayed()));

        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.close());

        onView(withText("RECIPES FRAGMENT")).check(matches(isDisplayed()));
    }

    @Test
    public void testIngredientEdit() {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_ingredients_menu_item));

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.et_ingredient_add_edit_amount))
                .perform(replaceText("1"));

        onView(withId(R.id.btn_ingredient_add_edit_save))
                .perform(click());

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withText("1")).check(matches(isDisplayed()));
    }
}
