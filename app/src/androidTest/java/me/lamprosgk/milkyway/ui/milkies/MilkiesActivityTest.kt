package me.lamprosgk.milkyway.ui.milkies

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import io.reactivex.Single
import me.lamprosgk.cvapp.util.withRecyclerView
import me.lamprosgk.milkyway.MilkyWayApplication
import me.lamprosgk.milkyway.NETWORK_DELAY_MS
import me.lamprosgk.milkyway.R
import me.lamprosgk.milkyway.TestDataProvider.errorObservable
import me.lamprosgk.milkyway.TestDataProvider.milkies
import me.lamprosgk.milkyway.TestDataProvider.networkErrorObservable
import me.lamprosgk.milkyway.TestDataProvider.successObservable
import me.lamprosgk.milkyway.WAIT_FOR_NETWORK_CALL
import me.lamprosgk.milkyway.di.DaggerTestComponent
import me.lamprosgk.milkyway.di.PresenterModule
import me.lamprosgk.milkyway.di.TestRepositoryModule
import me.lamprosgk.milkyway.domain.model.Milky
import me.lamprosgk.milkyway.domain.repository.MilkiesRepository
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import org.mockito.Mockito.`when` as whenever


class MilkiesActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MilkiesActivity::class.java,true,false)

    private val app: MilkyWayApplication = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
            as MilkyWayApplication

    @Inject
    lateinit var repository: MilkiesRepository

    @Before
    fun setUp() {

        val testComponent = DaggerTestComponent.builder()
            .testRepositoryModule(TestRepositoryModule())
            .presenterModule(PresenterModule())
            .build()

        testComponent.inject(this)
        app.appComponent = testComponent
    }

    @Test
    fun testToolbarTitleIsCorrect() {
        setMockRepositoryData(successObservable)
        activityRule.launchActivity(Intent())
        onView(withId(R.id.toolbar_title)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar_title)).check(matches(withText(R.string.toolbar_title)))
    }

    @Test
    fun testMessageVisibleOnScreenWhenLoading() {
        setMockRepositoryData(successObservable)
        activityRule.launchActivity(Intent())
        onView(withId(R.id.emptyTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyTextView)).check(matches(withText(R.string.message_loading)))
    }

    @Test
    fun testCorrectViewsAreVisibleOnSearchResult() {
        setMockRepositoryData(successObservable)
        launchActivityAndWaitForResponse()
        onView(withId(R.id.emptyTextView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.milkiesRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testViewsDisplayCorrectResults() {
        setMockRepositoryData(successObservable)
        launchActivityAndWaitForResponse()

        verifyRecyclerViewItemData(0)
        verifyRecyclerViewItemData(5)
    }

    private fun verifyRecyclerViewItemData(position: Int) {
        val milky = milkies[position]
        onView(withId(R.id.milkiesRecyclerView)).perform(scrollToPosition<MilkiesAdapter.ViewHolder>(position))

        onView(withRecyclerView(R.id.milkiesRecyclerView).atPosition(position))
            .check(matches(hasDescendant(withText(milky.title))))
        onView(withRecyclerView(R.id.milkiesRecyclerView).atPosition(position))
            .check(matches(hasDescendant(withText(milky.center))))
        onView(withRecyclerView(R.id.milkiesRecyclerView).atPosition(position))
            .check(matches(hasDescendant(withText(milky.dateCreated))))
    }

    @Test
    fun testMessageShownOnError() {
        setMockRepositoryData(errorObservable)
        launchActivityAndWaitForResponse()

        // verify views not visible
        onView(withId(R.id.milkiesRecyclerView)).check(matches(not(isDisplayed())))
        // error message visible
        onView(withId(R.id.emptyTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyTextView)).check(matches(withText(R.string.message_error_generic)))
    }

    @Test
    fun testMessageShownOnNetworkError() {
        setMockRepositoryData(networkErrorObservable)
        launchActivityAndWaitForResponse()

        // verify views not visible
        onView(withId(R.id.milkiesRecyclerView)).check(matches(not(isDisplayed())))
        // error message visible
        onView(withId(R.id.emptyTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyTextView)).check(matches(withText(R.string.message_error_connection)))
    }

    private fun setMockRepositoryData(responseObservable: Single<List<Milky>>) {
        // set response data and mimic network delay
        whenever(repository.getImages(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(responseObservable.delay(NETWORK_DELAY_MS, TimeUnit.MILLISECONDS))
    }

    private fun launchActivityAndWaitForResponse() {
        activityRule.launchActivity(Intent())
        Thread.sleep(WAIT_FOR_NETWORK_CALL)
    }

}