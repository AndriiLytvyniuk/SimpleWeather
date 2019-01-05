package weather.simple.alytvyniuk.serverapi

import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ServerApiTest {

    @Test
    fun requestCityGroupWeatherTest() {
        val cityList = getCityList()
        val result = arrayOf(false)
        val latch = CountDownLatch(1)
        ServerApi.instance.requestCityGroupWeather(object : ServerApi.ServerApiListener {
            override fun onSuccess(weathers: List<CityWeatherDisplayed>) {
                Assert.assertEquals(weathers.size, cityList.size)
                result[0] = true
                latch.countDown()
            }

            override fun onError() {
                result[0] = false
                fail()
            }

        }, *cityList.toTypedArray())
        latch.await(3, TimeUnit.SECONDS)
        assertTrue(result[0])
    }

    private fun getCityList() : List<City> {
        return listOf(
            City("Kiev", 703448),
            City("London", 2643743),
            City("Toronto", 6167865)
        )
    }
}