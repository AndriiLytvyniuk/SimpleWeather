package weather.simple.alytvyniuk.serverapi.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import weather.simple.alytvyniuk.serverapi.IServerApi
import weather.simple.alytvyniuk.serverapi.model.CityGroupWeather

interface WeatherRetrofitService {

    @GET("data/2.5/group?units=metric&appid=${IServerApi.ACCESS_TOKEN}")
    fun getCityGroupWeather(@Query("id") groupIds : String) : Call<CityGroupWeather>
}