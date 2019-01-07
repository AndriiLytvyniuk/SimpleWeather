package weather.simple.alytvyniuk

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import android.app.job.JobInfo
import android.content.ComponentName

import android.app.job.JobScheduler
import android.content.Context
import android.util.Log
import weather.simple.alytvyniuk.dagger.DaggerWeatherComponent
import weather.simple.alytvyniuk.dagger.WeatherComponent
import weather.simple.alytvyniuk.dagger.WeatherModule
import java.util.concurrent.TimeUnit


class SimpleWeatherApplication : Application() {

    companion object {
        private const val MAX_CACHE_SIZE = 2000000L
        private const val TAG = "WeatherApplication"
        private lateinit var weatherComponent : WeatherComponent
        fun getWeatherComponent() : WeatherComponent {
            return weatherComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        weatherComponent = DaggerWeatherComponent.builder().weatherModule(WeatherModule(this)).build()
        initPicasso()
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(
            JobInfo.Builder(1, ComponentName(this, WeatherJobService::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(TimeUnit.HOURS.toMillis(1))
                .build()
        )
    }

    private fun initPicasso() {
        Picasso.setSingletonInstance(
            Picasso.Builder(applicationContext)
                .downloader(OkHttp3Downloader(applicationContext, MAX_CACHE_SIZE))
                .build())
    }
}