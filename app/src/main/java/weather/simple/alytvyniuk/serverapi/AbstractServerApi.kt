package weather.simple.alytvyniuk.serverapi

import weather.simple.alytvyniuk.serverapi.ServerApi.Companion.MAX_GROUP_REQUEST_NUMBER
import weather.simple.alytvyniuk.serverapi.model.City
import java.lang.IllegalArgumentException

abstract class AbstractServerApi : ServerApi {

    override fun requestCityGroupWeather(listener: ServerApi.ServerApiListener, vararg cities: City) {
        if (cities.size > MAX_GROUP_REQUEST_NUMBER) {
            throw IllegalArgumentException("Max group request can be $MAX_GROUP_REQUEST_NUMBER")
        }
    }

}