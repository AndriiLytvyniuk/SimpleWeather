package weather.simple.alytvyniuk

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import weather.simple.alytvyniuk.db.WeatherDB
import weather.simple.alytvyniuk.serverapi.ServerApi
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed

class WeatherListViewModel(application : Application) : AndroidViewModel(application) {
    private lateinit var citiesWeather: MutableLiveData<List<CityWeatherDisplayed>>
    val weatherDB = Room.databaseBuilder(
        application,
        WeatherDB::class.java, "weather"
    ).allowMainThreadQueries().build()

    fun getWeatherDisplayed(cities: List<City>): LiveData<List<CityWeatherDisplayed>> {
        if (!::citiesWeather.isInitialized) {
            citiesWeather = MutableLiveData()
            val fromDB = weatherDB.getWeatherDao().getAll()
            citiesWeather.value = fromDB
            loadWeatherList(cities)
        }
        return citiesWeather
    }

    private fun loadWeatherList(cities: List<City>) {
        ServerApi.instance.requestCityGroupWeather(object : ServerApi.ServerApiListener {
            override fun onSuccess(weathers: List<CityWeatherDisplayed>) {
                weatherDB.getWeatherDao().insertAll(weathers)
                citiesWeather.value = weathers
            }

            override fun onError() {

            }

        }, *cities.toTypedArray())
    }

}