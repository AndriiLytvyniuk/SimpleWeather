package weather.simple.alytvyniuk.serverapi

import weather.simple.alytvyniuk.serverapi.model.City

interface ServerApi {

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/"
        const val ACCESS_TOKEN = "a1d1dc41d71e2b1c1d329e64770bf088"
        const val MAX_GROUP_REQUEST_NUMBER = 20
    }

    fun requestCityGroupWeather(listener: ServerApi.ServerApiListener, vararg cities: City)

    interface ServerApiListener {
        fun onSuccess()
        fun onError()
    }
}