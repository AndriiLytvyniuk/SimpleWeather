package weather.simple.alytvyniuk.serverapi.retrofit

import android.support.annotation.VisibleForTesting
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import weather.simple.alytvyniuk.serverapi.AbstractServerApi
import weather.simple.alytvyniuk.serverapi.ServerApi
import weather.simple.alytvyniuk.serverapi.model.City

class RetrofitApi : AbstractServerApi() {

    private val service : WeatherRetrofitService

    companion object {
        val instance = RetrofitApi()
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(ServerApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(WeatherRetrofitService::class.java)
    }

    override fun requestCityGroupWeather(listener: ServerApi.ServerApiListener, vararg cities: City) {
        super.requestCityGroupWeather(listener, *cities)
        val citiesString = getCitesString(*cities)
        service.getCityGroupWeather(citiesString)
    }

    @VisibleForTesting
    fun getCitesString(vararg cities: City)
            = cities.map { it.id }.joinToString(",")

}