package weather.simple.alytvyniuk

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import weather.simple.alytvyniuk.db.WeatherDB
import weather.simple.alytvyniuk.serverapi.ServerApi
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed

class WeatherListViewModel(application : Application) : AndroidViewModel(application) {
    val citiesWeather: MutableLiveData<ResponseWrapper>  = MutableLiveData()
    private var compositeDisposable = CompositeDisposable()

    private val weatherDB = Room.databaseBuilder(
        application,
        WeatherDB::class.java, "weather"
    ).build()

    fun requestWeatherDisplayed(cities: List<City>): LiveData<ResponseWrapper> {
        downloadWeatherList(cities)
        return citiesWeather
    }

    private fun downloadWeatherList(cities: List<City>) {
        ServerApi.instance.requestCityGroupWeather(object : ServerApi.ServerApiListener {
            override fun onSuccess(weathers: List<CityWeatherDisplayed>) {
                saveWeather(weathers)
                citiesWeather.value = ResponseWrapper(weathers, false)
            }

            override fun onError() {
                loadWeatherFromDB()
            }

        }, *cities.toTypedArray())
    }

    private fun saveWeather(weathers: List<CityWeatherDisplayed>) {
        Single.fromCallable {
            weatherDB.getWeatherDao().insertAll(weathers)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    private fun loadWeatherFromDB() {
        val disposable = weatherDB.getWeatherDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { weathers ->
                citiesWeather.value = ResponseWrapper(weathers, true)
                compositeDisposable.dispose()
            }
        compositeDisposable.addAll(disposable)
    }

    data class ResponseWrapper (val data : List<CityWeatherDisplayed>, val hasNetworkError : Boolean)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}