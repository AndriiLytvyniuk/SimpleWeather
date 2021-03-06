package weather.simple.alytvyniuk.serverapi.retrofit

import android.util.Log
import androidx.annotation.VisibleForTesting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityGroupWeather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import weather.simple.alytvyniuk.serverapi.IServerApi
import weather.simple.alytvyniuk.serverapi.model.CityWeather
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed


class RetrofitApi {

    companion object {
        private const val TAG = "RetrofitApi"
    }

    private val service : WeatherRetrofitService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val retrofit = Retrofit.Builder()
            .baseUrl(IServerApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        service = retrofit.create(WeatherRetrofitService::class.java)
    }

    fun requestCityGroupWeather(listener: IServerApi.ServerApiListener, cities: List<City>) {
        val citiesString = getCitesString(cities)
        service.getCityGroupWeather(citiesString).enqueue(object : Callback<CityGroupWeather> {
            override fun onFailure(call: Call<CityGroupWeather>, t: Throwable) {
                Log.e("Andrii", "onError", t)
                listener.onError()
            }

            override fun onResponse(call: Call<CityGroupWeather>, response: Response<CityGroupWeather>) {
                val result = response.body()?.list
                if (result == null) {
                    listener.onError()
                } else {
                    listener.onSuccess(cityWeatherToCityWeatherDisplayed(result))
                }
            }
        })
    }

    fun cityWeatherToCityWeatherDisplayed(weatherList: List<CityWeather>) : List<CityWeatherDisplayed> {
        val time = System.currentTimeMillis()
        return weatherList.map {
            CityWeatherDisplayed(
                it.id,
                it.name,
                it.sys.country,
                it.weather[0].description,
                Math.round(it.main.temp).toInt(),
                it.weather[0].icon,
                time)
        }
    }

    @VisibleForTesting
    fun getCitesString(cities: List<City>)
            = cities.map { it.id }.joinToString(",")

}