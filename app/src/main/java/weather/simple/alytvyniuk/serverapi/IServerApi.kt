package weather.simple.alytvyniuk.serverapi

import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed

/**
 * Interface for Network calls
 */
interface IServerApi {

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/"
        const val ACCESS_TOKEN = "a1d1dc41d71e2b1c1d329e64770bf088"
        const val MAX_GROUP_REQUEST_NUMBER = 20
    }

    /**
     * Make a network request for weather data
     */
    fun requestCityGroupWeather(listener: IServerApi.ServerApiListener, cities: List<City>)

    /**
     * Simplified listener for network events
     * TODO if required, extend onError
     */
    interface ServerApiListener {
        fun onSuccess(weathers : List<CityWeatherDisplayed>)
        fun onError()
    }
}