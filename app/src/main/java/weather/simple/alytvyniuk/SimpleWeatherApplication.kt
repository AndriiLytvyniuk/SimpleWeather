package weather.simple.alytvyniuk

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class SimpleWeatherApplication : Application() {

    companion object {
        private const val MAX_CACHE_SIZE = 2000000L
    }

    override fun onCreate() {
        super.onCreate()
        initPicasso()
    }

    private fun initPicasso() {
        Picasso.setSingletonInstance(
            Picasso.Builder(applicationContext)
                .downloader(OkHttp3Downloader(applicationContext, MAX_CACHE_SIZE))
                .build())
    }
}