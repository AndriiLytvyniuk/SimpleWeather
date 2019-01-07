package weather.simple.alytvyniuk.serverapi

import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed
import weather.simple.alytvyniuk.serverapi.retrofit.RetrofitApi
import java.lang.IllegalArgumentException

class ServerApi private constructor() {

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/"
        const val ACCESS_TOKEN = "a1d1dc41d71e2b1c1d329e64770bf088"
        const val MAX_GROUP_REQUEST_NUMBER = 20
        val instance = ServerApi()
    }
    private val api = RetrofitApi()

    fun requestCityGroupWeather(listener: ServerApi.ServerApiListener, cities: List<City>) {
        if (cities.size > MAX_GROUP_REQUEST_NUMBER) {
            throw IllegalArgumentException("Max group request can be $MAX_GROUP_REQUEST_NUMBER")
        }
        api.requestCityGroupWeather(listener, cities)
    }

    interface ServerApiListener {
        fun onSuccess(weathers : List<CityWeatherDisplayed>)
        fun onError()
    }
}