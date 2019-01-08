package weather.simple.alytvyniuk

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import weather.simple.alytvyniuk.db.WeatherDB
import weather.simple.alytvyniuk.serverapi.IServerApi
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed
import javax.inject.Inject

/**
 * Request new weather data every hour and updates database
 */
class WeatherJobService : JobService() {

    companion object {
        private const val TAG = "WeatherJobService"
    }

    init {
        SimpleWeatherApplication.getWeatherComponent().inject(this)
    }

    @Inject lateinit var weatherDB : WeatherDB
    @Inject lateinit var serverApi: IServerApi
    private var compositeDisposable = CompositeDisposable()

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob")
        val disposable = weatherDB.getWeatherDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { weatherCities ->
                if (!weatherCities.isEmpty()) {
                    serverApi.requestCityGroupWeather(object : IServerApi.ServerApiListener {
                        override fun onError() {
                            jobFinished(params, true)
                        }

                        override fun onSuccess(weathers: List<CityWeatherDisplayed>) {
                            saveWeather(weathers, params)
                        }
                    }, weatherCities.map { City(it.cityName, it.cityCode) })
                }
            }
        compositeDisposable.addAll(disposable)
        return true
    }

    private fun saveWeather(weathers: List<CityWeatherDisplayed>, params: JobParameters?) {
        val disposable = Single.fromCallable {
            weatherDB.getWeatherDao().insertAll(weathers)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer<Unit> {
                Log.d(TAG, "weather saved")
                jobFinished(params, true)
            })
        compositeDisposable.addAll(disposable)
    }


    override fun onStopJob(params: JobParameters?): Boolean {
        compositeDisposable.dispose()
        return true
    }

}