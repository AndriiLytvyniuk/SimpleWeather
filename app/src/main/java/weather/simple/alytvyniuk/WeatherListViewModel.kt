package weather.simple.alytvyniuk

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import weather.simple.alytvyniuk.serverapi.ServerApi
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed

class WeatherListViewModel : ViewModel() {
    private lateinit var citiesWeather: MutableLiveData<List<CityWeatherDisplayed>>

    fun getWeatherDisplayed(cities: List<City>): LiveData<List<CityWeatherDisplayed>> {
        if (!::citiesWeather.isInitialized) {
            citiesWeather = MutableLiveData()
            loadWeatherList(cities)
        }
        return citiesWeather
    }

    private fun loadWeatherList(cities: List<City>) {
        ServerApi.instance.requestCityGroupWeather(object : ServerApi.ServerApiListener {
            override fun onSuccess(weathers: List<CityWeatherDisplayed>) {
                citiesWeather.value = weathers
            }

            override fun onError() {

            }

        }, *cities.toTypedArray())
    }

}