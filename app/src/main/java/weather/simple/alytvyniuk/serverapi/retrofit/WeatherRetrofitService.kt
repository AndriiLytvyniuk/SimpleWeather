package weather.simple.alytvyniuk.serverapi.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import weather.simple.alytvyniuk.serverapi.ServerApi
import weather.simple.alytvyniuk.serverapi.model.CitiGroupWeather

interface WeatherRetrofitService {

    @GET("data/2.5/group?units=metric&appid=${ServerApi.ACCESS_TOKEN}")
    fun getCityGroupWeather(@Query("id") groupIds : String) : Call<CitiGroupWeather>
}