package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Lab 7 Participation: tests for switching to ShowActivity, displaying clicked city name,
 * and returning to MainActivity via a back button.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        // Needed for Intent assertions (activity switch test)
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    /**
     * 1) Check whether the activity correctly switched to ShowActivity.
     */
    @Test
    public void testActivitySwitchToShowActivity() {
        // Precondition: add at least one city so there is something to click.
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(androidx.test.espresso.action.ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click the first item in the city list.
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify that ShowActivity was launched.
        intended(hasComponent(ShowActivity.class.getName()));
    }

    /**
     * 2) Test whether the city name shown in ShowActivity matches what was clicked.
     */
    @Test
    public void testCityNameIsConsistentInShowActivity() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(androidx.test.espresso.action.ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Assumes ShowActivity has a TextView with id text_city_name.
        onView(withId(R.id.text_city_name)).check(matches(withText("Vancouver")));
    }

    /**
     * 3) Test the ShowActivity back button returns to MainActivity.
     */
    @Test
    public void testBackButtonReturnsToMainActivity() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(androidx.test.espresso.action.ViewActions.typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());

        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Assumes ShowActivity has a back button with id button_back.
        onView(withId(R.id.button_back)).perform(click());

        // Back on MainActivity: confirm main list is visible.
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
