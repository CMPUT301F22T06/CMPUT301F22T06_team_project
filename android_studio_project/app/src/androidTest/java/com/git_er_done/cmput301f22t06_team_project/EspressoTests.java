package com.git_er_done.cmput301f22t06_team_project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EspressoTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    // Will change
    @Test
    public void firstTest() {
        onView(withText("RECIPES FRAGMENT")).check(matches(isDisplayed()));
    }

    @Test
    public void testIngredientRecyclerView() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_ingredients_menu_item));


    }
}
