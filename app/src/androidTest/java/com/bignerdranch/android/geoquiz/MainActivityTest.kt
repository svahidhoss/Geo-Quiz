package com.bignerdranch.android.geoquiz

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun showsFirstQuestionOnLaunch() {
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.question_australia)))
    }

    @Test
    fun showsSecondQuestionAfterNextButtonPress() {
        onView(withId(R.id.next_button)).perform(click())
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.question_oceans)))
    }

    @Test
    fun handleActivityRecreation() {
        onView(withId(R.id.next_button)).perform(click())
        // To mimic recreation of activity (device rotation test)
        scenario.recreate()
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.question_oceans)))
    }

    @Test
    fun showsFirstQuestionAfterPrevButtonPress() {
        onView(withId(R.id.prev_button)).perform(click())
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.question_asia)))
    }
}
