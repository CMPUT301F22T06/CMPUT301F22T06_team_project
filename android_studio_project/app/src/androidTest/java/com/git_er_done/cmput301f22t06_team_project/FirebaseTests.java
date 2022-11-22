package com.git_er_done.cmput301f22t06_team_project;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FirebaseTests {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addIngredientTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Drawer Open"),
                        childAtPosition(
                                allOf(withId(R.id.my_toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
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
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatEditText.perform(scrollTo(), replaceText("Battery"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.et_ingredient_add_edit_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
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
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_ingredient_add_edit_save), withText("SAVE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        6),
                                1)));
        appCompatButton.perform(scrollTo(), click());
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
