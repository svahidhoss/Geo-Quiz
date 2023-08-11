package com.bignerdranch.android.geoquiz

import androidx.test.core.app.ActivityScenario
import org.junit.Assert.*

import org.junit.After
import org.junit.Before

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
}
