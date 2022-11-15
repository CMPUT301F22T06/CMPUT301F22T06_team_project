package com.git_er_done.cmput301f22t06_team_project;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.*;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import android.view.Gravity;

import androidx.test.espresso.contrib.*;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


// TODO: Change checks once placeholder text is gone
// TODO: Need a better way to detect which fragment is being shown

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

        onView(withText("cooking wine")).check(matches(isDisplayed()));
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

        onView(withId(R.id.fab_ingredient_add)).perform(click());

        onView(withId(R.id.et_ingredient_add_edit_name)).perform(replaceText("9V"));
        onView(withId(R.id.et_ingredient_add_edit_description)).perform(replaceText("Energizer"));
        onView(withId(R.id.dp_ingredient_add_edit_best_before_date)).perform(PickerActions.setDate(2023, 11, 14));

        onView(withId(R.id.sp_ingredient_add_edit_location)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("pantry"))).inRoot(isPlatformPopup()).perform(click()); // select pantry location

        onView(withId(R.id.et_ingredient_add_edit_amount)).perform(replaceText("3"));
        onView(withId(R.id.sp_ingredient_add_edit_unit)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("singles"))).inRoot(isPlatformPopup()).perform(click()); // select oz for units
        onView(withId(R.id.sp_ingredient_add_edit_category)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("miscellaneous"))).inRoot(isPlatformPopup()).perform(click()); // select vegetable type

        onView(withId(R.id.btn_ingredient_add_edit_save)).perform(click());

        // End create 9V

        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_ingredients_menu_item));

        //onView(withId(R.id.rv_ingredients_list))
        //        .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("9v")), longClick()));

        // Set amount to something other than original
        onView(withId(R.id.et_ingredient_add_edit_amount))
                .perform(replaceText("1"));

        onView(withId(R.id.btn_ingredient_add_edit_save))
                .perform(click());

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("9v")), longClick()));

        // Set amount to something other than the first set to ensure value actually changed.
        onView(withId(R.id.et_ingredient_add_edit_amount))
                .perform(replaceText("3"));

        onView(withId(R.id.btn_ingredient_add_edit_save))
                .perform(click());

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("9v")), longClick()));

        onView(withText("3")).check(matches(isDisplayed()));
    }

    @Test
    public void testIngredientEditFragmentDisplayed() {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_ingredients_menu_item));

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.et_ingredient_add_edit_name)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddIngredient() {
        create9V();

//        onView(withId(R.id.rv_ingredients_list))
//                .check(hasDescendant(withText("potato")));

    }

    public void create9V() {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_ingredients_menu_item));

        onView(withId(R.id.fab_ingredient_add))
                .perform(click());

        onView(withId(R.id.et_ingredient_add_edit_name)).perform(replaceText("9V"));
        onView(withId(R.id.et_ingredient_add_edit_description)).perform(replaceText("Energizer"));
        onView(withId(R.id.dp_ingredient_add_edit_best_before_date)).perform(PickerActions.setDate(2023, 11, 14));

        onView(withId(R.id.sp_ingredient_add_edit_location)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("pantry"))).inRoot(isPlatformPopup()).perform(click()); // select pantry location

        onView(withId(R.id.et_ingredient_add_edit_amount)).perform(replaceText("3"));
        onView(withId(R.id.sp_ingredient_add_edit_unit)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("singles"))).inRoot(isPlatformPopup()).perform(click()); // select oz for units
        onView(withId(R.id.sp_ingredient_add_edit_category)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("miscellaneous"))).inRoot(isPlatformPopup()).perform(click()); // select vegetable type

        onView(withId(R.id.btn_ingredient_add_edit_save)).perform(click());
    }
}
