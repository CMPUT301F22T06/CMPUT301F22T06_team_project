package com.git_er_done.cmput301f22t06_team_project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
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
        // TODO: Idler for FireStore so it doesn't generate an internal error
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
}
