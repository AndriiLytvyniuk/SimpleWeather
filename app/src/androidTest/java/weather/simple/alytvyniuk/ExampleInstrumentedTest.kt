package weather.simple.alytvyniuk

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Simple test to check if layout with connection error apears
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class OfflineModeTest {

    @get:Rule
    var activityRule: ActivityTestRule<WeatherListActivity>
            = ActivityTestRule(WeatherListActivity::class.java)

    @Test
    fun testOfflineModeActivity() {
        onView(withId(R.id.tv_offline_time))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}
