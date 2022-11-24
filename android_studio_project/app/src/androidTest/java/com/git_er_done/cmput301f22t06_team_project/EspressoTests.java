package com.git_er_done.cmput301f22t06_team_project;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.*;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.*;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


// TODO: Change checks once placeholder text is gone
// TODO: Need a better way to detect which fragment is being shown
// These tests are configured to run locally. Use "FirebaseTests" to run remotely.

@RunWith(AndroidJUnit4.class)
public class EspressoTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    // Fragment navigation Tests

    @Test
    public void testIngredientNavigation() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_ingredients_menu_item));

        ViewInteraction textView = onView(
                allOf(withText("Ingredients List"),
                        withParent(allOf(withId(R.id.my_toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Ingredients List")));
    }

    @Test
    public void testMealPlannerNavigation() {

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_meal_planner_menu_item));

        ViewInteraction textView = onView(
                allOf(withText("Meal Planner"),
                        withParent(allOf(withId(R.id.my_toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Meal Planner")));
    }

    @Test
    public void testShoppingListNavigation() {

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_shopping_list_menu_item));

        ViewInteraction textView = onView(
                allOf(withText("Shopping List"),
                        withParent(allOf(withId(R.id.my_toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Shopping List")));
    }

    @Test
    public void testRecipeNavigation() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_recipes_menu_item));

        ViewInteraction textView = onView(
                allOf(withText("Recipes"),
                        withParent(allOf(withId(R.id.my_toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Recipes")));
    }

    @Test
    public void testNavDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
                .check(matches(isOpen(Gravity.LEFT)));

        ViewInteraction textView = onView(
                allOf(withText("Menu"),
                        withParent(withParent(withId(com.google.android.material.R.id.navigation_header_container))),
                        isDisplayed()));
        textView.check(matches(withText("Menu")));

        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.close());

        ViewInteraction textView1 = onView(
                allOf(withText("Recipes"),
                        withParent(allOf(withId(R.id.my_toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView1.check(matches(withText("Recipes")));
    }

    // Ingredient Tests

    @Test
    public void testIngredientEdit() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Drawer Open"),
                        childAtPosition(
                                allOf(withId(R.id.my_toolbar),
                                        childAtPosition(
                                                withClassName(Matchers.is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_ingredients_menu_item),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_ingredient_add),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

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

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        // Set amount to something other than original
        onView(withId(R.id.et_ingredient_add_edit_amount))
                .perform(replaceText("1"));

        onView(withId(R.id.btn_ingredient_add_edit_save))
                .perform(click());

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        // Set amount to something other than the first set to ensure value actually changed.
        onView(withId(R.id.et_ingredient_add_edit_amount))
                .perform(replaceText("3"));

        onView(withId(R.id.btn_ingredient_add_edit_save))
                .perform(click());

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withText("3")).check(matches(isDisplayed()));

        onView(withId(R.id.btn_ingredient_add_edit_cancel))
                .perform(click());

        // Cleanup

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton = onView(
                allOf(withId(com.google.android.material.R.id.snackbar_action), withText("DELETE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("com.google.android.material.snackbar.Snackbar$SnackbarLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());
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
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Drawer Open"),
                        childAtPosition(
                                allOf(withId(R.id.my_toolbar),
                                        childAtPosition(
                                                withClassName(Matchers.is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_ingredients_menu_item),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_ingredient_add),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.et_ingredient_add_edit_name),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatEditText.perform(scrollTo(), replaceText("Battery"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.et_ingredient_add_edit_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.LinearLayout")),
                                        1),
                                1)));
        appCompatEditText2.perform(scrollTo(), replaceText("AAA"), closeSoftKeyboard());

        onView(withId(R.id.sp_ingredient_add_edit_location)).perform(click());
        onData(allOf(CoreMatchers.is(instanceOf(String.class)), CoreMatchers.is("pantry"))).inRoot(isPlatformPopup()).perform(click());

        onView(withId(R.id.sp_ingredient_add_edit_unit)).perform(click());
        onData(allOf(CoreMatchers.is(instanceOf(String.class)), CoreMatchers.is("singles"))).inRoot(isPlatformPopup()).perform(click()); // select oz for units

        onView(withId(R.id.sp_ingredient_add_edit_category)).perform(click());
        onData(allOf(CoreMatchers.is(instanceOf(String.class)), CoreMatchers.is("miscellaneous"))).inRoot(isPlatformPopup()).perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.et_ingredient_add_edit_amount),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_ingredient_add_edit_save), withText("SAVE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.LinearLayout")),
                                        6),
                                1)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_ingredient_list_item_name), withText("Battery"),
                        withParent(withParent(withId(R.id.background))),
                        isDisplayed()));
        textView.check(matches(withText("Battery")));
    }

    @Test
    public void testDeleteIngredient() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Drawer Open"),
                        childAtPosition(
                                allOf(withId(R.id.my_toolbar),
                                        childAtPosition(
                                                withClassName(Matchers.is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_ingredients_menu_item),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        onView(withId(R.id.rv_ingredients_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));

        ViewInteraction textView = onView(
                allOf(withText("Ingredients List"),
                        withParent(allOf(withId(R.id.my_toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Ingredients List")));
    }

    @Test
    public void testIngredientBlankName() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Drawer Open"),
                        childAtPosition(
                                allOf(withId(R.id.my_toolbar),
                                        childAtPosition(
                                                withClassName(Matchers.is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_ingredients_menu_item),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_ingredient_add),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.et_ingredient_add_edit_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.LinearLayout")),
                                        1),
                                1)));
        appCompatEditText2.perform(scrollTo(), replaceText("AAA"), closeSoftKeyboard());

        onView(withId(R.id.sp_ingredient_add_edit_location)).perform(click());
        onData(allOf(CoreMatchers.is(instanceOf(String.class)), CoreMatchers.is("pantry"))).inRoot(isPlatformPopup()).perform(click());

        onView(withId(R.id.sp_ingredient_add_edit_unit)).perform(click());
        onData(allOf(CoreMatchers.is(instanceOf(String.class)), CoreMatchers.is("singles"))).inRoot(isPlatformPopup()).perform(click()); // select oz for units

        onView(withId(R.id.sp_ingredient_add_edit_category)).perform(click());
        onData(allOf(CoreMatchers.is(instanceOf(String.class)), CoreMatchers.is("miscellaneous"))).inRoot(isPlatformPopup()).perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.et_ingredient_add_edit_amount),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_ingredient_add_edit_save), withText("SAVE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.LinearLayout")),
                                        6),
                                1)));
        appCompatButton.perform(scrollTo(), click());

        // TODO: Assert error checking occurred
    }

    // Recipe tests

    @Test
    public void testAddRecipe() {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_recipes_menu_item));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_recipe_add),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
