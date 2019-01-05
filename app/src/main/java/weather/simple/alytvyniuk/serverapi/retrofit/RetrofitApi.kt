package weather.simple.alytvyniuk.serverapi.retrofit

import androidx.annotation.VisibleForTesting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import weather.simple.alytvyniuk.serverapi.ServerApi
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityGroupWeather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import weather.simple.alytvyniuk.serverapi.model.CityWeather
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed


class RetrofitApi {

    private val service : WeatherRetrofitService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val retrofit = Retrofit.Builder()
            .baseUrl(ServerApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        service = retrofit.create(WeatherRetrofitService::class.java)
    }

    fun requestCityGroupWeather(listener: ServerApi.ServerApiListener, vararg cities: City) {
        val citiesString = getCitesString(*cities)
        service.getCityGroupWeather(citiesString).enqueue(object : Callback<CityGroupWeather> {
            override fun onFailure(call: Call<CityGroupWeather>, t: Throwable) {
                listener.onError()
            }

            override fun onResponse(call: Call<CityGroupWeather>, response: Response<CityGroupWeather>) {
                val result = response.body()?.list
                //TODO Think of success condition
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
                it.name,
                it.sys.country,
                it.weather[0].description,
                Math.round(it.main.temp).toInt(),
                it.weather[0].icon,
                time)
        }
    }

    @VisibleForTesting
    fun getCitesString(vararg cities: City)
            = cities.map { it.id }.joinToString(",")

}