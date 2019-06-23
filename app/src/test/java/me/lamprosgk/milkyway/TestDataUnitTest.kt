package me.lamprosgk.milkyway

import me.lamprosgk.milkyway.TestDataProvider.successResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TestDataUnitTest {

    @Test
    fun testTestDataLoadsCorrectly() {
        assertNotNull(successResponse)
        assertEquals("A monster in the Milky Way", successResponse.collection.items.first().info.first().title)
    }
}
