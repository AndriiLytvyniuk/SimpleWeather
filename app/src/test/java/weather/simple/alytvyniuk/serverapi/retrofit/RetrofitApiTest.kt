package weather.simple.alytvyniuk.serverapi.retrofit

import org.junit.Test

import org.junit.Assert.*
import weather.simple.alytvyniuk.serverapi.model.City

class RetrofitApiTest {

    @Test
    fun getCitesStringTest() {
        val s = RetrofitApi().getCitesString(*getCityList().toTypedArray())
        assertEquals("703448,2643743,6167865", s)
    }

    private fun getCityList() : List<City> {
        return listOf(
            City("Kiev", 703448),
            City("London", 2643743),
            City("Toronto", 6167865)
        )
    }
}