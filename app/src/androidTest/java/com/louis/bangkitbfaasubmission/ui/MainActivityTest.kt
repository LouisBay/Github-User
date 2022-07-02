package com.louis.bangkitbfaasubmission.ui

import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.louis.bangkitbfaasubmission.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private val dummyUserSearch = "LouisBayu"
    private val dummyTime = 1000L

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testLoadDetailUser() {
        onView(isRoot()).perform(waitFor(dummyTime))
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_users))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(15))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(15, click()))
        onView(isRoot()).perform(waitFor(dummyTime))
        onView(withId(R.id.iv_detail_avatar)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_name)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_username)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_repository_count)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_followers_count)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_following_count)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_company)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_location)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_blog)).check(matches(isDisplayed()))
    }

    @Test
    fun testSetTheme() {
        onView(isRoot()).perform(waitFor(dummyTime))
        onView(withId(R.id.toolbar_main)).check(matches(isDisplayed()))
        onView(withId(R.id.settings)).perform(click())
        onView(withId(R.id.switch_theme)).perform(click())
        onView(isRoot()).perform(waitFor(dummyTime))
        pressBack()
    }

    @Test
    fun testSearchUser(){
        onView(isRoot()).perform(waitFor(dummyTime))
        onView(withId(R.id.toolbar_main)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText(dummyUserSearch), closeSoftKeyboard(), pressKey(KeyEvent.KEYCODE_ENTER))
        onView(isRoot()).perform(waitFor(dummyTime))
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(isRoot()).perform(waitFor(dummyTime))
        onView(withId(R.id.tv_detail_username)).check(matches(withText(dummyUserSearch)))
    }

    @Test
    fun testAddtoFavorite(){
        onView(isRoot()).perform(waitFor(dummyTime))
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(isRoot()).perform(waitFor(dummyTime))
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(isRoot()).perform(waitFor(dummyTime))
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite)).perform(click())

    }

    private fun waitFor(time: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints() = isRoot()

            override fun getDescription() = "wait $time millis."

            override fun perform(uiController: UiController?, view: View?) { uiController?.loopMainThreadForAtLeast(time) }
        }
    }
}