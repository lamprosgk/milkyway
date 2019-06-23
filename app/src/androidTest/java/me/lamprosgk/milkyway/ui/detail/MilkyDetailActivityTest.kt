package me.lamprosgk.milkyway.ui.detail

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import me.lamprosgk.milkyway.R
import me.lamprosgk.milkyway.TestDataProvider.milkies
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MilkyDetailActivityTest {

    @get:Rule
    val intentsRule = IntentsTestRule(MilkyDetailActivity::class.java, true, false)
    private val milky = milkies.first()

    @Before
    fun setUp() {
        // launch activity with an entry from static json
        val i = Intent()
        i.putExtra(MilkyDetailActivity.EXTRA_IMAGE_TRANSITION_NAME, "")
        i.putExtra(MilkyDetailActivity.EXTRA_MILKY, milky)
        intentsRule.launchActivity(i)
    }

    @Test
    fun testAllViewsAreVisibleAndShowCorrectData() {
        onView(ViewMatchers.withId(R.id.milkyImageDetail)).check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.milkyDetailTitle)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.milkyDetailTitle)).check(matches(withText(milky.title)))

        onView(ViewMatchers.withId(R.id.milkyDetailCenterLabel)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.milkyDetailCenterLabel)).check(matches(withText(R.string.milky_detail_label_center)))

        onView(ViewMatchers.withId(R.id.milkyDetailCenter)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.milkyDetailCenter)).check(matches(withText(milky.center)))

        onView(ViewMatchers.withId(R.id.milkyDetailDateLabel)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.milkyDetailDateLabel)).check(matches(withText(R.string.milky_detail_label_date)))

        onView(ViewMatchers.withId(R.id.milkyDetailDate)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.milkyDetailDate)).check(matches(withText(milky.dateCreated)))

        onView(ViewMatchers.withId(R.id.milkyDetailDescription)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.milkyDetailDescription)).check(matches(withText(milky.description)))
    }
}