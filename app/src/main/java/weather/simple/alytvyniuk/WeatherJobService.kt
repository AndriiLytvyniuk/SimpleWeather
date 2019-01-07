package weather.simple.alytvyniuk

import android.app.job.JobParameters
import android.app.job.JobService
import androidx.room.Room
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import weather.simple.alytvyniuk.db.WeatherDB
import weather.simple.alytvyniuk.serverapi.ServerApi
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed

class WeatherJobService : JobService() {

    private var compositeDisposable = CompositeDisposable()

    override fun onStartJob(params: JobParameters?): Boolean {
        val weatherDB = Room.databaseBuilder(
            application,
            WeatherDB::class.java, "weather"
        ).build()

        val disposable = weatherDB.getWeatherDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { weatherCities ->
                ServerApi.instance.requestCityGroupWeather(object : ServerApi.ServerApiListener {
                    override fun onError() {
                        jobFinished(params, true)
                    }

                    override fun onSuccess(weathers: List<CityWeatherDisplayed>) {
                        saveWeather(weatherDB, weathers, params)
                        jobFinished(params, true)
                    }
                }, *weatherCities.map { City(it.cityName, it.cityCode) }.toTypedArray())
            }
        compositeDisposable.addAll(disposable)
        return true
    }

    private fun saveWeather(db: WeatherDB, weathers: List<CityWeatherDisplayed>, params: JobParameters?) {
        val disposable = Single.fromCallable {
            db.getWeatherDao().insertAll(weathers)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer<Unit> { jobFinished(params, true) })
        compositeDisposable.addAll(disposable)
    }


    override fun onStopJob(params: JobParameters?): Boolean {
        compositeDisposable.dispose()
        return true
    }

}