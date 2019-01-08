package weather.simple.alytvyniuk.serverapi

import weather.simple.alytvyniuk.serverapi.IServerApi.Companion.MAX_GROUP_REQUEST_NUMBER
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.retrofit.RetrofitApi
import java.lang.IllegalArgumentException

class ServerApi private constructor(): IServerApi  {

    companion object {

        val instance = ServerApi()
    }
    private val api = RetrofitApi()

    override fun requestCityGroupWeather(listener: IServerApi.ServerApiListener, cities: List<City>) {
        if (cities.size > MAX_GROUP_REQUEST_NUMBER) {
            throw IllegalArgumentException("Max group request can be $MAX_GROUP_REQUEST_NUMBER")
        }
        api.requestCityGroupWeather(listener, cities)
    }
}